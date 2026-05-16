package de.joesst.dev.fulfillmenttools.itemreturnjobs;

/**
 * The lifecycle status of an item return job.
 *
 * <p>Maps to the {@code ItemReturnJobStatus} schema in the fulfillmenttools OpenAPI specification.
 */
public enum ItemReturnJobStatus {
    OPEN,
    IN_PROGRESS,
    FINISHED,
    CANCELED
}
