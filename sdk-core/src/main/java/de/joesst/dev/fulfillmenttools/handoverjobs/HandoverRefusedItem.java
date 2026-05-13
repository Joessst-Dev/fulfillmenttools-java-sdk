package de.joesst.dev.fulfillmenttools.handoverjobs;

import java.util.Map;

/**
 * Records the refusal of a quantity of a handover line item or substitute line item.
 *
 * <p>Maps to the {@code RefusedItem} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param quantity        The number of units refused. Required; minimum 0.
 * @param reason          Optional free-text reason for refusal.
 * @param reasonLocalized Optional localized refusal reason strings, keyed by locale
 *                        (e.g. {@code en_US}).
 * @param comment         Optional additional comment about the refusal.
 */
public record HandoverRefusedItem(
        Integer quantity,
        String reason,
        Map<String, String> reasonLocalized,
        String comment
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private Integer quantity;
        private String reason;
        private Map<String, String> reasonLocalized;
        private String comment;

        public Builder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public Builder reason(String reason) { this.reason = reason; return this; }
        public Builder reasonLocalized(Map<String, String> reasonLocalized) { this.reasonLocalized = reasonLocalized; return this; }
        public Builder comment(String comment) { this.comment = comment; return this; }

        public HandoverRefusedItem build() {
            return new HandoverRefusedItem(quantity, reason, reasonLocalized, comment);
        }
    }
}
