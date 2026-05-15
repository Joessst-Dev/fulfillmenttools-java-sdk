package de.joesst.dev.fulfillmenttools.spring.eventing;

/**
 * Handles deserialized fulfillmenttools platform events.
 *
 * <p>Declare a bean of this type to take full control of event processing — the
 * auto-configured default handler backs off via {@code @ConditionalOnMissingBean}:
 * <pre>{@code
 * @Bean
 * public FulfillmenttoolsEventHandler myHandler() {
 *     return event -> switch (event.eventType()) {
 *         case "ORDER_CREATED"    -> { handleOrder((Order) event.payload()); event.ack(); }
 *         case "PICK_JOB_CREATED" -> { handlePickJob((PickJob) event.payload()); event.ack(); }
 *         default                 -> event.ack();
 *     };
 * }
 * }</pre>
 *
 * <p>When no bean is declared, the auto-configuration registers a default handler that
 * publishes each event to Spring's {@code ApplicationEventPublisher}, making
 * {@code @EventListener} work out of the box:
 * <pre>{@code
 * @EventListener(condition = "#event.eventType() == 'ORDER_CREATED'")
 * public void onOrderCreated(FulfillmenttoolsEvent<?> event) {
 *     Order order = (Order) event.payload();
 *     event.ack();
 * }
 * }</pre>
 *
 * <p>The handler is always called on a GCP Pub/Sub subscriber thread. If processing is
 * slow, consider handing off to an executor inside the handler and calling
 * {@code event.ack()} / {@code event.nack()} from the processing thread.
 */
@FunctionalInterface
public interface FulfillmenttoolsEventHandler {

    /**
     * Processes a single platform event.
     *
     * <p>The implementation is responsible for calling {@link FulfillmenttoolsEvent#ack()}
     * on success or {@link FulfillmenttoolsEvent#nack()} on failure (triggering redelivery).
     *
     * @param event the deserialized platform event
     */
    void onEvent(FulfillmenttoolsEvent<?> event);
}
