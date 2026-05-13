package de.joesst.dev.fulfillmenttools.checkoutoptions;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A geographic fence used to restrict checkout option results to facilities within a
 * circular area.
 *
 * <p>Maps to the {@code GeoFence} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param lat    Latitude of the center point.
 * @param lon    Longitude of the center point.
 * @param radius Search radius in kilometres. Must be at least 1 and at most 100.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GeoFence(double lat, double lon, double radius) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private double lat;
        private double lon;
        private double radius;

        public Builder lat(double lat) { this.lat = lat; return this; }
        public Builder lon(double lon) { this.lon = lon; return this; }
        public Builder radius(double radius) { this.radius = radius; return this; }

        public GeoFence build() {
            return new GeoFence(lat, lon, radius);
        }
    }
}
