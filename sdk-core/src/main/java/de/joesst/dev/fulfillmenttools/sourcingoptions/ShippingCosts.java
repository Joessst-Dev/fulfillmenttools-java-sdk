package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.model.Money;

import java.util.List;

/**
 * Shipping cost breakdown for a sourcing option.
 *
 * <p>Maps to the {@code ShippingCosts} schema in the fulfillmenttools OpenAPI specification.
 *
 * @param totalTransportCostAmount total transport cost across all packages
 * @param packageCosts             per-package cost breakdown
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ShippingCosts(
        Money totalTransportCostAmount,
        List<PackageCost> packageCosts
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private Money totalTransportCostAmount;
        private List<PackageCost> packageCosts;

        public Builder totalTransportCostAmount(Money totalTransportCostAmount) { this.totalTransportCostAmount = totalTransportCostAmount; return this; }
        public Builder packageCosts(List<PackageCost> packageCosts) { this.packageCosts = packageCosts; return this; }

        public ShippingCosts build() {
            return new ShippingCosts(totalTransportCostAmount, packageCosts);
        }
    }
}
