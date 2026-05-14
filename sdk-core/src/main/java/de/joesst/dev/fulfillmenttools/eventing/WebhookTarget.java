package de.joesst.dev.fulfillmenttools.eventing;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * A {@link SubscriptionTarget} that delivers events to an HTTP endpoint.
 *
 * <p>Example usage:
 * <pre>{@code
 * SubscriptionTarget target = new WebhookTarget(
 *         "WEBHOOK",
 *         "https://example.com/hook",
 *         List.of(new CallbackHeader("Authorization", "Bearer token")));
 * }</pre>
 *
 * @param type        discriminator value; always {@code "WEBHOOK"}
 * @param callbackUrl the HTTP URL that receives the event payload
 * @param headers     optional list of HTTP headers to include in each callback request
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record WebhookTarget(
        String type,
        String callbackUrl,
        List<CallbackHeader> headers
) implements SubscriptionTarget {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String type;
        private String callbackUrl;
        private List<CallbackHeader> headers;

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder callbackUrl(String callbackUrl) {
            this.callbackUrl = callbackUrl;
            return this;
        }

        public Builder headers(List<CallbackHeader> headers) {
            this.headers = headers;
            return this;
        }

        public WebhookTarget build() {
            return new WebhookTarget(type, callbackUrl, headers);
        }
    }
}
