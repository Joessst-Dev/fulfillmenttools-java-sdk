package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

/**
 * A condition in a routing strategy that evaluates a rule and, depending on the result, routes
 * to either a next condition or a next node.
 *
 * @param id                 unique identifier of this condition (required on read)
 * @param active             whether this condition is currently active (required)
 * @param rule               the condition rule to evaluate (required)
 * @param nameLocalized      localized display name (required)
 * @param descriptionLocalized localized description
 * @param name               optional plain-text name
 * @param description        optional plain-text description
 * @param nextNode           the node to route to when the rule matches (required)
 * @param nextCondition      the next condition to evaluate when the rule does not match
 * @param activationTimeFrames time frames during which this condition is active (max 1)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoutingStrategyCondition(
        String id,
        Boolean active,
        RoutingStrategyConditionRule rule,
        Map<String, String> nameLocalized,
        Map<String, String> descriptionLocalized,
        String name,
        String description,
        RoutingStrategyNode nextNode,
        RoutingStrategyCondition nextCondition,
        List<ActivationTimeFrame> activationTimeFrames
) {}
