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
public record GeoFence(double lat, double lon, double radius) {}
