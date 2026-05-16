package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.model.Money;

import java.util.List;

/**
 * Packaging information for a transfer, describing how items are packed and the associated cost.
 *
 * <p>Maps to the {@code SourcingOptionsTransferPackagingInformation} schema in the
 * fulfillmenttools OpenAPI specification.
 *
 * @param packagingUnit the packaging unit used
 * @param totalCosts    total cost for this packaging
 * @param packedItems   items packed in this packaging unit
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SourcingOptionsTransferPackagingInformation(
        SourcingOptionsTransferPackagingUnit packagingUnit,
        Money totalCosts,
        List<HandledItem> packedItems
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private SourcingOptionsTransferPackagingUnit packagingUnit;
        private Money totalCosts;
        private List<HandledItem> packedItems;

        public Builder packagingUnit(SourcingOptionsTransferPackagingUnit packagingUnit) { this.packagingUnit = packagingUnit; return this; }
        public Builder totalCosts(Money totalCosts) { this.totalCosts = totalCosts; return this; }
        public Builder packedItems(List<HandledItem> packedItems) { this.packedItems = packedItems; return this; }

        public SourcingOptionsTransferPackagingInformation build() {
            return new SourcingOptionsTransferPackagingInformation(packagingUnit, totalCosts, packedItems);
        }
    }
}
