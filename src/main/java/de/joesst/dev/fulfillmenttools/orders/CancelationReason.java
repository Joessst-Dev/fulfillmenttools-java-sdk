package de.joesst.dev.fulfillmenttools.orders;

import java.time.Instant;
import java.util.Map;

/**
 * A configured cancellation reason on a read-side order.
 *
 * <p>Maps to the {@code CancelationReason} schema in the fulfillmenttools OpenAPI spec.
 * Extends {@code AbstractReason} which extends {@code VersionedResource}.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id              The platform-generated identifier of the cancellation reason.
 * @param version         The optimistic-locking version.
 * @param created         The timestamp when this reason was created.
 * @param lastModified    The timestamp of the last modification.
 * @param action          The action this reason is attached to; always {@code CANCELATION}.
 * @param reason          The human-readable reason text.
 * @param reasonLocalized Localized translations for the reason, keyed by locale.
 */
public record CancelationReason(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String action,
        String reason,
        Map<String, String> reasonLocalized
) {}
