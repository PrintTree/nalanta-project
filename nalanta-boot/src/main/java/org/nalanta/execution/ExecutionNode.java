package org.nalanta.execution;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ExecutionNode {

    String name;

    Consumer<ExecutionContext> task;

    Map<String, ExecutionNode> nextNodes;

    ExecutionNode(String name, Consumer<ExecutionContext> task) {
        this.name = name;
        this.task = task;
        nextNodes = new HashMap<>(2);
    }

    void setNext(String nextName, ExecutionNode nextNode) {
        nextNodes.put(nextName, nextNode);
    }

    boolean hasNext() {
        return nextNodes.isEmpty();
    }

    ExecutionNode next(String nodeName) {
        return nextNodes.get(nodeName);
    }

    void execute(ExecutionContext context) {
        try {
            task.accept(context);
        }
        catch (Throwable throwable) {
            context.exception = throwable;
            context.state.set(ExecutionContext.ERROR);
        }
    }
}
