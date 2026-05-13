package de.joesst.dev.fulfillmenttools.sourcingoptions;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.TenantFacilityId;

import java.util.List;

/**
 * A facility node participating in a sourcing option, representing one source of stock
 * in the fulfillment plan.
 *
 * <p>Maps to the {@code SourcingOptionNode} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id               Unique identifier of this node.
 * @param facilityRef      Reference to the fulfillmenttools facility.
 * @param tenantFacilityId Tenant-specific {@link TenantFacilityId} facility identifier.
 * @param type             Node type (e.g. {@code SHIP_FROM_STORE}, {@code WAREHOUSE}).
 * @param isPickUpLocation Whether this node is a click-and-collect pickup location.
 * @param lineItems        The line items to be fulfilled from this node.
 */
public record SourcingOptionNode(
        String id,
        FacilityId facilityRef,
        TenantFacilityId tenantFacilityId,
        String type,
        Boolean isPickUpLocation,
        List<SourcingOptionNodeLineItem> lineItems
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private FacilityId facilityRef;
        private TenantFacilityId tenantFacilityId;
        private String type;
        private Boolean isPickUpLocation;
        private List<SourcingOptionNodeLineItem> lineItems;

        private Builder() {}

        public Builder id(String id) { this.id = id; return this; }
        public Builder facilityRef(FacilityId facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder tenantFacilityId(TenantFacilityId tenantFacilityId) { this.tenantFacilityId = tenantFacilityId; return this; }
        public Builder type(String type) { this.type = type; return this; }
        public Builder isPickUpLocation(Boolean isPickUpLocation) { this.isPickUpLocation = isPickUpLocation; return this; }
        public Builder lineItems(List<SourcingOptionNodeLineItem> lineItems) { this.lineItems = lineItems; return this; }

        public SourcingOptionNode build() {
            return new SourcingOptionNode(id, facilityRef, tenantFacilityId, type, isPickUpLocation, lineItems);
        }
    }
}
