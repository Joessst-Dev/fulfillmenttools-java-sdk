package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.CarrierId;

/**
 * Options controlling how delivery promises are calculated for sourcing options.
 *
 * <p>Maps to the {@code promisesOptions} field on {@code OrderForSourcingOptionsRequest}
 * in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param calculatePromises  Whether delivery promise times should be calculated.
 * @param carrierId          Optional carrier identifier to use for promise calculations.
 * @param carrierProductId   Optional carrier product identifier.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SourcingOptionsPromisesOptions(
        Boolean calculatePromises,
        CarrierId carrierId,
        String carrierProductId
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Boolean calculatePromises;
        private CarrierId carrierId;
        private String carrierProductId;

        private Builder() {}

        public Builder calculatePromises(Boolean calculatePromises) { this.calculatePromises = calculatePromises; return this; }
        public Builder carrierId(CarrierId carrierId) { this.carrierId = carrierId; return this; }
        public Builder carrierProductId(String carrierProductId) { this.carrierProductId = carrierProductId; return this; }

        public SourcingOptionsPromisesOptions build() {
            return new SourcingOptionsPromisesOptions(calculatePromises, carrierId, carrierProductId);
        }
    }
}
