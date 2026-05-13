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
) {}
