package de.joesst.dev.fulfillmenttools.sourcingoptions;

/**
 * Hints to control how the sourcing options engine optimizes its calculation.
 *
 * @param includeCalculationHints Whether to include detailed calculation hints in the result.
 * @param requestedResultCount The requested number of sourcing options to return.
 */
public record OptimizationHints(Boolean includeCalculationHints, Integer requestedResultCount) {}
