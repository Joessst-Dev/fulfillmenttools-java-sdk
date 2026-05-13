package de.joesst.dev.fulfillmenttools.processes;

import de.joesst.dev.fulfillmenttools.model.TagReference;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents a fulfillmenttools operative process, which tracks the lifecycle of an order
 * across all domain entities (pick jobs, pack jobs, shipments, etc.).
 *
 * <p>This record is a read-only projection of the API response. All collection fields may be
 * {@code null} when absent in the response JSON.
 *
 * <p>Not thread-safe (record is immutable by construction; individual collections are not
 * defensively copied).
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
        Map<String, DomainStatus> domainStatuses,
        List<DomainStatusHistoryItem> domainStatusHistory,
        List<LastDomainEntityStatusItem> lastDomainEntityStatuses,
        List<TagReference> tags
) {}
