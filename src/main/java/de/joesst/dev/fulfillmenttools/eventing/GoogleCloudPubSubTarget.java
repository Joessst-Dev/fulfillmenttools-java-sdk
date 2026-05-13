package de.joesst.dev.fulfillmenttools.eventing;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A {@link SubscriptionTarget} that delivers events to a Google Cloud Pub/Sub topic.
 *
 * <p>Example usage:
 * <pre>{@code
 * SubscriptionTarget target = new GoogleCloudPubSubTarget(
 *         "GOOGLE_CLOUD_PUB_SUB",
 *         "my-gcp-project",
 *         "my-topic");
 * }</pre>
 *
 * @param type      discriminator value; always {@code "GOOGLE_CLOUD_PUB_SUB"}
 * @param projectId Google Cloud project ID that owns the topic
 * @param topicId   Pub/Sub topic ID within the project
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GoogleCloudPubSubTarget(
        String type,
        String projectId,
        String topicId
) implements SubscriptionTarget {}
