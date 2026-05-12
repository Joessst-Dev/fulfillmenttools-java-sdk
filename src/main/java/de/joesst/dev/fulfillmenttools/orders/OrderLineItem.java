package de.joesst.dev.fulfillmenttools.orders;

import de.joesst.dev.fulfillmenttools.model.TagReference;

import java.util.List;
import java.util.Map;

/**
 * A read-side order line item containing article and fulfillment information.
 *
 * <p>Maps to the {@code OrderLineItem} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                          The platform-generated line item identifier.
 * @param quantity                    The ordered quantity.
 * @param article                     The article details.
 * @param measurementUnitKey          Optional unit of measurement identifier.
 * @param secondaryMeasurementUnitKey Optional secondary unit of measurement.
 * @param secondaryQuantity           Optional secondary quantity.
 * @param scannableCodes              Optional list of scannable codes for the article.
 * @param allowedSubstitutes          Optional list of allowed substitute articles.
 * @param measurementValidation       Optional tolerance configuration for quantity deviations.
 * @param tags                        Optional tags for categorization.
 * @param customAttributes            Free-form custom attributes.
 */
public record OrderLineItem(
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
        Map<String, Object> customAttributes
) {}
