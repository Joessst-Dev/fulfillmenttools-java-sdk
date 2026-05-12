package de.joesst.dev.fulfillmenttools.facilityconnections;

import java.util.List;

/**
 * Associates a day of the week with its list of cutoff configurations.
 *
 * @param day                  the day of the week
 * @param cutoffConfigurations ordered list of cutoff points for this day
 */
public record CutoffTimesWeekDay(WeekDay day, List<CutoffConfiguration> cutoffConfigurations) {}
