package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

/**
 * Listing details for an article within a sourcing option.
 *
 * <p>Maps to the {@code SourcingOptionListingDetails} schema in the fulfillmenttools OpenAPI spec.
 *
 * @param facilityRef      fulfillmenttools facility reference
 * @param tenantArticleId  tenant-specific article identifier
 * @param customAttributes free-form custom attributes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SourcingOptionListingDetails(
        String facilityRef,
        String tenantArticleId,
        Map<String, Object> customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String facilityRef;
        private String tenantArticleId;
        private Map<String, Object> customAttributes;

        public Builder facilityRef(String facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder tenantArticleId(String tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public SourcingOptionListingDetails build() {
            return new SourcingOptionListingDetails(facilityRef, tenantArticleId, customAttributes);
        }
    }
}
