package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.joesst.dev.fulfillmenttools.model.Money;

/**
 * A delivery cost entry for a packaging unit, extending a monetary amount with a weight-based
 * cost coefficient.
 *
 * <p>Maps to the {@code SourcingOptionsTransferDeliveryCost} schema in the fulfillmenttools
 * OpenAPI specification. The API schema uses {@code allOf: [Money]}, meaning the monetary
 * fields ({@code value}, {@code currency}, {@code decimalPlaces}) appear at the same JSON level
 * as {@code deliveryCostCoefficient}. {@link JsonUnwrapped} handles flattening for serialization;
 * {@link JsonCreator} handles reading the flat fields for deserialization.
 *
 * @param money                   the monetary amount (value, currency, decimalPlaces)
 * @param deliveryCostCoefficient optional weight-based cost coefficient
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class SourcingOptionsTransferDeliveryCost {

    @JsonUnwrapped
    private final Money money;
    private final SourcingOptionsTransferDeliverCostCoefficient deliveryCostCoefficient;

    private SourcingOptionsTransferDeliveryCost(Money money, SourcingOptionsTransferDeliverCostCoefficient deliveryCostCoefficient) {
        this.money = money;
        this.deliveryCostCoefficient = deliveryCostCoefficient;
    }

    @JsonCreator
    static SourcingOptionsTransferDeliveryCost fromJson(
            @JsonProperty("value") Double value,
            @JsonProperty("currency") String currency,
            @JsonProperty("decimalPlaces") Double decimalPlaces,
            @JsonProperty("deliveryCostCoefficient") SourcingOptionsTransferDeliverCostCoefficient deliveryCostCoefficient) {
        return new SourcingOptionsTransferDeliveryCost(
                Money.builder().value(value).currency(currency).decimalPlaces(decimalPlaces).build(),
                deliveryCostCoefficient);
    }

    public Money money() { return money; }
    public SourcingOptionsTransferDeliverCostCoefficient deliveryCostCoefficient() { return deliveryCostCoefficient; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private Builder() {}

        private Money money;
        private SourcingOptionsTransferDeliverCostCoefficient deliveryCostCoefficient;

        public Builder money(Money money) { this.money = money; return this; }
        public Builder deliveryCostCoefficient(SourcingOptionsTransferDeliverCostCoefficient deliveryCostCoefficient) {
            this.deliveryCostCoefficient = deliveryCostCoefficient; return this;
        }

        public SourcingOptionsTransferDeliveryCost build() {
            return new SourcingOptionsTransferDeliveryCost(money, deliveryCostCoefficient);
        }
    }
}
