package de.joesst.dev.fulfillmenttools.pickjobs;

import java.util.List;

/**
 * Identifies the physical storage location from which an article should be picked.
 *
 * <p>Maps to the {@code Location} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param locationRef    The id of the location.
 * @param scannableCodes Barcodes that may be scanned at this location.
 */
public record PickJobStockLocation(
        String locationRef,
        List<String> scannableCodes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String locationRef;
        private List<String> scannableCodes;

        private Builder() {}

        public Builder locationRef(String locationRef) { this.locationRef = locationRef; return this; }
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }

        public PickJobStockLocation build() {
            return new PickJobStockLocation(locationRef, scannableCodes);
        }
    }
}
