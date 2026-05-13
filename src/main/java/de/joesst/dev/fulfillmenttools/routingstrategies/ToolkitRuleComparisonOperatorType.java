package de.joesst.dev.fulfillmenttools.routingstrategies;

/**
 * The operator type applied when comparing left and right predicates in a
 * {@link ToolkitComparisonPredicate}.
 */
public enum ToolkitRuleComparisonOperatorType {
    NO_MATCHES,
    ALL_MATCHES,
    LEFT_CONTAINS_RIGHT,
    RIGHT_CONTAINS_LEFT
}
