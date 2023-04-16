package info6205.Graph.Problems.TravellingSalesMan.Algorithm;

import info6205.Graph.Edge;
import info6205.Graph.Node;
import info6205.Graph.UndirectedEdgeWeighedListGraph;
import info6205.Graph.Utils.Pair;

import java.util.List;
import java.util.Map;

public class ThreeOpt<NodeValue, NodeKeyValue, EdgeWeight extends Comparable<EdgeWeight>> {


    Map<Pair<Node<NodeValue, NodeKeyValue>, Node<NodeValue, NodeKeyValue>>, EdgeWeight> edgeWeights;

    public ThreeOpt(Map<Pair<Node<NodeValue, NodeKeyValue>, Node<NodeValue, NodeKeyValue>>, EdgeWeight> edgeWeights) {
        this.edgeWeights = edgeWeights;
    }

    public Double performThreeOpt(UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> graph,
                                  Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> edge1,
                                  Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> edge2, double temperature, Double currentWeightOfGraph) {
        try {
            return null;
        } catch (Exception e) {
            System.out.println("Exception While Performing ThreeOpt");
        }
    }




    public Double performThreeOpt(List<Node<NodeValue, NodeKeyValue>> order, int smallerIndex, int largerIndex,
                                  double temperature, Double currentWeightOfGraph) {
        try {

        } catch (Exception e) {
            System.out.println("Exception While Performing ThreeOpt e: " + e);
            throw e;
        }
    }
}
