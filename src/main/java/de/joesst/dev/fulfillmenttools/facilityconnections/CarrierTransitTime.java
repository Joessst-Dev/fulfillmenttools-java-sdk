package de.joesst.dev.fulfillmenttools.facilityconnections;

/**
 * Represents the transit time range advertised by a carrier for a given connection or
 * packaging unit.
 *
 * @param minTransitDays minimum number of transit days
 * @param maxTransitDays maximum number of transit days
 */
public record CarrierTransitTime(Integer minTransitDays, Integer maxTransitDays) {}
