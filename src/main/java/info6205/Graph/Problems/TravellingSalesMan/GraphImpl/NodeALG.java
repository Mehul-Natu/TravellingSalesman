package info6205.Graph.Problems.TravellingSalesMan.GraphImpl;

import info6205.Graph.Node;
import java.util.*;
import info6205.Graph.Key;




public class NodeALG implements Node<String, LatLongId> {

    private final Key<LatLongId> key;
    private final String value;
    //private final List<Node<String, LatLongId>> neighbours;
    //private final Map<Node<String, LatLongId>, Double> neighbourWeight;




    public NodeALG(Key<LatLongId> key, String value) {
        this.key = key;
        this.value = value;
        //this.neighbours = new LinkedList<>();
        //this.neighbourWeight = new HashMap<>();
    }

    @Override
    public Key<LatLongId> getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }

    /*
    @Override
    public List<Node<String, LatLongId>> getNeighbours() {
        return neighbours;
    }

    @Override
    public Node<String, LatLongId> getNeighbour(final Key<LatLongId> key) {
        return neighbours.stream().filter(p -> key.equals(p.getKey())).findFirst().orElse(null);
    }

    @Override
    public boolean addNeighbour(Node<String, LatLongId> neighbour) {
        try {
            neighbours.add(neighbour);
            neighbourWeight.put(neighbour, getDistance(this, neighbour));
            return true;
        } catch (Exception e) {
            System.out.println("exception while adding neighbour");
            throw e;
        }
    }

    public boolean addNeighbour(Node<String, LatLongId> neighbour, Double weight) {
        try {
            neighbours.add(neighbour);
            neighbourWeight.put(neighbour, weight);
            return true;
        } catch (Exception e) {
            System.out.println("exception while adding neighbour");
            throw e;
        }
    }

    public Map<Node<String, LatLongId>, Double> getNeighbourWeight() {
        return neighbourWeight;
    }

    private Double getDistance(Node<String, LatLongId> node1, Node<String, LatLongId> node2) {
        return Math.sqrt(Math.pow(node1.getKey().getValue().getLatitude() - node2.getKey().getValue().getLatitude(), 2)
                + Math.pow(node1.getKey().getValue().getLongitude() - node2.getKey().getValue().getLongitude(), 2));
    }
     */

    @Override
    public String toString() {
        return "NodeALG {" +
                "key=" + key.getValue() +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeALG)) return false;
        NodeALG nodeALG = (NodeALG) o;
        return getKey().equals(nodeALG.getKey()) && getValue().equals(nodeALG.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getValue());
    }
}
