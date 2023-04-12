package info6205.Graph.Problems.TravellingSalesMan.Algorithm;

import info6205.Graph.Edge;
import info6205.Graph.Node;
import info6205.Graph.UndirectedEdgeWeighedListGraph;
import info6205.Graph.Utils.Pair;

import java.util.*;

public class SimulatedAnnealing<NodeValue, NodeKeyValue, EdgeWeight extends Comparable<EdgeWeight>> {

    private Map<Pair<Node<NodeValue, NodeKeyValue>, Node<NodeValue, NodeKeyValue>>, EdgeWeight> edgeWeights;

    private final int equilibriumCountForTemp = 5;


    public SimulatedAnnealing(Map<Pair<Node<NodeValue, NodeKeyValue>, Node<NodeValue, NodeKeyValue>>, EdgeWeight> edgeWeights) {
        this.edgeWeights = edgeWeights;
    }

    public void runSimulatedAnnealing(UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> graph,
                                      int numberOfIterations) {

        List<Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight>> edgeList = new ArrayList<>(graph.getEdges().keySet());
        Random random = new Random();
        int maxEdgeSize = graph.getEdges().size();
        Double graphWeight = calculateGraphWeight(graph);
        double temp = 10000;
        double coolingRate = 0.0001;
        double min = Double.MAX_VALUE;

        while (temp > 1) {
            int tempEquilibriumCount = 0;

            while (tempEquilibriumCount != equilibriumCountForTemp) {

                edgeList = new ArrayList<>(graph.getEdges().keySet());

                int indexOfEdgeOne = random.nextInt(maxEdgeSize);
                int indexOfEdgeTwo = indexOfEdgeOne;
                while (indexOfEdgeOne == indexOfEdgeTwo || checkIfSameNodeInEdges(edgeList, indexOfEdgeOne, indexOfEdgeTwo)) {
                    indexOfEdgeTwo = random.nextInt(maxEdgeSize);
                }

                Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> edge1 = edgeList.get(indexOfEdgeOne);
                Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> edge2 = edgeList.get(indexOfEdgeTwo);
                Double newGraphWeight = twoOpt(graph, edgeList.get(indexOfEdgeOne),
                        edgeList.get(indexOfEdgeTwo), temp, graphWeight);

                if (newGraphWeight == null) {
                    tempEquilibriumCount++;
                } else {
                    if (newGraphWeight < min) {
                        min = newGraphWeight;
                    }
                    tempEquilibriumCount = 0;
                    graphWeight = newGraphWeight;
                }
                System.out.println("New Graph Weight : " + newGraphWeight);
            }
            temp *= 1 - coolingRate;
            //System.out.println("Temperature : " + temp);
        }
        System.out.println("hello min graph weight = " + min);

    }


    private Double twoOpt(UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> graph,
                          Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> edge1,
                          Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> edge2, double temperature, Double currentWeightOfGraph) {

        try {


            Double newWeight = currentWeightOfGraph - (Double) edge1.getEdgeWeight() - (Double) edge2.getEdgeWeight();
            graph.deleteEdge(edge1);
            graph.deleteEdge(edge2);
            Random random = new Random();

            //if (edgeWeights.get)

            Double edgeMatchingWeight = newWeight + (Double) edgeWeights.get(new Pair<>(edge1.getFirstNode(), edge2.getFirstNode()))
                    + (Double) edgeWeights.get(new Pair<>(edge1.getSecondNode(), edge2.getSecondNode()));

            //System.out.println("Random value:" + Math.exp((currentWeightOfGraph - edgeMatchingWeight) / temperature));

            if (edgeMatchingWeight < currentWeightOfGraph ) {//|| Math.exp((currentWeightOfGraph - edgeMatchingWeight) / temperature) > random.nextDouble()) {
                if (!isConnected(graph, edge1.getFirstNode(), edge2.getFirstNode(), new HashSet<>())) {
                    graph.addEdge(edge1.getFirstNode(), edge2.getFirstNode(),
                            edgeWeights.get(new Pair<>(edge1.getFirstNode(), edge2.getFirstNode())));
                    graph.addEdge(edge1.getSecondNode(), edge2.getSecondNode(),
                            edgeWeights.get(new Pair<>(edge1.getSecondNode(), edge2.getSecondNode())));
                    return edgeMatchingWeight;
                }
            }

            edgeMatchingWeight = newWeight + (Double) edgeWeights.get(new Pair<>(edge1.getFirstNode(), edge2.getSecondNode()))
                    + (Double) edgeWeights.get(new Pair<>(edge1.getSecondNode(), edge2.getFirstNode()));

            if (edgeMatchingWeight < currentWeightOfGraph) {// || Math.exp((currentWeightOfGraph - edgeMatchingWeight) / temperature) > random.nextDouble(1D)) {
                if (!isConnected(graph, edge1.getFirstNode(), edge2.getSecondNode(), new HashSet<>())) {
                    graph.addEdge(edge1.getFirstNode(), edge2.getSecondNode(),
                            edgeWeights.get(new Pair<>(edge1.getFirstNode(), edge2.getSecondNode())));
                    graph.addEdge(edge1.getSecondNode(), edge2.getFirstNode(),
                            edgeWeights.get(new Pair<>(edge1.getSecondNode(), edge2.getFirstNode())));
                    return edgeMatchingWeight;
                }

            }

            graph.addEdge(edge1.getFirstNode(), edge1.getSecondNode(), edge1.getEdgeWeight());
            graph.addEdge(edge2.getFirstNode(), edge2.getSecondNode(), edge2.getEdgeWeight());

            //if ()

            return null;
        } catch (Exception e) {
            System.out.println("Exception while TwoOpt" + e);
            throw e;
        }

    }


    private boolean isConnected(UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> graph,
                                Node<NodeValue, NodeKeyValue> currentNode, Node<NodeValue, NodeKeyValue> targetNode,
                                Set<Node<NodeValue, NodeKeyValue>> visited) {

        if (targetNode.equals(currentNode)) {
            return true;
        }

        if (visited.contains(currentNode)) {
            return false;
        }

        visited.add(currentNode);

        for (Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> edge : graph.getNeighbours(currentNode.getKey())) {
            if (isConnected(graph, edge.getSecondNode(), targetNode, visited)) {
                return true;
            }
        }
        return false;
    }

    private Double calculateGraphWeight(UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> graph) {
        Double ans = 0D;
        for (Node<NodeValue, NodeKeyValue> node : graph.getNodes()) {
            for (Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> edge : graph.getNeighbours(node.getKey())) {
                ans = ans +  (Double) edge.getEdgeWeight();
            }
        }
        return ans/2;
    }

    private boolean checkIfIntegerIsMissingForEdge(UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> graph) {
        for (Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> edge : graph.getEdges().keySet()) {
            if (graph.getEdges().get(edge) == null) {
                System.out.println("problem here");
                return true;
            }
        }
        return false;
    }

    private boolean checkIfSameNodeInEdges(List<Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight>> list, int indexOfFirst,
                                       int indexOfSecond) {
        Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> edge1 = list.get(indexOfFirst);
        Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> edge2 = list.get(indexOfSecond);

        if (edge1.getFirstNode().equals(edge2.getFirstNode()) || edge1.getFirstNode().equals(edge2.getSecondNode())
        || edge1.getSecondNode().equals(edge2.getSecondNode()) || edge1.getSecondNode().equals(edge2.getFirstNode())) {
            return true;
        }
        return false;

    }

}
