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

    /**
     * Configuration for the send-label behaviour.
     *
     * @param enabled Whether sending a label is enabled. Defaults to {@code false}.
     */
    public record SendLabelConfig(
            Boolean enabled
    ) {}
}
