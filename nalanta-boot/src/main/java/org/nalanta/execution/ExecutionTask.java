package org.nalanta.execution;

@FunctionalInterface
public interface ExecutionTask {

    void execute(ExecutionContext context) throws Throwable;

}
