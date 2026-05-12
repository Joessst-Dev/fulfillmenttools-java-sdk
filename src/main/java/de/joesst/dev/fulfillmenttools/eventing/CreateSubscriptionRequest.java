package de.joesst.dev.fulfillmenttools.eventing;

import java.util.Objects;

public final class CreateSubscriptionRequest {

    private final String topic;
    private final String callbackUrl;

    private CreateSubscriptionRequest(Builder builder) {
        this.topic = Objects.requireNonNull(builder.topic, "topic must not be null");
        this.callbackUrl = Objects.requireNonNull(builder.callbackUrl, "callbackUrl must not be null");
    }

    public String topic() { return topic; }
    public String callbackUrl() { return callbackUrl; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String topic;
        private String callbackUrl;

        public Builder topic(String topic) { this.topic = topic; return this; }
        public Builder callbackUrl(String callbackUrl) { this.callbackUrl = callbackUrl; return this; }

        public CreateSubscriptionRequest build() { return new CreateSubscriptionRequest(this); }
    }
}
