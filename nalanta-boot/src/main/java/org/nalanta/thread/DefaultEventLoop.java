package org.nalanta.thread;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class DefaultEventLoop<E> extends AbstractEventLoop<E> {

    public DefaultEventLoop() {
        this(UUID.randomUUID().toString().replaceAll("-", ""));
    }

    public DefaultEventLoop(String id) {
        super(id);
    }

    @Override
    protected Thread createThread() {
        Thread t = new Thread(() -> {
            for(;;) {
                //check lifecycleStatus
                if(lifecycleStatus.get() != WORKING) {
                    if(lifecycleStatus.get() == NEW) {
                        lifecycleStatus.set(TERMINATED);
                        throw new IllegalStateException("Programming Error: status should not be NEW after thread started");
                    }
                    else if(lifecycleStatus.get() == TERMINATED) {
                        throw new IllegalStateException("Programming Error: status should not be TERMINATED while looping");
                    }
                    else if(lifecycleStatus.get() == CLOSING) {
                        if(lifecycleStatus.compareAndSet(CLOSING, TERMINATED)) {
                            break;
                        } else {
                            lifecycleStatus.set(TERMINATED);
                            throw new IllegalStateException("Programming Error: status should not be changed by other thread while CLOSING");
                        }
                    }
                    else if(lifecycleStatus.get() == PAUSING) {
                        do {
                            LockSupport.park();
                        } while (lifecycleStatus.get() == PAUSING);
                    } else { //can not know what status it is now
                        continue;
                    }
                }
                //take event and handle it
                E event = null;
                try {
                    event = takeEventWaitTime > 0L ?
                            queue.poll(takeEventWaitTime, TimeUnit.MILLISECONDS) :
                            queue.take();
                }
                catch (InterruptedException interruptedException) {
                    //ignore take()/poll() be interrupted
                }
                if(event != null) {
                    try {
                        queuedEventHandler.accept(event);
                    }
                    catch (Throwable throwable) {
                        try {
                            queuedTaskExceptionHandler.accept(throwable);
                        }
                        catch (Throwable ignored) {}
                    }
                }
                if(loopTasks.size() != 0) {
                    for(Runnable r : loopTasks) {
                        try {
                            r.run();
                        }
                        catch (Throwable throwable) {
                            try {
                                loopTaskExceptionHandler.accept(throwable);
                            }
                            catch (Throwable ignored) {}
                        }
                    }
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(loopDelay);
                }
                catch (InterruptedException interruptedException) {
                    //ignore
                }
            }
        });
        t.setDaemon(true);
        t.setName("DefaultEventLoopThread-" + id);
        return t;
    }

    @Override
    protected BlockingQueue<E> createTaskQueue() {
        return new LinkedTransferQueue<>();
    }
}
