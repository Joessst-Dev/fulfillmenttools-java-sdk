package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * A single predicate used within a {@link RoutingStrategyConditionRule}.
 *
 * @param propertyPath       dot-separated path to the property being evaluated (required)
 * @param entityOperator     the operator to apply (required)
 * @param expectedValue      the value to compare against; may be a string, number, or boolean
 * @param transformation     optional transformation to apply to the property value
 * @param transformationArgs arguments for the transformation function
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoutingStrategyConditionPredicate(
        String propertyPath,
        ToolkitEntityOperatorType entityOperator,
        Object expectedValue,
        ToolkitTransformationType transformation,
        List<Object> transformationArgs
) {

    /**
     * Returns a builder for constructing a {@code RoutingStrategyConditionPredicate}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link RoutingStrategyConditionPredicate}.
     */
    public static final class Builder {

        private String propertyPath;
        private ToolkitEntityOperatorType entityOperator;
        private Object expectedValue;
        private ToolkitTransformationType transformation;
        private List<Object> transformationArgs;

        private Builder() {}

        /**
         * Sets the dot-separated path to the property being evaluated.
         * @param propertyPath the property path
         * @return this builder
         */
        public Builder propertyPath(String propertyPath) {
            this.propertyPath = propertyPath;
            return this;
        }

        /**
         * Sets the operator to apply.
         * @param entityOperator the entity operator
         * @return this builder
         */
        public Builder entityOperator(ToolkitEntityOperatorType entityOperator) {
            this.entityOperator = entityOperator;
            return this;
        }

        /**
         * Sets the value to compare against.
         * @param expectedValue the expected value (string, number, or boolean)
         * @return this builder
         */
        public Builder expectedValue(Object expectedValue) {
            this.expectedValue = expectedValue;
            return this;
        }

        /**
         * Sets the optional transformation to apply to the property value.
         * @param transformation the transformation type
         * @return this builder
         */
        public Builder transformation(ToolkitTransformationType transformation) {
            this.transformation = transformation;
            return this;
        }

        /**
         * Sets the arguments for the transformation function.
         * @param transformationArgs the transformation arguments
         * @return this builder
         */
        public Builder transformationArgs(List<Object> transformationArgs) {
            this.transformationArgs = transformationArgs;
            return this;
        }

        /**
         * Builds a {@link RoutingStrategyConditionPredicate}.
         *
         * @return a new instance.
         */
        public RoutingStrategyConditionPredicate build() {
            return new RoutingStrategyConditionPredicate(
                    propertyPath, entityOperator, expectedValue, transformation, transformationArgs);
        }
    }
}
