package org.nalanta.execution;

public class ExecutionNode {

    final String name;

    final ExecutionTask task;

    ExecutionNode(String name, ExecutionTask task) {
        this.name = name;
        this.task = task;
    }

    void execute(ExecutionContext context) throws ExecutionInterruptedSignal {
        try {
            task.execute(context);
            throw new IllegalStateException(context.getId() + ": lose control");
        }
        catch (ExecutionInterruptedSignal signal) {
            throw signal;
        }
        catch (Throwable throwable) {
            throw ExecutionInterruptedSignal.endSignal(throwable);
        }
    }

    @Override
    public String toString() {
        return name;
    }

}
