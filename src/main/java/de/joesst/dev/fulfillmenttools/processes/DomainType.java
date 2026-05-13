package de.joesst.dev.fulfillmenttools.processes;

/**
 * The type of a fulfillmenttools domain entity tracked within a process.
 *
 * <p>Maps to the {@code DomainType} schema in the fulfillmenttools OpenAPI specification.
 */
public enum DomainType {
    ORDER,
    ROUTING_PLAN,
    PICKJOB,
    PACKJOB,
    SHIPMENT,
    HANDOVER,
    RETURN,
    SERVICE_JOB,
    ITEM_RETURN_JOB,
    PARCEL,
    RESERVATION
}
