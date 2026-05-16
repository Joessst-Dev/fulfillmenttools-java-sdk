package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A weight-based cost coefficient applied to a delivery cost.
 *
 * <p>Maps to the {@code SourcingOptionsTransferDeliverCostCoefficient} schema in the
 * fulfillmenttools OpenAPI specification.
 *
 * @param value           the coefficient value
 * @param measurementUnit the unit of measurement (e.g. {@code GRAM})
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SourcingOptionsTransferDeliverCostCoefficient(
        Double value,
        CostCoefficientMeasurementUnit measurementUnit
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private Double value;
        private CostCoefficientMeasurementUnit measurementUnit;

        public Builder value(Double value) { this.value = value; return this; }
        public Builder measurementUnit(CostCoefficientMeasurementUnit measurementUnit) { this.measurementUnit = measurementUnit; return this; }

        public SourcingOptionsTransferDeliverCostCoefficient build() {
            return new SourcingOptionsTransferDeliverCostCoefficient(value, measurementUnit);
        }
    }
}
