package de.joesst.dev.fulfillmenttools.listings;

/**
 * Stock level information for a listing.
 *
 * <p>Maps to the {@code StockInformation} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p><strong>Deprecated:</strong> This field has been deprecated since 11 September 2023.
 * Use the {@code /api/stocks} endpoints instead.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param stock     The total amount of this article in stock for the facility.
 * @param reserved  The reserved portion of the stock (cannot be overridden via API).
 * @param available The actual available quantity (stock minus reserved).
 */
@Deprecated
public record ListingStockInformation(
        Integer stock,
        Integer reserved,
        Integer available
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Integer stock;
        private Integer reserved;
        private Integer available;

        private Builder() {}

        public Builder stock(Integer stock) { this.stock = stock; return this; }
        public Builder reserved(Integer reserved) { this.reserved = reserved; return this; }
        public Builder available(Integer available) { this.available = available; return this; }

        public ListingStockInformation build() {
            return new ListingStockInformation(stock, reserved, available);
        }
    }
}
