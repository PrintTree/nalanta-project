package org.nalanta.execution;

/**
 * this class used to ignore the code after
 * ExecutionContext's syncNext(), asyncNext, end() method invoked.
 * throw a Throwable is the only way to do that.
 */
public class ExecutionInterruptedSignal extends Throwable {

    static final ExecutionInterruptedSignal SIGNAL = new ExecutionInterruptedSignal();

}
