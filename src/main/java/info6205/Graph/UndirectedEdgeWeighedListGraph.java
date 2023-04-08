package info6205.Graph;


import info6205.Graph.Utils.IndexMinPQ;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight extends Comparable<EdgeWeight>>
        extends EdgeWeightedListGraph<NodeValue, NodeKeyValue, EdgeWeight> {

    private final EdgeCreator<NodeValue, NodeKeyValue, EdgeWeight> edgeCreator;

    public UndirectedEdgeWeighedListGraph(EdgeCreator<NodeValue, NodeKeyValue, EdgeWeight> edgeCreator) {
        super();
        this.edgeCreator = edgeCreator;
    }

    //how to stop this method being called if weighted Edge is used
    @Override
    protected boolean addNeighbour(Node<NodeValue, NodeKeyValue> nodeValue1, Node<NodeValue, NodeKeyValue> nodeValue2,
                                   EdgeWeight edgeWeight) {
        try {
            if (!nodes.containsKey(nodeValue1.getKey())) {
                addNode(nodeValue1);
            }

            if (!nodes.containsKey(nodeValue2.getKey())) {
                addNode(nodeValue2);
            }
            neighbourMap.get(nodeValue1.getKey()).add(edgeCreator.createEdge(nodeValue1, nodeValue2, edgeWeight));
            neighbourMap.get(nodeValue2.getKey()).add(edgeCreator.createEdge(nodeValue2, nodeValue1, edgeWeight));
            return true;

        } catch (Exception e) {
            System.out.println("Error while adding neighbour e: " + e);
            return false;
        }
    }

    @Override
    public boolean addEdge(Node<NodeValue, NodeKeyValue> node1, Node<NodeValue, NodeKeyValue> node2, EdgeWeight edgeWeight) {
        try {
            Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> edge = edgeCreator.createEdge(node1, node2, edgeWeight);
            ///todo mehul to add reverse node edge condition

            if (edges.contains(edge)) {
                return true;
            }

            edges.add(edge);
            addNeighbour(node1, node2, edgeWeight);


        } catch (Exception e) {
            System.out.println("Error while adding edge e: " + e);
            return false;
        }
        return false;
    }

    public void test() {
        Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> edge = this.neighbourMap.get(getNodes().get(0).getKey()).get(0);
        if (this.edges.contains(edge)) {
            System.out.println("Hash code working fine");
        }
    }


    public UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> getMSTByPrims() {
        try {

            UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> graphMst =
                    new UndirectedEdgeWeighedListGraph<>(edgeCreator);

            //Creating indices of Nodes and shortedEdgesNeighbour to reach those nodes
            // also weight to reach that node;
            Map<Key<NodeKeyValue>, Integer> nodeIndexMap = new HashMap<>();
            Map<Integer, Node<NodeValue, NodeKeyValue>> indexNodeMap = new HashMap<>();
            Set<Integer> visited = new HashSet<>();
            Set<Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight>> goodEdges = new HashSet<>();
            //Map<Integer, Integer> edgeNeighbourNode = new HashMap<>();
            int count = 0;
            Key<NodeKeyValue> startingKey = null;
            IndexMinPQ<SingleNodeEdgeWeight<EdgeWeight, NodeValue, NodeKeyValue>> indexedMinEdgePQ
                    = new IndexMinPQ<>(this.nodes.size());


            for (Key<NodeKeyValue> nodeKey : this.nodes.keySet()) {
                if (startingKey == null) {
                    startingKey = nodeKey;
                }
                //edgeNeighbourNode.put(, null);
                nodeIndexMap.put(nodeKey, count++);
                indexNodeMap.put(count - 1, this.nodes.get(nodeKey));
            }

            //initializing with first node
            visited.add(nodeIndexMap.get(startingKey));
            for (Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> edge : this.neighbourMap.get(startingKey)) {

                Node<NodeValue, NodeKeyValue> neighbour = edge.getSecondNode();

                EdgeWeight edgeWeight = edge.getEdgeWeight();

                int neighbourIndex = nodeIndexMap.get(neighbour.getKey());

                indexedMinEdgePQ.insert(neighbourIndex, new SingleNodeEdgeWeight<>(edgeWeight,
                        this.nodes.get(startingKey)));
            }

            //creating edges now

            while (visited.size() != this.nodes.size()) {

                int indexOfCurrentMinEdge = indexedMinEdgePQ.minIndex();

                //checking if the shortest edge has nodes already present in mst
                if (visited.contains(indexOfCurrentMinEdge)) {
                    indexedMinEdgePQ.delMin();
                    continue;
                }

                SingleNodeEdgeWeight<EdgeWeight, NodeValue, NodeKeyValue>
                        currMinEdge = indexedMinEdgePQ.minKey();

                if (visited.contains(nodeIndexMap.get(currMinEdge.neighbour.getKey()))) {
                    System.out.println("hello");
                }
                goodEdges.add(edgeCreator.createEdge(
                        currMinEdge.neighbour, indexNodeMap.get(indexOfCurrentMinEdge), currMinEdge.edgeWeight));

                indexedMinEdgePQ.delMin();

                visited.add(indexOfCurrentMinEdge);

                startingKey = indexNodeMap.get(indexOfCurrentMinEdge).getKey();

                for (Edge<Node<NodeValue, NodeKeyValue>, EdgeWeight> edge : this.neighbourMap.get(startingKey)) {

                    Node<NodeValue, NodeKeyValue> neighbour = edge.getSecondNode();

                    int neighbourIndex = nodeIndexMap.get(neighbour.getKey());

                    EdgeWeight edgeWeight = edge.getEdgeWeight();

                    if (visited.contains(neighbourIndex)) {
                        continue;
                    }

                    if (indexedMinEdgePQ.contains(neighbourIndex)) {
                        if (indexedMinEdgePQ.keyOf(neighbourIndex)
                                .edgeWeight.compareTo(edgeWeight) > 0) {
                            indexedMinEdgePQ.changeKey(neighbourIndex, new SingleNodeEdgeWeight<>(edgeWeight,
                                    this.nodes.get(startingKey)));
                        }
                    } else {
                        indexedMinEdgePQ.insert(neighbourIndex, new SingleNodeEdgeWeight<>(edgeWeight,
                                this.nodes.get(startingKey)));
                    }
                }
            }



            return null;
        } catch (Exception e) {
            System.out.println("error while creating MST from Prim's e" + e);
            throw e;
        }


    }

    static class SingleNodeEdgeWeight<EdgeWeight extends Comparable<EdgeWeight>, NodeValue, NodeKeyValue>
            implements Comparable<SingleNodeEdgeWeight<EdgeWeight, NodeValue, NodeKeyValue>> {

        EdgeWeight edgeWeight;

        Node<NodeValue, NodeKeyValue> neighbour;

        public SingleNodeEdgeWeight(EdgeWeight edgeWeight, Node<NodeValue, NodeKeyValue> neighbour) {
            this.edgeWeight = edgeWeight;
            this.neighbour = neighbour;
        }

        @Override
        public int compareTo(SingleNodeEdgeWeight o) {
            return this.edgeWeight.compareTo((EdgeWeight) o.edgeWeight);
        }
    }

}
