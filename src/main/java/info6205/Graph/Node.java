package info6205.Graph;


import java.util.List;

public interface Node<T, G> {

    Key<G> getKey();

    T getValue();

    //List<Node<T, G>> getNeighbours();

    //Node<T, G> getNeighbour(Key<G> key);

    //boolean addNeighbour(Node<T, G> neighbour);
}
