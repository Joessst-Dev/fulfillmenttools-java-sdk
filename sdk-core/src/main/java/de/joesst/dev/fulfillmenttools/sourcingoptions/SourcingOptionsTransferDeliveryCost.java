package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A delivery cost entry for a packaging unit, extending a monetary amount with a weight-based
 * cost coefficient.
 *
 * <p>Maps to the {@code SourcingOptionsTransferDeliveryCost} schema in the fulfillmenttools
 * OpenAPI specification. The schema inherits {@code Money} fields via {@code allOf}.
 *
 * @param value                   the monetary value
 * @param currency                the ISO 4217 currency code
 * @param decimalPlaces           the number of decimal places
 * @param deliveryCostCoefficient optional weight-based cost coefficient
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SourcingOptionsTransferDeliveryCost(
        Double value,
        String currency,
        Double decimalPlaces,
        SourcingOptionsTransferDeliverCostCoefficient deliveryCostCoefficient
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private Double value;
        private String currency;
        private Double decimalPlaces;
        private SourcingOptionsTransferDeliverCostCoefficient deliveryCostCoefficient;

        public Builder value(Double value) { this.value = value; return this; }
        public Builder currency(String currency) { this.currency = currency; return this; }
        public Builder decimalPlaces(Double decimalPlaces) { this.decimalPlaces = decimalPlaces; return this; }
        public Builder deliveryCostCoefficient(SourcingOptionsTransferDeliverCostCoefficient deliveryCostCoefficient) { this.deliveryCostCoefficient = deliveryCostCoefficient; return this; }

        public SourcingOptionsTransferDeliveryCost build() {
            return new SourcingOptionsTransferDeliveryCost(value, currency, decimalPlaces, deliveryCostCoefficient);
        }
    }
}
