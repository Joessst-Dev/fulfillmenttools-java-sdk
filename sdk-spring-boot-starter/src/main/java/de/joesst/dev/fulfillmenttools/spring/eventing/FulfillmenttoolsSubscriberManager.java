package de.joesst.dev.fulfillmenttools.spring.eventing;

import com.google.cloud.pubsub.v1.Subscriber;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Manages one GCP Pub/Sub {@link Subscriber} per configured subscription.
 *
 * <p>Started automatically by Spring's lifecycle processor during context refresh.
 * Each subscription receives a dedicated {@link Subscriber}; incoming messages are
 * forwarded to {@link FulfillmenttoolsEventDispatcher#dispatch(byte[], Runnable, Runnable)}
 * with the message's own ack/nack actions. Parse errors trigger an automatic nack.
 */
public class FulfillmenttoolsSubscriberManager implements SmartLifecycle {

    private static final Logger log = LoggerFactory.getLogger(FulfillmenttoolsSubscriberManager.class);

    private final PubSubTemplate pubSubTemplate;
    private final List<String> subscriptions;
    private final FulfillmenttoolsEventDispatcher dispatcher;

    private final List<Subscriber> activeSubscribers = new ArrayList<>();
    private volatile boolean running = false;

    public FulfillmenttoolsSubscriberManager(PubSubTemplate pubSubTemplate,
                                              List<String> subscriptions,
                                              FulfillmenttoolsEventDispatcher dispatcher) {
        this.pubSubTemplate = pubSubTemplate;
        this.subscriptions = List.copyOf(subscriptions);
        this.dispatcher = dispatcher;
    }

    @Override
    public void start() {
        for (String subscription : subscriptions) {
            log.info("Subscribing to fulfillmenttools events on: {}", subscription);
            Subscriber subscriber = pubSubTemplate.subscribe(subscription, message -> {
                AtomicBoolean acknowledged = new AtomicBoolean(false);
                Runnable safeAck = () -> { if (acknowledged.compareAndSet(false, true)) message.ack(); };
                Runnable safeNack = () -> { if (acknowledged.compareAndSet(false, true)) message.nack(); };
                try {
                    dispatcher.dispatch(
                            message.getPubsubMessage().getData().toByteArray(),
                            safeAck,
                            safeNack);
                } catch (Exception e) {
                    log.error("Failed to parse fulfillmenttools event from '{}': {}",
                            subscription, e.getMessage(), e);
                    safeNack.run();
                }
            });
            activeSubscribers.add(subscriber);
        }
        running = true;
    }

    @Override
    public void stop() {
        running = false;
        List<Subscriber> toStop = List.copyOf(activeSubscribers);
        activeSubscribers.clear();
        for (Subscriber subscriber : toStop) {
            subscriber.stopAsync();
        }
        for (Subscriber subscriber : toStop) {
            try {
                subscriber.awaitTerminated(5, TimeUnit.SECONDS);
            } catch (Exception e) {
                log.warn("Subscriber did not stop cleanly within 5 seconds", e);
            }
        }
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}
