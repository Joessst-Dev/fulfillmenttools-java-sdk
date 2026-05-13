package de.joesst.dev.fulfillmenttools.listings;

import java.util.Map;

/**
 * A customizable attribute on a listing whose value can be recorded during the picking process.
 *
 * <p>Maps to the {@code ListingRecordableAttribute} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param keyLocalized   Localized translations of the attribute key, keyed by locale
 *                       (e.g. {@code en_US}).
 * @param key            The translated key resolved from {@code keyLocalized} for the
 *                       request locale.
 * @param recordingRule  Whether the value must be recorded: {@code MANDATORY} or
 *                       {@code OPTIONAL}.
 * @param value          The recorded value; {@code null} if not yet recorded.
 */
public record ListingRecordableAttribute(
        Map<String, String> keyLocalized,
        String key,
        String recordingRule,
        String value
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Map<String, String> keyLocalized;
        private String key;
        private String recordingRule;
        private String value;

        private Builder() {}

        public Builder keyLocalized(Map<String, String> keyLocalized) { this.keyLocalized = keyLocalized; return this; }
        public Builder key(String key) { this.key = key; return this; }
        public Builder recordingRule(String recordingRule) { this.recordingRule = recordingRule; return this; }
        public Builder value(String value) { this.value = value; return this; }

        public ListingRecordableAttribute build() {
            return new ListingRecordableAttribute(keyLocalized, key, recordingRule, value);
        }
    }
}
