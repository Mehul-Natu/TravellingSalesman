package info6205.Graph.Problems.TravellingSalesMan.Algorithm.ThreeOpt;

import info6205.Graph.Edge;
import info6205.Graph.Node;
import info6205.Graph.UndirectedEdgeWeighedListGraph;
import info6205.Graph.Utils.Pair;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ThreeOpt<NodeValue, NodeKeyValue, EdgeWeight extends Comparable<EdgeWeight>> {


    Map<Pair<Node<NodeValue, NodeKeyValue>, Node<NodeValue, NodeKeyValue>>, EdgeWeight> edgeWeights;

    public ThreeOpt(Map<Pair<Node<NodeValue, NodeKeyValue>, Node<NodeValue, NodeKeyValue>>, EdgeWeight> edgeWeights) {
        this.edgeWeights = edgeWeights;
    }

    public Double performThreeOpt(UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> graph,
                                  Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> edge1,
                                  Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> edge2, Double temperature, Double currentWeightOfGraph) {
        try {
            return null;
        } catch (Exception e) {
            System.out.println("Exception While Performing ThreeOpt");
            throw e;
        }
    }


    public Double performThreeOpt(List<Node<NodeValue, NodeKeyValue>> order, int firstIndex, int secondIndex, int thirdIndex,
                                  Double temperature, Double currentWeightOfGraph) {
        try {
            Node<NodeValue, NodeKeyValue> nodeA = order.get(firstIndex - 1);
            Node<NodeValue, NodeKeyValue> nodeB = order.get(firstIndex);
            Node<NodeValue, NodeKeyValue> nodeC = order.get(secondIndex - 1);
            Node<NodeValue, NodeKeyValue> nodeD = order.get(secondIndex);
            Node<NodeValue, NodeKeyValue> nodeE = order.get(thirdIndex - 1);
            Node<NodeValue, NodeKeyValue> nodeF = order.get(thirdIndex % order.size());

            Double d0 = (Double) edgeWeights.get(new Pair<>(nodeA, nodeB)) + (Double) edgeWeights.get(new Pair<>(nodeC, nodeD)) +
                    (Double) edgeWeights.get(new Pair<>(nodeE, nodeF));
            Double d1 = (Double) edgeWeights.get(new Pair<>(nodeA, nodeC)) + (Double) edgeWeights.get(new Pair<>(nodeB, nodeD)) +
                    (Double) edgeWeights.get(new Pair<>(nodeE, nodeF));
            Double d2 = (Double) edgeWeights.get(new Pair<>(nodeA, nodeB)) + (Double) edgeWeights.get(new Pair<>(nodeC, nodeE)) +
                    (Double) edgeWeights.get(new Pair<>(nodeD, nodeF));
            Double d3 = (Double) edgeWeights.get(new Pair<>(nodeA, nodeD)) + (Double) edgeWeights.get(new Pair<>(nodeE, nodeB)) +
                    (Double) edgeWeights.get(new Pair<>(nodeC, nodeF));
            Double d4 = (Double) edgeWeights.get(new Pair<>(nodeF, nodeB)) + (Double) edgeWeights.get(new Pair<>(nodeC, nodeD)) +
                    (Double) edgeWeights.get(new Pair<>(nodeE, nodeA));

            if (d0 > d1) {
                Collections.reverse(order.subList(firstIndex, secondIndex + 1));
                return -d0 + d1;
            } else if (d0 > d2) {
                Collections.reverse(order.subList(secondIndex, thirdIndex + 1));
                return -d0 + d2;
            } else if (d0 > d3) {
                Collections.reverse(order.subList(firstIndex, secondIndex + 1));
                return -d0 + d3;
            } else if (d0 > d4) {
                Collections.reverse(order.subList(firstIndex, secondIndex + 1));
                return -d0 + d4;
            }

            return null;

        } catch (Exception e) {
            System.out.println("Exception While Performing ThreeOpt e: " + e);
            throw e;
        }
    }
}
