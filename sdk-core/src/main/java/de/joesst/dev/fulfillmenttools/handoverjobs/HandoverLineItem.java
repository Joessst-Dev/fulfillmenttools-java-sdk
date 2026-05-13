package de.joesst.dev.fulfillmenttools.handoverjobs;

import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.orders.Sticker;

import java.util.List;

/**
 * A line item within a handover job, representing a quantity of an article to be handed over.
 *
 * <p>Maps to the {@code HandoverLineItem} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                   Unique identifier of this line item. Required.
 * @param quantity             Number of units provided for handover. Required; minimum 1.
 * @param handedOverQuantity   Number of units that have actually been handed over.
 *                             Required; minimum 0.
 * @param originId             The original line item id before any split occurred.
 * @param status               Current status of the line item ({@code OPEN}, {@code CLOSED}).
 * @param article              The article to be handed over. Required.
 * @param refused              Refusal records for units not accepted by the recipient.
 * @param stickers             Visual stickers attached to this line item.
 * @param substituteLineItems  Alternative articles offered when the original is unavailable
 *                             (Beta feature).
 * @param tags                 Tag references attached to this line item.
 */
public record HandoverLineItem(
        String id,
        Integer quantity,
        Integer handedOverQuantity,
        String originId,
        String status,
        HandoverLineItemArticle article,
        List<HandoverRefusedItem> refused,
        List<Sticker> stickers,
        List<HandoverSubstituteLineItem> substituteLineItems,
        List<TagReference> tags
) {}
