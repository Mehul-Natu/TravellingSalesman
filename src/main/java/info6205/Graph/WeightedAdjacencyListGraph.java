package info6205.Graph;

import java.util.List;


//should we break the method of adding edge and neighbour from weighted and unweighted interface??
public interface WeightedAdjacencyListGraph<NodeValue, NodeKeyValue, EdgeWeight extends Comparable<EdgeWeight>> {

    int getSize();

    boolean addNode(Node<NodeValue, NodeKeyValue> node);

    Node<NodeValue, NodeKeyValue> hasNode(Key<NodeKeyValue> key);

    List<Node<NodeValue, NodeKeyValue>> getNodes();

    boolean addEdge(Node<NodeValue, NodeKeyValue> node1, Node<NodeValue, NodeKeyValue> node2, EdgeWeight weight);
}
