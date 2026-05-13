package de.joesst.dev.fulfillmenttools.eventing;

import java.util.List;
import java.util.Objects;

/**
 * Request object for creating a new event subscription.
 *
 * <p>Use the fluent {@link Builder} to construct instances:
 * <pre>{@code
 * CreateSubscriptionRequest request = CreateSubscriptionRequest.builder()
 *         .name("order-hook")
 *         .event("ORDER_CREATED")
 *         .target(new WebhookTarget("WEBHOOK", "https://example.com/hook", null))
 *         .build();
 * }</pre>
 *
 * <p>Both {@code name} and {@code event} are required; all other fields are optional.
 */
public final class CreateSubscriptionRequest {

    private final String name;
    private final String event;
    private final String callbackUrl;
    private final List<SubscriptionContext> contexts;
    private final List<CallbackHeader> headers;
    private final SubscriptionTarget target;

    private CreateSubscriptionRequest(Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "name must not be null");
        this.event = Objects.requireNonNull(builder.event, "event must not be null");
        this.callbackUrl = builder.callbackUrl;
        this.contexts = builder.contexts;
        this.headers = builder.headers;
        this.target = builder.target;
    }

    /** @return human-readable name of the subscription */
    public String name() { return name; }

    /** @return event type to subscribe to (e.g. {@code ORDER_CREATED}) */
    public String event() { return event; }

    /**
     * @return deprecated callback URL; prefer supplying a {@link WebhookTarget} via {@link #target()}
     * @deprecated use {@link WebhookTarget} via {@link #target()} instead
     */
    @Deprecated
    public String callbackUrl() { return callbackUrl; }

    /** @return optional list of facility/facility-group scoping contexts */
    public List<SubscriptionContext> contexts() { return contexts; }

    /**
     * @return deprecated list of callback headers; prefer {@link WebhookTarget#headers()}
     * @deprecated use {@link WebhookTarget#headers()} instead
     */
    @Deprecated
    public List<CallbackHeader> headers() { return headers; }

    /** @return the typed delivery target, or {@code null} when using deprecated callback fields */
    public SubscriptionTarget target() { return target; }

    /** @return a new {@link Builder} */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link CreateSubscriptionRequest}.
     */
    public static final class Builder {

        private String name;
        private String event;
        private String callbackUrl;
        private List<SubscriptionContext> contexts;
        private List<CallbackHeader> headers;
        private SubscriptionTarget target;

        /** @param name human-readable subscription name (required) */
        public Builder name(String name) { this.name = name; return this; }

        /** @param event event type to subscribe to (required) */
        public Builder event(String event) { this.event = event; return this; }

        /**
         * @param callbackUrl deprecated HTTP callback URL
         * @deprecated use {@link #target(SubscriptionTarget)} with a {@link WebhookTarget} instead
         */
        @Deprecated
        public Builder callbackUrl(String callbackUrl) { this.callbackUrl = callbackUrl; return this; }

        /** @param contexts optional facility/facility-group scoping contexts */
        public Builder contexts(List<SubscriptionContext> contexts) { this.contexts = contexts; return this; }

        /**
         * @param headers deprecated list of callback headers
         * @deprecated use {@link WebhookTarget#headers()} instead
         */
        @Deprecated
        public Builder headers(List<CallbackHeader> headers) { this.headers = headers; return this; }

        /**
         * Sets the typed delivery target for this subscription.
         *
         * @param target one of {@link WebhookTarget}, {@link AzureServiceBusTarget},
         *               or {@link GoogleCloudPubSubTarget}
         */
        public Builder target(SubscriptionTarget target) { this.target = target; return this; }

        /** @return the constructed {@link CreateSubscriptionRequest} */
        public CreateSubscriptionRequest build() { return new CreateSubscriptionRequest(this); }
    }
}
