package info6205.Graph.Problems.TravellingSalesMan.GraphImpl;

public class Key implements info6205.Graph.Key<LatLongId> {

    private final LatLongId latLongId;

    public Key(LatLongId latLongId) {
        this.latLongId = latLongId;
    }

    @Override
    public LatLongId getValue() {
        return latLongId;
    }
}
