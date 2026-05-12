package de.joesst.dev.fulfillmenttools.facilities;

import java.util.List;

public record PickingTimes(
        List<TimeRange> monday,
        List<TimeRange> tuesday,
        List<TimeRange> wednesday,
        List<TimeRange> thursday,
        List<TimeRange> friday,
        List<TimeRange> saturday,
        List<TimeRange> sunday
) {}
