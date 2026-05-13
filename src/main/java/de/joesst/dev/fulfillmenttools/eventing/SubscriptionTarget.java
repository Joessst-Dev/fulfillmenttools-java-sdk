package de.joesst.dev.fulfillmenttools.eventing;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Sealed interface representing the delivery target of a subscription.
 *
 * <p>Three target types are supported by the fulfillmenttools platform:
 * <ul>
 *   <li>{@link WebhookTarget} — HTTP callback to a URL</li>
 *   <li>{@link AzureServiceBusTarget} — Microsoft Azure Service Bus queue or topic</li>
 *   <li>{@link GoogleCloudPubSubTarget} — Google Cloud Pub/Sub topic</li>
 * </ul>
 *
 * <p>Jackson resolves the concrete type from the {@code type} field present in the JSON payload.
 * The discriminator value is retained on the deserialized record so that round-tripping is lossless.
 *
 * <p>Example usage:
 * <pre>{@code
 * SubscriptionTarget target = new WebhookTarget("WEBHOOK", "https://example.com/hook", null);
 * }</pre>
 *
 * @see WebhookTarget
 * @see AzureServiceBusTarget
 * @see GoogleCloudPubSubTarget
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = WebhookTarget.class, name = "WEBHOOK"),
        @JsonSubTypes.Type(value = AzureServiceBusTarget.class, name = "MICROSOFT_AZURE_SERVICE_BUS"),
        @JsonSubTypes.Type(value = GoogleCloudPubSubTarget.class, name = "GOOGLE_CLOUD_PUB_SUB")
})
public sealed interface SubscriptionTarget
        permits WebhookTarget, AzureServiceBusTarget, GoogleCloudPubSubTarget {

    /**
     * Returns the discriminator value that identifies this target's type on the wire.
     *
     * @return the {@code type} string (e.g. {@code "WEBHOOK"})
     */
    String type();
}
