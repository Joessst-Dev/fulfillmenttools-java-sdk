package de.joesst.dev.fulfillmenttools.facilities;

/**
 * Represents a fulfillment service offered by a facility.
 *
 * @param type service type identifier (e.g. "PICKING", "PACKING", "STORAGE")
 */
public record FacilityService(String type) {}
