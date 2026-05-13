package de.joesst.dev.fulfillmenttools.routingplans;

import java.util.List;

/**
 * A reference to a custom service attached to a routing plan.
 *
 * <p>Maps to the {@code CustomServiceReference} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                     The custom service reference identifier (required).
 * @param customServiceDefinition The definition of the custom service (required).
 * @param articleItems            The article items associated with this custom service (required).
 * @param customServiceItems      The custom service sub-items (required).
 */
public record CustomServiceReference(
        String id,
        CustomServiceDefinition customServiceDefinition,
        List<ArticleItem> articleItems,
        List<CustomServiceItem> customServiceItems
) {}
