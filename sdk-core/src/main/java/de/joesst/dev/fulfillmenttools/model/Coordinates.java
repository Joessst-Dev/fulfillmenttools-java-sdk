package de.joesst.dev.fulfillmenttools.model;

/**
 * Geographic coordinates used to locate a facility.
 *
 * @param lat the latitude coordinate in degrees.
 * @param lon the longitude coordinate in degrees.
 */
public record Coordinates(double lat, double lon) {}
