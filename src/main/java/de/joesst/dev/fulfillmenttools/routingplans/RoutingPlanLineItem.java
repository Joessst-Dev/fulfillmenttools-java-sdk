package de.joesst.dev.fulfillmenttools.routingplans;

import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.orders.MeasurementValidation;
import de.joesst.dev.fulfillmenttools.orders.OrderLineItemArticle;
import de.joesst.dev.fulfillmenttools.orders.Substitute;

import java.util.List;
import java.util.Map;

/**
 * A line item on a routing plan, extending the base order line item with routing-specific
 * availability and stock information.
 *
 * <p>Maps to the {@code RoutingPlanLineItem} schema in the fulfillmenttools OpenAPI spec,
 * which extends {@code OrderLineItem}.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                          The platform-generated line item identifier.
 * @param quantity                    The ordered quantity.
 * @param article                     The article details.
 * @param measurementUnitKey          Optional unit of measurement identifier.
 * @param secondaryMeasurementUnitKey Optional secondary unit of measurement.
 * @param secondaryQuantity           Optional secondary quantity.
 * @param scannableCodes              Optional scannable codes for the article.
 * @param allowedSubstitutes          Optional allowed substitute articles.
 * @param measurementValidation       Optional tolerance configuration for quantity deviations.
 * @param tags                        Optional categorization tags.
 * @param customAttributes            Free-form custom attributes.
 * @param available                   The quantity available for this routing plan.
 * @param picked                      The quantity already picked.
 * @param outOfStockBehaviour         The behaviour when the article is out of stock.
 * @param availabilityTimeframe       Optional time frame when the article becomes available.
 */
public record RoutingPlanLineItem(
        String id,
        Integer quantity,
        OrderLineItemArticle article,
        String measurementUnitKey,
        String secondaryMeasurementUnitKey,
        Integer secondaryQuantity,
        List<String> scannableCodes,
        List<Substitute> allowedSubstitutes,
        MeasurementValidation measurementValidation,
        List<TagReference> tags,
        Map<String, Object> customAttributes,
        Double available,
        Double picked,
        String outOfStockBehaviour,
        AvailabilityTimeframe availabilityTimeframe
) {}
