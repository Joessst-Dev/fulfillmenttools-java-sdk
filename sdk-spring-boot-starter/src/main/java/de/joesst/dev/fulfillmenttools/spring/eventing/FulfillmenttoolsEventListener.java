package de.joesst.dev.fulfillmenttools.spring.eventing;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method as a fulfillmenttools platform event handler.
 *
 * <p>Declare the event type(s) to handle in {@link #value()}. The SDK scans all Spring beans
 * for methods carrying this annotation and dispatches matching events to them.
 *
 * <h3>Method signatures</h3>
 *
 * <p><b>Payload only</b> — the SDK auto-acks on success and auto-nacks on exception:
 * <pre>{@code
 * @FulfillmenttoolsEventListener("ORDER_CREATED")
 * public void onOrderCreated(Order order) {
 *     // ...
 * }
 * }</pre>
 *
 * <p><b>Payload + event</b> — caller controls ack/nack explicitly:
 * <pre>{@code
 * @FulfillmenttoolsEventListener("ORDER_CREATED")
 * public void onOrderCreated(Order order, FulfillmenttoolsEvent<?> event) {
 *     try {
 *         // ...
 *         event.ack();
 *     } catch (RetryableException e) {
 *         event.nack();
 *     }
 * }
 * }</pre>
 *
 * <p><b>Multiple event types</b> on one method:
 * <pre>{@code
 * @FulfillmenttoolsEventListener({"PICK_JOB_CREATED", "PICK_JOB_UPDATED"})
 * public void onPickJobChange(PickJob job) { ... }
 * }</pre>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FulfillmenttoolsEventListener {

    /** One or more platform event type strings this method handles (e.g. {@code "ORDER_CREATED"}). */
    String[] value();
}
