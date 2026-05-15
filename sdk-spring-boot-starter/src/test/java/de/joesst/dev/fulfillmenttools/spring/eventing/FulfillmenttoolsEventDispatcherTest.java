package de.joesst.dev.fulfillmenttools.spring.eventing;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.joesst.dev.fulfillmenttools.orders.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FulfillmenttoolsEventDispatcherTest {

    private final List<FulfillmenttoolsEvent<?>> publishedEvents = new ArrayList<>();
    private final FulfillmenttoolsEventHandler eventHandler = publishedEvents::add;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

    private FulfillmenttoolsEventTypeRegistry registry;
    private FulfillmenttoolsEventDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        registry = new FulfillmenttoolsEventTypeRegistry();
        dispatcher = new FulfillmenttoolsEventDispatcher(objectMapper, registry, eventHandler);
        publishedEvents.clear();
    }

    @Nested
    class WhenEventTypeIsRegistered {

        @Test
        void dispatch_publishesTypedEvent_whenEventTypeIsRegistered() {
            // Given
            registry.register("ORDER_CREATED", Order.class);
            byte[] message = json("""
                    {"eventId":"order-evt-1","event":"ORDER_CREATED","payload":{"id":"ord-42"}}
                    """);

            // When
            dispatcher.dispatch(message, () -> {}, () -> {});

            // Then
            assertThat(publishedEvents).hasSize(1);
            FulfillmenttoolsEvent<?> event = publishedEvents.get(0);
            assertThat(event.eventType()).isEqualTo("ORDER_CREATED");
            assertThat(event.eventId()).isEqualTo("order-evt-1");
            assertThat(event.payload()).isInstanceOf(Order.class);
        }

        @Test
        void dispatch_doesNotAutoAck_afterPublish() {
            // Given: the dispatcher must NOT ack/nack automatically — listener is responsible
            registry.register("ORDER_CREATED", Order.class);
            AtomicBoolean ackCalled = new AtomicBoolean(false);
            AtomicBoolean nackCalled = new AtomicBoolean(false);
            byte[] message = json("""
                    {"eventId":"e1","event":"ORDER_CREATED","payload":{}}
                    """);

            // When
            dispatcher.dispatch(message, () -> ackCalled.set(true), () -> nackCalled.set(true));

            // Then
            assertThat(ackCalled).isFalse();
            assertThat(nackCalled).isFalse();
        }
    }

    @Nested
    class WhenAckOrNackIsCalledOnEvent {

        @Test
        void ack_invokesAckCallback() {
            // Given
            AtomicBoolean ackCalled = new AtomicBoolean(false);
            byte[] message = json("""
                    {"eventId":"e1","event":"UNKNOWN_EVENT","payload":{}}
                    """);
            dispatcher.dispatch(message, () -> ackCalled.set(true), () -> {});

            // When
            publishedEvents.get(0).ack();

            // Then
            assertThat(ackCalled).isTrue();
        }

        @Test
        void nack_invokesNackCallback() {
            // Given
            AtomicBoolean nackCalled = new AtomicBoolean(false);
            byte[] message = json("""
                    {"eventId":"e1","event":"UNKNOWN_EVENT","payload":{}}
                    """);
            dispatcher.dispatch(message, () -> {}, () -> nackCalled.set(true));

            // When
            publishedEvents.get(0).nack();

            // Then
            assertThat(nackCalled).isTrue();
        }
    }

    @Nested
    class WhenEventTypeIsUnknown {

        @Test
        void dispatch_publishesMapEvent_whenEventTypeIsUnknown() {
            // Given
            byte[] message = json("""
                    {"eventId":"unknown-evt-1","event":"SOME_FUTURE_EVENT","payload":{"foo":"bar"}}
                    """);

            // When
            dispatcher.dispatch(message, () -> {}, () -> {});

            // Then
            assertThat(publishedEvents).hasSize(1);
            FulfillmenttoolsEvent<?> event = publishedEvents.get(0);
            assertThat(event.eventType()).isEqualTo("SOME_FUTURE_EVENT");
            assertThat(event.eventId()).isEqualTo("unknown-evt-1");
            assertThat(event.payload()).isInstanceOf(Map.class);
            @SuppressWarnings("unchecked")
            Map<String, Object> payload = (Map<String, Object>) event.payload();
            assertThat(payload).containsEntry("foo", "bar");
        }
    }

    @Nested
    class WhenPayloadIsAbsent {

        @Test
        void dispatch_publishesNullPayload_whenPayloadIsNull() {
            // Given
            byte[] message = json("""
                    {"eventId":"e1","event":"ORDER_CREATED"}
                    """);

            // When
            dispatcher.dispatch(message, () -> {}, () -> {});

            // Then
            assertThat(publishedEvents).hasSize(1);
            FulfillmenttoolsEvent<?> event = publishedEvents.get(0);
            assertThat(event.eventId()).isEqualTo("e1");
            assertThat(event.payload()).isNull();
        }
    }

    @Nested
    class WhenMessageIsMalformed {

        @Test
        void dispatch_throwsUncheckedIOException_whenMessageIsMalformed() {
            // Given
            byte[] message = "not valid json {{{".getBytes(StandardCharsets.UTF_8);

            // When / Then
            assertThatThrownBy(() -> dispatcher.dispatch(message, () -> {}, () -> {}))
                    .isInstanceOf(UncheckedIOException.class);
        }
    }

    private static byte[] json(String text) {
        return text.strip().getBytes(StandardCharsets.UTF_8);
    }
}
