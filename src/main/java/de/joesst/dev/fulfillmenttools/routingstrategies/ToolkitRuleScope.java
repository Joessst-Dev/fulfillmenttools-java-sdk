package de.joesst.dev.fulfillmenttools.routingstrategies;

/**
 * The evaluation scope for a toolkit rule — whether the rule is evaluated for the whole entity
 * or per line item.
 */
public enum ToolkitRuleScope {
    /** Scope is the whole entity. */
    WHOLE_ENTITY,
    /** Scope is per line item. */
    LINE_ITEM
}
