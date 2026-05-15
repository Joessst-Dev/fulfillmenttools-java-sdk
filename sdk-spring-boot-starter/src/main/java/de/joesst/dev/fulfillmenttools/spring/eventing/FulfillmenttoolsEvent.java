package de.joesst.dev.fulfillmenttools.spring.eventing;

/**
 * Spring application event published for each fulfillmenttools platform event received from
 * GCP Pub/Sub.
 *
 * <p>The type parameter {@code T} is the deserialized entity type. For known event types it
 * will be the concrete entity class (e.g. {@code Order}, {@code PickJob}). For unrecognised
 * event types it will be {@code java.util.Map<String, Object>}. The payload may be
 * {@code null} when the raw message carries no {@code payload} field.
 *
 * <p>Consumers listen via Spring's standard {@code @EventListener} mechanism:
 * <pre>{@code
 * @EventListener
 * public void onOrderCreated(FulfillmenttoolsEvent<Order> event) {
 *     Order order = event.payload();
 *     // ...
 * }
 * }</pre>
 *
 * @param <T>       the type of the deserialized payload
 * @param eventType the platform event name (e.g. {@code "ORDER_CREATED"})
 * @param eventId   the unique identifier of the event
 * @param payload   the deserialized payload, or {@code null} when absent in the raw message
 */
public record FulfillmenttoolsEvent<T>(
        String eventType,
        String eventId,
        T payload
) {}
