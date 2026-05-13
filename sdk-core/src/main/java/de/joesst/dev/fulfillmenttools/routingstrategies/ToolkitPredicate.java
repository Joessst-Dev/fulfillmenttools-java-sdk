package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * A predicate used in a toolkit rule (V1 style) that evaluates a property of an entity against
 * an expected value using the specified operator.
 *
 * @param entity            the entity whose property is evaluated
 * @param entityOperator    the operator to apply
 * @param propertyPath      dot-separated path to the entity property (required)
 * @param expectedValue     the value to compare against
 * @param transformation    optional transformation to apply to the property value before comparison
 * @param transformationArgs arguments for the transformation function
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ToolkitPredicate(
        ToolkitAllowedEntities entity,
        ToolkitEntityOperatorType entityOperator,
        String propertyPath,
        Object expectedValue,
        ToolkitTransformationType transformation,
        List<Object> transformationArgs
) {

    /**
     * Returns a builder for constructing a {@code ToolkitPredicate}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link ToolkitPredicate}.
     */
    public static final class Builder {

        private ToolkitAllowedEntities entity;
        private ToolkitEntityOperatorType entityOperator;
        private String propertyPath;
        private Object expectedValue;
        private ToolkitTransformationType transformation;
        private List<Object> transformationArgs;

        private Builder() {}

        /**
         * Sets the entity whose property is evaluated.
         * @param entity the entity
         * @return this builder
         */
        public Builder entity(ToolkitAllowedEntities entity) {
            this.entity = entity;
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
         * Sets the dot-separated path to the entity property.
         * @param propertyPath the property path
         * @return this builder
         */
        public Builder propertyPath(String propertyPath) {
            this.propertyPath = propertyPath;
            return this;
        }

        /**
         * Sets the value to compare against.
         * @param expectedValue the expected value
         * @return this builder
         */
        public Builder expectedValue(Object expectedValue) {
            this.expectedValue = expectedValue;
            return this;
        }

        /**
         * Sets the optional transformation to apply to the property value before comparison.
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
         * Builds a {@link ToolkitPredicate}.
         *
         * @return a new instance.
         */
        public ToolkitPredicate build() {
            return new ToolkitPredicate(
                    entity, entityOperator, propertyPath,
                    expectedValue, transformation, transformationArgs);
        }
    }
}
