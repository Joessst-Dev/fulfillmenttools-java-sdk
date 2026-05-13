package de.joesst.dev.fulfillmenttools.facilities;

/**
 * A time of day represented as hour and minute components.
 *
 * @param hour hour value (0-23)
 * @param minute minute value (0-59)
 */
public record TimeStamp(Integer hour, Integer minute) {}
