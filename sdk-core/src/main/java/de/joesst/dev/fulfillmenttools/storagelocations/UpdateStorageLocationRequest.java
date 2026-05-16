package de.joesst.dev.fulfillmenttools.storagelocations;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

import java.util.List;
import java.util.Objects;

/**
 * Request parameters for updating an existing storage location via
 * {@link StorageLocationsClient#update(de.joesst.dev.fulfillmenttools.id.FacilityId, de.joesst.dev.fulfillmenttools.id.StorageLocationId, UpdateStorageLocationRequest)}.
 *
 * <p>Use the fluent {@link Builder} to construct instances:
 * <pre>{@code
 * UpdateStorageLocationRequest request = UpdateStorageLocationRequest.builder()
 *     .version(2)
 *     .name("Aisle 1A")
 *     .build();
 * }</pre>
 *
 * <p>Thread-safety: immutable after construction; safe for concurrent use.
 */
public final class UpdateStorageLocationRequest {

    private final Integer version;
    private final String name;
    private final String type;
    private final List<String> scannableCodes;
    private final List<StorageLocationSequenceItem> runningSequences;
    private final String information;
    private final String tenantLocationId;
    private final String zoneRef;
    private final List<StorageLocationTraitConfigEntry> traitConfig;
    private final CustomAttributes customAttributes;

    private UpdateStorageLocationRequest(Builder builder) {
        this.version = Objects.requireNonNull(builder.version, "version must not be null");
        this.name = builder.name;
        this.type = builder.type;
        this.scannableCodes = builder.scannableCodes;
        this.runningSequences = builder.runningSequences;
        this.information = builder.information;
        this.tenantLocationId = builder.tenantLocationId;
        this.zoneRef = builder.zoneRef;
        this.traitConfig = builder.traitConfig;
        this.customAttributes = builder.customAttributes;
    }

    /**
     * Returns the current version of the storage location; used for optimistic locking.
     *
     * @return the version
     */
    public Integer version() { return version; }

    /**
     * Returns the new display name of the storage location.
     *
     * @return the name, or {@code null} if not updating
     */
    public String name() { return name; }

    /**
     * Returns the new type of the storage location.
     *
     * @return the type, or {@code null} if not updating
     */
    public String type() { return type; }

    /**
     * Returns the new barcodes or scannable identifiers for this storage location.
     *
     * @return the scannable codes, or {@code null} if not updating
     */
    public List<String> scannableCodes() { return scannableCodes; }

    /**
     * Returns the new running sequence items (picking and restow sequences).
     *
     * @return the running sequences, or {@code null} if not updating
     */
    public List<StorageLocationSequenceItem> runningSequences() { return runningSequences; }

    /**
     * Returns the new optional textual information about this storage location.
     *
     * @return the information, or {@code null} if not updating
     */
    public String information() { return information; }

    /**
     * Returns the new tenant-specific storage location identifier.
     *
     * @return the tenant location ID, or {@code null} if not updating
     */
    public String tenantLocationId() { return tenantLocationId; }

    /**
     * Returns the new reference to the zone this storage location belongs to.
     *
     * @return the zone reference, or {@code null} if not updating
     */
    public String zoneRef() { return zoneRef; }

    /**
     * Returns the new trait configuration entries defining per-trait enable/disable status.
     *
     * @return the trait config, or {@code null} if not updating
     */
    public List<StorageLocationTraitConfigEntry> traitConfig() { return traitConfig; }

    /**
     * Returns the new free-form custom attributes.
     *
     * @return the custom attributes, or {@code null} if not updating
     */
    public CustomAttributes customAttributes() { return customAttributes; }

    /**
     * Returns a new {@link Builder} for constructing an {@code UpdateStorageLocationRequest}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link UpdateStorageLocationRequest}.
     */
    public static final class Builder {

        /**
         * Creates a new Builder.
         */
        public Builder() {}

        private Integer version;
        private String name;
        private String type;
        private List<String> scannableCodes;
        private List<StorageLocationSequenceItem> runningSequences;
        private String information;
        private String tenantLocationId;
        private String zoneRef;
        private List<StorageLocationTraitConfigEntry> traitConfig;
        private CustomAttributes customAttributes;

        /**
         * Sets the current version of the storage location; used for optimistic locking.
         *
         * @param version the version
         * @return this builder
         */
        public Builder version(Integer version) { this.version = version; return this; }

        /**
         * Sets the new display name of the storage location.
         *
         * @param name the name
         * @return this builder
         */
        public Builder name(String name) { this.name = name; return this; }

        /**
         * Sets the new type of the storage location.
         *
         * @param type the type
         * @return this builder
         */
        public Builder type(String type) { this.type = type; return this; }

        /**
         * Sets the new barcodes or scannable identifiers for this storage location.
         *
         * @param scannableCodes the scannable codes
         * @return this builder
         */
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }

        /**
         * Sets the new running sequence items (picking and restow sequences).
         *
         * @param runningSequences the running sequences
         * @return this builder
         */
        public Builder runningSequences(List<StorageLocationSequenceItem> runningSequences) { this.runningSequences = runningSequences; return this; }

        /**
         * Sets the new optional textual information about this storage location.
         *
         * @param information the information
         * @return this builder
         */
        public Builder information(String information) { this.information = information; return this; }

        /**
         * Sets the new tenant-specific storage location identifier.
         *
         * @param tenantLocationId the tenant location ID
         * @return this builder
         */
        public Builder tenantLocationId(String tenantLocationId) { this.tenantLocationId = tenantLocationId; return this; }

        /**
         * Sets the new reference to the zone this storage location belongs to.
         *
         * @param zoneRef the zone reference
         * @return this builder
         */
        public Builder zoneRef(String zoneRef) { this.zoneRef = zoneRef; return this; }

        /**
         * Sets the new trait configuration entries defining per-trait enable/disable status.
         *
         * @param traitConfig the trait config
         * @return this builder
         */
        public Builder traitConfig(List<StorageLocationTraitConfigEntry> traitConfig) { this.traitConfig = traitConfig; return this; }

        /**
         * Sets the new free-form custom attributes.
         *
         * @param customAttributes the custom attributes
         * @return this builder
         */
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        /**
         * Builds the {@link UpdateStorageLocationRequest}. Throws {@link NullPointerException}
         * if version is absent.
         *
         * @return a new {@code UpdateStorageLocationRequest}
         */
        public UpdateStorageLocationRequest build() { return new UpdateStorageLocationRequest(this); }
    }
}
