package de.joesst.dev.fulfillmenttools.facilities;

import java.util.List;

/**
 * Scanning rule configuration for a facility, defining the priority and type of scans to be performed.
 *
 * @param values ordered list of scanning rule values by priority
 */
public record ScanningRuleConfiguration(List<ScanningRuleValue> values) {

    /**
     * Returns a builder for constructing a {@link ScanningRuleConfiguration}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link ScanningRuleConfiguration}.
     */
    public static final class Builder {

        private List<ScanningRuleValue> values;

        private Builder() {}

        /**
         * @param values ordered list of scanning rule values by priority
         * @return this builder
         */
        public Builder values(List<ScanningRuleValue> values) {
            this.values = values;
            return this;
        }

        /**
         * Builds a {@link ScanningRuleConfiguration}.
         *
         * @return a new instance
         */
        public ScanningRuleConfiguration build() {
            return new ScanningRuleConfiguration(values);
        }
    }
}
