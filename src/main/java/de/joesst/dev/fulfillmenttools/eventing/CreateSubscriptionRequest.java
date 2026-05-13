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

    /**
     * Returns the human-readable name of the subscription.
     * @return the subscription name; never {@code null}
     */
    public String name() { return name; }

    /**
     * Returns the event type to subscribe to.
     * @return the event type (e.g. {@code ORDER_CREATED}); never {@code null}
     */
    public String event() { return event; }

    /**
     * Returns the deprecated callback URL.
     * @return the callback URL, or {@code null} if not set
     * @deprecated use {@link WebhookTarget} via {@link #target()} instead
     */
    @Deprecated
    public String callbackUrl() { return callbackUrl; }

    /**
     * Returns the optional list of facility/facility-group scoping contexts.
     * @return the contexts, or {@code null} if not set
     */
    public List<SubscriptionContext> contexts() { return contexts; }

    /**
     * Returns the deprecated list of callback headers.
     * @return the headers, or {@code null} if not set
     * @deprecated use {@link WebhookTarget#headers()} instead
     */
    @Deprecated
    public List<CallbackHeader> headers() { return headers; }

    /**
     * Returns the typed delivery target.
     * @return the delivery target, or {@code null} when using deprecated callback fields
     */
    public SubscriptionTarget target() { return target; }

    /**
     * Creates a new builder for constructing a {@link CreateSubscriptionRequest}.
     * @return a new builder instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link CreateSubscriptionRequest}.
     */
    public static final class Builder {

        /** Creates a new Builder instance. */
        public Builder() {}

        private String name;
        private String event;
        private String callbackUrl;
        private List<SubscriptionContext> contexts;
        private List<CallbackHeader> headers;
        private SubscriptionTarget target;

        /**
         * Sets the subscription name (required).
         * @param name human-readable subscription name
         * @return this builder
         */
        public Builder name(String name) { this.name = name; return this; }

        /**
         * Sets the event type to subscribe to (required).
         * @param event the event type
         * @return this builder
         */
        public Builder event(String event) { this.event = event; return this; }

        /**
         * Sets the deprecated HTTP callback URL.
         * @param callbackUrl the callback URL
         * @return this builder
         * @deprecated use {@link #target(SubscriptionTarget)} with a {@link WebhookTarget} instead
         */
        @Deprecated
        public Builder callbackUrl(String callbackUrl) { this.callbackUrl = callbackUrl; return this; }

        /**
         * Sets the optional facility/facility-group scoping contexts.
         * @param contexts the scoping contexts
         * @return this builder
         */
        public Builder contexts(List<SubscriptionContext> contexts) { this.contexts = contexts; return this; }

        /**
         * Sets the deprecated list of callback headers.
         * @param headers the callback headers
         * @return this builder
         * @deprecated use {@link WebhookTarget#headers()} instead
         */
        @Deprecated
        public Builder headers(List<CallbackHeader> headers) { this.headers = headers; return this; }

        /**
         * Sets the typed delivery target for this subscription.
         *
         * @param target one of {@link WebhookTarget}, {@link AzureServiceBusTarget},
         *               or {@link GoogleCloudPubSubTarget}
         * @return this builder
         */
        public Builder target(SubscriptionTarget target) { this.target = target; return this; }

        /**
         * Builds and returns the {@link CreateSubscriptionRequest}.
         * @return a new request instance
         * @throws NullPointerException if any required field is not set
         */
        public CreateSubscriptionRequest build() { return new CreateSubscriptionRequest(this); }
    }
}
