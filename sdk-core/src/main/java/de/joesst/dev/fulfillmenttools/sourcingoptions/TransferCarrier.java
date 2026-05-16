package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Carrier assigned to a sourcing option transfer.
 *
 * @param carrierKey  the carrier's unique key
 * @param carrierName the human-readable carrier name
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TransferCarrier(
        String carrierKey,
        String carrierName
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String carrierKey;
        private String carrierName;

        public Builder carrierKey(String carrierKey) { this.carrierKey = carrierKey; return this; }
        public Builder carrierName(String carrierName) { this.carrierName = carrierName; return this; }

        public TransferCarrier build() {
            return new TransferCarrier(carrierKey, carrierName);
        }
    }
}
