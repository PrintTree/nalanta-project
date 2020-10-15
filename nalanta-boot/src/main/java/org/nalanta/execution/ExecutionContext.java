package org.nalanta.execution;

import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class ExecutionContext {

    //============================== state code ==============================

    private static final int STAND_BY = 1; //if state > STAND_BY, state =X=> STAND_BY
    private static final int EXECUTING = 1 << 1;
    private static final int SYNC_WAITING = 1 << 2;
    private static final int ASYNC_WAITING = 1 << 3;
    private static final int SYNC_TIMEOUT = 1 << 4; //immutable
    private static final int ASYNC_TIMEOUT = 1 << 5; //immutable
    private static final int SUCCESS = 1 << 6; //immutable
    private static final int ERROR = 1 << 7; //immutable

    //============================== private field ==============================

    private final String id;

    private final AtomicInteger state = new AtomicInteger(0);

    private final ConcurrentMap<String, Object> data = new ConcurrentHashMap<>(16);

    private final BlockingQueue<Object> messageQueue = new LinkedTransferQueue<>();

    private final Lock stateLock = new ReentrantLock();

    private final long deadLine;

    private final ExecutionManager executionManager;

    private final ExecutionGraph executionGraph;

    private ExecutionNode pointer;

    private Thread currentThread;

    private Throwable exception;

    private AtomicInteger asyncSignalCount;

    //============================== public api ==============================

    public ExecutionContext(String id,
                            ExecutionManager manager,
                            ExecutionGraph graph,
                            long aliveTime,
                            ConcurrentMap<String, Object> initialData
    ) {
        this.id = Objects.requireNonNull(id, "ExecutionContext.id can not be null");
        executionManager = Objects.requireNonNull(manager, "ExecutionManager can not be null");
        executionGraph = Objects.requireNonNull(graph, "ExecutionGraph can not be null");
        aliveTime = Math.max(aliveTime, 0L);
        deadLine = aliveTime == 0L ? 0L : System.currentTimeMillis() + aliveTime;
        if(initialData != null) {
            data.putAll(initialData);
        }
        state.set(STAND_BY);
        //disclose this reference is not good, but before this line, we have already finished construct context,
        //and context's fields have already flushed to memory, so they are visible to other threads.
        executionManager.registerContext(this);
    }

    public String getId() {
        return id;
    }

    /**
     * start the execution of context's graph with default entrance.
     */
    public void start() {
        start(null);
    }

    /**
     * start the execution of context's graph.
     * this method can not be invoke twice because once lose STAND_BY state,
     * it can not be back.
     * @param nodeName start node name
     */
    public void start(String nodeName) {
        pointer = checkStartNode(nodeName);
        if(!state.compareAndSet(STAND_BY, EXECUTING)) {
            throw new IllegalStateException(snapshot() + ": current state is not STAND_BY while setting EXECUTING");
        }
        currentThread = Thread.currentThread();
        executeNodeTask();
    }

    /**
     * @param key key
     * @param value value
     * @return context itself
     */
    public ExecutionContext put(String key, Object value) {
        data.put(key, value);
        return this;
    }

    /**
     * @param key
     * @return
     */
    public Object get(String key) {
        return data.get(key);
    }

    public void syncNext(String nodeName) throws Throwable {
        syncNext(nodeName, 0L, 0, null);
    }

    public void syncNext(String nodeName,
                         long waitTime,
                         Consumer<Object> messageHandler
    ) throws Throwable {
        syncNext(nodeName, waitTime, 1, messageHandler);
    }

    public void syncNext(String nodeName,
                         long waitTime,
                         int signalCount,
                         Consumer<Object> messageHandler
    ) throws Throwable {
        pointer = checkNextNode(nodeName);
        if(!state.compareAndSet(EXECUTING, SYNC_WAITING)) {
            throw new IllegalStateException(snapshot() + ": current state is not EXECUTING while setting SYNC_WAITING");
        }
        long waitStartTime = System.currentTimeMillis();
        for(int i = 0; i < signalCount; i++) {
            Object message = messageQueue.poll(waitTime, TimeUnit.MILLISECONDS);
            if(message == null) { //timeout
                if(!state.compareAndSet(SYNC_WAITING, SYNC_TIMEOUT)) {
                    throw new IllegalStateException(snapshot() + ": current state is not SYNC_WAITING while setting SYNC_TIMEOUT");
                }
                throw ExecutionInterruptedSignal.endSignal();
            }
            else {
                if(messageHandler != null) {
                    messageHandler.accept(message);
                }
                waitTime -= (System.currentTimeMillis() - waitStartTime);
            }
        }
        if(!state.compareAndSet(SYNC_WAITING, EXECUTING)) {
            throw new IllegalStateException(snapshot() + ": current state is not SYNC_WAITING while setting EXECUTING");
        }
        if(Thread.interrupted()) {
            throw new InterruptedException();
        }
        throw ExecutionInterruptedSignal.syncSignal();
    }

    public void asyncNext(String nodeName) throws ExecutionInterruptedSignal {
        asyncNext(nodeName, 0L, 0, null);
    }

    public void asyncNext(String nodeName,
                          long waitTime,
                          Consumer<Object> messageHandler
    ) throws ExecutionInterruptedSignal {
        asyncNext(nodeName, waitTime, 1, messageHandler);
    }

    public void asyncNext(String nodeName,
                          long waitTime,
                          int signalCount,
                          Consumer<Object> messageHandler
    ) throws ExecutionInterruptedSignal {
        pointer = checkNextNode(nodeName);
        if(currentThread != Thread.currentThread()) {
            throw new IllegalStateException(snapshot() + ": currentThread != Thread.currentThread()");
        }
        if(state.get() != EXECUTING) {
            throw new IllegalStateException(snapshot() + ": current state is not EXECUTING before setting ASYNC_WAITING");
        }
        currentThread = null;
        asyncSignalCount = new AtomicInteger(signalCount);
        if(!state.compareAndSet(EXECUTING, ASYNC_WAITING)) {
            throw new IllegalStateException(snapshot() + ": current state is not EXECUTING while setting ASYNC_WAITING");
        }

        throw ExecutionInterruptedSignal.asyncSignal();
    }

    public void end() throws ExecutionInterruptedSignal {
        pointer = null;
        currentThread = null;
        throw ExecutionInterruptedSignal.endSignal();
    }

    //============================== package api ==============================

    void sendMessage(Objects message) {
        messageQueue.offer(message);
    }

    void asyncProceed() {
        if(!state.compareAndSet(ASYNC_WAITING, EXECUTING)) {

        }
        asyncSignalCount = null;
        currentThread = Thread.currentThread();
    }

    void asyncEnd() {}

    void trySyncEnd() {
        try {
            currentThread.interrupt();
        }
        catch (Throwable ignored) {}
    }

    String snapshot() {
        return "ExecutionContext(" + id + ")[" + currentThread + "][" + pointer + "]";
    }

    //============================== private method ==============================

    private ExecutionNode checkStartNode(String nodeName) {
        ExecutionNode node = null;
        if(nodeName != null) {
            node = executionGraph.getNode(nodeName);
        }
        if(node == null) {
            node = executionGraph.getDefaultEntrance();
            if(node == null) {
                throw new IllegalStateException("no ExecutionNode can found");
            }
        }
        return node;
    }

    private ExecutionNode checkNextNode(String nodeName) {
        ExecutionNode node;
        if(nodeName == null) {
            throw new IllegalStateException("ExecutionContext(" + id + ")[" + pointer.name + "]>[null] - null name");
        }
        node = executionGraph.getNode(nodeName);
        if(node == null) {
            throw new IllegalStateException("ExecutionContext(" + id + ")[" + pointer.name + "]>[" + nodeName + "] - not found");
        }
        return node;
    }

    private void executeNodeTask() {
        executeTask: while (true) {
            try {
                pointer.execute(this);
            }
            catch (ExecutionInterruptedSignal signal) {
                handleSignal: while (true) {
                    switch (signal.getType()) {
                        case ExecutionInterruptedSignal.SYNC:
                            if(!state.compareAndSet(SYNC_WAITING, EXECUTING)) {
                                signal = ExecutionInterruptedSignal.endSignal(new IllegalStateException(snapshot() + ": current state is not SYNC_WAITING while setting EXECUTING"));
                                continue handleSignal;
                            }
                            continue executeTask;
                        case ExecutionInterruptedSignal.ASYNC:
                            break executeTask;
                        case ExecutionInterruptedSignal.END:
                            Throwable t = signal.getError();
                            if(t != null) {
                                exception = t;
                                state.set(ERROR);
                                t.printStackTrace();
                            }
                            else {
                                state.set(SUCCESS);
                            }
                            break executeTask;
                    }
                }
            }
        }
    }

}
