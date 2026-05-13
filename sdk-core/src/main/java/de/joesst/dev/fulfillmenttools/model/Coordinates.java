package de.joesst.dev.fulfillmenttools.model;

/**
 * Geographic coordinates used to locate a facility.
 *
 * @param lat the latitude coordinate in degrees.
 * @param lon the longitude coordinate in degrees.
 */
public record Coordinates(double lat, double lon) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private double lat;
        private double lon;

        public Builder lat(double lat) { this.lat = lat; return this; }
        public Builder lon(double lon) { this.lon = lon; return this; }

        public Coordinates build() {
            return new Coordinates(lat, lon);
        }
    }
}
