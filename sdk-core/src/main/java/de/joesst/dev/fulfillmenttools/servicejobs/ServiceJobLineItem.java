package de.joesst.dev.fulfillmenttools.servicejobs;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

/**
 * A single line item within a service job.
 *
 * @param id the platform-generated identifier for this line item
 * @param article the article this line item covers
 * @param quantity the required quantity
 * @param globalLineItemId cross-domain global line item identifier
 * @param measurementUnitKey unit of measure for this line item
 * @param customAttributes tenant-defined key/value extension attributes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ServiceJobLineItem(
        String id,
        ServiceJobLineItemArticle article,
        Integer quantity,
        String globalLineItemId,
        String measurementUnitKey,
        Map<String, Object> customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String id;
        private ServiceJobLineItemArticle article;
        private Integer quantity;
        private String globalLineItemId;
        private String measurementUnitKey;
        private Map<String, Object> customAttributes;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder article(ServiceJobLineItemArticle article) {
            this.article = article;
            return this;
        }

        public Builder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder globalLineItemId(String globalLineItemId) {
            this.globalLineItemId = globalLineItemId;
            return this;
        }

        public Builder measurementUnitKey(String measurementUnitKey) {
            this.measurementUnitKey = measurementUnitKey;
            return this;
        }

        public Builder customAttributes(Map<String, Object> customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        public ServiceJobLineItem build() {
            return new ServiceJobLineItem(id, article, quantity, globalLineItemId,
                    measurementUnitKey, customAttributes);
        }
    }
}
