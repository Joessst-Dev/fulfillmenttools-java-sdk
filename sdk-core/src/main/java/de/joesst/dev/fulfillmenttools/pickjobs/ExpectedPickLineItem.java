package de.joesst.dev.fulfillmenttools.pickjobs;

import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.orders.MeasurementValidation;
import de.joesst.dev.fulfillmenttools.orders.Sticker;
import de.joesst.dev.fulfillmenttools.orders.Substitute;

import java.util.List;
import java.util.Map;

/**
 * An expected pick line item representing an article that is anticipated to be picked
 * but has not yet been assigned to an active pick line.
 *
 * <p>Maps to the {@code ExpectedPickLineItem} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                          Unique identifier, generated during creation.
 * @param transferId                  Reference to the transfer this item belongs to.
 * @param quantity                    Number of units to be picked (minimum 1).
 * @param article                     The article to be picked.
 * @param measurementUnitKey          Identifier for the unit of measurement.
 * @param secondaryMeasurementUnitKey Identifier for the secondary unit of measurement.
 * @param secondaryQuantity           Quantity in secondary measurement units.
 * @param scannableCodes              Codes that identify the article for scanning.
 * @param measurementValidation       Tolerance configuration for quantity deviations.
 * @param partialStockLocations       Candidate stock locations for picking.
 * @param allowedSubstitutes          Substitute articles allowed if the primary is unavailable.
 * @param stickers                    Visual stickers for display in the platform UI.
 * @param tags                        Tag references attached to this item.
 * @param customAttributes            Free-form custom attributes.
 */
public record ExpectedPickLineItem(
        String id,
        String transferId,
        Integer quantity,
        PickLineItemArticle article,
        String measurementUnitKey,
        String secondaryMeasurementUnitKey,
        Integer secondaryQuantity,
        List<String> scannableCodes,
        MeasurementValidation measurementValidation,
        List<PickJobLineItemPartialStockLocation> partialStockLocations,
        List<Substitute> allowedSubstitutes,
        List<Sticker> stickers,
        List<TagReference> tags,
        Map<String, Object> customAttributes
) {}
