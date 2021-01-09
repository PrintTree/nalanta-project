package org.nalanta.execution;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ExecutionGraph {

    final Map<String, ExecutionNode> nodes = new HashMap<>(2);

    ExecutionNode defaultEntrance;

    ExecutionNode getDefaultEntrance() {
        return defaultEntrance;
    }

    ExecutionNode getNode(String name) {
        return nodes.get(name);
    }

    public static Builder builder() {
        return new Builder();
    }

    ExecutionGraph() {}

    public static class Builder {

        boolean finished;

        ExecutionGraph graph;

        Builder() {
            finished = false;
            graph = new ExecutionGraph();
        }

        public Builder addNode(String name, ExecutionTask task) {
            requireNotFinished();
            if(name == null || task == null) {
                throw new NullPointerException();
            }
            graph.nodes.put(name, new ExecutionNode(name, task));
            return this;
        }

        public Builder setDefaultEntrance(String name) {
            requireNotFinished();
            graph.defaultEntrance = Objects.requireNonNull(graph.nodes.get(name));
            return this;
        }

        public ExecutionGraph build() {
            requireNotFinished();
            finished = true;
            return graph;
        }

        private void requireNotFinished() {
            if(finished) {
                throw new IllegalStateException("can not operate graph after build()");
            }
        }
    }
}
