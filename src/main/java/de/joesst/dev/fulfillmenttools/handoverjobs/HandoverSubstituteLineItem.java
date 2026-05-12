package de.joesst.dev.fulfillmenttools.handoverjobs;

import java.time.Instant;
import java.util.List;

/**
 * A substitute line item within a handover job, representing an alternative article
 * offered when the original item cannot be handed over.
 *
 * <p>Maps to the {@code HandoverSubstituteLineItem} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                   Unique identifier of this substitute line item. Required.
 * @param quantity             Number of units provided for handover. Required; minimum 1.
 * @param handedOverQuantity   Number of units that have actually been handed over; minimum 0.
 * @param priority             Ranking among substitutes; lower value means more preferred.
 * @param pickedAt             Timestamp when this line was picked.
 * @param article              The substitute article. Required.
 * @param partialStockLocations Stock locations from which articles were sourced.
 * @param refused              List of refusal records for units not accepted.
 * @param scannableCodes       Scannable codes identifying the substitute article.
 */
public record HandoverSubstituteLineItem(
        String id,
        Integer quantity,
        Integer handedOverQuantity,
        Double priority,
        Instant pickedAt,
        HandoverSubstituteLineItemArticle article,
        List<HandoverSubstituteLineItemStockLocation> partialStockLocations,
        List<HandoverRefusedItem> refused,
        List<String> scannableCodes
) {}
