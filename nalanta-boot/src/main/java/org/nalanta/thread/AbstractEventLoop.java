package org.nalanta.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;

public abstract class AbstractEventLoop<E> implements EventLoop<E> {

    //settings before eventLoop start
    
    protected String id;
    
    protected Thread thread;
    
    protected BlockingQueue<E> queue;

    protected List<Runnable> loopTasks;
    
    protected Consumer<E> queuedTaskHandler;

    protected Consumer<E> rejectionHandler;
    
    protected int queueSize;
    
    protected long loopDelay;

    protected long takeTaskWaitTime;
    
    protected Consumer<Throwable> queuedTaskExceptionHandler;

    protected Consumer<Throwable> loopTaskExceptionHandler;

    //status

    //00001 => NEW
    protected static final int NEW = 1;
    //00010 => WORKING
    protected static final int WORKING = 1 << 1;
    //00100 => PAUSING
    protected static final int PAUSING = 1 << 2;
    //10000 => WAITING FOR TERMINATE
    protected static final int CLOSING = 1 << 3;
    //10000 => TERMINATED
    protected static final int TERMINATED = 1 << 4;

    protected AtomicInteger lifecycleStatus;

    //attributes while eventLoop working

    protected AtomicInteger queuedTaskCount;
    
    //constructors
    
    protected AbstractEventLoop(String id) {
        if(id == null || "".equals(id)) {
            throw new IllegalArgumentException("id.length must > 0");
        }
        this.id = id;
        lifecycleStatus = new AtomicInteger(NEW);
        queuedTaskCount = new AtomicInteger(0);
    }

    protected abstract Thread createThread();

    protected abstract BlockingQueue<E> createTaskQueue();
    
    //methods default implementation
    
    @Override
    public String id() {
        return id;
    }

    @Override
    public boolean start() {
        if(lifecycleStatus.compareAndSet(NEW, WORKING)) {
            Thread t = createThread();
            if(t == null) {
                lifecycleStatus.set(NEW);
                return false;
            }
            BlockingQueue<E> q = createTaskQueue();
            if(q == null) {
                lifecycleStatus.set(NEW);
                return false;
            }
            if(queuedTaskHandler == null) {
                lifecycleStatus.set(NEW);
                return false;
            }
            thread = t;
            queue = q;
            if(queuedTaskExceptionHandler == null) {
                queuedTaskExceptionHandler = th -> {};
            }
            if(rejectionHandler == null) {
                rejectionHandler = e -> {};
            }
            if(loopTasks == null) {
                loopTasks = new ArrayList<>(0);
            }
            if(loopTaskExceptionHandler == null) {
                loopTaskExceptionHandler = th -> {};
            }
            if(queueSize <= 0) {
                queueSize = 32;
            }
            if(loopDelay < 0L) {
                loopDelay = 0L;
            }
            if(takeTaskWaitTime < 0L) {
                takeTaskWaitTime = 0L;
            }
            thread.start();
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean pause() {
        //EventLoop Thread will check lifecycleStatus
        return lifecycleStatus.compareAndSet(WORKING, PAUSING);
    }

    @Override
    public boolean resume() {
        if(lifecycleStatus.compareAndSet(PAUSING, WORKING)) {
            //be careful: change status must before unpark
            LockSupport.unpark(thread);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean shutdown() {
        lifecycleStatus.set(CLOSING);
        return true;
    }

    @Override
    public EventLoop<E> setQueuedTaskHandler(Consumer<E> handler) {
        queuedTaskHandler = handler;
        return this;
    }

    @Override
    public EventLoop<E> setRejectionHandler(Consumer<E> handler) {
        rejectionHandler = handler;
        return this;
    }

    @Override
    public EventLoop<E> setQueueSize(int size) {
        queueSize = size;
        return this;
    }

    @Override
    public EventLoop<E> setLoopTasks(List<Runnable> task) {
        if(task == null) {
            return this;
        }
        if(loopTasks == null) {
            loopTasks = new ArrayList<>(task.size());
        }
        loopTasks.addAll(task);
        return this;
    }

    @Override
    public EventLoop<E> setLoopDelay(long milliseconds) {
        loopDelay = milliseconds;
        return this;
    }

    @Override
    public EventLoop<E> setTakeTaskWaitTime(long milliseconds) {
        takeTaskWaitTime = milliseconds;
        return this;
    }

    @Override
    public EventLoop<E> setQueuedTaskExceptionHandler(Consumer<Throwable> handler) {
        queuedTaskExceptionHandler = handler;
        return this;
    }

    @Override
    public EventLoop<E> setLoopTaskExceptionHandler(Consumer<Throwable> handler) {
        loopTaskExceptionHandler = handler;
        return this;
    }
    
    @Override
    public boolean enqueue(E event) {
        boolean isSuccess = true;
        if((lifecycleStatus.get() & 0B110) == 0) { //WORKING or PAUSING allows enqueue
            isSuccess = false;
        }
        else if(queuedTaskCount.incrementAndGet() > queueSize
                || !queue.offer(event)) {
            queuedTaskCount.decrementAndGet();
            isSuccess = false;
        }
        if(!isSuccess) {
            try {
                rejectionHandler.accept(event);
            }
            catch (Throwable ignored) {}
        }
        return isSuccess;
    }
    
}
