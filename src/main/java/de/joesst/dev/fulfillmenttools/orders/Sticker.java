package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

/**
 * A visual sticker attached to an order for categorization and display in the platform UI.
 *
 * <p>Maps to the {@code Sticker} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param key           Unique identifier for the sticker.
 * @param nameLocalized Localized display names keyed by locale (e.g. {@code en_US}).
 * @param name          The translated display name (resolved from {@code nameLocalized}).
 * @param color         Optional color code for the sticker (e.g. {@code #19b6b5}).
 * @param priority      Display priority (1–10000); lower value means higher priority.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Sticker(
        String key,
        Map<String, String> nameLocalized,
        String name,
        String color,
        Integer priority
) {}
