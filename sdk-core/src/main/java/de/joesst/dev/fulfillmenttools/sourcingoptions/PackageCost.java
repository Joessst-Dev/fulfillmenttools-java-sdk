package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.model.Money;

/**
 * Cost entry for a single packaging unit within the shipping costs breakdown.
 *
 * <p>Maps to the {@code PackageCost} schema in the fulfillmenttools OpenAPI specification.
 *
 * @param packageName the name of the package (e.g. {@code Small Box})
 * @param costAmount  the cost amount for this package
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PackageCost(
        String packageName,
        Money costAmount
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String packageName;
        private Money costAmount;

        public Builder packageName(String packageName) { this.packageName = packageName; return this; }
        public Builder costAmount(Money costAmount) { this.costAmount = costAmount; return this; }

        public PackageCost build() {
            return new PackageCost(packageName, costAmount);
        }
    }
}
