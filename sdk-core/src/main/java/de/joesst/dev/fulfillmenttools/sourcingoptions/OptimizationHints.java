package de.joesst.dev.fulfillmenttools.sourcingoptions;

/**
 * Hints to control how the sourcing options engine optimizes its calculation.
 *
 * @param includeCalculationHints Whether to include detailed calculation hints in the result.
 * @param requestedResultCount The requested number of sourcing options to return.
 */
public record OptimizationHints(Boolean includeCalculationHints, Integer requestedResultCount) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private Boolean includeCalculationHints;
        private Integer requestedResultCount;

        public Builder includeCalculationHints(Boolean includeCalculationHints) { this.includeCalculationHints = includeCalculationHints; return this; }
        public Builder requestedResultCount(Integer requestedResultCount) { this.requestedResultCount = requestedResultCount; return this; }

        public OptimizationHints build() {
            return new OptimizationHints(includeCalculationHints, requestedResultCount);
        }
    }
}
