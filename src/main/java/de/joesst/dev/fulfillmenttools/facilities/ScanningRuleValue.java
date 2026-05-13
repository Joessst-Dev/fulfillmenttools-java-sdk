package de.joesst.dev.fulfillmenttools.facilities;

/**
 * A scanning rule with a priority level.
 *
 * @param priority selection priority — lower values are applied first
 * @param scanningRuleType type of scan to perform (e.g. "ARTICLE", "PICKING_UNIT", "CONTAINER")
 */
public record ScanningRuleValue(Double priority, String scanningRuleType) {}
