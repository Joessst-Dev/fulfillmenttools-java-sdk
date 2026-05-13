package de.joesst.dev.fulfillmenttools.facilities;

/**
 * A scanning rule with a priority level.
 *
 * @param priority selection priority — lower values are applied first
 * @param scanningRuleType type of scan to perform (e.g. "ARTICLE", "PICKING_UNIT", "CONTAINER")
 */
public record ScanningRuleValue(Double priority, String scanningRuleType) {

    /**
     * Returns a builder for constructing a {@link ScanningRuleValue}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link ScanningRuleValue}.
     */
    public static final class Builder {

        private Double priority;
        private String scanningRuleType;

        private Builder() {}

        /**
         * @param priority selection priority — lower values are applied first
         * @return this builder
         */
        public Builder priority(Double priority) {
            this.priority = priority;
            return this;
        }

        /**
         * @param scanningRuleType type of scan to perform (e.g. "ARTICLE", "PICKING_UNIT", "CONTAINER")
         * @return this builder
         */
        public Builder scanningRuleType(String scanningRuleType) {
            this.scanningRuleType = scanningRuleType;
            return this;
        }

        /**
         * Builds a {@link ScanningRuleValue}.
         *
         * @return a new instance
         */
        public ScanningRuleValue build() {
            return new ScanningRuleValue(priority, scanningRuleType);
        }
    }
}
