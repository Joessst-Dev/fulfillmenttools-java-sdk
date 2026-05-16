package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscountValue;
import de.joesst.dev.fulfillmenttools.model.Money;

import java.util.List;

/**
 * Sales price information for an article within a sourcing option, including any applied discounts.
 *
 * <p>Maps to the {@code SourcingOptionSalesPrice} schema in the fulfillmenttools OpenAPI
 * specification.
 *
 * @param tenantArticleId                        the tenant article identifier
 * @param amount                                 the quantity of the article in the solution
 * @param sourceFacilityRef                      facility reference of the source facility
 * @param sourceTenantFacilityId                 tenant facility id of the source facility
 * @param salesPricePerUnitWithoutDiscountAmount  unit price before discount
 * @param salesPricePerUnitWithDiscountAmount     unit price after discount
 * @param discount                               discounts applied to this article's price
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SourcingOptionSalesPrice(
        String tenantArticleId,
        Double amount,
        String sourceFacilityRef,
        String sourceTenantFacilityId,
        Money salesPricePerUnitWithoutDiscountAmount,
        Money salesPricePerUnitWithDiscountAmount,
        List<FacilityDiscountValue> discount
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String tenantArticleId;
        private Double amount;
        private String sourceFacilityRef;
        private String sourceTenantFacilityId;
        private Money salesPricePerUnitWithoutDiscountAmount;
        private Money salesPricePerUnitWithDiscountAmount;
        private List<FacilityDiscountValue> discount;

        public Builder tenantArticleId(String tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }
        public Builder amount(Double amount) { this.amount = amount; return this; }
        public Builder sourceFacilityRef(String sourceFacilityRef) { this.sourceFacilityRef = sourceFacilityRef; return this; }
        public Builder sourceTenantFacilityId(String sourceTenantFacilityId) { this.sourceTenantFacilityId = sourceTenantFacilityId; return this; }
        public Builder salesPricePerUnitWithoutDiscountAmount(Money m) { this.salesPricePerUnitWithoutDiscountAmount = m; return this; }
        public Builder salesPricePerUnitWithDiscountAmount(Money m) { this.salesPricePerUnitWithDiscountAmount = m; return this; }
        public Builder discount(List<FacilityDiscountValue> discount) { this.discount = discount; return this; }

        public SourcingOptionSalesPrice build() {
            return new SourcingOptionSalesPrice(tenantArticleId, amount, sourceFacilityRef, sourceTenantFacilityId,
                    salesPricePerUnitWithoutDiscountAmount, salesPricePerUnitWithDiscountAmount, discount);
        }
    }
}
