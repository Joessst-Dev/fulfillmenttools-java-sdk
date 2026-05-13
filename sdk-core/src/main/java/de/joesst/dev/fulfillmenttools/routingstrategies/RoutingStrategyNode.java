package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

/**
 * A node in the routing strategy decision tree. Nodes hold the actual fence/rating configuration
 * and can chain to further conditions via {@code nextCondition}.
 *
 * @param id                   unique identifier of this node (required on read)
 * @param active               whether this node is active (required)
 * @param config               the fence/rating/split/reroute configuration for this node (required)
 * @param nameLocalized        localized display name (required)
 * @param descriptionLocalized localized description
 * @param name                 optional plain-text name
 * @param description          optional plain-text description
 * @param categoryRef          optional reference to a node config category
 * @param nextCondition        optional condition chain evaluated after this node
 * @param activationTimeFrames time frames during which this node is active (max 1)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoutingStrategyNode(
        String id,
        Boolean active,
        RoutingStrategyNodeConfig config,
        Map<String, String> nameLocalized,
        Map<String, String> descriptionLocalized,
        String name,
        String description,
        String categoryRef,
        RoutingStrategyCondition nextCondition,
        List<ActivationTimeFrame> activationTimeFrames
) {

    /**
     * Returns a builder for constructing a {@code RoutingStrategyNode}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link RoutingStrategyNode}.
     */
    public static final class Builder {

        private String id;
        private Boolean active;
        private RoutingStrategyNodeConfig config;
        private Map<String, String> nameLocalized;
        private Map<String, String> descriptionLocalized;
        private String name;
        private String description;
        private String categoryRef;
        private RoutingStrategyCondition nextCondition;
        private List<ActivationTimeFrame> activationTimeFrames;

        private Builder() {}

        /**
         * Sets the unique identifier of this node.
         * @param id the node ID
         * @return this builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets whether this node is active.
         * @param active the active flag
         * @return this builder
         */
        public Builder active(Boolean active) {
            this.active = active;
            return this;
        }

        /**
         * Sets the fence/rating/split/reroute configuration for this node.
         * @param config the node configuration
         * @return this builder
         */
        public Builder config(RoutingStrategyNodeConfig config) {
            this.config = config;
            return this;
        }

        /**
         * Sets the localized display name.
         * @param nameLocalized map of locale to localized name
         * @return this builder
         */
        public Builder nameLocalized(Map<String, String> nameLocalized) {
            this.nameLocalized = nameLocalized;
            return this;
        }

        /**
         * Sets the localized description.
         * @param descriptionLocalized map of locale to localized description
         * @return this builder
         */
        public Builder descriptionLocalized(Map<String, String> descriptionLocalized) {
            this.descriptionLocalized = descriptionLocalized;
            return this;
        }

        /**
         * Sets the optional plain-text name.
         * @param name the plain-text name
         * @return this builder
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the optional plain-text description.
         * @param description the plain-text description
         * @return this builder
         */
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the optional reference to a node config category.
         * @param categoryRef the category reference
         * @return this builder
         */
        public Builder categoryRef(String categoryRef) {
            this.categoryRef = categoryRef;
            return this;
        }

        /**
         * Sets the optional condition chain evaluated after this node.
         * @param nextCondition the next condition
         * @return this builder
         */
        public Builder nextCondition(RoutingStrategyCondition nextCondition) {
            this.nextCondition = nextCondition;
            return this;
        }

        /**
         * Sets the time frames during which this node is active.
         * @param activationTimeFrames the activation time frames (max 1)
         * @return this builder
         */
        public Builder activationTimeFrames(List<ActivationTimeFrame> activationTimeFrames) {
            this.activationTimeFrames = activationTimeFrames;
            return this;
        }

        /**
         * Builds a {@link RoutingStrategyNode}.
         *
         * @return a new instance.
         */
        public RoutingStrategyNode build() {
            return new RoutingStrategyNode(
                    id, active, config, nameLocalized, descriptionLocalized,
                    name, description, categoryRef, nextCondition, activationTimeFrames);
        }
    }
}
