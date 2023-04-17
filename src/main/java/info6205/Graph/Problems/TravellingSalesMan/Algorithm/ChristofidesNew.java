package info6205.Graph.Problems.TravellingSalesMan.Algorithm;

import info6205.Graph.Edge;
import info6205.Graph.Key;
import info6205.Graph.Node;
import info6205.Graph.Problems.TravellingSalesMan.GraphImpl.LatLongId;
import info6205.Graph.UndirectedEdgeWeighedListGraph;

import java.util.*;

public class ChristofidesNew<NodeValue, NodeKeyValue, EdgeWeight extends Comparable<EdgeWeight>> {
/*
    UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> graph;

    public ChristofidesNew(UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> graph) {
        this.graph = graph;
    }

    public void runChristofides() {
        try {
            addEdgesBetweenNodes();
            graph.test();
            System.out.println("hello hello");
            UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> mst = graph.getMSTByPrims();

            List<Node<NodeValue, NodeKeyValue>> oddEdgeNodes = getOddEdgesNodes(mst);
            List<Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight>> edges =
                    System.out.println("yo");

        } catch (Exception e) {
            System.out.println("exception while performing christofedies : " + e);
        }
    }

    private void addEdgesBetweenNodes() {
        List<Node<NodeValue, NodeKeyValue>> nodeList = graph.getNodes();
        for (int i = 0; i < nodeList.size() - 1; i++) {
            for (int j = i + 1; j < nodeList.size(); j++) {
                graph.addEdge(nodeList.get(i), nodeList.get(j),
                        (EdgeWeight) getDistanceBetweenNodes((Node<String, LatLongId>) nodeList.get(i),
                                (Node<String, LatLongId>) nodeList.get(j)));
            }
        }

    }

    private Double getDistanceBetweenNodes(Node<String, LatLongId> node1, Node<String, LatLongId> node2) {
        return Math.sqrt(Math.pow(node1.getKey().getValue().getLatitude() - node2.getKey().getValue().getLatitude(), 2)
                + Math.pow(node1.getKey().getValue().getLongitude() - node2.getKey().getValue().getLongitude(), 2));
    }

    private List<Node<NodeValue, NodeKeyValue>> getOddEdgesNodes(UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> graph) {
        List<Node<NodeValue, NodeKeyValue>> oddNodes = new ArrayList<>();
        for (Node<NodeValue, NodeKeyValue> node : graph.getNodes()) {
            if (graph.getNeighbours(node.getKey()).size() % 2 != 0) {
                oddNodes.add(node);
            }
        }
        return oddNodes;
    }

    private List<Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight>> createEdgesForOddNode(List<Node<NodeValue, NodeKeyValue>> listOfOddNode) {
        Set<KeyLatLongId<NodeKeyValue>> setOfNodesDone = new HashSet<>();
        for (int i = 0; i < listOfOddNode.size() - 1; i++) {
            int minIndex = -1;
            Double minEdgeWeight = Double.MIN_VALUE;
            for (int j = i + 1; j < listOfOddNode.size(); j++) {
                if ()
            }
        }
    }




 */

}

