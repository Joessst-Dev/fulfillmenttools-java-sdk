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
 * <p>The consumer is responsible for acknowledging the message by calling {@link #ack()} on
 * success or {@link #nack()} when processing fails (so the message is redelivered):
 * <pre>{@code
 * @EventListener
 * public void onOrderCreated(FulfillmenttoolsEvent<Order> event) {
 *     try {
 *         process(event.payload());
 *         event.ack();
 *     } catch (Exception e) {
 *         event.nack();
 *     }
 * }
 * }</pre>
 *
 * <p>Note: {@code @EventListener} methods are called synchronously by default. If you use
 * {@code @Async}, ensure you call {@code ack()} or {@code nack()} before the method returns
 * so the Pub/Sub lease does not expire.
 *
 * @param <T> the type of the deserialized payload
 */
public final class FulfillmenttoolsEvent<T> {

    private final String eventType;
    private final String eventId;
    private final T payload;
    private final Runnable ackAction;
    private final Runnable nackAction;

    public FulfillmenttoolsEvent(String eventType, String eventId, T payload,
                                  Runnable ackAction, Runnable nackAction) {
        this.eventType = eventType;
        this.eventId = eventId;
        this.payload = payload;
        this.ackAction = ackAction;
        this.nackAction = nackAction;
    }

    /** Returns the platform event name (e.g. {@code "ORDER_CREATED"}). */
    public String eventType() { return eventType; }

    /** Returns the unique identifier of this event. */
    public String eventId() { return eventId; }

    /**
     * Returns the deserialized payload, or {@code null} when absent in the raw message.
     */
    public T payload() { return payload; }

    /** Acknowledges the underlying Pub/Sub message. Call this on successful processing. */
    public void ack() { ackAction.run(); }

    /** Negatively acknowledges the message, causing it to be redelivered. */
    public void nack() { nackAction.run(); }
}
