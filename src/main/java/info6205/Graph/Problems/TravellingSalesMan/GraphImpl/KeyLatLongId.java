package info6205.Graph.Problems.TravellingSalesMan.GraphImpl;

public class KeyLatLongId implements info6205.Graph.Key<LatLongId> {

    private final LatLongId latLongId;

    public KeyLatLongId(LatLongId latLongId) {
        this.latLongId = latLongId;
    }

    @Override
    public LatLongId getValue() {
        return latLongId;
    }
}
