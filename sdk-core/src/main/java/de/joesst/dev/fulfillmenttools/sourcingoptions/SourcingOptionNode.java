package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.TenantFacilityId;

import java.util.List;

/**
 * A facility node participating in a sourcing option, representing one source of stock
 * in the fulfillment plan.
 *
 * <p>Maps to the {@code SourcingOptionNode} schema in the fulfillmenttools OpenAPI spec.
 *
 * @param id               unique identifier of this node
 * @param type             node type ({@link NodeType})
 * @param facilityRef      fulfillmenttools facility reference
 * @param tenantFacilityId tenant-specific facility identifier
 * @param isPickUpLocation whether this node is a click-and-collect pickup location
 * @param lineItems        articles and quantities to be fulfilled from this node
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SourcingOptionNode(
        String id,
        NodeType type,
        FacilityId facilityRef,
        TenantFacilityId tenantFacilityId,
        Boolean isPickUpLocation,
        List<HandledItem> lineItems
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String id;
        private NodeType type;
        private FacilityId facilityRef;
        private TenantFacilityId tenantFacilityId;
        private Boolean isPickUpLocation;
        private List<HandledItem> lineItems;

        public Builder id(String id) { this.id = id; return this; }
        public Builder type(NodeType type) { this.type = type; return this; }
        public Builder facilityRef(FacilityId facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder tenantFacilityId(TenantFacilityId tenantFacilityId) { this.tenantFacilityId = tenantFacilityId; return this; }
        public Builder isPickUpLocation(Boolean isPickUpLocation) { this.isPickUpLocation = isPickUpLocation; return this; }
        public Builder lineItems(List<HandledItem> lineItems) { this.lineItems = lineItems; return this; }

        public SourcingOptionNode build() {
            return new SourcingOptionNode(id, type, facilityRef, tenantFacilityId, isPickUpLocation, lineItems);
        }
    }
}
