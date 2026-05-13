package de.joesst.dev.fulfillmenttools.processes;

/**
 * The lifecycle status of a fulfillmenttools domain entity within a process.
 *
 * <p>Maps to the {@code DomainStatus} schema in the fulfillmenttools OpenAPI specification.
 */
public enum DomainStatus {
    /** The domain entity has not yet been processed. */
    PENDING,
    /** The domain entity has been created. */
    CREATED,
    /** The domain entity is actively being processed. */
    IN_PROGRESS,
    /** Processing is blocked or stalled. */
    STUCK,
    /** The domain entity was successfully completed. */
    FINISHED,
    /** The domain entity was canceled. */
    CANCELED,
    /** The domain entity is no longer relevant. */
    OBSOLETE
}
