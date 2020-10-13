package org.nalanta.execution;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutionContext {

    static final int STAND_BY = 1;
    static final int EXECUTING = 1 << 1;
    static final int SYNC_WAITING = 1 << 2;
    static final int ASYNC_WAITING = 1 << 3;
    static final int SYNC_TIMEOUT = 1 << 4;
    static final int ASYNC_TIMEOUT = 1 << 5;
    static final int SUCCESS = 1 << 6;
    static final int ERROR = 1 << 7;

    final AtomicInteger state = new AtomicInteger(0);

    final ConcurrentMap<String, Object> data = new ConcurrentHashMap<>(16);

    long maxAliveTime;

    long deadLine;

    ExecutionManager executionManager;

    ExecutionGraph executionGraph;

    ExecutionNode pointer;

    Thread currentThread;

    Throwable exception;

    public ExecutionContext(ExecutionManager manager,
                            ExecutionGraph graph,
                            ConcurrentMap<String, Object> initialData,
                            long aliveTime) {
        executionManager = Objects.requireNonNull(manager, "ExecutionManager can not be null");
        executionGraph = Objects.requireNonNull(graph, "ExecutionGraph can not be null");
        if(initialData != null) {
            data.putAll(initialData);
        }
        maxAliveTime = Math.max(aliveTime, 0L);
        //executionManager.accept(this);
        state.set(STAND_BY);
    }

    public void start() {
        if(!state.compareAndSet(STAND_BY, EXECUTING)) {
            throw new IllegalStateException("current state is not STAND_BY");
        }
        currentThread = Thread.currentThread();
        pointer = executionGraph.entranceNode;
        deadLine = maxAliveTime == 0L ? 0L : System.currentTimeMillis() + maxAliveTime;
        pointer.execute(this);
    }

    public ExecutionContext put(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public Object get(String key) {
        return data.get(key);
    }

    public ExecutionContext next(String nodeName) {
        ExecutionNode next = pointer.next(nodeName);
        pointer = next;
        return this;
    }

    public void syncWait(long waitTime) {
        syncWait(waitTime, 1);
    }

    public void syncWait(long waitTime, int signalCount) {}

    public void asyncWait(long waitTime) {
        asyncWait(waitTime, 1);
    }

    public void asyncWait(long waitTime, int signalCount) {
        currentThread = null;
    }

    public void end() {}
}
