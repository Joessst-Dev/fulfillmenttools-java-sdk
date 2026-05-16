package de.joesst.dev.fulfillmenttools.itemreturnjobs;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

/**
 * A single line item within an item return job, tracking returnable and returned quantities.
 *
 * @param id the platform-generated identifier for this line item
 * @param article the article being returned
 * @param delivered the total delivered quantity
 * @param returned the quantity already returned
 * @param returnable the quantity still eligible for return
 * @param globalLineItemId cross-domain global line item identifier
 * @param scannableCodes scannable codes associated with this line item
 * @param serviceJobRefs references to associated service jobs
 * @param customAttributes tenant-defined key/value extension attributes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ItemReturnJobLineItem(
        String id,
        ItemReturnJobLineItemArticle article,
        Double delivered,
        Double returned,
        Double returnable,
        String globalLineItemId,
        List<String> scannableCodes,
        List<String> serviceJobRefs,
        Map<String, Object> customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String id;
        private ItemReturnJobLineItemArticle article;
        private Double delivered;
        private Double returned;
        private Double returnable;
        private String globalLineItemId;
        private List<String> scannableCodes;
        private List<String> serviceJobRefs;
        private Map<String, Object> customAttributes;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder article(ItemReturnJobLineItemArticle article) {
            this.article = article;
            return this;
        }

        public Builder delivered(Double delivered) {
            this.delivered = delivered;
            return this;
        }

        public Builder returned(Double returned) {
            this.returned = returned;
            return this;
        }

        public Builder returnable(Double returnable) {
            this.returnable = returnable;
            return this;
        }

        public Builder globalLineItemId(String globalLineItemId) {
            this.globalLineItemId = globalLineItemId;
            return this;
        }

        public Builder scannableCodes(List<String> scannableCodes) {
            this.scannableCodes = scannableCodes;
            return this;
        }

        public Builder serviceJobRefs(List<String> serviceJobRefs) {
            this.serviceJobRefs = serviceJobRefs;
            return this;
        }

        public Builder customAttributes(Map<String, Object> customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        public ItemReturnJobLineItem build() {
            return new ItemReturnJobLineItem(id, article, delivered, returned, returnable,
                    globalLineItemId, scannableCodes, serviceJobRefs, customAttributes);
        }
    }
}
