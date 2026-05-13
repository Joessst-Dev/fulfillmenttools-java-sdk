package de.joesst.dev.fulfillmenttools.returns;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.TenantOrderId;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents a single item return in the fulfillmenttools system.
 *
 * @param id the platform-generated item return identifier
 * @param created the timestamp when this item return was created
 * @param lastModified the timestamp of the last modification
 * @param status the current item return status
 * @param returnFacilityRef the reference to the facility where this item should be returned
 * @param tenantOrderId the tenant's external {@link TenantOrderId} order reference number
 * @param scannableCodes scannable codes (e.g., barcodes) for this item return
 * @param parcelRefs references to the parcels involved in this item return
 * @param returnedLineItems the line items being returned
 * @param customAttributes free-form custom attributes
 */
public record ReturnItem(
        String id,
        Instant created,
        Instant lastModified,
        String status,
        FacilityId returnFacilityRef,
        TenantOrderId tenantOrderId,
        List<String> scannableCodes,
        List<String> parcelRefs,
        List<ReturnItemLineItem> returnedLineItems,
        Map<String, Object> customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private Instant created;
        private Instant lastModified;
        private String status;
        private FacilityId returnFacilityRef;
        private TenantOrderId tenantOrderId;
        private List<String> scannableCodes;
        private List<String> parcelRefs;
        private List<ReturnItemLineItem> returnedLineItems;
        private Map<String, Object> customAttributes;

        private Builder() {}

        public Builder id(String id) { this.id = id; return this; }
        public Builder created(Instant created) { this.created = created; return this; }
        public Builder lastModified(Instant lastModified) { this.lastModified = lastModified; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder returnFacilityRef(FacilityId returnFacilityRef) { this.returnFacilityRef = returnFacilityRef; return this; }
        public Builder tenantOrderId(TenantOrderId tenantOrderId) { this.tenantOrderId = tenantOrderId; return this; }
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }
        public Builder parcelRefs(List<String> parcelRefs) { this.parcelRefs = parcelRefs; return this; }
        public Builder returnedLineItems(List<ReturnItemLineItem> returnedLineItems) { this.returnedLineItems = returnedLineItems; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public ReturnItem build() {
            return new ReturnItem(id, created, lastModified, status, returnFacilityRef, tenantOrderId, scannableCodes, parcelRefs, returnedLineItems, customAttributes);
        }
    }
}
