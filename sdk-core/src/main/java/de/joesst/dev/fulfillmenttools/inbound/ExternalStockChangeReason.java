package de.joesst.dev.fulfillmenttools.inbound;

import java.util.Map;

/**
 * The reason for an external stock change operation as returned by the API.
 *
 * <p>Maps to the {@code ExternalStockChangeReasonOutput} schema in the fulfillmenttools
 * OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param reasonLocalized Localized reason texts, keyed by locale (e.g. {@code en_US}).
 * @param tenantReasonId  Tenant-defined identifier for a preconfigured reason.
 */
public record ExternalStockChangeReason(
        Map<String, String> reasonLocalized,
        String tenantReasonId
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Map<String, String> reasonLocalized;
        private String tenantReasonId;

        private Builder() {}

        public Builder reasonLocalized(Map<String, String> reasonLocalized) { this.reasonLocalized = reasonLocalized; return this; }
        public Builder tenantReasonId(String tenantReasonId) { this.tenantReasonId = tenantReasonId; return this; }

        public ExternalStockChangeReason build() {
            return new ExternalStockChangeReason(reasonLocalized, tenantReasonId);
        }
    }
}
