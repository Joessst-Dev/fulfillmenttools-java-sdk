package de.joesst.dev.fulfillmenttools.purchaseorders;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;
import de.joesst.dev.fulfillmenttools.model.Quantity;

import java.util.Map;

/**
 * A single line item on a purchase order, identifying the requested article and quantity.
 *
 * @param tenantArticleId the tenant's article identifier
 * @param quantity the requested quantity
 * @param stockProperties additional stock property key/value pairs
 * @param customAttributes tenant-defined key/value extension attributes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record InboundLineItem(
        String tenantArticleId,
        Quantity quantity,
        Map<String, String> stockProperties,
        CustomAttributes customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String tenantArticleId;
        private Quantity quantity;
        private Map<String, String> stockProperties;
        private CustomAttributes customAttributes;

        public Builder tenantArticleId(String tenantArticleId) {
            this.tenantArticleId = tenantArticleId;
            return this;
        }

        public Builder quantity(Quantity quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder stockProperties(Map<String, String> stockProperties) {
            this.stockProperties = stockProperties;
            return this;
        }

        public Builder customAttributes(CustomAttributes customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        public InboundLineItem build() {
            return new InboundLineItem(tenantArticleId, quantity, stockProperties, customAttributes);
        }
    }
}
