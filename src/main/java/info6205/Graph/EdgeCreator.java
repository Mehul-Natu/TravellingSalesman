package info6205.Graph;

public interface EdgeCreator<NodeValue, NodeKeyValue, EdgeWeight> {

    Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> createEdge(Node<NodeValue, NodeKeyValue> node1,
                                                                      Node<NodeValue, NodeKeyValue> node2,
                                                                      EdgeWeight edgeWeight);
}
