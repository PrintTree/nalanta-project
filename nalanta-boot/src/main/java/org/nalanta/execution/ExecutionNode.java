package org.nalanta.execution;

public class ExecutionNode {

    final String name;

    final ExecutionTask task;

    ExecutionNode(String name, ExecutionTask task) {
        this.name = name;
        this.task = task;
    }

    void execute(ExecutionContext context) {
        try {
            task.execute(context);
        }
        catch (ExecutionInterruptedSignal executionInterruptedSignal) {
            context.nextNode();
            return;
        }
        catch (Throwable throwable) {
            context.unexpectedException();
            return;
        }
        throw new IllegalStateException(context.snapshot() + ": lose control, please invoke end or wait method");
    }

    @Override
    public String toString() {
        return name;
    }

}
