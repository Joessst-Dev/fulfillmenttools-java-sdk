package de.joesst.dev.fulfillmenttools.storagelocations;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    public String name() { return name; }
    public String type() { return type; }
    public List<String> scannableCodes() { return scannableCodes; }
    public List<StorageLocationSequenceItem> runningSequences() { return runningSequences; }
    public String information() { return information; }
    public String tenantLocationId() { return tenantLocationId; }
    public String zoneRef() { return zoneRef; }
    public String zoneName() { return zoneName; }
    public List<StorageLocationTraitConfigEntry> traitConfig() { return traitConfig; }
    public List<String> traits() { return traits; }
    public Map<String, Object> customAttributes() { return customAttributes; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

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

        public Builder name(String name) { this.name = name; return this; }
        public Builder type(String type) { this.type = type; return this; }
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }
        public Builder runningSequences(List<StorageLocationSequenceItem> runningSequences) { this.runningSequences = runningSequences; return this; }
        public Builder information(String information) { this.information = information; return this; }
        public Builder tenantLocationId(String tenantLocationId) { this.tenantLocationId = tenantLocationId; return this; }
        public Builder zoneRef(String zoneRef) { this.zoneRef = zoneRef; return this; }
        public Builder zoneName(String zoneName) { this.zoneName = zoneName; return this; }
        public Builder traitConfig(List<StorageLocationTraitConfigEntry> traitConfig) { this.traitConfig = traitConfig; return this; }
        public Builder traits(List<String> traits) { this.traits = traits; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public CreateStorageLocationRequest build() { return new CreateStorageLocationRequest(this); }
    }
}
