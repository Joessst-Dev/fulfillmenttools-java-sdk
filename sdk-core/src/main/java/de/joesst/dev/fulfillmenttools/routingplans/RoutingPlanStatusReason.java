package de.joesst.dev.fulfillmenttools.routingplans;

/**
 * An obsolete status reason entry on a routing plan.
 *
 * <p>Maps to the {@code RoutingPlanStatusReason} schema in the fulfillmenttools OpenAPI spec.
 * This is marked as deprecated in the API; use {@link RoutingPlanHistory} for current status
 * tracking.
 *
 * <p>Known values for {@code reason}:
 * {@code REROUTE_AFTER_SHORTPICK}, {@code MANUALLY_REROUTED}, {@code MANUALLY_ASSIGNED},
 * {@code ORDER_MODIFIED}.
 *
 * <p>Known values for {@code status}: {@code OBSOLETE}.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param reason The reason code for this status entry.
 * @param status The associated status value.
 */
public record RoutingPlanStatusReason(
        String reason,
        String status
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String reason;
        private String status;

        private Builder() {}

        public Builder reason(String reason) { this.reason = reason; return this; }
        public Builder status(String status) { this.status = status; return this; }

        public RoutingPlanStatusReason build() {
            return new RoutingPlanStatusReason(reason, status);
        }
    }
}
