package de.joesst.dev.fulfillmenttools.facilities;

/**
 * A time interval with an optional capacity limit.
 *
 * @param start start time of the range
 * @param end end time of the range
 * @param capacity optional capacity limit or utilization for this time range
 */
public record TimeRange(TimeStamp start, TimeStamp end, Double capacity) {}
