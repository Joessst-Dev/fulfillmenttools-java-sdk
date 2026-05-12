package de.joesst.dev.fulfillmenttools.processes;

import java.time.Instant;
import java.util.List;
import java.util.Map;

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
        Boolean isAnonymized,
        Instant gdprCleanupDate,
        Instant deletionDate,
        List<String> facilityRefs,
        List<String> pickJobRefs,
        List<String> packJobRefs,
        List<String> handoverJobRefs,
        List<String> shipmentRefs,
        List<String> returnRefs,
        List<String> routingPlanRefs,
        List<String> serviceJobRefs,
        List<String> itemReturnJobsRef,
        List<String> externalActionRefs,
        List<String> documentRefs,
        List<String> flatRefs,
        Map<String, Object> domainStatuses,
        List<Map<String, Object>> domainStatusHistory,
        List<Map<String, Object>> lastDomainEntityStatuses,
        List<Map<String, Object>> tags
) {}
