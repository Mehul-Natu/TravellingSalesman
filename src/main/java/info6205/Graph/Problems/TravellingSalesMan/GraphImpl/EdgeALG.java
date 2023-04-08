package info6205.Graph.Problems.TravellingSalesMan.GraphImpl;


import info6205.Graph.Edge;
import info6205.Graph.Node;

import java.util.Objects;

public class EdgeALG implements Edge<Node<String, LatLongId>, Double> {

    private final Node<String, LatLongId> firstNode;
    private final Node<String, LatLongId> secondNode;
    private final Double edgeWeight;


    public EdgeALG(Node<String, LatLongId> firstNode, Node<String, LatLongId> secondNode, Double edgeWeight) {
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        this.edgeWeight = edgeWeight;
    }

    @Override
    public Node<String, LatLongId> getFirstNode() {
        return firstNode;
    }

    @Override
    public Node<String, LatLongId> getSecondNode() {
        return secondNode;
    }

    @Override
    public Double getEdgeWeight() {
        return edgeWeight;
    }

    @Override
    public String toString() {
        return "EdgeALG {" +
                "firstNode=" + firstNode +
                ", secondNode=" + secondNode +
                ", edgeWeight=" + edgeWeight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EdgeALG edgeALG)) return false;
        return getFirstNode().equals(edgeALG.getFirstNode()) && getSecondNode().equals(edgeALG.getSecondNode()) && getEdgeWeight().equals(edgeALG.getEdgeWeight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstNode(), getSecondNode(), getEdgeWeight());
    }
}
