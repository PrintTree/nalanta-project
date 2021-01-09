package org.nalanta.execution;

/**
 * this class used to ignore the code after
 * ExecutionContext's syncNext(), asyncNext(), end() method invoked.
 * throw a Throwable is the only way to do that.
 */
public class ExecutionInterruptedSignal extends Throwable {

    public static final int SYNC = 1 << 1;
    public static final int ASYNC = 1 << 2;
    public static final int END = 1 << 3;

    private static final ExecutionInterruptedSignal SYNC_SIGNAL = new ExecutionInterruptedSignal(SYNC);
    private static final ExecutionInterruptedSignal ASYNC_SIGNAL = new ExecutionInterruptedSignal(ASYNC);
    private static final ExecutionInterruptedSignal END_SIGNAL = new ExecutionInterruptedSignal(END);

    final private int type;
    final private Throwable error;

    ExecutionInterruptedSignal(int type) {
        super();
        this.type = type;
        error = null;
    }

    ExecutionInterruptedSignal(Throwable t) {
        super();
        this.type = END;
        error = t;
    }

    public int getType() {
        return type;
    }

    public Throwable getError() {
        return error;
    }

    public static ExecutionInterruptedSignal syncSignal() {
        return SYNC_SIGNAL;
    }

    public static ExecutionInterruptedSignal asyncSignal() {
        return ASYNC_SIGNAL;
    }

    public static ExecutionInterruptedSignal endSignal() {
        return END_SIGNAL;
    }

    public static ExecutionInterruptedSignal endSignal(Throwable t) {
        return new ExecutionInterruptedSignal(t);
    }
}
