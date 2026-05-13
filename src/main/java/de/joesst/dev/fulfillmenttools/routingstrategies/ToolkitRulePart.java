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
) {}
