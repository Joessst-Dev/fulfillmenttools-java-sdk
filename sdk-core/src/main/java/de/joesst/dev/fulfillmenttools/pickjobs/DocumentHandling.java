package de.joesst.dev.fulfillmenttools.pickjobs;

/**
 * Document-handling configuration for a pick job, controlling whether labels should be sent.
 *
 * <p>Maps to the {@code DocumentHandling} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param sendLabel Configuration for whether a label should be sent for this pick job.
 */
public record DocumentHandling(
        SendLabelConfig sendLabel
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private SendLabelConfig sendLabel;

        private Builder() {}

        public Builder sendLabel(SendLabelConfig sendLabel) { this.sendLabel = sendLabel; return this; }

        public DocumentHandling build() {
            return new DocumentHandling(sendLabel);
        }
    }

    /**
     * Configuration for the send-label behaviour.
     *
     * @param enabled Whether sending a label is enabled. Defaults to {@code false}.
     */
    public record SendLabelConfig(
            Boolean enabled
    ) {

        public static Builder builder() {
            return new Builder();
        }

        public static final class Builder {
            private Boolean enabled;

            private Builder() {}

            public Builder enabled(Boolean enabled) { this.enabled = enabled; return this; }

            public SendLabelConfig build() {
                return new SendLabelConfig(enabled);
            }
        }
    }
}
