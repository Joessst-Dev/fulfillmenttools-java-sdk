package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

/**
 * A condition in a routing strategy that evaluates a rule and, depending on the result, routes
 * to either a next condition or a next node.
 *
 * @param id                 unique identifier of this condition (required on read)
 * @param active             whether this condition is currently active (required)
 * @param rule               the condition rule to evaluate (required)
 * @param nameLocalized      localized display name (required)
 * @param descriptionLocalized localized description
 * @param name               optional plain-text name
 * @param description        optional plain-text description
 * @param nextNode           the node to route to when the rule matches (required)
 * @param nextCondition      the next condition to evaluate when the rule does not match
 * @param activationTimeFrames time frames during which this condition is active (max 1)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoutingStrategyCondition(
        String id,
        Boolean active,
        RoutingStrategyConditionRule rule,
        Map<String, String> nameLocalized,
        Map<String, String> descriptionLocalized,
        String name,
        String description,
        RoutingStrategyNode nextNode,
        RoutingStrategyCondition nextCondition,
        List<ActivationTimeFrame> activationTimeFrames
) {

    /**
     * Returns a builder for constructing a {@code RoutingStrategyCondition}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link RoutingStrategyCondition}.
     */
    public static final class Builder {

        private String id;
        private Boolean active;
        private RoutingStrategyConditionRule rule;
        private Map<String, String> nameLocalized;
        private Map<String, String> descriptionLocalized;
        private String name;
        private String description;
        private RoutingStrategyNode nextNode;
        private RoutingStrategyCondition nextCondition;
        private List<ActivationTimeFrame> activationTimeFrames;

        private Builder() {}

        /**
         * Sets the unique identifier of this condition.
         * @param id the condition ID
         * @return this builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets whether this condition is currently active.
         * @param active the active flag
         * @return this builder
         */
        public Builder active(Boolean active) {
            this.active = active;
            return this;
        }

        /**
         * Sets the condition rule to evaluate.
         * @param rule the condition rule
         * @return this builder
         */
        public Builder rule(RoutingStrategyConditionRule rule) {
            this.rule = rule;
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
         * Sets the node to route to when the rule matches.
         * @param nextNode the next node
         * @return this builder
         */
        public Builder nextNode(RoutingStrategyNode nextNode) {
            this.nextNode = nextNode;
            return this;
        }

        /**
         * Sets the next condition to evaluate when the rule does not match.
         * @param nextCondition the next condition
         * @return this builder
         */
        public Builder nextCondition(RoutingStrategyCondition nextCondition) {
            this.nextCondition = nextCondition;
            return this;
        }

        /**
         * Sets the time frames during which this condition is active.
         * @param activationTimeFrames the activation time frames (max 1)
         * @return this builder
         */
        public Builder activationTimeFrames(List<ActivationTimeFrame> activationTimeFrames) {
            this.activationTimeFrames = activationTimeFrames;
            return this;
        }

        /**
         * Builds a {@link RoutingStrategyCondition}.
         *
         * @return a new instance.
         */
        public RoutingStrategyCondition build() {
            return new RoutingStrategyCondition(
                    id, active, rule, nameLocalized, descriptionLocalized,
                    name, description, nextNode, nextCondition, activationTimeFrames);
        }
    }
}
