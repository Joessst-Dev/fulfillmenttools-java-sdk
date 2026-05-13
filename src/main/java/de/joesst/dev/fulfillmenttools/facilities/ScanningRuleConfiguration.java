package de.joesst.dev.fulfillmenttools.facilities;

import java.util.List;

/**
 * Scanning rule configuration for a facility, defining the priority and type of scans to be performed.
 *
 * @param values ordered list of scanning rule values by priority
 */
public record ScanningRuleConfiguration(List<ScanningRuleValue> values) {}
