package de.joesst.dev.fulfillmenttools.routingplans;

import java.util.List;

/**
 * An item within a custom service reference on a routing plan.
 *
 * <p>Maps to the {@code CustomServiceItem} schema in the fulfillmenttools OpenAPI spec.
 * Items can be nested recursively via {@code customServiceItems}.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                     The item identifier (required).
 * @param customServiceDefinition The custom service definition for this item (required).
 * @param articleItems            The article items associated with this service item (required).
 * @param customServiceItems      Nested custom service items (required).
 */
public record CustomServiceItem(
        String id,
        CustomServiceDefinition customServiceDefinition,
        List<ArticleItem> articleItems,
        List<CustomServiceItem> customServiceItems
) {}
