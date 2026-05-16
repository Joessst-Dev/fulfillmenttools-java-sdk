package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.joesst.dev.fulfillmenttools.model.Money;

/**
 * A delivery cost entry for a packaging unit, extending a monetary amount with a weight-based
 * cost coefficient.
 *
 * <p>Maps to the {@code SourcingOptionsTransferDeliveryCost} schema in the fulfillmenttools
 * OpenAPI specification. The API schema uses {@code allOf: [Money]}, meaning the monetary
 * fields ({@code value}, {@code currency}, {@code decimalPlaces}) appear at the same JSON level
 * as {@code deliveryCostCoefficient}. {@link JsonCreator} reads the flat fields for deserialization
 * and composes them into a {@link Money} object; explicit {@link JsonProperty} getters write them
 * back at the same level for serialization.
 *
 * @param money                   the monetary amount (value, currency, decimalPlaces)
 * @param deliveryCostCoefficient optional weight-based cost coefficient
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class SourcingOptionsTransferDeliveryCost {

    private final Money money;
    private final SourcingOptionsTransferDeliverCostCoefficient deliveryCostCoefficient;

    private SourcingOptionsTransferDeliveryCost(Money money, SourcingOptionsTransferDeliverCostCoefficient deliveryCostCoefficient) {
        this.money = money;
        this.deliveryCostCoefficient = deliveryCostCoefficient;
    }

    @JsonCreator
    public static SourcingOptionsTransferDeliveryCost fromJson(
            @JsonProperty("value") Double value,
            @JsonProperty("currency") String currency,
            @JsonProperty("decimalPlaces") Double decimalPlaces,
            @JsonProperty("deliveryCostCoefficient") SourcingOptionsTransferDeliverCostCoefficient deliveryCostCoefficient) {
        return new SourcingOptionsTransferDeliveryCost(
                Money.builder().value(value).currency(currency).decimalPlaces(decimalPlaces).build(),
                deliveryCostCoefficient);
    }

    /** Returns the composed monetary amount. Serialized as flat {@code value}/{@code currency}/{@code decimalPlaces} fields. */
    @JsonIgnore
    public Money money() { return money; }

    public SourcingOptionsTransferDeliverCostCoefficient deliveryCostCoefficient() { return deliveryCostCoefficient; }

    @JsonProperty("value")
    public Double value() { return money != null ? money.value() : null; }

    @JsonProperty("currency")
    public String currency() { return money != null ? money.currency() : null; }

    @JsonProperty("decimalPlaces")
    public Double decimalPlaces() { return money != null ? money.decimalPlaces() : null; }

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
