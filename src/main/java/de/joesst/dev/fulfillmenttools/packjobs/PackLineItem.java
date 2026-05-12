package de.joesst.dev.fulfillmenttools.packjobs;

import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.orders.Sticker;

import java.util.List;

/**
 * A single line item within a pack job representing an article quantity to be packed.
 *
 * <p>Maps to the {@code PackLineItem} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                 Unique identifier of the line item.
 * @param quantity           Quantity of this article to be packed.
 * @param article            The article to be packed.
 * @param packed             The amount of articles that have already been packed.
 * @param measurementUnitKey Identifier for the item's unit of measurement (e.g. {@code liter}).
 * @param originId           Id of the original line item this one was split from.
 * @param serviceJobRefs     References to service jobs that have altered this line item.
 * @param stickers           Visual stickers attached to this line item.
 * @param tags               Tag references attached to this line item.
 */
public record PackLineItem(
        String id,
        Integer quantity,
        PackLineItemArticle article,
        Integer packed,
        String measurementUnitKey,
        String originId,
        List<String> serviceJobRefs,
        List<Sticker> stickers,
        List<TagReference> tags
) {}
