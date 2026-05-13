package de.joesst.dev.fulfillmenttools.sourcingoptions;

import de.joesst.dev.fulfillmenttools.pickjobs.PickJobLineItemPartialStockLocation;

import java.util.List;
import java.util.Map;

/**
 * A line item assigned to a specific sourcing option node, representing a quantity
 * of an article to be fulfilled from the node's facility.
 *
 * <p>Maps to the line item schema within {@code SourcingOptionNode}
 * in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                    Unique identifier of this node line item.
 * @param orderLineItemRef      Reference to the original order line item.
 * @param quantity              Quantity of the article to be fulfilled from this node.
 * @param measurementUnitKey    Unit of measurement identifier.
 * @param scannableCodes        Scannable codes for the article.
 * @param partialStockLocations Stock locations from which the article should be picked.
 * @param customAttributes      Free-form custom attributes.
 */
public record SourcingOptionNodeLineItem(
        String id,
        String orderLineItemRef,
        Integer quantity,
        String measurementUnitKey,
        List<String> scannableCodes,
        List<PickJobLineItemPartialStockLocation> partialStockLocations,
        Map<String, Object> customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String id;
        private String orderLineItemRef;
        private Integer quantity;
        private String measurementUnitKey;
        private List<String> scannableCodes;
        private List<PickJobLineItemPartialStockLocation> partialStockLocations;
        private Map<String, Object> customAttributes;

        public Builder id(String id) { this.id = id; return this; }
        public Builder orderLineItemRef(String orderLineItemRef) { this.orderLineItemRef = orderLineItemRef; return this; }
        public Builder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public Builder measurementUnitKey(String measurementUnitKey) { this.measurementUnitKey = measurementUnitKey; return this; }
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }
        public Builder partialStockLocations(List<PickJobLineItemPartialStockLocation> partialStockLocations) { this.partialStockLocations = partialStockLocations; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public SourcingOptionNodeLineItem build() {
            return new SourcingOptionNodeLineItem(id, orderLineItemRef, quantity, measurementUnitKey, scannableCodes, partialStockLocations, customAttributes);
        }
    }
}
