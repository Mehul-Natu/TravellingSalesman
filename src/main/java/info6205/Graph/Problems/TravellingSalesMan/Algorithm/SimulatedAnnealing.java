package info6205.Graph.Problems.TravellingSalesMan.Algorithm;

import info6205.Graph.Edge;
import info6205.Graph.Node;
import info6205.Graph.Problems.TravellingSalesMan.GraphImpl.NodeALG;
import info6205.Graph.UndirectedEdgeWeighedListGraph;
import info6205.Graph.Utils.Pair;

import java.util.*;

public class SimulatedAnnealing<NodeValue, NodeKeyValue, EdgeWeight extends Comparable<EdgeWeight>> {

    private Map<Pair<Node<NodeValue, NodeKeyValue>, Node<NodeValue, NodeKeyValue>>, EdgeWeight> edgeWeights;

    private final int equilibriumCountForTemp = 20;


    public SimulatedAnnealing(Map<Pair<Node<NodeValue, NodeKeyValue>, Node<NodeValue, NodeKeyValue>>, EdgeWeight> edgeWeights) {
        this.edgeWeights = edgeWeights;
    }


    public List<Node<NodeValue, NodeKeyValue>>
    runSimulatedAnnealingTwo(List<Node<NodeValue, NodeKeyValue>> order, Double graphWeight, long temp, double coolingRate,
                             long maxIteration) {
        try {
            Node<NodeValue, NodeKeyValue>[] orderArray = order.toArray((Node<NodeValue, NodeKeyValue>[]) new Node[order.size()]);
            Random random = new Random();
            //double temp = 1;
            //double coolingRate = 0.1;
            boolean twoOptNew = true;
            double min = Double.MAX_VALUE;
            int graphV = order.size();
            int equilibriumCountForTemp = 20;
            int iteration = 0;
            while (temp > 1 || iteration < maxIteration) {
                iteration = iteration + 1;
                int tempEquilibriumCount = 0;

                //while (tempEquilibriumCount != equilibriumCountForTemp) {

                    int indexOfNodeOne = random.nextInt(1, graphV - 1);
                    int indexOfNodeTwo = indexOfNodeOne;

                    while (indexOfNodeOne == indexOfNodeTwo || Math.absExact(indexOfNodeTwo - indexOfNodeOne) < 2) {
                        indexOfNodeTwo = random.nextInt(1, graphV - 1);
                    }

                    Double newGraphWeight = null;

                    newGraphWeight = twoOptNew(order, indexOfNodeOne,
                                indexOfNodeTwo, temp, graphWeight);

                    if (newGraphWeight == null) {
                        //tempEquilibriumCount++;
                    } else {
                        if (newGraphWeight < min) {
                            min = newGraphWeight;
                        }
                        //tempEquilibriumCount = 0;
                        graphWeight = newGraphWeight;
                    }
                    tempEquilibriumCount++;
                    System.out.println("New Graph Weight : " + iteration + "graph weight");
                //}
                temp *= 1 - coolingRate;
                //System.out.println("min : " + newGraphWeight);
            }
            System.out.println("hello min graph weight = " + iteration);
            return order;
        } catch (Exception e) {
            System.out.println("Error while running simulated Annealing by order : " + e);
            throw e;
        }
    }

    public List<Node<NodeValue, NodeKeyValue>>
    runSimulatedAnnealing(List<Node<NodeValue, NodeKeyValue>> order, Double graphWeight) {
        try {
            Node<NodeValue, NodeKeyValue>[] orderArray = order.toArray((Node<NodeValue, NodeKeyValue>[]) new Node[order.size()]);
            Random random = new Random();
            double temp = 100000;
            double coolingRate = 0.005;
            double min = Double.MAX_VALUE;
            int graphV = order.size();

            while (temp > 1) {
                int tempEquilibriumCount = 0;

                //while (tempEquilibriumCount != order.size() * (order.size() - 1)) {

                    int indexOfNodeOne = random.nextInt(1, graphV - 1);

                    int indexOfNodeTwo = indexOfNodeOne;
                    while (indexOfNodeOne == indexOfNodeTwo || Math.absExact(indexOfNodeTwo - indexOfNodeOne) < 2) {

                        indexOfNodeTwo = random.nextInt(indexOfNodeOne + 1, graphV - 1);
                    }
                    Double newGraphWeight = null;

                    newGraphWeight = twoOpt(orderArray, indexOfNodeOne,
                                indexOfNodeTwo, temp, graphWeight);


                    if (newGraphWeight == null) {
                        //tempEquilibriumCount++;
                    } else {
                        if (newGraphWeight < min) {
                            min = newGraphWeight;
                        }
                        //tempEquilibriumCount = 0;
                        graphWeight = newGraphWeight;
                    }
                    tempEquilibriumCount++;
                    //System.out.println("New Graph Weight : " + newGraphWeight);
                //}
                temp *= 1 - coolingRate;
                //System.out.println("min : " + newGraphWeight);
            }
            System.out.println("hello min graph weight = " + min);
            return new ArrayList<>(Arrays.asList(orderArray));
        } catch (Exception e) {
            System.out.println("Error while running simulated Annealing by order : " + e);
            throw e;
        }
    }

    public void runSimulatedAnnealing(UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> graph) {

        List<Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight>> edgeList = new ArrayList<>(graph.getEdges().keySet());
        Random random = new Random();
        int maxEdgeSize = graph.getEdges().size();
        Double graphWeight = calculateGraphWeight(graph);
        double temp = 1000000;
        double coolingRate = 0.00005;
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

                //Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> edge1 = edgeList.get(indexOfEdgeOne);
                //Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> edge2 = edgeList.get(indexOfEdgeTwo);
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
                //System.out.println("New Graph Weight : " + newGraphWeight);
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

            if (edgeMatchingWeight < currentWeightOfGraph) {//|| Math.exp((currentWeightOfGraph - edgeMatchingWeight) / temperature) > random.nextDouble()) {
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


    private Double twoOpt(Node<NodeValue, NodeKeyValue>[] order, int indexOne, int indexTwo,
                          double temperature, Double currentWeightOfGraph) {
        try {
            double nodeOneEdgesLost = 0;
            double nodeTwoEdgesLost = 0;
            double nodeOneEdgesGain = 0;
            double nodeTwoEdgesGain = 0;

            int nodeOneLeft = -1;
            int nodeOneRight = -1;
            int nodeTwoLeft = -1;
            int nodeTwoRight = -1;

            if (indexOne == 0 || indexOne == order.length - 1) {
                if (indexOne == 0) {
                    nodeOneLeft = order.length - 1;
                    nodeOneRight = indexOne + 1;
                } else {
                    nodeOneLeft = indexOne - 1;
                    nodeOneRight = 0;
                }
            } else {
                nodeOneLeft = indexOne - 1;
                nodeOneRight = indexOne + 1;
            }

            if (indexTwo == 0 || indexTwo == order.length - 1) {
                if (indexTwo == 0) {
                    nodeTwoLeft = order.length - 1;
                    nodeTwoRight = indexTwo + 1;
                } else {
                    nodeTwoLeft = indexTwo - 1;
                    nodeTwoRight = 0;
                }
            } else {
                nodeTwoLeft = indexTwo - 1;
                nodeTwoRight = indexTwo + 1;
            }

            nodeOneEdgesLost = (Double) edgeWeights.get(new Pair<>(order[indexOne], order[nodeOneRight]))
            + (Double) edgeWeights.get(new Pair<>(order[indexOne], order[nodeOneLeft]));

            nodeTwoEdgesLost = (Double) edgeWeights.get(new Pair<>(order[indexTwo], order[nodeTwoRight]))
                    + (Double) edgeWeights.get(new Pair<>(order[indexTwo], order[nodeTwoLeft]));


            nodeOneEdgesGain = (Double) edgeWeights.get(new Pair<>(order[indexOne], order[nodeTwoRight]))
                    + (Double) edgeWeights.get(new Pair<>(order[indexOne], order[nodeTwoLeft]));

            nodeTwoEdgesGain = (Double) edgeWeights.get(new Pair<>(order[indexTwo], order[nodeOneRight]))
                    + (Double) edgeWeights.get(new Pair<>(order[indexTwo], order[nodeOneLeft]));


            Double newWeight = currentWeightOfGraph + nodeTwoEdgesGain + nodeOneEdgesGain - nodeOneEdgesLost - nodeTwoEdgesLost;

            Random random = new Random();

            //System.out.println("Random value:" + Math.exp((currentWeightOfGraph - edgeMatchingWeight) / temperature));

            if (newWeight < currentWeightOfGraph) {// || Math.exp((currentWeightOfGraph - newWeight) / temperature) > random.nextDouble()) {
                Node<NodeValue, NodeKeyValue> nodeOne = order[indexOne];
                order[indexOne] = order[indexTwo];
                order[indexTwo] = nodeOne;
                System.out.println("finally here with new weight: " + newWeight);
                return newWeight;
            }

            return null;
        } catch (Exception e) {
            System.out.println("Exception in new Two opt + e" + e);
            throw e;
        }
    }

    private Double twoOptNew(List<Node<NodeValue, NodeKeyValue>> order, int smallerIndex, int largerIndex,
                          double temperature, Double currentWeightOfGraph) {
        try {
            if (smallerIndex > largerIndex) {
                int temp = largerIndex;
                largerIndex = smallerIndex;
                smallerIndex = temp;
            }

            double nodeOneEdgesLost = 0;
            double nodeTwoEdgesLost = 0;
            double nodeOneEdgesGain = 0;
            double nodeTwoEdgesGain = 0;

            nodeOneEdgesLost = (Double) edgeWeights.get(new Pair<>(order.get(smallerIndex), order.get(smallerIndex + 1)));

            nodeTwoEdgesLost = (Double) edgeWeights.get(new Pair<>(order.get(largerIndex), order.get(largerIndex + 1)));


            nodeOneEdgesGain = (Double) edgeWeights.get(new Pair<>(order.get(smallerIndex), order.get(largerIndex)));

            nodeTwoEdgesGain = (Double) edgeWeights.get(new Pair<>(order.get(smallerIndex + 1), order.get(largerIndex + 1)));


            Double newWeight = currentWeightOfGraph + nodeTwoEdgesGain + nodeOneEdgesGain - nodeOneEdgesLost - nodeTwoEdgesLost;

            Random random = new Random();

            //System.out.println("Random value:" + Math.exp((currentWeightOfGraph - edgeMatchingWeight) / temperature));

            if (newWeight < currentWeightOfGraph || Math.exp((currentWeightOfGraph - newWeight) / temperature) > random.nextDouble()) {

                List<Node<NodeValue, NodeKeyValue>> startToSmallerIndex = createNewSubList(order, 0, smallerIndex, false);
                List<Node<NodeValue, NodeKeyValue>> plusOneSmallerToLargerIndexReverse = createNewSubList(order, smallerIndex + 1, largerIndex, true);
                List<Node<NodeValue, NodeKeyValue>> plusOneLargerIndexToEnd = createNewSubList(order, largerIndex + 1, order.size() - 1, false);
                List<Node<NodeValue, NodeKeyValue>> ansList = new ArrayList<>();
                order.clear();
                order.addAll(startToSmallerIndex);
                order.addAll(plusOneSmallerToLargerIndexReverse);
                order.addAll(plusOneLargerIndexToEnd);
                //System.out.println("finally here with new weight: " + newWeight);
                return newWeight;
            }

            return null;
        } catch (Exception e) {
            System.out.println("Exception in new Two opt + e" + e);
            throw e;
        }
    }

    private List<Node<NodeValue, NodeKeyValue>> createNewSubList(List<Node<NodeValue, NodeKeyValue>> list, int from, int to, boolean reverse) {
        List<Node<NodeValue, NodeKeyValue>> newlist = new ArrayList<>(to - from + 1);
        if (reverse) {
            for (int i = to; i >= from; i--) {
                newlist.add(list.get(i));
            }
        } else {
            for (int i = from; i <= to; i++) {
                newlist.add(list.get(i));
            }
        }
        return newlist;
    }

}
