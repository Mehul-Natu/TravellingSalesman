package info6205.Graph;

import info6205.Graph.Problems.TravellingSalesMan.GraphImpl.LatLongId;

import java.util.*;

public abstract class EdgeWeightedListGraph<NodeValue, NodeKeyValue, EdgeWeight extends Comparable<EdgeWeight>>
        implements WeightedAdjacencyListGraph<NodeValue, NodeKeyValue, EdgeWeight> {


    protected final Map<Key<NodeKeyValue>, Node<NodeValue, NodeKeyValue>> nodes;
    protected final Map<Key<NodeKeyValue>, List<Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight>>> neighbourMap;

    //now every thing depends on the implementation of adding weighted edge of directed and undirected
    protected final Map<Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight>, Integer> edges;


    public EdgeWeightedListGraph() {
        this.nodes = new HashMap<>();
        this.neighbourMap = new HashMap<>();
        this.edges = new HashMap<>();
    }

    //@Override
    public int getSize() {
        return neighbourMap.size();
    }

    @Override
    public Node<NodeValue, NodeKeyValue> hasNode(Key<NodeKeyValue> key) {
        return nodes.getOrDefault(key, null);
    }

    @Override
    public List<Node<NodeValue, NodeKeyValue>> getNodes() {
        return new LinkedList<>(this.nodes.values());
    }


    @Override
    public boolean addNode(Node<NodeValue, NodeKeyValue> node) {
        try {
            nodes.put(node.getKey(), node);
            neighbourMap.put(node.getKey(), new LinkedList<>());
            return true;
        } catch (Exception e) {
            System.out.println("Exception while saving a Node : " + e);
            return false;
        }
    }

    public List<Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight>> getNeighbours(Key<NodeKeyValue> key) {
        return this.neighbourMap.getOrDefault(key, null);
    }

    public abstract boolean addEdge(Node<NodeValue, NodeKeyValue> node1, Node<NodeValue, NodeKeyValue> node2, EdgeWeight edgeWeight);

    protected abstract boolean addNeighbour(Node<NodeValue, NodeKeyValue> nodeValue1, Node<NodeValue, NodeKeyValue> nodeValue2,
                                            EdgeWeight edgeWeight);

    public abstract Map<Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight>, Integer> getEdges();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EdgeWeightedListGraph<?, ?, ?> that)) return false;
        return getNodes().equals(that.getNodes()) && neighbourMap.equals(that.neighbourMap) && getEdges().equals(that.getEdges());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNodes(), neighbourMap, getEdges());
    }
}
