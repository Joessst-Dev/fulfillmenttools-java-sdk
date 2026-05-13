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
) {}
