package de.joesst.dev.fulfillmenttools.pickjobs;

import java.time.Instant;

/**
 * A record of a single user modification event on a pick job.
 *
 * <p>Maps to the {@code UserModificationHistory} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param modificationDate Timestamp when the modification occurred.
 * @param userId           ID of the user who made the modification.
 * @param username         Username of the user who made the modification.
 */
public record UserModificationHistory(
        Instant modificationDate,
        String userId,
        String username
) {}
