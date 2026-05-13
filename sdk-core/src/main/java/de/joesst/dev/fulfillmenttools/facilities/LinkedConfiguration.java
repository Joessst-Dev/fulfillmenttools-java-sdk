package de.joesst.dev.fulfillmenttools.facilities;

/**
 * A reference to a linked configuration resource with relationship information.
 *
 * @param ref the URI or identifier of the linked configuration
 * @param rel the relationship type (e.g. "self", "parent", "config")
 */
public record LinkedConfiguration(String ref, String rel) {

    /**
     * Returns a builder for constructing a {@link LinkedConfiguration}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link LinkedConfiguration}.
     */
    public static final class Builder {

        private String ref;
        private String rel;

        private Builder() {}

        /**
         * @param ref the URI or identifier of the linked configuration
         * @return this builder
         */
        public Builder ref(String ref) {
            this.ref = ref;
            return this;
        }

        /**
         * @param rel the relationship type (e.g. "self", "parent", "config")
         * @return this builder
         */
        public Builder rel(String rel) {
            this.rel = rel;
            return this;
        }

        /**
         * Builds a {@link LinkedConfiguration}.
         *
         * @return a new instance
         */
        public LinkedConfiguration build() {
            return new LinkedConfiguration(ref, rel);
        }
    }
}
