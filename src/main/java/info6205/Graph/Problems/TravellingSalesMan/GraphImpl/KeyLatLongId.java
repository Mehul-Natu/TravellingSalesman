package info6205.Graph.Problems.TravellingSalesMan.GraphImpl;

import java.util.Objects;

public class KeyLatLongId implements info6205.Graph.Key<LatLongId> {

    private final LatLongId latLongId;

    public KeyLatLongId(LatLongId latLongId) {
        this.latLongId = latLongId;
    }

    @Override
    public LatLongId getValue() {
        return latLongId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KeyLatLongId that)) return false;
        return latLongId.equals(that.latLongId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latLongId);
    }
}
