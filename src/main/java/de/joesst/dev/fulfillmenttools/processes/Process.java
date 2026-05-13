package de.joesst.dev.fulfillmenttools.processes;

import de.joesst.dev.fulfillmenttools.id.ExternalActionId;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.HandoverJobId;
import de.joesst.dev.fulfillmenttools.id.OrderId;
import de.joesst.dev.fulfillmenttools.id.PackJobId;
import de.joesst.dev.fulfillmenttools.id.PickJobId;
import de.joesst.dev.fulfillmenttools.id.ProcessId;
import de.joesst.dev.fulfillmenttools.id.ReturnId;
import de.joesst.dev.fulfillmenttools.id.RoutingPlanId;
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
 *
 * @param id the platform-generated {@link ProcessId}
 * @param version the optimistic-locking version
 * @param created the timestamp when this process was created
 * @param lastModified the timestamp of the last modification
 * @param tenantOrderId the tenant's external order reference number
 * @param orderRef the {@link OrderId} reference to the order associated with this process
 * @param status the overall process status
 * @param domsStatus the DOMS (fulfillmenttools domain order management system) status
 * @param operativeStatus the operative execution status
 * @param inventoryStatus the inventory allocation status
 * @param returnStatus the return fulfillment status
 * @param isAnonymized whether this process has been anonymized for GDPR compliance
 * @param gdprCleanupDate the date when GDPR cleanup was performed, if applicable
 * @param deletionDate the date when this process was marked for deletion, if applicable
 * @param facilityRefs {@link FacilityId} references to all facilities involved in fulfilling this process
 * @param pickJobRefs {@link PickJobId} references to all picking jobs associated with this process
 * @param packJobRefs {@link PackJobId} references to all packing jobs associated with this process
 * @param handoverJobRefs {@link HandoverJobId} references to all handover jobs associated with this process
 * @param shipmentRefs references to all shipments associated with this process
 * @param returnRefs {@link ReturnId} references to all returns associated with this process
 * @param routingPlanRefs {@link RoutingPlanId} references to all routing plans used for this process
 * @param serviceJobRefs references to all service jobs associated with this process
 * @param itemReturnJobsRef references to all item return jobs associated with this process
 * @param externalActionRefs {@link ExternalActionId} references to all external actions associated with this process
 * @param documentRefs references to all documents associated with this process
 * @param flatRefs references to flat entities associated with this process
 * @param domainStatuses a map of domain type to its current status
 * @param domainStatusHistory a list of all domain status changes for this process
 * @param lastDomainEntityStatuses a list of the last status for each domain entity type
 * @param tags tags attached to this process
 */
public record Process(
        ProcessId id,
        Integer version,
        Instant created,
        Instant lastModified,
        String tenantOrderId,
        OrderId orderRef,
        String status,
        String domsStatus,
        String operativeStatus,
        String inventoryStatus,
        String returnStatus,
        Boolean isAnonymized,
        Instant gdprCleanupDate,
        Instant deletionDate,
        List<FacilityId> facilityRefs,
        List<PickJobId> pickJobRefs,
        List<PackJobId> packJobRefs,
        List<HandoverJobId> handoverJobRefs,
        List<String> shipmentRefs,
        List<ReturnId> returnRefs,
        List<RoutingPlanId> routingPlanRefs,
        List<String> serviceJobRefs,
        List<String> itemReturnJobsRef,
        List<ExternalActionId> externalActionRefs,
        List<String> documentRefs,
        List<String> flatRefs,
        Map<String, DomainStatus> domainStatuses,
        List<DomainStatusHistoryItem> domainStatusHistory,
        List<LastDomainEntityStatusItem> lastDomainEntityStatuses,
        List<TagReference> tags
) {}
