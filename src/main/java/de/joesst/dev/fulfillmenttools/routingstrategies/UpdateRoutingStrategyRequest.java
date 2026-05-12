package de.joesst.dev.fulfillmenttools.routingstrategies;

import java.util.Map;
import java.util.Objects;

public final class UpdateRoutingStrategyRequest {

    private final Integer version;
    private final Map<String, Object> nameLocalized;
    private final Map<String, Object> rootNode;
    private final Map<String, Object> globalConfiguration;

    private UpdateRoutingStrategyRequest(Builder builder) {
        this.version = Objects.requireNonNull(builder.version, "version must not be null");
        this.nameLocalized = Objects.requireNonNull(builder.nameLocalized, "nameLocalized must not be null");
        this.rootNode = Objects.requireNonNull(builder.rootNode, "rootNode must not be null");
        this.globalConfiguration = builder.globalConfiguration;
    }

    public Integer version() { return version; }
    public Map<String, Object> nameLocalized() { return nameLocalized; }
    public Map<String, Object> rootNode() { return rootNode; }
    public Map<String, Object> globalConfiguration() { return globalConfiguration; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Integer version;
        private Map<String, Object> nameLocalized;
        private Map<String, Object> rootNode;
        private Map<String, Object> globalConfiguration;

        public Builder version(Integer version) { this.version = version; return this; }
        public Builder nameLocalized(Map<String, Object> nameLocalized) { this.nameLocalized = nameLocalized; return this; }
        public Builder rootNode(Map<String, Object> rootNode) { this.rootNode = rootNode; return this; }
        public Builder globalConfiguration(Map<String, Object> globalConfiguration) { this.globalConfiguration = globalConfiguration; return this; }

        public UpdateRoutingStrategyRequest build() { return new UpdateRoutingStrategyRequest(this); }
    }
}
