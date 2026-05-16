package de.joesst.dev.fulfillmenttools.itemreturnjobs;

/**
 * The lifecycle status of an individual item return within an item return job.
 *
 * <p>Maps to the {@code ItemReturnStatus} schema in the fulfillmenttools OpenAPI specification.
 */
public enum ItemReturnStatus {
    ANNOUNCED,
    OPEN,
    IN_PROGRESS,
    PAUSED,
    FINISHED,
    WAITING_FOR_INPUT
}
