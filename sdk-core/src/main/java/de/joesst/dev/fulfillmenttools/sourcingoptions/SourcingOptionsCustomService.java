package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

/**
 * A custom service attached to an order for sourcing options evaluation.
 *
 * <p>Maps to the {@code customServices} items schema on {@code OrderForSourcingOptionsRequest}
 * in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param type             The service type identifier.
 * @param attributes       Service-specific attributes.
 * @param customAttributes Free-form custom attributes.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SourcingOptionsCustomService(
        String type,
        Map<String, Object> attributes,
        Map<String, Object> customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String type;
        private Map<String, Object> attributes;
        private Map<String, Object> customAttributes;

        private Builder() {}

        public Builder type(String type) { this.type = type; return this; }
        public Builder attributes(Map<String, Object> attributes) { this.attributes = attributes; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public SourcingOptionsCustomService build() {
            return new SourcingOptionsCustomService(type, attributes, customAttributes);
        }
    }
}
