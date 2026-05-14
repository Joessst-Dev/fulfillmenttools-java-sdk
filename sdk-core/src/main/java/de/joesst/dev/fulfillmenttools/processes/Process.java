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
import de.joesst.dev.fulfillmenttools.id.TenantOrderId;
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
 * @param tenantOrderId the tenant's external {@link TenantOrderId} order reference number
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
        TenantOrderId tenantOrderId,
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
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private ProcessId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private TenantOrderId tenantOrderId;
        private OrderId orderRef;
        private String status;
        private String domsStatus;
        private String operativeStatus;
        private String inventoryStatus;
        private String returnStatus;
        private Boolean isAnonymized;
        private Instant gdprCleanupDate;
        private Instant deletionDate;
        private List<FacilityId> facilityRefs;
        private List<PickJobId> pickJobRefs;
        private List<PackJobId> packJobRefs;
        private List<HandoverJobId> handoverJobRefs;
        private List<String> shipmentRefs;
        private List<ReturnId> returnRefs;
        private List<RoutingPlanId> routingPlanRefs;
        private List<String> serviceJobRefs;
        private List<String> itemReturnJobsRef;
        private List<ExternalActionId> externalActionRefs;
        private List<String> documentRefs;
        private List<String> flatRefs;
        private Map<String, DomainStatus> domainStatuses;
        private List<DomainStatusHistoryItem> domainStatusHistory;
        private List<LastDomainEntityStatusItem> lastDomainEntityStatuses;
        private List<TagReference> tags;

        public Builder id(ProcessId id) {
            this.id = id;
            return this;
        }

        public Builder version(Integer version) {
            this.version = version;
            return this;
        }

        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        public Builder lastModified(Instant lastModified) {
            this.lastModified = lastModified;
            return this;
        }

        public Builder tenantOrderId(TenantOrderId tenantOrderId) {
            this.tenantOrderId = tenantOrderId;
            return this;
        }

        public Builder orderRef(OrderId orderRef) {
            this.orderRef = orderRef;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder domsStatus(String domsStatus) {
            this.domsStatus = domsStatus;
            return this;
        }

        public Builder operativeStatus(String operativeStatus) {
            this.operativeStatus = operativeStatus;
            return this;
        }

        public Builder inventoryStatus(String inventoryStatus) {
            this.inventoryStatus = inventoryStatus;
            return this;
        }

        public Builder returnStatus(String returnStatus) {
            this.returnStatus = returnStatus;
            return this;
        }

        public Builder isAnonymized(Boolean isAnonymized) {
            this.isAnonymized = isAnonymized;
            return this;
        }

        public Builder gdprCleanupDate(Instant gdprCleanupDate) {
            this.gdprCleanupDate = gdprCleanupDate;
            return this;
        }

        public Builder deletionDate(Instant deletionDate) {
            this.deletionDate = deletionDate;
            return this;
        }

        public Builder facilityRefs(List<FacilityId> facilityRefs) {
            this.facilityRefs = facilityRefs;
            return this;
        }

        public Builder pickJobRefs(List<PickJobId> pickJobRefs) {
            this.pickJobRefs = pickJobRefs;
            return this;
        }

        public Builder packJobRefs(List<PackJobId> packJobRefs) {
            this.packJobRefs = packJobRefs;
            return this;
        }

        public Builder handoverJobRefs(List<HandoverJobId> handoverJobRefs) {
            this.handoverJobRefs = handoverJobRefs;
            return this;
        }

        public Builder shipmentRefs(List<String> shipmentRefs) {
            this.shipmentRefs = shipmentRefs;
            return this;
        }

        public Builder returnRefs(List<ReturnId> returnRefs) {
            this.returnRefs = returnRefs;
            return this;
        }

        public Builder routingPlanRefs(List<RoutingPlanId> routingPlanRefs) {
            this.routingPlanRefs = routingPlanRefs;
            return this;
        }

        public Builder serviceJobRefs(List<String> serviceJobRefs) {
            this.serviceJobRefs = serviceJobRefs;
            return this;
        }

        public Builder itemReturnJobsRef(List<String> itemReturnJobsRef) {
            this.itemReturnJobsRef = itemReturnJobsRef;
            return this;
        }

        public Builder externalActionRefs(List<ExternalActionId> externalActionRefs) {
            this.externalActionRefs = externalActionRefs;
            return this;
        }

        public Builder documentRefs(List<String> documentRefs) {
            this.documentRefs = documentRefs;
            return this;
        }

        public Builder flatRefs(List<String> flatRefs) {
            this.flatRefs = flatRefs;
            return this;
        }

        public Builder domainStatuses(Map<String, DomainStatus> domainStatuses) {
            this.domainStatuses = domainStatuses;
            return this;
        }

        public Builder domainStatusHistory(List<DomainStatusHistoryItem> domainStatusHistory) {
            this.domainStatusHistory = domainStatusHistory;
            return this;
        }

        public Builder lastDomainEntityStatuses(List<LastDomainEntityStatusItem> lastDomainEntityStatuses) {
            this.lastDomainEntityStatuses = lastDomainEntityStatuses;
            return this;
        }

        public Builder tags(List<TagReference> tags) {
            this.tags = tags;
            return this;
        }

        public Process build() {
            return new Process(id, version, created, lastModified, tenantOrderId, orderRef, status,
                    domsStatus, operativeStatus, inventoryStatus, returnStatus, isAnonymized,
                    gdprCleanupDate, deletionDate, facilityRefs, pickJobRefs, packJobRefs,
                    handoverJobRefs, shipmentRefs, returnRefs, routingPlanRefs, serviceJobRefs,
                    itemReturnJobsRef, externalActionRefs, documentRefs, flatRefs, domainStatuses,
                    domainStatusHistory, lastDomainEntityStatuses, tags);
        }
    }
}
