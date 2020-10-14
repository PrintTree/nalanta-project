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

    public static ExecutionGraphBuilder builder() {
        return new ExecutionGraphBuilder();
    }

    ExecutionGraph() {}

    public static class ExecutionGraphBuilder {

        boolean finished;

        ExecutionGraph graph;

        ExecutionGraphBuilder() {
            finished = false;
            graph = new ExecutionGraph();
        }

        public ExecutionGraphBuilder addNode(String name, ExecutionTask task) {
            requireNotFinished();
            if(name == null || task == null) {
                throw new NullPointerException();
            }
            graph.nodes.put(name, new ExecutionNode(name, task));
            return this;
        }

        public ExecutionGraphBuilder setDefaultEntrance(String name) {
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
