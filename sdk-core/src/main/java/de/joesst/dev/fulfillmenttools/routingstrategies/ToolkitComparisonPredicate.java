package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * A predicate for a Toolkit V2 fence that compares a left-side entity property against a
 * right-side entity property using the specified operator.
 *
 * @param entityOperator        the comparison operator (required)
 * @param leftEntity            the entity for the left side (required)
 * @param leftPropertyPath      path to the left-side property (required)
 * @param leftTransformation    optional transformation for the left value
 * @param leftTransformationArgs arguments for the left transformation
 * @param rightEntity           the entity for the right side (required)
 * @param rightPropertyPath     path to the right-side property (required)
 * @param rightTransformation   optional transformation for the right value
 * @param rightTransformationArgs arguments for the right transformation
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ToolkitComparisonPredicate(
        ToolkitRuleComparisonOperatorType entityOperator,
        ToolkitAllowedEntities leftEntity,
        String leftPropertyPath,
        ToolkitTransformationType leftTransformation,
        List<Object> leftTransformationArgs,
        ToolkitAllowedEntities rightEntity,
        String rightPropertyPath,
        ToolkitTransformationType rightTransformation,
        List<Object> rightTransformationArgs
) {

    /**
     * Returns a builder for constructing a {@code ToolkitComparisonPredicate}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link ToolkitComparisonPredicate}.
     */
    public static final class Builder {

        private ToolkitRuleComparisonOperatorType entityOperator;
        private ToolkitAllowedEntities leftEntity;
        private String leftPropertyPath;
        private ToolkitTransformationType leftTransformation;
        private List<Object> leftTransformationArgs;
        private ToolkitAllowedEntities rightEntity;
        private String rightPropertyPath;
        private ToolkitTransformationType rightTransformation;
        private List<Object> rightTransformationArgs;

        private Builder() {}

        /**
         * Sets the comparison operator.
         * @param entityOperator the comparison operator
         * @return this builder
         */
        public Builder entityOperator(ToolkitRuleComparisonOperatorType entityOperator) {
            this.entityOperator = entityOperator;
            return this;
        }

        /**
         * Sets the entity for the left side.
         * @param leftEntity the left-side entity
         * @return this builder
         */
        public Builder leftEntity(ToolkitAllowedEntities leftEntity) {
            this.leftEntity = leftEntity;
            return this;
        }

        /**
         * Sets the path to the left-side property.
         * @param leftPropertyPath the left property path
         * @return this builder
         */
        public Builder leftPropertyPath(String leftPropertyPath) {
            this.leftPropertyPath = leftPropertyPath;
            return this;
        }

        /**
         * Sets the optional transformation for the left value.
         * @param leftTransformation the left transformation type
         * @return this builder
         */
        public Builder leftTransformation(ToolkitTransformationType leftTransformation) {
            this.leftTransformation = leftTransformation;
            return this;
        }

        /**
         * Sets the arguments for the left transformation.
         * @param leftTransformationArgs the left transformation arguments
         * @return this builder
         */
        public Builder leftTransformationArgs(List<Object> leftTransformationArgs) {
            this.leftTransformationArgs = leftTransformationArgs;
            return this;
        }

        /**
         * Sets the entity for the right side.
         * @param rightEntity the right-side entity
         * @return this builder
         */
        public Builder rightEntity(ToolkitAllowedEntities rightEntity) {
            this.rightEntity = rightEntity;
            return this;
        }

        /**
         * Sets the path to the right-side property.
         * @param rightPropertyPath the right property path
         * @return this builder
         */
        public Builder rightPropertyPath(String rightPropertyPath) {
            this.rightPropertyPath = rightPropertyPath;
            return this;
        }

        /**
         * Sets the optional transformation for the right value.
         * @param rightTransformation the right transformation type
         * @return this builder
         */
        public Builder rightTransformation(ToolkitTransformationType rightTransformation) {
            this.rightTransformation = rightTransformation;
            return this;
        }

        /**
         * Sets the arguments for the right transformation.
         * @param rightTransformationArgs the right transformation arguments
         * @return this builder
         */
        public Builder rightTransformationArgs(List<Object> rightTransformationArgs) {
            this.rightTransformationArgs = rightTransformationArgs;
            return this;
        }

        /**
         * Builds a {@link ToolkitComparisonPredicate}.
         *
         * @return a new instance.
         */
        public ToolkitComparisonPredicate build() {
            return new ToolkitComparisonPredicate(
                    entityOperator, leftEntity, leftPropertyPath,
                    leftTransformation, leftTransformationArgs,
                    rightEntity, rightPropertyPath,
                    rightTransformation, rightTransformationArgs);
        }
    }
}
