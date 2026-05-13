package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * A preferred carrier combined with a specific carrier product and optional carrier services.
 *
 * <p>Maps to the {@code PreferredCarrierWithProduct} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param carrierKey      The carrier identifier key (e.g. {@code DPD}).
 * @param carrierProduct  Optional carrier product (e.g. {@code WORLDWIDE}).
 * @param carrierServices Optional list of carrier service identifiers.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PreferredCarrierWithProduct(
        String carrierKey,
        String carrierProduct,
        List<String> carrierServices
) {

    /**
     * Returns a builder for constructing a {@code PreferredCarrierWithProduct}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link PreferredCarrierWithProduct}.
     */
    public static final class Builder {

        private String carrierKey;
        private String carrierProduct;
        private List<String> carrierServices;

        private Builder() {}

        /**
         * Sets the carrier identifier key.
         * @param carrierKey the carrier key (e.g. {@code DPD})
         * @return this builder
         */
        public Builder carrierKey(String carrierKey) {
            this.carrierKey = carrierKey;
            return this;
        }

        /**
         * Sets the optional carrier product.
         * @param carrierProduct the carrier product (e.g. {@code WORLDWIDE})
         * @return this builder
         */
        public Builder carrierProduct(String carrierProduct) {
            this.carrierProduct = carrierProduct;
            return this;
        }

        /**
         * Sets the optional list of carrier service identifiers.
         * @param carrierServices the carrier service identifiers
         * @return this builder
         */
        public Builder carrierServices(List<String> carrierServices) {
            this.carrierServices = carrierServices;
            return this;
        }

        /**
         * Builds a {@link PreferredCarrierWithProduct}.
         *
         * @return a new instance.
         */
        public PreferredCarrierWithProduct build() {
            return new PreferredCarrierWithProduct(carrierKey, carrierProduct, carrierServices);
        }
    }
}
