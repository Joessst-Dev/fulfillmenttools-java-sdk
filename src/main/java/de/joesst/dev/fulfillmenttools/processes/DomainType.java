package de.joesst.dev.fulfillmenttools.processes;

/**
 * The type of a fulfillmenttools domain entity tracked within a process.
 *
 * <p>Maps to the {@code DomainType} schema in the fulfillmenttools OpenAPI specification.
 */
public enum DomainType {
    /** An order. */
    ORDER,
    /** A routing plan. */
    ROUTING_PLAN,
    /** A pick job. */
    PICKJOB,
    /** A pack job. */
    PACKJOB,
    /** A shipment. */
    SHIPMENT,
    /** A handover. */
    HANDOVER,
    /** A return. */
    RETURN,
    /** A service job. */
    SERVICE_JOB,
    /** An item return job. */
    ITEM_RETURN_JOB,
    /** A parcel. */
    PARCEL,
    /** A reservation. */
    RESERVATION
}
