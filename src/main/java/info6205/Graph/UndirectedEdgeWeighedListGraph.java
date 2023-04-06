package info6205.Graph;


public class UndirectedEdgeWeighedListGraph<NodeValue, NodeKeyValue, EdgeWeight> extends EdgeWeightedListGraph<NodeValue, NodeKeyValue, EdgeWeight> {


    public UndirectedEdgeWeighedListGraph() {
        super();
    }

    //how to stop this method being called if weighted Edge is used
    @Override
    protected boolean addNeighbour(Node<NodeValue, NodeKeyValue> nodeValue1, Node<NodeValue, NodeKeyValue> nodeValue2) {
        try {
            if (!nodes.containsKey(nodeValue1.getKey())) {
                addNode(nodeValue1);
            }

            if (!nodes.containsKey(nodeValue2.getKey())) {
                addNode(nodeValue2);
            }

            neighbourMap.get(nodeValue1.getKey()).add(nodeValue2);
            neighbourMap.get(nodeValue2.getKey()).add(nodeValue1);
            return true;

        } catch (Exception e) {
            System.out.println("Error while adding neighbour e: " + e);
            return false;
        }
    }

    @Override
    public boolean addEdge(Node<NodeValue, NodeKeyValue> node1, Node<NodeValue, NodeKeyValue> node2, EdgeWeight edgeWeight) {
        try {
            addNeighbour(node1, node2);


        } catch (Exception e) {
            System.out.println("Error while adding edge e: " + e);
            return false;
        }
        return false;
    }



}
