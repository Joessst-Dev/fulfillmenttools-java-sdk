package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

/**
 * Shipping-specific delivery preferences for an order.
 *
 * <p>Maps to the {@code DeliveryPreferencesShipping} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param serviceLevel                The desired service level: {@code DELIVERY} (standard
 *                                    multi-day shipping) or {@code SAMEDAY}.
 * @param desiredDeliveryTime         Optional target delivery timestamp.
 * @param preferredCarriers           Optional list of preferred carrier keys (e.g. {@code DPD}).
 * @param preferredCarriersWithProduct Optional list of carriers with specific carrier products.
 * @param preselectedFacilities       Optional list of facilities pre-selected for fulfillment.
 * @param carrierProductCategory      Optional carrier product category filter.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DeliveryPreferencesShipping(
        String serviceLevel,
        Instant desiredDeliveryTime,
        List<String> preferredCarriers,
        List<PreferredCarrierWithProduct> preferredCarriersWithProduct,
        List<PreselectedFacility> preselectedFacilities,
        String carrierProductCategory
) {

    /**
     * Returns a builder for constructing a {@code DeliveryPreferencesShipping}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link DeliveryPreferencesShipping}.
     */
    public static final class Builder {

        private String serviceLevel;
        private Instant desiredDeliveryTime;
        private List<String> preferredCarriers;
        private List<PreferredCarrierWithProduct> preferredCarriersWithProduct;
        private List<PreselectedFacility> preselectedFacilities;
        private String carrierProductCategory;

        private Builder() {}

        /**
         * Sets the desired service level.
         * @param serviceLevel the service level (e.g. {@code DELIVERY} or {@code SAMEDAY})
         * @return this builder
         */
        public Builder serviceLevel(String serviceLevel) {
            this.serviceLevel = serviceLevel;
            return this;
        }

        /**
         * Sets the optional target delivery timestamp.
         * @param desiredDeliveryTime the desired delivery time
         * @return this builder
         */
        public Builder desiredDeliveryTime(Instant desiredDeliveryTime) {
            this.desiredDeliveryTime = desiredDeliveryTime;
            return this;
        }

        /**
         * Sets the optional list of preferred carrier keys.
         * @param preferredCarriers the preferred carrier keys (e.g. {@code DPD})
         * @return this builder
         */
        public Builder preferredCarriers(List<String> preferredCarriers) {
            this.preferredCarriers = preferredCarriers;
            return this;
        }

        /**
         * Sets the optional list of carriers with specific carrier products.
         * @param preferredCarriersWithProduct the preferred carriers with product
         * @return this builder
         */
        public Builder preferredCarriersWithProduct(List<PreferredCarrierWithProduct> preferredCarriersWithProduct) {
            this.preferredCarriersWithProduct = preferredCarriersWithProduct;
            return this;
        }

        /**
         * Sets the optional list of facilities pre-selected for fulfillment.
         * @param preselectedFacilities the pre-selected facilities
         * @return this builder
         */
        public Builder preselectedFacilities(List<PreselectedFacility> preselectedFacilities) {
            this.preselectedFacilities = preselectedFacilities;
            return this;
        }

        /**
         * Sets the optional carrier product category filter.
         * @param carrierProductCategory the carrier product category
         * @return this builder
         */
        public Builder carrierProductCategory(String carrierProductCategory) {
            this.carrierProductCategory = carrierProductCategory;
            return this;
        }

        /**
         * Builds a {@link DeliveryPreferencesShipping}.
         *
         * @return a new instance.
         */
        public DeliveryPreferencesShipping build() {
            return new DeliveryPreferencesShipping(
                    serviceLevel, desiredDeliveryTime, preferredCarriers,
                    preferredCarriersWithProduct, preselectedFacilities, carrierProductCategory);
        }
    }
}
