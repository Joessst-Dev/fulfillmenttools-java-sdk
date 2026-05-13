package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * One half of a {@link ToolkitRule}, containing predicates and an optional boolean connector.
 *
 * @param predicates         the predicates for this rule part (required, 1–100 items)
 * @param predicateConnector optional boolean operator connecting multiple predicates
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ToolkitRulePart(
        List<ToolkitPredicate> predicates,
        ToolkitPredicateConnector predicateConnector
) {

    /**
     * Returns a builder for constructing a {@code ToolkitRulePart}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link ToolkitRulePart}.
     */
    public static final class Builder {

        private List<ToolkitPredicate> predicates;
        private ToolkitPredicateConnector predicateConnector;

        private Builder() {}

        /**
         * Sets the predicates for this rule part.
         * @param predicates the predicates (1–100 items)
         * @return this builder
         */
        public Builder predicates(List<ToolkitPredicate> predicates) {
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
         * Builds a {@link ToolkitRulePart}.
         *
         * @return a new instance.
         */
        public ToolkitRulePart build() {
            return new ToolkitRulePart(predicates, predicateConnector);
        }
    }
}
