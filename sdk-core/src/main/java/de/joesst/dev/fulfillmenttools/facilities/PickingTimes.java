package de.joesst.dev.fulfillmenttools.facilities;

import java.util.List;

/**
 * Operating hours for facility picking operations, broken down by day of the week.
 *
 * @param monday list of time ranges for Monday operations
 * @param tuesday list of time ranges for Tuesday operations
 * @param wednesday list of time ranges for Wednesday operations
 * @param thursday list of time ranges for Thursday operations
 * @param friday list of time ranges for Friday operations
 * @param saturday list of time ranges for Saturday operations
 * @param sunday list of time ranges for Sunday operations
 */
public record PickingTimes(
        List<TimeRange> monday,
        List<TimeRange> tuesday,
        List<TimeRange> wednesday,
        List<TimeRange> thursday,
        List<TimeRange> friday,
        List<TimeRange> saturday,
        List<TimeRange> sunday
) {}
