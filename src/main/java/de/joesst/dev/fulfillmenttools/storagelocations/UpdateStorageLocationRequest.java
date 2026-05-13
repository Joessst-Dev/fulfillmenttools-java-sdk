package de.joesst.dev.fulfillmenttools.storagelocations;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private final Map<String, Object> customAttributes;

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

    public Integer version() { return version; }
    public String name() { return name; }
    public String type() { return type; }
    public List<String> scannableCodes() { return scannableCodes; }
    public List<StorageLocationSequenceItem> runningSequences() { return runningSequences; }
    public String information() { return information; }
    public String tenantLocationId() { return tenantLocationId; }
    public String zoneRef() { return zoneRef; }
    public List<StorageLocationTraitConfigEntry> traitConfig() { return traitConfig; }
    public Map<String, Object> customAttributes() { return customAttributes; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Integer version;
        private String name;
        private String type;
        private List<String> scannableCodes;
        private List<StorageLocationSequenceItem> runningSequences;
        private String information;
        private String tenantLocationId;
        private String zoneRef;
        private List<StorageLocationTraitConfigEntry> traitConfig;
        private Map<String, Object> customAttributes;

        public Builder version(Integer version) { this.version = version; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder type(String type) { this.type = type; return this; }
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }
        public Builder runningSequences(List<StorageLocationSequenceItem> runningSequences) { this.runningSequences = runningSequences; return this; }
        public Builder information(String information) { this.information = information; return this; }
        public Builder tenantLocationId(String tenantLocationId) { this.tenantLocationId = tenantLocationId; return this; }
        public Builder zoneRef(String zoneRef) { this.zoneRef = zoneRef; return this; }
        public Builder traitConfig(List<StorageLocationTraitConfigEntry> traitConfig) { this.traitConfig = traitConfig; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public UpdateStorageLocationRequest build() { return new UpdateStorageLocationRequest(this); }
    }
}
