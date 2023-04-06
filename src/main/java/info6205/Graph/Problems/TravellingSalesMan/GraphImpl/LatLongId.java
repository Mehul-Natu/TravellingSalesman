package info6205.Graph.Problems.TravellingSalesMan.GraphImpl;

import java.util.Objects;

public class LatLongId {

    private final Double latitude;

    private final Double longitude;

    private final String id;

    public LatLongId(Double latitude, Double longitude, String id) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }


    @Override
    public String toString() {
        return "LatLongId {" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LatLongId latLongId)) return false;
        return getLatitude().equals(latLongId.getLatitude()) && getLongitude().equals(latLongId.getLongitude()) && id.equals(latLongId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLatitude(), getLongitude(), id);
    }
}
