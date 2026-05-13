package de.joesst.dev.fulfillmenttools.sourcingoptions;

/**
 * Aggregated cost breakdown for a sourcing option.
 *
 * <p>Maps to the {@code SourcingOptionCosts} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param currency The ISO 4217 currency code (e.g. {@code EUR}).
 * @param total    Total cost value.
 */
public record SourcingOptionCosts(
        String currency,
        Double total
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String currency;
        private Double total;

        private Builder() {}

        public Builder currency(String currency) { this.currency = currency; return this; }
        public Builder total(Double total) { this.total = total; return this; }

        public SourcingOptionCosts build() {
            return new SourcingOptionCosts(currency, total);
        }
    }
}
