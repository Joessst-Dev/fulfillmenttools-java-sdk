package de.joesst.dev.fulfillmenttools.routingplans;

import java.util.List;
import java.util.Map;

/**
 * The definition of a custom service applied to order line items.
 *
 * <p>Maps to the {@code CustomServiceDefinition} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param customServiceRef         Reference to the custom service entity.
 * @param tenantCustomServiceId    The tenant's ID for this custom service.
 * @param isBundled                If {@code true}, all articles under this service form a bundle.
 * @param additionalInformation    Additional information required to fulfil the custom service.
 * @param customAttributes         Free-form custom attributes.
 */
public record CustomServiceDefinition(
        String customServiceRef,
        String tenantCustomServiceId,
        Boolean isBundled,
        List<CustomServiceAdditionalInformation> additionalInformation,
        Map<String, Object> customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String customServiceRef;
        private String tenantCustomServiceId;
        private Boolean isBundled;
        private List<CustomServiceAdditionalInformation> additionalInformation;
        private Map<String, Object> customAttributes;

        private Builder() {}

        public Builder customServiceRef(String customServiceRef) { this.customServiceRef = customServiceRef; return this; }
        public Builder tenantCustomServiceId(String tenantCustomServiceId) { this.tenantCustomServiceId = tenantCustomServiceId; return this; }
        public Builder isBundled(Boolean isBundled) { this.isBundled = isBundled; return this; }
        public Builder additionalInformation(List<CustomServiceAdditionalInformation> additionalInformation) { this.additionalInformation = additionalInformation; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public CustomServiceDefinition build() {
            return new CustomServiceDefinition(customServiceRef, tenantCustomServiceId, isBundled, additionalInformation, customAttributes);
        }
    }
}
