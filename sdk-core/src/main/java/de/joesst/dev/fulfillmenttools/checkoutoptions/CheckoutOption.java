package de.joesst.dev.fulfillmenttools.checkoutoptions;

import java.util.List;
import java.util.Map;

/**
 * A checkout option with available facilities.
 *
 * @param facilities list of facility options available for checkout
 */
public record CheckoutOption(List<Map<String, Object>> facilities) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private List<Map<String, Object>> facilities;

        public Builder facilities(List<Map<String, Object>> facilities) { this.facilities = facilities; return this; }

        public CheckoutOption build() {
            return new CheckoutOption(facilities);
        }
    }
}
