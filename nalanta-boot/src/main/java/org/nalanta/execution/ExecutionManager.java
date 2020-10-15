package org.nalanta.execution;

import org.nalanta.thread.DefaultEventLoop;
import org.nalanta.thread.EventLoop;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutionManager {

    private final String name;

    private final long timeoutCheckDelay;

    private final ConcurrentMap<String, ExecutionContext> contexts;

    private final EventLoop<ExecutionEvent> eventLoop;

    private final ExecutorService asyncWorkers;

    private long lastCheckTimeoutTime;

    /**
     * create ExecutionManager with default config.
     */
    public ExecutionManager(String managerName) {
        if(managerName == null || managerName.trim().length() < 1) {
            throw new IllegalArgumentException("manager name can not be empty");
        }
        name = managerName;
        timeoutCheckDelay = 1000L;

        contexts = new ConcurrentHashMap<>(1 << 10); //1024

        eventLoop = new DefaultEventLoop<ExecutionEvent>()
                .setLoopDelay(0L)
                .setTakeEventWaitTime(100L)
                .setQueuedEventHandler(e -> {

                })
                .setLoopTaskExceptionHandler(t -> {

                })
                .setLoopTasks(Arrays.asList(this::checkTimeout))
                .setLoopTaskExceptionHandler(t -> {

                })
                .setQueueSize(1 << 15)
                .setRejectionHandler(e -> {});

        asyncWorkers = new ThreadPoolExecutor(
                8,
                8,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedTransferQueue<>(),
                new ThreadFactory() {
                    private final ThreadGroup group;
                    private final AtomicInteger threadNumber = new AtomicInteger(1);
                    private final String namePrefix;

                    {
                        SecurityManager s = System.getSecurityManager();
                        group = (s != null) ? s.getThreadGroup() :
                                Thread.currentThread().getThreadGroup();
                        namePrefix = name + "-asyncWorker-";
                    }

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(group, r,
                                namePrefix + threadNumber.getAndIncrement(),
                                0);
                        if (t.isDaemon())
                            t.setDaemon(false);
                        if (t.getPriority() != Thread.NORM_PRIORITY)
                            t.setPriority(Thread.NORM_PRIORITY);
                        return t;
                    }
                });

    }

    public void registerContext(ExecutionContext context) {}

    private void checkTimeout() {
        if(System.currentTimeMillis() - lastCheckTimeoutTime >= timeoutCheckDelay) {
            //check
            lastCheckTimeoutTime = System.currentTimeMillis();
        }
    }
}
