package de.joesst.dev.fulfillmenttools.returns;

import java.util.Map;

/**
 * A reason attached to a returned line item, explaining why the item was returned.
 *
 * <p>Maps to the {@code ItemReturnLineItemReason} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param reasonLocalized Localized reason strings, keyed by locale (e.g. {@code en_US}). Required.
 * @param reason          Translated reason string resolved from {@code reasonLocalized}.
 * @param comment         Optional free-text comment providing additional context.
 */
public record ReturnLineItemReason(
        Map<String, String> reasonLocalized,
        String reason,
        String comment
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Map<String, String> reasonLocalized;
        private String reason;
        private String comment;

        private Builder() {}

        public Builder reasonLocalized(Map<String, String> reasonLocalized) { this.reasonLocalized = reasonLocalized; return this; }
        public Builder reason(String reason) { this.reason = reason; return this; }
        public Builder comment(String comment) { this.comment = comment; return this; }

        public ReturnLineItemReason build() {
            return new ReturnLineItemReason(reasonLocalized, reason, comment);
        }
    }
}
