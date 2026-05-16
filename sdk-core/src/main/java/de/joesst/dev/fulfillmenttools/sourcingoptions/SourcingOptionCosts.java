package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.model.Money;

import java.util.List;

/**
 * Aggregated cost breakdown for a sourcing option.
 *
 * <p>Maps to the {@code SourcingOptionCosts} schema in the fulfillmenttools OpenAPI spec.
 *
 * @param salesPrices             per-article sales prices with applied discounts
 * @param totalCosts              total cost amount
 * @param totalSalesPriceAmount   total sales price amount
 * @param totalShippingCosts      shipping cost breakdown
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SourcingOptionCosts(
        List<SourcingOptionSalesPrice> salesPrices,
        Money totalCosts,
        Money totalSalesPriceAmount,
        ShippingCosts totalShippingCosts
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private List<SourcingOptionSalesPrice> salesPrices;
        private Money totalCosts;
        private Money totalSalesPriceAmount;
        private ShippingCosts totalShippingCosts;

        public Builder salesPrices(List<SourcingOptionSalesPrice> salesPrices) { this.salesPrices = salesPrices; return this; }
        public Builder totalCosts(Money totalCosts) { this.totalCosts = totalCosts; return this; }
        public Builder totalSalesPriceAmount(Money totalSalesPriceAmount) { this.totalSalesPriceAmount = totalSalesPriceAmount; return this; }
        public Builder totalShippingCosts(ShippingCosts totalShippingCosts) { this.totalShippingCosts = totalShippingCosts; return this; }

        public SourcingOptionCosts build() {
            return new SourcingOptionCosts(salesPrices, totalCosts, totalSalesPriceAmount, totalShippingCosts);
        }
    }
}
