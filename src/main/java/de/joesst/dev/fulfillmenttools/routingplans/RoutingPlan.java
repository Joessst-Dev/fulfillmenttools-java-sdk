package de.joesst.dev.fulfillmenttools.routingplans;

import java.time.Instant;
import java.util.Map;

/**
 * Represents a routing plan in fulfillmenttools.
 */
public record RoutingPlan(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String facilityRef,
        String orderRef,
        String tenantOrderId,
        String processId,
        String status,
        Instant orderDate,
        Integer priority,
        Integer routingRun,
        String runId,
        String runType,
        Map<String, Object> customAttributes,
        Boolean anonymized
) {}
