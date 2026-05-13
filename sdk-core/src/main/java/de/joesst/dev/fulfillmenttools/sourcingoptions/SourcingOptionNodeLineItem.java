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
) {}
