package de.joesst.dev.fulfillmenttools.pickjobs;

import java.util.Map;

/**
 * A customizable attribute on a pick line item whose value can be recorded during the
 * picking process.
 *
 * <p>Maps to the {@code RecordableAttribute} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id             Unique identifier of the recordable attribute.
 * @param keyLocalized   Localized translations of the attribute key, keyed by locale
 *                       (e.g. {@code en_US}).
 * @param key            The translated key resolved from {@code keyLocalized} for the
 *                       request locale.
 * @param value          The recorded value; {@code null} if not yet recorded.
 * @param group          Optional grouping label (e.g. {@code "general"}).
 * @param originId       Id of the original line item the attribute was split from.
 * @param recordingRule  Whether the value must be recorded: {@code MANDATORY} or
 *                       {@code OPTIONAL}.
 */
public record RecordableAttribute(
        String id,
        Map<String, String> keyLocalized,
        String key,
        String value,
        String group,
        String originId,
        String recordingRule
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private Map<String, String> keyLocalized;
        private String key;
        private String value;
        private String group;
        private String originId;
        private String recordingRule;

        private Builder() {}

        public Builder id(String id) { this.id = id; return this; }
        public Builder keyLocalized(Map<String, String> keyLocalized) { this.keyLocalized = keyLocalized; return this; }
        public Builder key(String key) { this.key = key; return this; }
        public Builder value(String value) { this.value = value; return this; }
        public Builder group(String group) { this.group = group; return this; }
        public Builder originId(String originId) { this.originId = originId; return this; }
        public Builder recordingRule(String recordingRule) { this.recordingRule = recordingRule; return this; }

        public RecordableAttribute build() {
            return new RecordableAttribute(id, keyLocalized, key, value, group, originId, recordingRule);
        }
    }
}
