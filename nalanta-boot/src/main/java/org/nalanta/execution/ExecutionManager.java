package org.nalanta.execution;

public class ExecutionManager {

    public void registerContext(ExecutionContext context) {}

    void notifyStartNextNode(String contextId) {}

    void notifyException(String contextId) {}

    void notifyExecutionEnd(String contextId) {}
}
