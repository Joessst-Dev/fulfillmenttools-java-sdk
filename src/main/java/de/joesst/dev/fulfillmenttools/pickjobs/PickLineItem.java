package de.joesst.dev.fulfillmenttools.pickjobs;

import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.orders.MeasurementValidation;
import de.joesst.dev.fulfillmenttools.orders.Sticker;
import de.joesst.dev.fulfillmenttools.orders.Substitute;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * A single pick line item within a pick job, representing a quantity of a specific article
 * to be picked.
 *
 * <p>Maps to the {@code PickLineItem} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                          Unique identifier of this pick line item.
 * @param quantity                    Number of units that have been ordered.
 * @param picked                      Number of units that have been picked.
 * @param status                      Current status of the pick line item.
 * @param article                     The article to be picked.
 * @param measurementUnitKey          Identifier for the primary unit of measurement.
 * @param secondaryMeasurementUnitKey Identifier for the secondary unit of measurement.
 * @param secondaryQuantity           Quantity in the secondary unit of measurement.
 * @param secondaryPicked             Picked quantity in the secondary unit of measurement.
 * @param globalLineItemId            Cross-entity line item identifier.
 * @param originId                    Id of the originating line item before any split.
 * @param pickJobLineItemRef          Reference used for line items in pick runs.
 * @param pickedAt                    Timestamp when this line was picked.
 * @param stockEmptied                Whether the stock at the pick location was depleted.
 * @param scannableCodes              Codes that can be scanned to identify the article.
 * @param partialStockLocations       Stock locations from which the article should be picked.
 * @param recordableAttributes        Customizable attributes that can be recorded during picking.
 * @param measurementValidation       Tolerance configuration for quantity deviations.
 * @param shortPickReason             Reason provided when fewer units were picked than requested.
 * @param allowedSubstitutes          Articles that may substitute the primary if unavailable.
 * @param stickers                    Visual stickers attached to this line item.
 * @param tags                        Tag references attached to this line item.
 * @param customAttributes            Free-form custom attributes.
 */
public record PickLineItem(
        String id,
        Integer quantity,
        Integer picked,
        String status,
        PickLineItemArticle article,
        String measurementUnitKey,
        String secondaryMeasurementUnitKey,
        Integer secondaryQuantity,
        Integer secondaryPicked,
        String globalLineItemId,
        String originId,
        String pickJobLineItemRef,
        Instant pickedAt,
        Boolean stockEmptied,
        List<String> scannableCodes,
        List<PickJobLineItemPartialStockLocation> partialStockLocations,
        List<RecordableAttribute> recordableAttributes,
        MeasurementValidation measurementValidation,
        PickLineShortPickReason shortPickReason,
        List<Substitute> allowedSubstitutes,
        List<Sticker> stickers,
        List<TagReference> tags,
        Map<String, Object> customAttributes
) {}
