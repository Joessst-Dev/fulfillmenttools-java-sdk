package de.joesst.dev.fulfillmenttools.pickjobs;

/**
 * Payment information attached to a pick job.
 *
 * <p>Maps to the {@code PaymentInformation} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param currency The ISO 4217 currency code in which the consumer paid (e.g. {@code EUR}).
 */
public record PickJobPaymentInformation(
        String currency
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String currency;

        private Builder() {}

        public Builder currency(String currency) { this.currency = currency; return this; }

        public PickJobPaymentInformation build() {
            return new PickJobPaymentInformation(currency);
        }
    }
}
