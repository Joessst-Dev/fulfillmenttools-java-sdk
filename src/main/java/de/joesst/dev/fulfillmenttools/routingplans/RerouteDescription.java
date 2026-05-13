package de.joesst.dev.fulfillmenttools.routingplans;

import java.time.Instant;
import java.util.Map;

/**
 * Describes why a routing plan was rerouted.
 *
 * <p>Maps to the {@code RerouteDescription} schema in the fulfillmenttools OpenAPI spec,
 * which extends {@code AbstractReason} (itself composed of {@code AbstractReasonForCreation}
 * and {@code VersionedResource}). The {@code action} field is always {@code "REROUTE"}.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id              The ID of this reroute description (required).
 * @param action          The action this description is attached to; always {@code "REROUTE"}
 *                        (required).
 * @param reason          Human-readable text explaining the reroute reason (required).
 * @param reasonLocalized Optional localized version of the reason, keyed by locale.
 * @param version         The version of this resource for optimistic locking (required).
 * @param created         The creation timestamp (required).
 * @param lastModified    The last-modification timestamp (required).
 */
public record RerouteDescription(
        String id,
        String action,
        String reason,
        Map<String, String> reasonLocalized,
        Integer version,
        Instant created,
        Instant lastModified
) {}
