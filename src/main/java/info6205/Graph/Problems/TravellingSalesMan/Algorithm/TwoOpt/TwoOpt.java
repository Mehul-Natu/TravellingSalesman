package info6205.Graph.Problems.TravellingSalesMan.Algorithm.TwoOpt;

import info6205.Graph.Node;
import info6205.Graph.Utils.Pair;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TwoOpt<NodeValue, NodeKeyValue, EdgeWeight extends Comparable<EdgeWeight>> {

    Map<Pair<Node<NodeValue, NodeKeyValue>, Node<NodeValue, NodeKeyValue>>, EdgeWeight> edgeWeights;

    List<int[]> segments;

    public TwoOpt(Map<Pair<Node<NodeValue, NodeKeyValue>, Node<NodeValue, NodeKeyValue>>, EdgeWeight> edgeWeights, int size) {
        this.edgeWeights = edgeWeights;
        //segments = allSegments(size);
    }

    public Double performTwoOpt(List<Node<NodeValue, NodeKeyValue>> order, Double tsp) {
        try {
            long iteration = 0;
            Random random = new Random();
            while (true) {
                Double delta = 0D;
                for (int a = 1; a < order.size() - 1; a++) {
                    for (int b = a + 2; b < order.size(); b++) {
                        if (random.nextDouble() < 0.6) {
                        //    continue;
                        }
                        Double temp = twoOpt(order, a, b, tsp);
                        delta += temp;
                        //System.out.println("\nDelta : " + temp + ", Tsp: " + tsp);
                        tsp += temp;
                        //System.out.println("Tsp weight : " + tsp + ", iteration = " + iteration++);
                    }
                }
                if (delta >= 0) {
                    break;
                }
                iteration++;
            }
            System.out.println("iterations" + iteration);
            return tsp;
        } catch (Exception e) {
            System.out.println("Exception While Performing TwoOpt");
            throw e;
        }
    }

    private Double twoOpt(List<Node<NodeValue, NodeKeyValue>> order, int smallerIndex, int largerIndex,
                              Double currentWeightOfGraph) {
        try {
            if (smallerIndex > largerIndex) {
                int temp = largerIndex;
                largerIndex = smallerIndex;
                smallerIndex = temp;
            }
            largerIndex = largerIndex % order.size();

            double nodeOneEdgesLost = 0;
            double nodeTwoEdgesLost = 0;
            double nodeOneEdgesGain = 0;
            double nodeTwoEdgesGain = 0;

            nodeOneEdgesLost = (Double) edgeWeights.get(new Pair<>(order.get(smallerIndex), order.get(smallerIndex + 1)));
            nodeTwoEdgesLost = (Double) edgeWeights.get(new Pair<>(order.get(largerIndex), order.get((largerIndex + 1) % order.size())));

            nodeOneEdgesGain = (Double) edgeWeights.get(new Pair<>(order.get(smallerIndex), order.get(largerIndex)));
            nodeTwoEdgesGain = (Double) edgeWeights.get(new Pair<>(order.get(smallerIndex + 1), order.get((largerIndex + 1) % order.size())));

            Double newWeight = currentWeightOfGraph + nodeTwoEdgesGain + nodeOneEdgesGain - nodeOneEdgesLost - nodeTwoEdgesLost;
            Random random = new Random();

            //System.out.println("Random value:" + Math.exp((currentWeightOfGraph - edgeMatchingWeight) / temperature));

            if (newWeight < currentWeightOfGraph) {// || Math.exp((currentWeightOfGraph - newWeight) / temperature) > random.nextDouble()) {
                Collections.reverse(order.subList(smallerIndex + 1, largerIndex + 1));
                return newWeight - currentWeightOfGraph;
            }

            return 0.0;
        } catch (Exception e) {
            System.out.println("Exception in new Two opt + e" + e);
            throw e;
        }
    }

    private Double twoOpt(List<Node<NodeValue, NodeKeyValue>> order, int smallerIndex, int largerIndex,
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
                Collections.reverse(order.subList(smallerIndex + 1, largerIndex + 1));
                return newWeight;
            }

            return null;
        } catch (Exception e) {
            System.out.println("Exception in new Two opt + e" + e);
            throw e;
        }
    }
}
