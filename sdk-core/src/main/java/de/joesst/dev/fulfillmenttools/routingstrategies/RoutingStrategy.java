package de.joesst.dev.fulfillmenttools.routingstrategies;

import de.joesst.dev.fulfillmenttools.id.RoutingStrategyId;

import java.time.Instant;
import java.util.Map;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * A routing strategy that determines how fulfillment orders are routed to facilities.
 *
 * <p>Only one strategy can be active ({@code inUse}) at a time.
 *
 * @param id                  unique identifier
 * @param version             optimistic-locking version
 * @param created             timestamp when this strategy was created
 * @param lastModified        timestamp when this strategy was last modified
 * @param name                plain-text name
 * @param nameLocalized       localized name keyed by locale (e.g. {@code "en_US"})
 * @param inUse               whether this is the currently active strategy (required)
 * @param revision            the revision number of the strategy (required)
 * @param globalConfiguration global routing configuration applied across all nodes
 * @param rootNode            the root node of the routing decision tree
 * @param customAttributes    arbitrary key/value pairs set by the caller
 */
public record RoutingStrategy(
        RoutingStrategyId id,
        Integer version,
        Instant created,
        Instant lastModified,
        String name,
        Map<String, String> nameLocalized,
        Boolean inUse,
        Integer revision,
        RoutingStrategyGlobalConfiguration globalConfiguration,
        RoutingStrategyNode rootNode,
        CustomAttributes customAttributes
) {

    /**
     * Returns a builder for constructing a {@code RoutingStrategy}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link RoutingStrategy}.
     */
    public static final class Builder {

        private RoutingStrategyId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private String name;
        private Map<String, String> nameLocalized;
        private Boolean inUse;
        private Integer revision;
        private RoutingStrategyGlobalConfiguration globalConfiguration;
        private RoutingStrategyNode rootNode;
        private CustomAttributes customAttributes;

        private Builder() {}

        /**
         * Sets the unique identifier.
         * @param id the routing strategy ID
         * @return this builder
         */
        public Builder id(RoutingStrategyId id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the optimistic-locking version.
         * @param version the version number
         * @return this builder
         */
        public Builder version(Integer version) {
            this.version = version;
            return this;
        }

        /**
         * Sets the timestamp when this strategy was created.
         * @param created the creation timestamp
         * @return this builder
         */
        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        /**
         * Sets the timestamp when this strategy was last modified.
         * @param lastModified the last-modified timestamp
         * @return this builder
         */
        public Builder lastModified(Instant lastModified) {
            this.lastModified = lastModified;
            return this;
        }

        /**
         * Sets the plain-text name.
         * @param name the strategy name
         * @return this builder
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the localized name keyed by locale.
         * @param nameLocalized map of locale to localized name
         * @return this builder
         */
        public Builder nameLocalized(Map<String, String> nameLocalized) {
            this.nameLocalized = nameLocalized;
            return this;
        }

        /**
         * Sets whether this is the currently active strategy.
         * @param inUse the in-use flag
         * @return this builder
         */
        public Builder inUse(Boolean inUse) {
            this.inUse = inUse;
            return this;
        }

        /**
         * Sets the revision number.
         * @param revision the revision number
         * @return this builder
         */
        public Builder revision(Integer revision) {
            this.revision = revision;
            return this;
        }

        /**
         * Sets the global routing configuration applied across all nodes.
         * @param globalConfiguration the global configuration
         * @return this builder
         */
        public Builder globalConfiguration(RoutingStrategyGlobalConfiguration globalConfiguration) {
            this.globalConfiguration = globalConfiguration;
            return this;
        }

        /**
         * Sets the root node of the routing decision tree.
         * @param rootNode the root node
         * @return this builder
         */
        public Builder rootNode(RoutingStrategyNode rootNode) {
            this.rootNode = rootNode;
            return this;
        }

        /**
         * Sets the arbitrary key/value custom attributes.
         * @param customAttributes map of custom attributes
         * @return this builder
         */
        public Builder customAttributes(CustomAttributes customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        /**
         * Builds a {@link RoutingStrategy}.
         *
         * @return a new instance.
         */
        public RoutingStrategy build() {
            return new RoutingStrategy(
                    id, version, created, lastModified, name, nameLocalized,
                    inUse, revision, globalConfiguration, rootNode, customAttributes);
        }
    }
}
