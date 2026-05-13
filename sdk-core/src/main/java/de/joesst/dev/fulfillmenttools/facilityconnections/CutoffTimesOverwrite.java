package de.joesst.dev.fulfillmenttools.facilityconnections;

import java.time.LocalDate;
import java.util.List;

/**
 * Overrides the regular cutoff schedule for a specific calendar date.
 *
 * @param date                 the calendar date (YYYY-MM-DD) for which the override applies
 * @param cutoffConfigurations cutoff points to use on this date instead of the regular schedule
 */
public record CutoffTimesOverwrite(LocalDate date, List<CutoffConfiguration> cutoffConfigurations) {}
