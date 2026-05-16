package de.joesst.dev.fulfillmenttools.servicejobs;

/**
 * The lifecycle status of a service job.
 *
 * <p>Maps to the {@code ServiceJobStatus} schema in the fulfillmenttools OpenAPI specification.
 */
public enum ServiceJobStatus {
    OPEN,
    IN_PROGRESS,
    FINISHED,
    CANCELLED,
    WAITING_FOR_INPUT,
    OBSOLETE,
    NOT_READY
}
