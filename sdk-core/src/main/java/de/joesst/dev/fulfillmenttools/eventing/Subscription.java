package de.joesst.dev.fulfillmenttools.eventing;

import de.joesst.dev.fulfillmenttools.id.SubscriptionId;

import java.time.Instant;
import java.util.List;

/**
 * Represents a fulfillmenttools event subscription as returned by the platform API.
 *
 * <p>The {@code target} field is polymorphic and will be one of {@link WebhookTarget},
 * {@link AzureServiceBusTarget}, or {@link GoogleCloudPubSubTarget} depending on the
 * subscription configuration. It may be {@code null} when the deprecated
 * {@code callbackUrl}/{@code headers} fields are used instead.
 *
 * @param id          unique identifier assigned by the platform
 * @param created     timestamp when the subscription was created
 * @param name        human-readable name of the subscription
 * @param event       event type this subscription listens for (e.g. {@code ORDER_CREATED})
 * @param callbackUrl deprecated; use {@link WebhookTarget} via {@code target} instead
 * @param contexts    optional list of facility or facility-group scoping contexts
 * @param headers     deprecated; use {@link WebhookTarget#headers()} instead
 * @param target      the delivery target configuration; {@code null} when using deprecated fields
 */
public record Subscription(
        SubscriptionId id,
        Instant created,
        String name,
        String event,
        String callbackUrl,
        List<SubscriptionContext> contexts,
        List<CallbackHeader> headers,
        SubscriptionTarget target
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private SubscriptionId id;
        private Instant created;
        private String name;
        private String event;
        private String callbackUrl;
        private List<SubscriptionContext> contexts;
        private List<CallbackHeader> headers;
        private SubscriptionTarget target;

        public Builder id(SubscriptionId id) {
            this.id = id;
            return this;
        }

        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder event(String event) {
            this.event = event;
            return this;
        }

        public Builder callbackUrl(String callbackUrl) {
            this.callbackUrl = callbackUrl;
            return this;
        }

        public Builder contexts(List<SubscriptionContext> contexts) {
            this.contexts = contexts;
            return this;
        }

        public Builder headers(List<CallbackHeader> headers) {
            this.headers = headers;
            return this;
        }

        public Builder target(SubscriptionTarget target) {
            this.target = target;
            return this;
        }

        public Subscription build() {
            return new Subscription(id, created, name, event, callbackUrl, contexts, headers, target);
        }
    }
}
