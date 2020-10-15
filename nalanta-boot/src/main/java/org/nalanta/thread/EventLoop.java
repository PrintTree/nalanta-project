package org.nalanta.thread;

import java.util.List;
import java.util.function.Consumer;

public interface EventLoop<E> {

    String id();

    boolean start();

    boolean pause();

    boolean resume();

    boolean shutdown();

    EventLoop<E> setQueuedEventHandler(Consumer<E> handler);

    EventLoop<E> setRejectionHandler(Consumer<E> handler);

    EventLoop<E> setQueueSize(int size);

    EventLoop<E> setLoopTasks(List<Runnable> task);

    EventLoop<E> setLoopDelay(long milliseconds);

    EventLoop<E> setTakeEventWaitTime(long milliseconds);

    EventLoop<E> setQueuedTaskExceptionHandler(Consumer<Throwable> handler);

    EventLoop<E> setLoopTaskExceptionHandler(Consumer<Throwable> handler);

    boolean enqueue(E event);

}
