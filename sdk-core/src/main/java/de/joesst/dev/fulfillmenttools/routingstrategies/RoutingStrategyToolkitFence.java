package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

/**
 * A custom (toolkit) routing strategy fence that filters candidate facilities based on a
 * user-defined rule.
 *
 * <p>Discriminator value: {@code "ToolkitFence"}
 *
 * @param type               discriminator field, always {@code "ToolkitFence"} (required)
 * @param active             whether this fence is active (default {@code false})
 * @param nameLocalized      localized display name
 * @param descriptionLocalized localized description
 * @param name               optional plain-text name
 * @param description        optional plain-text description
 * @param comparisonRule     a V2 comparison rule; mutually exclusive with {@code rule}
 * @param rule               a V1 toolkit rule; mutually exclusive with {@code comparisonRule}
 * @param order              execution order of this fence
 * @param referenceId        external reference identifier
 * @param entity1            deprecated — entity for the left side (use predicate-level entity instead)
 * @param entity2            deprecated — entity for the right side (use predicate-level entity instead)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoutingStrategyToolkitFence(
        String type,
        Boolean active,
        Map<String, String> nameLocalized,
        Map<String, String> descriptionLocalized,
        String name,
        String description,
        ToolkitComparisonRule comparisonRule,
        ToolkitRule rule,
        Integer order,
        String referenceId,
        ToolkitAllowedEntities entity1,
        ToolkitAllowedEntities entity2
) implements RoutingStrategyFence {

    /**
     * Returns a builder for constructing a {@code RoutingStrategyToolkitFence}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link RoutingStrategyToolkitFence}.
     */
    public static final class Builder {

        private String type;
        private Boolean active;
        private Map<String, String> nameLocalized;
        private Map<String, String> descriptionLocalized;
        private String name;
        private String description;
        private ToolkitComparisonRule comparisonRule;
        private ToolkitRule rule;
        private Integer order;
        private String referenceId;
        private ToolkitAllowedEntities entity1;
        private ToolkitAllowedEntities entity2;

        private Builder() {}

        /**
         * Sets the discriminator field.
         * @param type the type value, always {@code "ToolkitFence"}
         * @return this builder
         */
        public Builder type(String type) {
            this.type = type;
            return this;
        }

        /**
         * Sets whether this fence is active.
         * @param active the active flag (default {@code false})
         * @return this builder
         */
        public Builder active(Boolean active) {
            this.active = active;
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
         * Sets the V2 comparison rule; mutually exclusive with {@code rule}.
         * @param comparisonRule the comparison rule
         * @return this builder
         */
        public Builder comparisonRule(ToolkitComparisonRule comparisonRule) {
            this.comparisonRule = comparisonRule;
            return this;
        }

        /**
         * Sets the V1 toolkit rule; mutually exclusive with {@code comparisonRule}.
         * @param rule the toolkit rule
         * @return this builder
         */
        public Builder rule(ToolkitRule rule) {
            this.rule = rule;
            return this;
        }

        /**
         * Sets the execution order of this fence.
         * @param order the execution order
         * @return this builder
         */
        public Builder order(Integer order) {
            this.order = order;
            return this;
        }

        /**
         * Sets the external reference identifier.
         * @param referenceId the reference ID
         * @return this builder
         */
        public Builder referenceId(String referenceId) {
            this.referenceId = referenceId;
            return this;
        }

        /**
         * Sets the deprecated left-side entity.
         * @param entity1 the left-side entity
         * @return this builder
         */
        public Builder entity1(ToolkitAllowedEntities entity1) {
            this.entity1 = entity1;
            return this;
        }

        /**
         * Sets the deprecated right-side entity.
         * @param entity2 the right-side entity
         * @return this builder
         */
        public Builder entity2(ToolkitAllowedEntities entity2) {
            this.entity2 = entity2;
            return this;
        }

        /**
         * Builds a {@link RoutingStrategyToolkitFence}.
         *
         * @return a new instance.
         */
        public RoutingStrategyToolkitFence build() {
            return new RoutingStrategyToolkitFence(
                    type, active, nameLocalized, descriptionLocalized,
                    name, description, comparisonRule, rule,
                    order, referenceId, entity1, entity2);
        }
    }
}
