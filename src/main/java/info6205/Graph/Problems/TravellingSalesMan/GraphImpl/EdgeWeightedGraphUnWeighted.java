package info6205.Graph.Problems.TravellingSalesMan.GraphImpl;


import info6205.Graph.UnWeightedAdjacencyListGraph;
import info6205.Graph.Node;
import info6205.Graph.Key;

import java.util.*;

public class EdgeWeightedGraphUnWeighted {//implements UnWeightedAdjacencyListGraph<String, LatLongId> {

    private final List<Node<String, LatLongId>> nodes;
    private final Map<Key<LatLongId>, Node<String, LatLongId>> nodesSet;

    //private final Map<Key<LatLongId>, Map<Key<LatLongId>>> edge;


    public EdgeWeightedGraphUnWeighted(List<Node<String, LatLongId>> nodes, Map<Key<LatLongId>, Node<String, LatLongId>> nodesSet) {
        this.nodes = nodes;
        this.nodesSet = nodesSet;
    }

    //@Override
    public int getSize() {
        return nodes.size();
    }

    //@Override
    public Node<String, LatLongId> hasNode(Key<LatLongId> key) {
        return nodesSet.get(key);
    }

    //@Override
    public List<Node<String, LatLongId>> getNodes() {
        return this.nodes;
    }


    /*@Override
    public boolean addNode(Node<String, LatLongId> node) {
        try {
            nodes.add(node);
            nodesSet.put(node.getKey(), node);
            return true;
        } catch (Exception e) {
            System.out.println("Exception while saving a Node : " + e);
            return false;
        }
    }

     */

    public boolean addEdge(Node<String, LatLongId> node1, Node<String, LatLongId> node2) {
        try {
            //now for directed only called on one
            //for un directed called on both

            if (!this.nodesSet.containsKey(node1.getKey()) || !this.nodesSet.containsKey(node2.getKey())) {
                throw new RuntimeException("node does not exist");
            }
            //node1.addNeighbour(node2);
            //node2.addNeighbour(node1);

            return true;
        } catch (Exception e) {
            //System.out.println("Exception while adding neighbour UnWeightedAdjacencyListGraph : " + e);
            return false;
        }
    }

}
