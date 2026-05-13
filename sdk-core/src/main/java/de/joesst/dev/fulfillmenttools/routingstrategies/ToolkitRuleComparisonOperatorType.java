package de.joesst.dev.fulfillmenttools.routingstrategies;

/**
 * The operator type applied when comparing left and right predicates in a
 * {@link ToolkitComparisonPredicate}.
 */
public enum ToolkitRuleComparisonOperatorType {
    /** No matches operator. */
    NO_MATCHES,
    /** All matches operator. */
    ALL_MATCHES,
    /** Left contains right operator. */
    LEFT_CONTAINS_RIGHT,
    /** Right contains left operator. */
    RIGHT_CONTAINS_LEFT
}
