package info6205;

import info6205.Graph.Problems.TravellingSalesMan.GraphImpl.EdgeALG;
import info6205.Graph.UnWeightedAdjacencyListGraph;
import info6205.Graph.Problems.TravellingSalesMan.Algorithm.Christofides;
import info6205.Graph.Problems.TravellingSalesMan.Algorithm.GraphReader;
import info6205.Graph.Problems.TravellingSalesMan.GraphImpl.LatLongId;
import info6205.Graph.UndirectedEdgeWeighedListGraph;

public class Runner {

    public static void main(String[] args) {
        UndirectedEdgeWeighedListGraph<String, LatLongId, Double> graph = GraphReader
                .getGraphFromFile("crimeSample.csv", EdgeALG::new);
        Christofides christofidesRunner = new Christofides(graph);
        christofidesRunner.runChristofides();
    }
}
