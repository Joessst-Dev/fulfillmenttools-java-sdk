package de.joesst.dev.fulfillmenttools.processes;

import java.time.Instant;
import java.util.List;

/**
 * Represents an operative process in fulfillmenttools, aggregating all jobs
 * and references for a single order fulfilment lifecycle.
 */
public record Process(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String tenantOrderId,
        String orderRef,
        String status,
        String domsStatus,
        String operativeStatus,
        String inventoryStatus,
        String returnStatus,
        List<String> facilityRefs,
        List<String> pickJobRefs,
        List<String> packJobRefs,
        List<String> handoverJobRefs,
        List<String> shipmentRefs,
        List<String> returnRefs,
        List<String> routingPlanRefs,
        Boolean isAnonymized,
        Instant gdprCleanupDate
) {}
