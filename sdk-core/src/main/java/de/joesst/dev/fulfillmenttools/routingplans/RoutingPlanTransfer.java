package de.joesst.dev.fulfillmenttools.routingplans;

/**
 * Describes a transfer relationship associated with a routing plan.
 *
 * <p>Maps to the {@code Transfer} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Known values for {@code transferType}: {@code SUPPLIER}, {@code RECEIVER}.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param transferId   The identifier of the transfer.
 * @param transferType The role of this routing plan in the transfer, either
 *                     {@code "SUPPLIER"} or {@code "RECEIVER"}.
 */
public record RoutingPlanTransfer(
        String transferId,
        String transferType
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String transferId;
        private String transferType;

        private Builder() {}

        public Builder transferId(String transferId) { this.transferId = transferId; return this; }
        public Builder transferType(String transferType) { this.transferType = transferType; return this; }

        public RoutingPlanTransfer build() {
            return new RoutingPlanTransfer(transferId, transferType);
        }
    }
}
