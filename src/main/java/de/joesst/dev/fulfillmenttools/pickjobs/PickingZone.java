package de.joesst.dev.fulfillmenttools.pickjobs;

/**
 * A zone reference from which articles may be picked in a pick job.
 *
 * <p>Maps to the {@code PickingZone} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param zoneRef Reference to the zone entity.
 */
public record PickingZone(
        String zoneRef
) {}
