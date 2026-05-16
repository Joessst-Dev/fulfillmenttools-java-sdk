package de.joesst.dev.fulfillmenttools.itemreturnjobs;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents a single physical item return within an item return job.
 *
 * @param id the platform-generated identifier for this item return
 * @param status the current lifecycle status
 * @param returnFacilityRef reference to the facility handling this return
 * @param returnedLineItems the line items that were physically returned
 * @param created the timestamp when this item return was created
 * @param lastModified the timestamp of the last modification
 * @param parcelRefs references to parcels associated with this return
 * @param scannableCodes scannable codes associated with this return
 * @param tenantOrderId the tenant's external order reference
 * @param customAttributes tenant-defined key/value extension attributes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ItemReturn(
        String id,
        ItemReturnStatus status,
        String returnFacilityRef,
        List<Map<String, Object>> returnedLineItems,
        Instant created,
        Instant lastModified,
        List<String> parcelRefs,
        List<String> scannableCodes,
        String tenantOrderId,
        Map<String, Object> customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String id;
        private ItemReturnStatus status;
        private String returnFacilityRef;
        private List<Map<String, Object>> returnedLineItems;
        private Instant created;
        private Instant lastModified;
        private List<String> parcelRefs;
        private List<String> scannableCodes;
        private String tenantOrderId;
        private Map<String, Object> customAttributes;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder status(ItemReturnStatus status) {
            this.status = status;
            return this;
        }

        public Builder returnFacilityRef(String returnFacilityRef) {
            this.returnFacilityRef = returnFacilityRef;
            return this;
        }

        public Builder returnedLineItems(List<Map<String, Object>> returnedLineItems) {
            this.returnedLineItems = returnedLineItems;
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

        public Builder parcelRefs(List<String> parcelRefs) {
            this.parcelRefs = parcelRefs;
            return this;
        }

        public Builder scannableCodes(List<String> scannableCodes) {
            this.scannableCodes = scannableCodes;
            return this;
        }

        public Builder tenantOrderId(String tenantOrderId) {
            this.tenantOrderId = tenantOrderId;
            return this;
        }

        public Builder customAttributes(Map<String, Object> customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        public ItemReturn build() {
            return new ItemReturn(id, status, returnFacilityRef, returnedLineItems,
                    created, lastModified, parcelRefs, scannableCodes, tenantOrderId, customAttributes);
        }
    }
}
