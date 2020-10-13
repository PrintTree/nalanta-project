package org.nalanta.execution;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ExecutionGraph {

    ExecutionNode entranceNode;

    public static ExecutionGraphBuilder builder() {
        return new ExecutionGraphBuilder();
    }

    ExecutionGraph() {}

    public static class ExecutionGraphBuilder {

        boolean finished;

        ExecutionGraph graph;

        Map<String, ExecutionNode> nodes;

        ExecutionGraphBuilder() {
            finished = false;
            graph = new ExecutionGraph();
            nodes = new HashMap<>();
        }

        public ExecutionGraphBuilder addNode(String name, Consumer<ExecutionContext> task) {
            if(finished) {
                throw new IllegalStateException("can not modify graph after build()");
            }
            if(name == null || task == null) {
                throw new NullPointerException();
            }
            ExecutionNode node = new ExecutionNode(name, task);
            nodes.put(name, node);
            return this;
        }

        public ExecutionGraphBuilder linkNodes(String from, String to) {
            if(finished) {
                throw new IllegalStateException("can not modify graph after build()");
            }
            ExecutionNode fromNode = nodes.get(from);
            ExecutionNode toNode = nodes.get(to);
            if(fromNode == null || toNode == null) {
                throw new NullPointerException();
            }
            fromNode.setNext(to, toNode);
            return this;
        }

        public ExecutionGraphBuilder setEntrance(String name) {
            ExecutionNode entranceNode = nodes.get(name);
            if(entranceNode == null) {
                throw new NullPointerException();
            }
            graph.entranceNode = entranceNode;
            return this;
        }

        public ExecutionGraph build() {
            if(graph.entranceNode == null) {
                throw new IllegalStateException("can not build graph with no entrance");
            }
            if(finished) {
                throw new IllegalStateException("can not rebuild graph after build()");
            }
            finished = true;
            return graph;
        }
    }
}
