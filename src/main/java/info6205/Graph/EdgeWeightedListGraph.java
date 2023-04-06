package info6205.Graph;

import java.util.*;

public abstract class EdgeWeightedListGraph<NodeValue, NodeKeyValue, EdgeWeight> implements WeightedAdjacencyListGraph<NodeValue, NodeKeyValue, EdgeWeight> {


    protected final Map<Key<NodeKeyValue>, Node<NodeValue, NodeKeyValue>> nodes;
    protected final Map<Key<NodeKeyValue>, List<Node<NodeValue, NodeKeyValue>>> neighbourMap;

    //now every thing depends on the implementation of adding weighted edge of directed and undirected
    protected final Set<Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight>> edges;


    public EdgeWeightedListGraph() {
        this.nodes = new HashMap<>();
        this.neighbourMap = new HashMap<>();
        this.edges = new HashSet<>();
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

    //abstract boolean addEdge(Node<NodeValue, NodeKeyValue> node1, Node<NodeValue, NodeKeyValue> node2, EdgeWeight edgeWeight);

    protected abstract boolean addNeighbour(Node<NodeValue, NodeKeyValue> nodeValue1, Node<NodeValue, NodeKeyValue> nodeValue2);




}
