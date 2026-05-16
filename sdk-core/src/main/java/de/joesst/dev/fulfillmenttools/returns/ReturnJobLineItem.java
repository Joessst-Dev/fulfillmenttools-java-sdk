package de.joesst.dev.fulfillmenttools.returns;

import de.joesst.dev.fulfillmenttools.pickjobs.RecordableAttribute;

import java.util.List;
import java.util.Map;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * A line item on a return job, describing an article that is or is not returnable.
 *
 * <p>Maps to the {@code ItemReturnJobLineItem} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                   Unique identifier of this line item. Required.
 * @param status               Processing status of this line item.
 * @param globalLineItemId     Cross-entity identifier linking this line item to related
 *                             operational entities.
 * @param delivered            Quantity of this line item that was delivered. Required.
 * @param returned             Quantity of this line item that has been returned. Required.
 * @param returnable           Quantity of this line item that can still be returned. Required.
 * @param article              The article associated with this line item. Required.
 * @param scannableCodes       Codes that can be used for scanning this line item.
 * @param serviceJobRefs       References to service jobs that altered this line item.
 * @param recordableAttributes Attributes whose values can be recorded during the return process.
 * @param customAttributes     Free-form custom attributes.
 */
public record ReturnJobLineItem(
        String id,
        String status,
        String globalLineItemId,
        Double delivered,
        Double returned,
        Double returnable,
        ReturnJobLineItemArticle article,
        List<String> scannableCodes,
        List<String> serviceJobRefs,
        List<RecordableAttribute> recordableAttributes,
        CustomAttributes customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String status;
        private String globalLineItemId;
        private Double delivered;
        private Double returned;
        private Double returnable;
        private ReturnJobLineItemArticle article;
        private List<String> scannableCodes;
        private List<String> serviceJobRefs;
        private List<RecordableAttribute> recordableAttributes;
        private CustomAttributes customAttributes;

        private Builder() {}

        public Builder id(String id) { this.id = id; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder globalLineItemId(String globalLineItemId) { this.globalLineItemId = globalLineItemId; return this; }
        public Builder delivered(Double delivered) { this.delivered = delivered; return this; }
        public Builder returned(Double returned) { this.returned = returned; return this; }
        public Builder returnable(Double returnable) { this.returnable = returnable; return this; }
        public Builder article(ReturnJobLineItemArticle article) { this.article = article; return this; }
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }
        public Builder serviceJobRefs(List<String> serviceJobRefs) { this.serviceJobRefs = serviceJobRefs; return this; }
        public Builder recordableAttributes(List<RecordableAttribute> recordableAttributes) { this.recordableAttributes = recordableAttributes; return this; }
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        public ReturnJobLineItem build() {
            return new ReturnJobLineItem(id, status, globalLineItemId, delivered, returned, returnable, article, scannableCodes, serviceJobRefs, recordableAttributes, customAttributes);
        }
    }
}
