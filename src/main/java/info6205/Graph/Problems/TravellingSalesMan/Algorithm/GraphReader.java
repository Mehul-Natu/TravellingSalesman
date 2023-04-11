package info6205.Graph.Problems.TravellingSalesMan.Algorithm;

import info6205.Graph.EdgeCreator;
import info6205.Graph.Node;
import info6205.Graph.Problems.TravellingSalesMan.GraphImpl.Key;
import info6205.Graph.Problems.TravellingSalesMan.GraphImpl.LatLongId;
import info6205.Graph.Problems.TravellingSalesMan.GraphImpl.NodeALG;
import info6205.Graph.UndirectedEdgeWeighedListGraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;


public class GraphReader {

    public static UndirectedEdgeWeighedListGraph<String, LatLongId, Double> getGraphFromFile(String fileName,
                                                                                             EdgeCreator<String, LatLongId, Double> edgeCreator) {
        try {
            String path = new File(".").getCanonicalPath();

            File file = new File(path + "/csvData/" + fileName);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            UndirectedEdgeWeighedListGraph<String, LatLongId, Double> graph = new UndirectedEdgeWeighedListGraph<>(edgeCreator);
            System.out.println(bufferedReader.readLine());
            HashSet<String> set = new HashSet<>();
            while (true) {
                Node<String, LatLongId> node = createNodeFromData(bufferedReader);
                if (node == null) {
                    break;
                } else {

                    String key = String.valueOf(node.getKey().getValue().getLatitude()) +
                            String.valueOf(node.getKey().getValue().getLongitude());
                    if (!set.contains(key)) {
                        //System.out.println("Duplicate not found");
                        graph.addNode(node);
                    }
                    set.add(key);
                }
            }
            //graph.createEdgesFromPresentNodes();

           return graph;


        } catch (Exception e) {
            System.out.println("Error while creating graph : " + e);
            return null;
        }
    }

    private static Node<String, LatLongId> createNodeFromData(BufferedReader br) throws IOException{
        String nodeData = br.readLine();
        if (nodeData != null) {
            String[] idLongLat = nodeData.split(",");
            return new NodeALG(new Key(
                    new LatLongId(Double.valueOf(idLongLat[2]), Double.valueOf(idLongLat[1]), idLongLat[0])),
                    idLongLat[0]);

        }
        return null;
    }
}
