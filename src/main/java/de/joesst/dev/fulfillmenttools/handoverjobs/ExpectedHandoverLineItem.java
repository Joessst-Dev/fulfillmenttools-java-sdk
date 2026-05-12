package de.joesst.dev.fulfillmenttools.handoverjobs;

import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.orders.Sticker;

import java.util.List;
import java.util.Map;

/**
 * A line item that is expected (anticipated) on a handover job but not yet confirmed.
 *
 * <p>Maps to the {@code ExpectedHandoverLineItem} schema in the fulfillmenttools OpenAPI spec,
 * which extends {@code ExpectedHandoverLineItemForCreation} with a generated {@code id}.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id               Unique identifier of the expected line item. Required.
 * @param quantity         Number of units expected for handover. Required; minimum 1.
 * @param transferId       Reference to the transfer this expected line item belongs to. Required.
 * @param article          The expected article.
 * @param status           Current status of the expected line item ({@code OPEN}, {@code CLOSED}).
 * @param stickers         Visual stickers attached to this expected line item.
 * @param scannableCodes   Scannable codes that can identify this line item.
 * @param tags             Tag references attached to this expected line item.
 * @param customAttributes Free-form custom attributes attached to this line item.
 */
public record ExpectedHandoverLineItem(
        String id,
        Integer quantity,
        String transferId,
        HandoverLineItemArticle article,
        String status,
        List<Sticker> stickers,
        List<String> scannableCodes,
        List<TagReference> tags,
        Map<String, Object> customAttributes
) {}
