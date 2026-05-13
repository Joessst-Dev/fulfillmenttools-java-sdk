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
) {}
