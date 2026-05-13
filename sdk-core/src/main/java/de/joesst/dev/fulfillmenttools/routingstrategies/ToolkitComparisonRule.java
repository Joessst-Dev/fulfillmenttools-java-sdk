package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * A V2 toolkit comparison rule that evaluates a set of {@link ToolkitComparisonPredicate}s,
 * comparing left-side entity properties against right-side entity properties.
 *
 * @param predicates          the comparison predicates (required, 1–100 items)
 * @param predicateConnector  optional boolean operator connecting multiple predicates
 * @param evaluationScope     optional scope controlling per-entity vs per-line-item evaluation
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ToolkitComparisonRule(
        List<ToolkitComparisonPredicate> predicates,
        ToolkitPredicateConnector predicateConnector,
        ToolkitRuleScope evaluationScope
) {

    /**
     * Returns a builder for constructing a {@code ToolkitComparisonRule}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link ToolkitComparisonRule}.
     */
    public static final class Builder {

        private List<ToolkitComparisonPredicate> predicates;
        private ToolkitPredicateConnector predicateConnector;
        private ToolkitRuleScope evaluationScope;

        private Builder() {}

        /**
         * Sets the comparison predicates.
         * @param predicates the predicates (1–100 items)
         * @return this builder
         */
        public Builder predicates(List<ToolkitComparisonPredicate> predicates) {
            this.predicates = predicates;
            return this;
        }

        /**
         * Sets the optional boolean operator connecting multiple predicates.
         * @param predicateConnector the predicate connector
         * @return this builder
         */
        public Builder predicateConnector(ToolkitPredicateConnector predicateConnector) {
            this.predicateConnector = predicateConnector;
            return this;
        }

        /**
         * Sets the optional scope controlling per-entity vs per-line-item evaluation.
         * @param evaluationScope the evaluation scope
         * @return this builder
         */
        public Builder evaluationScope(ToolkitRuleScope evaluationScope) {
            this.evaluationScope = evaluationScope;
            return this;
        }

        /**
         * Builds a {@link ToolkitComparisonRule}.
         *
         * @return a new instance.
         */
        public ToolkitComparisonRule build() {
            return new ToolkitComparisonRule(predicates, predicateConnector, evaluationScope);
        }
    }
}
