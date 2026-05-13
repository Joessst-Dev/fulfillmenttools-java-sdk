package de.joesst.dev.fulfillmenttools.processes;

/**
 * The lifecycle status of a fulfillmenttools domain entity within a process.
 *
 * <p>Maps to the {@code DomainStatus} schema in the fulfillmenttools OpenAPI specification.
 */
public enum DomainStatus {
    PENDING,
    CREATED,
    IN_PROGRESS,
    STUCK,
    FINISHED,
    CANCELED,
    OBSOLETE
}
