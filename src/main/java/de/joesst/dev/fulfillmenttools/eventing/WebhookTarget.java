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
) implements SubscriptionTarget {}
