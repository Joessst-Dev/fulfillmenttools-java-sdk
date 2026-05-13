package de.joesst.dev.fulfillmenttools.storagelocations;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents a physical storage location (e.g. aisle, rack, bin) within a facility.
 *
 * <p>Maps to the {@code StorageLocation} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                  Server-assigned unique identifier for this storage location.
 * @param version             Optimistic-locking version counter.
 * @param created             Timestamp when this storage location was created.
 * @param lastModified        Timestamp when this storage location was last modified.
 * @param facilityRef         Reference to the owning facility.
 * @param name                Display name of the storage location.
 * @param type                Type of the storage location (e.g. {@code AISLE}, {@code RACK}).
 * @param tenantLocationId    The tenant's own storage location identifier.
 * @param zoneName            Name of the zone this location belongs to.
 * @param zoneRef             Reference to the zone this location belongs to.
 * @param information         Optional textual information about this storage location.
 * @param traits              Operational trait identifiers active on this location.
 * @param scannableCodes      Barcodes or scannable identifiers for this location.
 * @param customAttributes    Free-form custom metadata.
 * @param schemaVersion       The schema version of this storage location.
 * @param traitConfig         Per-trait enable/disable configuration entries.
 * @param runningSequences    Running sequence items (picking and restow sequences).
 */
public record StorageLocation(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String facilityRef,
        String name,
        String type,
        String tenantLocationId,
        String zoneName,
        String zoneRef,
        String information,
        List<String> traits,
        List<String> scannableCodes,
        Map<String, Object> customAttributes,
        Double schemaVersion,
        List<StorageLocationTraitConfigEntry> traitConfig,
        List<StorageLocationSequenceItem> runningSequences
) {}
