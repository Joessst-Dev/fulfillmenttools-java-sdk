package de.joesst.dev.fulfillmenttools.routingstrategies;

/**
 * The transformations available to apply on an entity property value before a predicate is evaluated.
 */
public enum ToolkitTransformationType {
    /** Substring transformation. */
    SUBSTRING,
    /** Count transformation. */
    COUNT,
    /** Sum transformation. */
    SUM,
    /** Last transformation. */
    LAST
}
