package info6205.Graph.Problems.TravellingSalesMan.Algorithm;import info6205.Graph.*;import info6205.Graph.Problems.TravellingSalesMan.GraphImpl.LatLongId;import info6205.Graph.Utils.Pair;import info6205.Graph.Utils.PointPlotter;import java.awt.*;import java.util.*;import java.util.List;import java.util.stream.Collectors;public class Christofides<NodeValue, NodeKeyValue, EdgeWeight extends Comparable<EdgeWeight>> {    private UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> graph;    private Map<Pair<Node<NodeValue, NodeKeyValue>, Node<NodeValue, NodeKeyValue>>, EdgeWeight> edgeWeights;    private final EdgeCreator<NodeValue, NodeKeyValue, EdgeWeight> edgeCreator;    //private final SimulatedAnnealing<NodeValue, NodeKeyValue, EdgeWeight> simulatedAnnealing;    public Christofides(UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> graph,                        EdgeCreator<NodeValue, NodeKeyValue, EdgeWeight> edgeCreator) {        this.graph = graph;        this.edgeWeights = new HashMap<>(graph.getSize() * graph.getSize() * 2);        this.edgeCreator = edgeCreator;    }    public void runChristofides() {        try {            addEdgesBetweenNodes();            graph.test();            PointPlotter.initializePointPlotter();            //System.out.println("hello hello");            UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> mst = graph.getMSTByPrims();            double mstWeight = calculateGraphWeight(mst);            List<Node<NodeValue, NodeKeyValue>> oddEdgedNodes = getOddEdgesNodes(mst);            //plotGraph(mst);            //plotEdges(edges);            //for multimatch check            List<Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight>> edges = createEdgesForOddNodeBruteForce(oddEdgedNodes);            createMultiGraphFromMstAndEdges(mst, edges);            UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> multiGraphEulerian = mst;            //here in createEulerianTourFromMinWeightMultiMatchGraphStarter using external removal for edges and not using graphs method            List<Node<NodeValue, NodeKeyValue>> eulerianTour = createEulerianTourFromMinWeightMultiMatchGraphStarter(multiGraphEulerian);            //without traversing multimatch            //List<Node<NodeValue, NodeKeyValue>> eulerianTour = createOrderFromDFSStarter(mst);            UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> christoTsp = createGraphWithSameNodes(this.graph);            generateTspTourFromEulerianTourStarter(eulerianTour, christoTsp);            //PointPlotter pointPlotter = new PointPlotter();            double tspWeight = calculateGraphWeight(christoTsp);            SimulatedAnnealing<NodeValue, NodeKeyValue, EdgeWeight> simulatedAnnealing = new SimulatedAnnealing<>(edgeWeights);            simulatedAnnealing.runSimulatedAnnealing(christoTsp, 100);            System.out.println("here 2");            plotGraph(christoTsp);            tspWeight = calculateGraphWeight(christoTsp);            System.out.println("yo" + tspWeight);        } catch (Exception e) {            System.out.println("exception while performing christofedies : " +  Arrays.stream(e.getStackTrace()).map(p -> p.toString()).collect(Collectors.joining(", ")));        }    }    private void addEdgesBetweenNodes() {        List<Node<NodeValue, NodeKeyValue>> nodeList = graph.getNodes();        for (int i = 0; i < nodeList.size() - 1; i++) {            for (int j = i + 1; j < nodeList.size(); j++) {                EdgeWeight edgeWeight =  (EdgeWeight) getDistanceBetweenNodes((Node<String, LatLongId>) nodeList.get(i),                        (Node<String, LatLongId>) nodeList.get(j));                graph.addEdge(nodeList.get(i), nodeList.get(j), edgeWeight);                edgeWeights.put(new Pair<>(nodeList.get(i), nodeList.get(j)), edgeWeight);                edgeWeights.put(new Pair<>(nodeList.get(j), nodeList.get(i)), edgeWeight);            }        }    }    private UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> createGraphWithSameNodes(            UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> graph) {        UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> graphCopy = new UndirectedEdgeWeighedListGraph<>(edgeCreator);        for (Node<NodeValue, NodeKeyValue> node : graph.getNodes()) {            graphCopy.addNode(node);        }        return graphCopy;    }    private Double getDistanceBetweenNodes(Node<String, LatLongId> node1, Node<String, LatLongId> node2) {        return Math.sqrt(Math.pow(node1.getKey().getValue().getLatitude() - node2.getKey().getValue().getLatitude(), 2)                + Math.pow(node1.getKey().getValue().getLongitude() - node2.getKey().getValue().getLongitude(), 2));    }    private List<Node<NodeValue, NodeKeyValue>> getOddEdgesNodes(UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> graph) {        List<Node<NodeValue, NodeKeyValue>> oddNodes = new ArrayList<>();        for (Node<NodeValue, NodeKeyValue> node : graph.getNodes()) {            if (graph.getNeighbours(node.getKey()).size() % 2 != 0) {                oddNodes.add(node);            }        }        return oddNodes;    }    private List<Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight>> createEdgesForOddNodeBruteForce(            List<Node<NodeValue, NodeKeyValue>> listOfOddNode) {        try {            Set<Key<NodeKeyValue>> setOfNodesDone = new HashSet<>();            List<Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight>> listOfEdges = new LinkedList<>();            for (int i = 0; i < listOfOddNode.size() - 1; i++) {                if (setOfNodesDone.contains(listOfOddNode.get(i).getKey())) {                    continue;                }                //int minIndex = -1;                int jMin = -1;                EdgeWeight minEdgeWeight = null;                for (int j = i + 1; j < listOfOddNode.size(); j++) {                    if (setOfNodesDone.contains(listOfOddNode.get(j).getKey())) {                        continue;                    }                    if (minEdgeWeight == null                     || minEdgeWeight.compareTo(edgeWeights.get(new Pair<>(listOfOddNode.get(i), listOfOddNode.get(j)))) > 0) {                        jMin = j;                        minEdgeWeight = edgeWeights.get(new Pair<>(listOfOddNode.get(i), listOfOddNode.get(j)));                    }                }                //System.out.println("Jmin " + jMin + " i " + i);                listOfEdges.add(edgeCreator.createEdge(listOfOddNode.get(i), listOfOddNode.get(jMin), minEdgeWeight));                setOfNodesDone.add(listOfOddNode.get(i).getKey());                setOfNodesDone.add(listOfOddNode.get(jMin).getKey());            }            return listOfEdges;        } catch (Exception e) {            System.out.println("Exception while creating edges " + Arrays.stream(e.getStackTrace()).map(p -> p.toString()).collect(Collectors.joining(", ")));            throw e;        }    }    private List<Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight>> createEdgesForOddNodesBlossoms(List<Node<NodeValue, NodeKeyValue>> listOfOddNode) {        return null;    }    public void createMultiGraphFromMstAndEdges(UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> mst,                                    List<Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight>> edges) {        for (Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> edge : edges) {            mst.addEdge(edge.getFirstNode(), edge.getSecondNode(), edge.getEdgeWeight());        }    }    private List<Node<NodeValue, NodeKeyValue>> createOrderFromDFSStarter(            UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> graph) {        Set<Key<NodeKeyValue>> visited = new HashSet<>();        Set<Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight>> edgeSet = new HashSet<>();        List<Node<NodeValue, NodeKeyValue>> orderList = new LinkedList<>();        createEulerianTourFromMinWeightMultiMatchGraph(visited, edgeSet, graph, graph.getNodes().get(0), orderList);        return orderList;    }    private void createOrderFromDFS(Set<Key<NodeKeyValue>> visited,                                                                Set<Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight>> edgeSet,                                                                UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> graph,                                                                Node<NodeValue, NodeKeyValue> node,                                                                List<Node<NodeValue, NodeKeyValue>> orderList) {        if (visited.contains(node.getKey())) {            return;        }        visited.add(node.getKey());        orderList.add(node);        while (graph.getNeighbours(node.getKey()).size() != 0) {            Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> edge = graph.getNeighbours(node.getKey()).get(0);            graph.getNeighbours(node.getKey()).remove(edge);            Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> reverseEdge = edge.createReverseEdge();            if (edgeSet.contains(edge) || edgeSet.contains(reverseEdge)) {                edgeSet.remove(edgeSet.contains(edge) ? edge : reverseEdge);            } else {                edgeSet.add(edge);                createEulerianTourFromMinWeightMultiMatchGraph(visited, edgeSet, graph, edge.getSecondNode(), orderList);            }        }    }    private List<Node<NodeValue, NodeKeyValue>> createEulerianTourFromMinWeightMultiMatchGraphStarter(            UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> graph) {        Set<Key<NodeKeyValue>> visited = new HashSet<>();        Set<Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight>> edgeSet = new HashSet<>();        List<Node<NodeValue, NodeKeyValue>> orderList = new LinkedList<>();        createEulerianTourFromMinWeightMultiMatchGraph(visited, edgeSet, graph, graph.getNodes().get(0), orderList);        return orderList;    }    private void createEulerianTourFromMinWeightMultiMatchGraph(Set<Key<NodeKeyValue>> visited,                                                                Set<Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight>> edgeSet,                                                                UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> graph,                                                                Node<NodeValue, NodeKeyValue> node,                                                                List<Node<NodeValue, NodeKeyValue>> orderList) {        /*        if (visited.contains(node.getKey())) {            return;        }        //visited.add(node.getKey());         */        orderList.add(node);        while (graph.getNeighbours(node.getKey()).size() != 0) {            Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> edge = graph.getNeighbours(node.getKey()).get(0);            graph.getNeighbours(node.getKey()).remove(edge);            Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> reverseEdge = edge.createReverseEdge();            if (edgeSet.contains(edge) || edgeSet.contains(reverseEdge)) {                edgeSet.remove(edgeSet.contains(edge) ? edge : reverseEdge);            } else {                edgeSet.add(edge);                createEulerianTourFromMinWeightMultiMatchGraph(visited, edgeSet, graph, edge.getSecondNode(), orderList);            }        }    }    private void generateTspTourFromEulerianTourStarter(List<Node<NodeValue, NodeKeyValue>> eulerianTour,                                                 UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> graph) {        Set<Node<NodeValue, NodeKeyValue>> visitedNodes = new HashSet<>();        Node<NodeValue, NodeKeyValue> previousNode = eulerianTour.get(0);        int currentNodeIndex = 1;        int lastNodeUsed = -1;        visitedNodes.add(previousNode);        while (visitedNodes.size() != graph.getSize()) {            if (visitedNodes.contains(eulerianTour.get(currentNodeIndex))) {                currentNodeIndex++;                continue;            }            graph.addEdge(previousNode, eulerianTour.get(currentNodeIndex),                    edgeWeights.get(new Pair<>(previousNode, eulerianTour.get(currentNodeIndex))));            lastNodeUsed = currentNodeIndex;            previousNode = eulerianTour.get(currentNodeIndex);            visitedNodes.add(previousNode);            currentNodeIndex++;        }        graph.addEdge(eulerianTour.get(0), eulerianTour.get(lastNodeUsed),                edgeWeights.get(new Pair<>(eulerianTour.get(0), eulerianTour.get(lastNodeUsed))));    }    public void plotEdges(java.util.List<Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight>> edgeList) {        //java.util.List<Edge<Node<String, LatLongId>, Double>> edgeList =        for (Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> edge : edgeList) {            PointPlotter.addLineOuter(((LatLongId) edge.getFirstNode().getKey().getValue()).getLatitude(),                    ((LatLongId)edge.getFirstNode().getKey().getValue()).getLongitude(),                    ((LatLongId)edge.getSecondNode().getKey().getValue()).getLatitude(),                    ((LatLongId)edge.getSecondNode().getKey().getValue()).getLongitude(),                    Color.GREEN);        }    }    public void plotGraph(UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> graph) {        for (Node<NodeValue, NodeKeyValue> node : graph.getNodes()) {            PointPlotter.addPointOuter(((LatLongId)node.getKey().getValue()).getLatitude(),                    ((LatLongId)node.getKey().getValue()).getLongitude(), Color.RED);        }        for (Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> edge : graph.getEdges().keySet()) {            PointPlotter.addLineOuter(((LatLongId) edge.getFirstNode().getKey().getValue()).getLatitude(),                    ((LatLongId)edge.getFirstNode().getKey().getValue()).getLongitude(),                    ((LatLongId)edge.getSecondNode().getKey().getValue()).getLatitude(),                    ((LatLongId)edge.getSecondNode().getKey().getValue()).getLongitude(),                    Color.BLUE);        }    }    private Double calculateGraphWeight(UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> graph) {        Double ans = 0D;        for (Node<NodeValue, NodeKeyValue> node : graph.getNodes()) {            for (Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> edge : graph.getNeighbours(node.getKey())) {                ans = ans +  (Double) edge.getEdgeWeight();            }        }        return ans/2;    }}