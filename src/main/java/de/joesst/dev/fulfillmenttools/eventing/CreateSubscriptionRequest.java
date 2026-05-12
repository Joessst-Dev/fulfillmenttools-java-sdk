package de.joesst.dev.fulfillmenttools.eventing;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class CreateSubscriptionRequest {

    private final String name;
    private final String event;
    private final String callbackUrl;
    private final List<SubscriptionContext> contexts;
    private final List<CallbackHeader> headers;
    private final Map<String, Object> target;

    private CreateSubscriptionRequest(Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "name must not be null");
        this.event = Objects.requireNonNull(builder.event, "event must not be null");
        this.callbackUrl = builder.callbackUrl;
        this.contexts = builder.contexts;
        this.headers = builder.headers;
        this.target = builder.target;
    }

    public String name() { return name; }
    public String event() { return event; }
    public String callbackUrl() { return callbackUrl; }
    public List<SubscriptionContext> contexts() { return contexts; }
    public List<CallbackHeader> headers() { return headers; }
    public Map<String, Object> target() { return target; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String name;
        private String event;
        private String callbackUrl;
        private List<SubscriptionContext> contexts;
        private List<CallbackHeader> headers;
        private Map<String, Object> target;

        public Builder name(String name) { this.name = name; return this; }
        public Builder event(String event) { this.event = event; return this; }
        public Builder callbackUrl(String callbackUrl) { this.callbackUrl = callbackUrl; return this; }
        public Builder contexts(List<SubscriptionContext> contexts) { this.contexts = contexts; return this; }
        public Builder headers(List<CallbackHeader> headers) { this.headers = headers; return this; }
        public Builder target(Map<String, Object> target) { this.target = target; return this; }

        public CreateSubscriptionRequest build() { return new CreateSubscriptionRequest(this); }
    }
}
