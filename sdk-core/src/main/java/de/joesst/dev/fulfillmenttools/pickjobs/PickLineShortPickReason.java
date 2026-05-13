package de.joesst.dev.fulfillmenttools.pickjobs;

import java.util.Map;

/**
 * The reason a pick line item was short-picked (i.e., fewer units were picked than requested).
 *
 * <p>Maps to the {@code PickLineShortPickReason} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param reasonLocalized Localized reason texts keyed by locale (e.g. {@code en_US}).
 * @param reason          The translated reason resolved from {@code reasonLocalized} for the
 *                        request locale.
 */
public record PickLineShortPickReason(
        Map<String, String> reasonLocalized,
        String reason
) {}
