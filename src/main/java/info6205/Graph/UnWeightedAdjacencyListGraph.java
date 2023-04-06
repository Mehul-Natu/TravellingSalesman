package info6205.Graph;

import java.util.List;

public interface UnWeightedAdjacencyListGraph<NodeValue, NodeKeyValue> {

    int getSize();

    boolean addNode(Node<NodeValue, NodeKeyValue> node);

    Node<NodeValue, NodeKeyValue> hasNode(Key<NodeKeyValue> key);

    List<Node<NodeValue, NodeKeyValue>> getNodes();

    boolean addNeighbour(Node<NodeValue, NodeKeyValue> nodeValue1, Node<NodeValue, NodeKeyValue> nodeValue2);

}
