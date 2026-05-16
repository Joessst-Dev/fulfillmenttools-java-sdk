package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.TenantArticleId;

import java.util.Map;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

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
        FacilityId facilityRef,
        TenantArticleId tenantArticleId,
        CustomAttributes customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private FacilityId facilityRef;
        private TenantArticleId tenantArticleId;
        private CustomAttributes customAttributes;

        public Builder facilityRef(FacilityId facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder tenantArticleId(TenantArticleId tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        public SourcingOptionListingDetails build() {
            return new SourcingOptionListingDetails(facilityRef, tenantArticleId, customAttributes);
        }
    }
}
