package de.joesst.dev.fulfillmenttools.inbound;

import java.util.List;
import java.util.Map;

/**
 * A single line item within a stow job, describing an article that needs to be taken
 * from one location and stowed to one or more destinations.
 *
 * <p>Maps to the {@code StowLineItem} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id               Server-assigned ID of this stow line item.
 * @param article          The article to be stowed.
 * @param customAttributes Free-form custom attributes.
 * @param heldStockRef     Reference to a transient stock holding the goods after a take but
 *                         before the stow has taken place.
 * @param reasons          Reasons for the stow operation (at most 10).
 * @param stowTo           Instructions on where and how to stow the article.
 * @param takeFrom         Instructions on where and how to take the article from.
 */
public record StowLineItem(
        String id,
        StowLineItemArticle article,
        Map<String, Object> customAttributes,
        String heldStockRef,
        List<ExternalStockChangeReason> reasons,
        List<StowLineItemStowTo> stowTo,
        StowLineItemTakeFrom takeFrom
) {}
