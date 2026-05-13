package de.joesst.dev.fulfillmenttools.routingplans;

import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.orders.MeasurementValidation;
import de.joesst.dev.fulfillmenttools.orders.OrderLineItemArticle;
import de.joesst.dev.fulfillmenttools.orders.Substitute;

import java.util.List;
import java.util.Map;

/**
 * An expected line item on a routing plan, extending the base order line item with
 * transfer and facility context.
 *
 * <p>Maps to the {@code ExpectedLineItem} schema in the fulfillmenttools OpenAPI spec,
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
 * @param facilityRef                 The facility this line item is expected at (required).
 * @param transferId                  The transfer this line item is part of (required).
 */
public record ExpectedLineItem(
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
        String facilityRef,
        String transferId
) {}
