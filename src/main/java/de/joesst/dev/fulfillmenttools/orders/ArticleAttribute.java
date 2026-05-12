package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

/**
 * An attribute attached to an order line item article, used for customization and display.
 *
 * <p>Maps to the {@code ArticleAttributeItem} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param key            The attribute key (e.g. {@code %%subtitle%%} for subtitle display).
 * @param value          The string value of the attribute.
 * @param type           The data type of the attribute value ({@code STRING}, {@code NUMBER},
 *                       {@code CURRENCY}, {@code BOOLEAN}). Defaults to {@code STRING}.
 * @param category       The category controlling how the platform uses the attribute
 *                       (e.g. {@code descriptive}, {@code miscellaneous}, {@code salesPrice}).
 * @param priority       Sort priority within the category; lower value means higher priority.
 * @param keyLocalized   Localized translations for the attribute key.
 * @param valueLocalized Localized translations for the attribute value.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ArticleAttribute(
        String key,
        String value,
        String type,
        String category,
        Integer priority,
        Map<String, String> keyLocalized,
        Map<String, String> valueLocalized
) {}
