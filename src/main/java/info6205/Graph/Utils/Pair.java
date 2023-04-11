package info6205.Graph.Utils;

import java.util.Objects;

public class Pair<X, Y> {

    private X first;
    private Y second;

    public Pair(X first, Y second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair<?, ?> pair)) return false;
        return first.equals(pair.first) && second.equals(pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    public X getFirst() {
        return first;
    }

    public Y getSecond() {
        return second;
    }
}
