package de.joesst.dev.fulfillmenttools.facilityconnections;

import java.util.List;

/**
 * Complete cutoff schedule for a facility connection, combining regular weekday patterns
 * with date-specific overrides.
 *
 * @param weekdays  per-weekday cutoff configurations
 * @param overwrites date-specific overrides that take precedence over the weekday schedule
 */
public record CutoffTimes(List<CutoffTimesWeekDay> weekdays, List<CutoffTimesOverwrite> overwrites) {}
