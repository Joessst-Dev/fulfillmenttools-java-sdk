package de.joesst.dev.fulfillmenttools.storagelocations;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Request parameters for creating a new storage location via
 * {@link StorageLocationsClient#create(String, CreateStorageLocationRequest)}.
 *
 * <p>Use the fluent {@link Builder} to construct instances:
 * <pre>{@code
 * CreateStorageLocationRequest request = CreateStorageLocationRequest.builder()
 *     .name("Aisle 1")
 *     .type("AISLE")
 *     .scannableCodes(List.of("A1"))
 *     .runningSequences(List.of())
 *     .build();
 * }</pre>
 *
 * <p>Thread-safety: immutable after construction; safe for concurrent use.
 */
public final class CreateStorageLocationRequest {

    private final String name;
    private final String type;
    private final List<String> scannableCodes;
    private final List<StorageLocationSequenceItem> runningSequences;
    private final String information;
    private final String tenantLocationId;
    private final String zoneRef;
    private final String zoneName;
    private final List<StorageLocationTraitConfigEntry> traitConfig;
    private final List<String> traits;
    private final Map<String, Object> customAttributes;

    private CreateStorageLocationRequest(Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "name must not be null");
        this.type = Objects.requireNonNull(builder.type, "type must not be null");
        this.scannableCodes = Objects.requireNonNull(builder.scannableCodes, "scannableCodes must not be null");
        this.runningSequences = Objects.requireNonNull(builder.runningSequences, "runningSequences must not be null");
        this.information = builder.information;
        this.tenantLocationId = builder.tenantLocationId;
        this.zoneRef = builder.zoneRef;
        this.zoneName = builder.zoneName;
        this.traitConfig = builder.traitConfig;
        this.traits = builder.traits;
        this.customAttributes = builder.customAttributes;
    }

    /**
     * Returns the display name of the storage location.
     *
     * @return the name
     */
    public String name() { return name; }

    /**
     * Returns the type of the storage location (e.g. {@code AISLE}, {@code RACK}).
     *
     * @return the type
     */
    public String type() { return type; }

    /**
     * Returns the barcodes or scannable identifiers for this storage location.
     *
     * @return the scannable codes
     */
    public List<String> scannableCodes() { return scannableCodes; }

    /**
     * Returns the running sequence items (picking and restow sequences) for this location.
     *
     * @return the running sequences
     */
    public List<StorageLocationSequenceItem> runningSequences() { return runningSequences; }

    /**
     * Returns optional textual information about this storage location.
     *
     * @return the information, or {@code null} if not set
     */
    public String information() { return information; }

    /**
     * Returns the tenant-specific storage location identifier.
     *
     * @return the tenant location ID, or {@code null} if not set
     */
    public String tenantLocationId() { return tenantLocationId; }

    /**
     * Returns the reference to the zone this storage location belongs to.
     *
     * @return the zone reference, or {@code null} if not set
     */
    public String zoneRef() { return zoneRef; }

    /**
     * Returns the name of the zone this storage location belongs to.
     *
     * @return the zone name, or {@code null} if not set
     */
    public String zoneName() { return zoneName; }

    /**
     * Returns the trait configuration entries defining per-trait enable/disable status.
     *
     * @return the trait config, or {@code null} if not set
     */
    public List<StorageLocationTraitConfigEntry> traitConfig() { return traitConfig; }

    /**
     * Returns the traits active on this storage location.
     *
     * @return the traits, or {@code null} if not set
     */
    public List<String> traits() { return traits; }

    /**
     * Returns free-form custom attributes.
     *
     * @return the custom attributes, or {@code null} if not set
     */
    public Map<String, Object> customAttributes() { return customAttributes; }

    /**
     * Returns a new {@link Builder} for constructing a {@code CreateStorageLocationRequest}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link CreateStorageLocationRequest}.
     */
    public static final class Builder {

        /** Creates a new Builder instance. */
        public Builder() {}

        private String name;
        private String type;
        private List<String> scannableCodes;
        private List<StorageLocationSequenceItem> runningSequences;
        private String information;
        private String tenantLocationId;
        private String zoneRef;
        private String zoneName;
        private List<StorageLocationTraitConfigEntry> traitConfig;
        private List<String> traits;
        private Map<String, Object> customAttributes;

        /**
         * Sets the display name of the storage location.
         *
         * @param name the name
         * @return this builder
         */
        public Builder name(String name) { this.name = name; return this; }

        /**
         * Sets the type of the storage location (e.g. {@code AISLE}, {@code RACK}).
         *
         * @param type the type
         * @return this builder
         */
        public Builder type(String type) { this.type = type; return this; }

        /**
         * Sets the barcodes or scannable identifiers for this storage location.
         *
         * @param scannableCodes the scannable codes
         * @return this builder
         */
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }

        /**
         * Sets the running sequence items (picking and restow sequences) for this location.
         *
         * @param runningSequences the running sequences
         * @return this builder
         */
        public Builder runningSequences(List<StorageLocationSequenceItem> runningSequences) { this.runningSequences = runningSequences; return this; }

        /**
         * Sets optional textual information about this storage location.
         *
         * @param information the information
         * @return this builder
         */
        public Builder information(String information) { this.information = information; return this; }

        /**
         * Sets the tenant-specific storage location identifier.
         *
         * @param tenantLocationId the tenant location ID
         * @return this builder
         */
        public Builder tenantLocationId(String tenantLocationId) { this.tenantLocationId = tenantLocationId; return this; }

        /**
         * Sets the reference to the zone this storage location belongs to.
         *
         * @param zoneRef the zone reference
         * @return this builder
         */
        public Builder zoneRef(String zoneRef) { this.zoneRef = zoneRef; return this; }

        /**
         * Sets the name of the zone this storage location belongs to.
         *
         * @param zoneName the zone name
         * @return this builder
         */
        public Builder zoneName(String zoneName) { this.zoneName = zoneName; return this; }

        /**
         * Sets the trait configuration entries defining per-trait enable/disable status.
         *
         * @param traitConfig the trait config
         * @return this builder
         */
        public Builder traitConfig(List<StorageLocationTraitConfigEntry> traitConfig) { this.traitConfig = traitConfig; return this; }

        /**
         * Sets the traits active on this storage location.
         *
         * @param traits the traits
         * @return this builder
         */
        public Builder traits(List<String> traits) { this.traits = traits; return this; }

        /**
         * Sets free-form custom attributes.
         *
         * @param customAttributes the custom attributes
         * @return this builder
         */
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        /**
         * Builds the {@link CreateStorageLocationRequest}. Throws {@link NullPointerException}
         * if any required field is absent.
         *
         * @return a new {@code CreateStorageLocationRequest}
         */
        public CreateStorageLocationRequest build() { return new CreateStorageLocationRequest(this); }
    }
}
