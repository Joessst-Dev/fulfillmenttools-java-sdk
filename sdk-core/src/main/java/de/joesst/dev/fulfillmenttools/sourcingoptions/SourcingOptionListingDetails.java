package de.joesst.dev.fulfillmenttools.sourcingoptions;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.ListingId;
import de.joesst.dev.fulfillmenttools.id.TenantArticleId;
import de.joesst.dev.fulfillmenttools.id.TenantFacilityId;

import java.util.Map;

/**
 * Listing details for an article within a sourcing option, including stock availability
 * and listing configuration at a specific facility.
 *
 * <p>Maps to the {@code SourcingOptionListingDetails} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param listingRef        Reference to the listing.
 * @param facilityRef       Reference to the facility this listing belongs to.
 * @param tenantFacilityId  Tenant-specific {@link TenantFacilityId} facility identifier.
 * @param tenantArticleId   Tenant-specific article identifier.
 * @param stockInformation  Stock availability details for this listing.
 * @param customAttributes  Free-form custom attributes.
 */
public record SourcingOptionListingDetails(
        ListingId listingRef,
        FacilityId facilityRef,
        TenantFacilityId tenantFacilityId,
        TenantArticleId tenantArticleId,
        SourcingOptionStockInformation stockInformation,
        Map<String, Object> customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private ListingId listingRef;
        private FacilityId facilityRef;
        private TenantFacilityId tenantFacilityId;
        private TenantArticleId tenantArticleId;
        private SourcingOptionStockInformation stockInformation;
        private Map<String, Object> customAttributes;

        private Builder() {}

        public Builder listingRef(ListingId listingRef) { this.listingRef = listingRef; return this; }
        public Builder facilityRef(FacilityId facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder tenantFacilityId(TenantFacilityId tenantFacilityId) { this.tenantFacilityId = tenantFacilityId; return this; }
        public Builder tenantArticleId(TenantArticleId tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }
        public Builder stockInformation(SourcingOptionStockInformation stockInformation) { this.stockInformation = stockInformation; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public SourcingOptionListingDetails build() {
            return new SourcingOptionListingDetails(listingRef, facilityRef, tenantFacilityId, tenantArticleId, stockInformation, customAttributes);
        }
    }
}
