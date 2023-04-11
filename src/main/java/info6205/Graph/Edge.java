package info6205.Graph;

public interface Edge<Node, Weight> {

    Node getFirstNode();
    Node getSecondNode();
    Weight getEdgeWeight();

    Edge<Node, Weight> createReverseEdge();

}
