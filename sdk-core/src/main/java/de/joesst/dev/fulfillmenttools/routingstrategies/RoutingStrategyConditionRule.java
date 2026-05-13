package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * The rule that determines whether a routing strategy condition evaluates to {@code true}.
 *
 * @param predicates         the condition predicates (required, 1–100 items)
 * @param predicateConnector optional boolean operator connecting multiple predicates
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoutingStrategyConditionRule(
        List<RoutingStrategyConditionPredicate> predicates,
        ToolkitPredicateConnector predicateConnector
) {

    /**
     * Returns a builder for constructing a {@code RoutingStrategyConditionRule}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link RoutingStrategyConditionRule}.
     */
    public static final class Builder {

        private List<RoutingStrategyConditionPredicate> predicates;
        private ToolkitPredicateConnector predicateConnector;

        private Builder() {}

        /**
         * Sets the condition predicates.
         * @param predicates the predicates (1–100 items)
         * @return this builder
         */
        public Builder predicates(List<RoutingStrategyConditionPredicate> predicates) {
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
         * Builds a {@link RoutingStrategyConditionRule}.
         *
         * @return a new instance.
         */
        public RoutingStrategyConditionRule build() {
            return new RoutingStrategyConditionRule(predicates, predicateConnector);
        }
    }
}
