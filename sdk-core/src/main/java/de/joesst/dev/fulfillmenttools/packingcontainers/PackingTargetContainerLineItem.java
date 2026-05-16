package de.joesst.dev.fulfillmenttools.packingcontainers;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

/**
 * A single line item within a packing target container, tracking what has been packed.
 *
 * @param id the platform-generated identifier for this line item
 * @param quantity the packed quantity
 * @param globalLineItemId cross-domain global line item identifier
 * @param customAttributes tenant-defined key/value extension attributes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PackingTargetContainerLineItem(
        String id,
        Integer quantity,
        String globalLineItemId,
        Map<String, Object> customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String id;
        private Integer quantity;
        private String globalLineItemId;
        private Map<String, Object> customAttributes;

        public Builder id(String id) {
            this.id = id;
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

        public Builder customAttributes(Map<String, Object> customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        public PackingTargetContainerLineItem build() {
            return new PackingTargetContainerLineItem(id, quantity, globalLineItemId, customAttributes);
        }
    }
}
