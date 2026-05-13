package de.joesst.dev.fulfillmenttools.eventing;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A {@link SubscriptionTarget} that delivers events to a Microsoft Azure Service Bus queue or topic.
 *
 * <p>Example usage:
 * <pre>{@code
 * SubscriptionTarget target = new AzureServiceBusTarget(
 *         "MICROSOFT_AZURE_SERVICE_BUS",
 *         "my-tenant-id",
 *         "my-client-id",
 *         "my-client-secret",
 *         "my-namespace",
 *         "my-queue");
 * }</pre>
 *
 * @param type               discriminator value; always {@code "MICROSOFT_AZURE_SERVICE_BUS"}
 * @param tenantId           Azure Active Directory tenant ID
 * @param clientId           service principal client ID used for authentication
 * @param clientSecret       service principal client secret used for authentication
 * @param namespace          Azure Service Bus namespace
 * @param queueOrTopicName   name of the target queue or topic within the namespace
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AzureServiceBusTarget(
        String type,
        String tenantId,
        String clientId,
        String clientSecret,
        String namespace,
        String queueOrTopicName
) implements SubscriptionTarget {}
