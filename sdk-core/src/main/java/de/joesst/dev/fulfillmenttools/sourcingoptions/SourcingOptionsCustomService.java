package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

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
        CustomAttributes customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String type;
        private Map<String, Object> attributes;
        private CustomAttributes customAttributes;

        public Builder type(String type) { this.type = type; return this; }
        public Builder attributes(Map<String, Object> attributes) { this.attributes = attributes; return this; }
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        public SourcingOptionsCustomService build() {
            return new SourcingOptionsCustomService(type, attributes, customAttributes);
        }
    }
}
