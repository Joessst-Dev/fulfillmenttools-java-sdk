package de.joesst.dev.fulfillmenttools.handoverjobs;

import java.util.Map;

/**
 * Records the refusal of a quantity of a handover line item or substitute line item.
 *
 * <p>Maps to the {@code RefusedItem} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param quantity        The number of units refused. Required; minimum 0.
 * @param reason          Optional free-text reason for refusal.
 * @param reasonLocalized Optional localized refusal reason strings, keyed by locale
 *                        (e.g. {@code en_US}).
 * @param comment         Optional additional comment about the refusal.
 */
public record HandoverRefusedItem(
        Integer quantity,
        String reason,
        Map<String, String> reasonLocalized,
        String comment
) {}
