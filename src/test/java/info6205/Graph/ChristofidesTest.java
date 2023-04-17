package info6205.Graph;

import info6205.Graph.Problems.TravellingSalesMan.Algorithm.Christofides;
import info6205.Graph.Problems.TravellingSalesMan.GraphImpl.EdgeALG;
import info6205.Graph.Problems.TravellingSalesMan.GraphImpl.LatLongId;
import info6205.Graph.Utils.Pair;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static info6205.Graph.Utility.Utility.createNode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ChristofidesTest {

    @Test
    public void testAddEdgesBetweenNodes() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        UndirectedEdgeWeighedListGraph<String, LatLongId, Double> graph = new UndirectedEdgeWeighedListGraph<>(EdgeALG::new);
        Node<String, LatLongId> node1 = createNode(1.0, 1.0, "1");
        Node<String, LatLongId> node2 = createNode(2.0, 2.0, "2");
        Node<String, LatLongId> node3 = createNode(3.0, 3.0, "3");
        Node<String, LatLongId> node4 = createNode(4.0, 4.0, "4");
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        Method addEdgesBetweenNodesMethod = getMethodByName("addEdgesBetweenNodes");
        Christofides<String, LatLongId, Double> christofides = new Christofides<>(graph, EdgeALG::new);
        addEdgesBetweenNodesMethod.invoke(christofides);
        assertEquals(6, graph.getEdges().size());
        assertEquals(3, graph.getNeighbours(node1.getKey()).size());
        assertEquals(3, graph.getNeighbours(node2.getKey()).size());
        assertEquals(3, graph.getNeighbours(node3.getKey()).size());
        assertEquals(3, graph.getNeighbours(node4.getKey()).size());
    }

    @Test
    public void testCreateGraphWithSameNodes() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        UndirectedEdgeWeighedListGraph<String, LatLongId, Double> graph = new UndirectedEdgeWeighedListGraph<>(EdgeALG::new);
        Node<String, LatLongId> node1 = createNode(1.0, 1.0, "1");
        Node<String, LatLongId> node2 = createNode(2.0, 2.0, "2");
        Node<String, LatLongId> node3 = createNode(3.0, 3.0, "3");
        Node<String, LatLongId> node4 = createNode(4.0, 4.0, "4");
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        Christofides<String, LatLongId, Double> christofides = new Christofides<>(graph, EdgeALG::new);
        Method createGraphWithSameNodesMethod = getMethodByName("createGraphWithSameNodes");
        UndirectedEdgeWeighedListGraph<String, LatLongId, Double> copyGraph = (UndirectedEdgeWeighedListGraph<String, LatLongId, Double>) createGraphWithSameNodesMethod.invoke(christofides, graph);
        assertTrue(graph.equals(copyGraph));
    }

    @Test
    public void testGetOddEdgesNodes() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        UndirectedEdgeWeighedListGraph<String, LatLongId, Double> graph = new UndirectedEdgeWeighedListGraph<>(EdgeALG::new);
        Node<String, LatLongId> node1 = createNode(1.0, 1.0, "1");
        Node<String, LatLongId> node2 = createNode(2.0, 2.0, "2");
        Node<String, LatLongId> node3 = createNode(3.0, 3.0, "3");
        Node<String, LatLongId> node4 = createNode(4.0, 4.0, "4");
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.addEdge(node1, node2, 2D);
        graph.addEdge(node1, node3, 2D);
        graph.addEdge(node3, node4, 2D);
        HashSet<Node<String, LatLongId>> oddEdgesNodeSet = new HashSet<>();
        oddEdgesNodeSet.add(node2);
        oddEdgesNodeSet.add(node4);
        Christofides<String, LatLongId, Double> christofides = new Christofides<>(graph, EdgeALG::new);
        Method createGraphWithSameNodesMethod = getMethodByName("getOddEdgesNodes");
        List<Node<String, LatLongId>> oddEdgeNodeListToBeTested = (List<Node<String, LatLongId>>) createGraphWithSameNodesMethod.invoke(christofides, graph);
        assertTrue(oddEdgesNodeSet.containsAll(oddEdgeNodeListToBeTested));
    }

    @Test
    public void testCreateMinimumWeightPerfectMatchingBruteForce() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        UndirectedEdgeWeighedListGraph<String, LatLongId, Double> graph = new UndirectedEdgeWeighedListGraph<>(EdgeALG::new);
        Node<String, LatLongId> node1 = createNode(1.0, 1.0, "1");
        Node<String, LatLongId> node2 = createNode(2.0, 2.0, "2");
        Node<String, LatLongId> node3 = createNode(3.0, 3.0, "3");
        Node<String, LatLongId> node4 = createNode(4.0, 4.0, "4");
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.addEdge(node1, node2, 2D);
        graph.addEdge(node1, node3, 2D);
        graph.addEdge(node3, node4, 2D);
        Map<Pair<Node<String, LatLongId>, Node<String, LatLongId>>, Double> edgeWeights = new HashMap<>();

        edgeWeights.put(new Pair<>(node1, node2), 1D);
        edgeWeights.put(new Pair<>(node2, node1), 1D);
        edgeWeights.put(new Pair<>(node1, node3), 2D);
        edgeWeights.put(new Pair<>(node3, node1), 2D);
        edgeWeights.put(new Pair<>(node3, node4), 4D);
        edgeWeights.put(new Pair<>(node4, node3), 4D);
        edgeWeights.put(new Pair<>(node4, node2), 2D);
        edgeWeights.put(new Pair<>(node2, node4), 2D);

        List<Node<String, LatLongId>> oddEdgesNodeList = new ArrayList<>();
        oddEdgesNodeList.add(node2);
        oddEdgesNodeList.add(node4);
        Christofides<String, LatLongId, Double> christofides = new Christofides<>(graph, EdgeALG::new);
        christofides.setEdgeWeights(edgeWeights);
        Method createGraphWithSameNodesMethod = getMethodByName("createMinimumWeightPerfectMatchingBruteForce");
        List<Edge<Node<String, LatLongId>, Double>> multiMatchEdges = (List<Edge<Node<String, LatLongId>, Double>>) createGraphWithSameNodesMethod.invoke(christofides, graph.getNodes());
        assertEquals(2, multiMatchEdges.size());
    }

    private Method getMethodByName(String methodName) throws NoSuchMethodException {
        Method[] methods = Christofides.class.getDeclaredMethods();
        Method privateMethod = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                privateMethod = method;
                privateMethod.setAccessible(true); // Set the method accessible
                break;
            }
        }
        return privateMethod;
    }


    //getDistanceBetweenNodes Left
    private Method getGetDistanceBetweenNodesMethod() throws NoSuchMethodException {
        Method method = Christofides.class.getDeclaredMethod("getDistanceBetweenNodes");
        method.setAccessible(true);
        return method;
    }
    //getOddEdgesNodes
    private Method getGetOddEdgesNodesMethod() throws NoSuchMethodException {
        Method method = Christofides.class.getDeclaredMethod("getOddEdgesNodes");
        method.setAccessible(true);
        return method;
    }

    //createEdgesForOddNodeBruteForce
    private Method getCreateEdgesForOddNodeBruteForceMethod() throws NoSuchMethodException {
        Method method = Christofides.class.getDeclaredMethod("createEdgesForOddNodeBruteForce");
        method.setAccessible(true);
        return method;
    }

    //createMultiGraphFromMstAndEdges
    private Method getCreateMultiGraphFromMstAndEdgesMethod() throws NoSuchMethodException {
        Method method = Christofides.class.getDeclaredMethod("createMultiGraphFromMstAndEdges");
        method.setAccessible(true);
        return method;
    }
}
