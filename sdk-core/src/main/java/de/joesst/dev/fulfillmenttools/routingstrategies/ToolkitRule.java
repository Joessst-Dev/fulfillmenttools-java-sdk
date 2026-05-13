package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A V1 toolkit rule that evaluates two rule parts (left and right) against each other using
 * a rule operator.
 *
 * @param operator       the operator applied to left and right results (required)
 * @param leftPart       the left-side rule part (required)
 * @param rightPart      the right-side rule part
 * @param evaluationScope optional scope controlling whether evaluation is per entity or per line item
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ToolkitRule(
        ToolkitRuleOperatorType operator,
        ToolkitRulePart leftPart,
        ToolkitRulePart rightPart,
        ToolkitRuleScope evaluationScope
) {

    /**
     * Returns a builder for constructing a {@code ToolkitRule}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link ToolkitRule}.
     */
    public static final class Builder {

        private ToolkitRuleOperatorType operator;
        private ToolkitRulePart leftPart;
        private ToolkitRulePart rightPart;
        private ToolkitRuleScope evaluationScope;

        private Builder() {}

        /**
         * Sets the operator applied to left and right results.
         * @param operator the rule operator
         * @return this builder
         */
        public Builder operator(ToolkitRuleOperatorType operator) {
            this.operator = operator;
            return this;
        }

        /**
         * Sets the left-side rule part.
         * @param leftPart the left rule part
         * @return this builder
         */
        public Builder leftPart(ToolkitRulePart leftPart) {
            this.leftPart = leftPart;
            return this;
        }

        /**
         * Sets the right-side rule part.
         * @param rightPart the right rule part
         * @return this builder
         */
        public Builder rightPart(ToolkitRulePart rightPart) {
            this.rightPart = rightPart;
            return this;
        }

        /**
         * Sets the optional scope controlling whether evaluation is per entity or per line item.
         * @param evaluationScope the evaluation scope
         * @return this builder
         */
        public Builder evaluationScope(ToolkitRuleScope evaluationScope) {
            this.evaluationScope = evaluationScope;
            return this;
        }

        /**
         * Builds a {@link ToolkitRule}.
         *
         * @return a new instance.
         */
        public ToolkitRule build() {
            return new ToolkitRule(operator, leftPart, rightPart, evaluationScope);
        }
    }
}
