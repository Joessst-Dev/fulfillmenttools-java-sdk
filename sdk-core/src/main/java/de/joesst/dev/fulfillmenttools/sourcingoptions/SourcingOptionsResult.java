package de.joesst.dev.fulfillmenttools.sourcingoptions;

import de.joesst.dev.fulfillmenttools.id.SourcingOptionsRequestId;

import java.util.List;

/**
 * The result of evaluating sourcing options for an order.
 *
 * <p>Contains computed feasible sourcing options and the request ID that produced them.
 *
 * @param id The unique identifier of the sourcing options request.
 * @param options The list of feasible sourcing options for the order.
 */
public record SourcingOptionsResult(
        SourcingOptionsRequestId id,
        List<SourcingOption> options
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private SourcingOptionsRequestId id;
        private List<SourcingOption> options;

        public Builder id(SourcingOptionsRequestId id) { this.id = id; return this; }
        public Builder options(List<SourcingOption> options) { this.options = options; return this; }

        public SourcingOptionsResult build() {
            return new SourcingOptionsResult(id, options);
        }
    }
}
