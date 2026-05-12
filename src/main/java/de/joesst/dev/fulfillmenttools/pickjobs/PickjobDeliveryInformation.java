package de.joesst.dev.fulfillmenttools.pickjobs;

import java.time.Instant;
import java.util.Map;

public record PickjobDeliveryInformation(
        String channel,
        Instant targetTime,
        Instant targetTimeBaseDate,
        Map<String, Object> details
) {}
