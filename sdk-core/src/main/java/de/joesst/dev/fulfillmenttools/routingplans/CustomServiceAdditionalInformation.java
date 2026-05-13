package de.joesst.dev.fulfillmenttools.routingplans;

/**
 * An additional information entry on a custom service definition.
 *
 * <p>Maps to the inline additional information object within the {@code CustomServiceDefinition}
 * schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param additionalInformationRef       Reference to the specific additional information entry
 *                                       on the custom service (required).
 * @param tenantAdditionalInformationId  The tenant's ID for this additional information.
 * @param value                          The value of the additional information (string, number,
 *                                       integer, or boolean serialized as a string by Jackson).
 */
public record CustomServiceAdditionalInformation(
        String additionalInformationRef,
        String tenantAdditionalInformationId,
        Object value
) {}
