package de.joesst.dev.fulfillmenttools.returns;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.ProcessId;
import de.joesst.dev.fulfillmenttools.id.ReturnId;
import de.joesst.dev.fulfillmenttools.id.TenantOrderId;
import de.joesst.dev.fulfillmenttools.orders.ConsumerAddress;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

import java.time.Instant;
import java.util.List;

/**
 * Represents a return in the fulfillmenttools system.
 *
 * @param id the platform-generated return identifier
 * @param version the optimistic-locking version
 * @param created the timestamp when this return was created
 * @param lastModified the timestamp of the last modification
 * @param status the current return status
 * @param shortId a short human-readable identifier for this return
 * @param processRef the reference to the process associated with this return
 * @param tenantOrderId the tenant's external {@link TenantOrderId} order reference number
 * @param originFacilityRefs references to the origin facilities for this return
 * @param scannableCodes scannable codes (e.g., barcodes) for this return
 * @param consumerAddresses addresses associated with this return
 * @param returnableLineItems line items that can be returned
 * @param notReturnableLineItems line items that cannot be returned
 * @param itemReturns the individual item returns for this return
 * @param anonymized whether this return has been anonymized for GDPR compliance
 * @param customAttributes free-form custom attributes
 */
public record Return(
        ReturnId id,
        Integer version,
        Instant created,
        Instant lastModified,
        String status,
        String shortId,
        ProcessId processRef,
        TenantOrderId tenantOrderId,
        List<FacilityId> originFacilityRefs,
        List<String> scannableCodes,
        List<ConsumerAddress> consumerAddresses,
        List<ReturnJobLineItem> returnableLineItems,
        List<ReturnJobLineItem> notReturnableLineItems,
        List<ReturnItem> itemReturns,
        Boolean anonymized,
        CustomAttributes customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private ReturnId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private String status;
        private String shortId;
        private ProcessId processRef;
        private TenantOrderId tenantOrderId;
        private List<FacilityId> originFacilityRefs;
        private List<String> scannableCodes;
        private List<ConsumerAddress> consumerAddresses;
        private List<ReturnJobLineItem> returnableLineItems;
        private List<ReturnJobLineItem> notReturnableLineItems;
        private List<ReturnItem> itemReturns;
        private Boolean anonymized;
        private CustomAttributes customAttributes;

        private Builder() {}

        public Builder id(ReturnId id) { this.id = id; return this; }
        public Builder version(Integer version) { this.version = version; return this; }
        public Builder created(Instant created) { this.created = created; return this; }
        public Builder lastModified(Instant lastModified) { this.lastModified = lastModified; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder shortId(String shortId) { this.shortId = shortId; return this; }
        public Builder processRef(ProcessId processRef) { this.processRef = processRef; return this; }
        public Builder tenantOrderId(TenantOrderId tenantOrderId) { this.tenantOrderId = tenantOrderId; return this; }
        public Builder originFacilityRefs(List<FacilityId> originFacilityRefs) { this.originFacilityRefs = originFacilityRefs; return this; }
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }
        public Builder consumerAddresses(List<ConsumerAddress> consumerAddresses) { this.consumerAddresses = consumerAddresses; return this; }
        public Builder returnableLineItems(List<ReturnJobLineItem> returnableLineItems) { this.returnableLineItems = returnableLineItems; return this; }
        public Builder notReturnableLineItems(List<ReturnJobLineItem> notReturnableLineItems) { this.notReturnableLineItems = notReturnableLineItems; return this; }
        public Builder itemReturns(List<ReturnItem> itemReturns) { this.itemReturns = itemReturns; return this; }
        public Builder anonymized(Boolean anonymized) { this.anonymized = anonymized; return this; }
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        public Return build() {
            return new Return(id, version, created, lastModified, status, shortId, processRef, tenantOrderId, originFacilityRefs, scannableCodes, consumerAddresses, returnableLineItems, notReturnableLineItems, itemReturns, anonymized, customAttributes);
        }
    }
}
