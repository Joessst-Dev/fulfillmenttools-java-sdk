package de.joesst.dev.fulfillmenttools.orders;

import java.time.Instant;

/**
 * Represents details of a single order update event.
 *
 * @param created the instant when this update was created
 * @param changes the changes made in this update
 * @param comment an optional comment explaining the update
 * @param user the user who made the update
 */
public record OrderUpdateDetail(
        Instant created,
        OrderUpdateDetailChanges changes,
        String comment,
        String user
) {}
