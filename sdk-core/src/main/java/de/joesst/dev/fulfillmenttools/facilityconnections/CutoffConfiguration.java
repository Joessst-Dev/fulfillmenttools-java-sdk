package de.joesst.dev.fulfillmenttools.facilityconnections;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A single cutoff point within a day, optionally bounded by a capacity limit.
 *
 * @param time     cutoff time in HH:mm format
 * @param capacity optional maximum order capacity for this cutoff window; {@code null} means unlimited
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CutoffConfiguration(String time, Double capacity) {}
