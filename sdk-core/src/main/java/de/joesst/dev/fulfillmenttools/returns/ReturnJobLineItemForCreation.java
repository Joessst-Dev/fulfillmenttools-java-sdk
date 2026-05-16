package de.joesst.dev.fulfillmenttools.returns;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.pickjobs.RecordableAttribute;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

import java.util.List;

/**
 * A line item supplied when creating or updating a return job, describing an article
 * that is (or is not) returnable.
 *
 * <p>Maps to the {@code ItemReturnJobLineItemForCreation} schema in the fulfillmenttools
 * OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param article               The article being returned. Required.
 * @param delivered             Quantity of this line item that was delivered. Required; minimum 1.
 * @param globalLineItemId      Optional cross-entity line item identifier.
 * @param scannableCodes        Optional codes that can be used for scanning this line item.
 * @param serviceJobRefs        Optional references to service jobs that altered this line item.
 * @param recordableAttributes  Optional attributes whose values can be recorded during the
 *                              return process.
 * @param customAttributes      Free-form custom attributes.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ReturnJobLineItemForCreation(
        ReturnJobLineItemArticle article,
        Double delivered,
        String globalLineItemId,
        List<String> scannableCodes,
        List<String> serviceJobRefs,
        List<RecordableAttribute> recordableAttributes,
        CustomAttributes customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private ReturnJobLineItemArticle article;
        private Double delivered;
        private String globalLineItemId;
        private List<String> scannableCodes;
        private List<String> serviceJobRefs;
        private List<RecordableAttribute> recordableAttributes;
        private CustomAttributes customAttributes;

        private Builder() {}

        public Builder article(ReturnJobLineItemArticle article) { this.article = article; return this; }
        public Builder delivered(Double delivered) { this.delivered = delivered; return this; }
        public Builder globalLineItemId(String globalLineItemId) { this.globalLineItemId = globalLineItemId; return this; }
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }
        public Builder serviceJobRefs(List<String> serviceJobRefs) { this.serviceJobRefs = serviceJobRefs; return this; }
        public Builder recordableAttributes(List<RecordableAttribute> recordableAttributes) { this.recordableAttributes = recordableAttributes; return this; }
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        public ReturnJobLineItemForCreation build() {
            return new ReturnJobLineItemForCreation(article, delivered, globalLineItemId, scannableCodes, serviceJobRefs, recordableAttributes, customAttributes);
        }
    }
}
