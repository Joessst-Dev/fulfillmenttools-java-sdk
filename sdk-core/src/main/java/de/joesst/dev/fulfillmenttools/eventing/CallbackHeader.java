package de.joesst.dev.fulfillmenttools.eventing;

/**
 * A callback header for webhook subscriptions.
 *
 * @param key the header name
 * @param value the header value
 */
public record CallbackHeader(String key, String value) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String key;
        private String value;

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public CallbackHeader build() {
            return new CallbackHeader(key, value);
        }
    }
}
