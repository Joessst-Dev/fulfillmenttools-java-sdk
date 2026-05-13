package de.joesst.dev.fulfillmenttools.routingplans;

/**
 * A lightweight reference to a decision log entry on a routing plan.
 *
 * <p>Maps to the {@code DecisionLogRef} schema in the fulfillmenttools OpenAPI spec. The full
 * decision log can be retrieved via the URL contained in this reference.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param url        URL path to the decision log, e.g.
 *                   {@code /api/routingplans/{routingPlanId}/decisionlogs/{routingRun}}.
 * @param routingRun The routing run number this decision log belongs to.
 */
public record DecisionLogRef(
        String url,
        Double routingRun
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String url;
        private Double routingRun;

        private Builder() {}

        public Builder url(String url) { this.url = url; return this; }
        public Builder routingRun(Double routingRun) { this.routingRun = routingRun; return this; }

        public DecisionLogRef build() {
            return new DecisionLogRef(url, routingRun);
        }
    }
}
