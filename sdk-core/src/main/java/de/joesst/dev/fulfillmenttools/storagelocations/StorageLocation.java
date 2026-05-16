package de.joesst.dev.fulfillmenttools.storagelocations;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.StorageLocationId;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

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
        StorageLocationId id,
        Integer version,
        Instant created,
        Instant lastModified,
        FacilityId facilityRef,
        String name,
        String type,
        String tenantLocationId,
        String zoneName,
        String zoneRef,
        String information,
        List<String> traits,
        List<String> scannableCodes,
        CustomAttributes customAttributes,
        Double schemaVersion,
        List<StorageLocationTraitConfigEntry> traitConfig,
        List<StorageLocationSequenceItem> runningSequences
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private StorageLocationId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private FacilityId facilityRef;
        private String name;
        private String type;
        private String tenantLocationId;
        private String zoneName;
        private String zoneRef;
        private String information;
        private List<String> traits;
        private List<String> scannableCodes;
        private CustomAttributes customAttributes;
        private Double schemaVersion;
        private List<StorageLocationTraitConfigEntry> traitConfig;
        private List<StorageLocationSequenceItem> runningSequences;

        public Builder id(StorageLocationId id) {
            this.id = id;
            return this;
        }

        public Builder version(Integer version) {
            this.version = version;
            return this;
        }

        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        public Builder lastModified(Instant lastModified) {
            this.lastModified = lastModified;
            return this;
        }

        public Builder facilityRef(FacilityId facilityRef) {
            this.facilityRef = facilityRef;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder tenantLocationId(String tenantLocationId) {
            this.tenantLocationId = tenantLocationId;
            return this;
        }

        public Builder zoneName(String zoneName) {
            this.zoneName = zoneName;
            return this;
        }

        public Builder zoneRef(String zoneRef) {
            this.zoneRef = zoneRef;
            return this;
        }

        public Builder information(String information) {
            this.information = information;
            return this;
        }

        public Builder traits(List<String> traits) {
            this.traits = traits;
            return this;
        }

        public Builder scannableCodes(List<String> scannableCodes) {
            this.scannableCodes = scannableCodes;
            return this;
        }

        public Builder customAttributes(CustomAttributes customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        public Builder schemaVersion(Double schemaVersion) {
            this.schemaVersion = schemaVersion;
            return this;
        }

        public Builder traitConfig(List<StorageLocationTraitConfigEntry> traitConfig) {
            this.traitConfig = traitConfig;
            return this;
        }

        public Builder runningSequences(List<StorageLocationSequenceItem> runningSequences) {
            this.runningSequences = runningSequences;
            return this;
        }

        public StorageLocation build() {
            return new StorageLocation(id, version, created, lastModified, facilityRef, name, type,
                    tenantLocationId, zoneName, zoneRef, information, traits, scannableCodes,
                    customAttributes, schemaVersion, traitConfig, runningSequences);
        }
    }
}
