package de.joesst.dev.fulfillmenttools.spring.eventing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AnnotationDrivenEventHandlerTest {

    private AnnotationDrivenEventHandler handler;

    @BeforeEach
    void setUp() {
        handler = new AnnotationDrivenEventHandler();
    }

    private void registerBean(String name, Object bean) {
        ConfigurableApplicationContext ctx = mock(ConfigurableApplicationContext.class);
        ConfigurableListableBeanFactory factory = mock(ConfigurableListableBeanFactory.class);
        when(ctx.getBeanDefinitionNames()).thenReturn(new String[]{name});
        when(ctx.getBean(name)).thenReturn(bean);
        when(ctx.getAutowireCapableBeanFactory()).thenReturn(factory);
        handler.setApplicationContext(ctx);
        handler.afterSingletonsInstantiated();
    }

    private FulfillmenttoolsEvent<String> event(String eventType, String payload) {
        AtomicBoolean acked = new AtomicBoolean(false);
        AtomicBoolean nacked = new AtomicBoolean(false);
        return new FulfillmenttoolsEvent<>(
                eventType, "evt-id", payload,
                () -> acked.set(true), () -> nacked.set(true));
    }

    @Nested
    class WhenNoHandlerIsRegistered {

        @Test
        void shouldAutoAckUnknownEvent() {
            // Given: no handlers registered
            registerBean("emptyBean", new Object());
            AtomicBoolean acked = new AtomicBoolean(false);
            FulfillmenttoolsEvent<String> event = new FulfillmenttoolsEvent<>(
                    "ORDER_CREATED", "e1", "payload",
                    () -> acked.set(true), () -> {});

            // When
            handler.onEvent(event);

            // Then
            assertThat(acked).isTrue();
        }
    }

    @Nested
    class WhenHandlerHasPayloadOnlyParam {

        @Test
        void shouldAutoAckOnSuccess() {
            // Given
            AtomicReference<String> received = new AtomicReference<>();
            Object listener = new Object() {
                @FulfillmenttoolsEventListener("ORDER_CREATED")
                public void handle(String payload) {
                    received.set(payload);
                }
            };
            registerBean("listener", listener);
            AtomicBoolean acked = new AtomicBoolean(false);
            FulfillmenttoolsEvent<String> event = new FulfillmenttoolsEvent<>(
                    "ORDER_CREATED", "e1", "order-payload",
                    () -> acked.set(true), () -> {});

            // When
            handler.onEvent(event);

            // Then
            assertThat(received).hasValue("order-payload");
            assertThat(acked).isTrue();
        }

        @Test
        void shouldAutoNackWhenMethodThrows() {
            // Given
            Object listener = new Object() {
                @FulfillmenttoolsEventListener("ORDER_CREATED")
                public void handle(String payload) {
                    throw new RuntimeException("processing failed");
                }
            };
            registerBean("listener", listener);
            AtomicBoolean nacked = new AtomicBoolean(false);
            FulfillmenttoolsEvent<String> event = new FulfillmenttoolsEvent<>(
                    "ORDER_CREATED", "e1", "payload",
                    () -> {}, () -> nacked.set(true));

            // When
            handler.onEvent(event);

            // Then
            assertThat(nacked).isTrue();
        }
    }

    @Nested
    class WhenHandlerHasEventParam {

        @Test
        void shouldNotAutoAckOrNack_callerControlsAck() {
            // Given
            AtomicBoolean methodCalled = new AtomicBoolean(false);
            Object listener = new Object() {
                @FulfillmenttoolsEventListener("ORDER_CREATED")
                public void handle(String payload, FulfillmenttoolsEvent<?> event) {
                    methodCalled.set(true);
                    // intentionally NOT calling event.ack()
                }
            };
            registerBean("listener", listener);
            AtomicBoolean acked = new AtomicBoolean(false);
            AtomicBoolean nacked = new AtomicBoolean(false);
            FulfillmenttoolsEvent<String> event = new FulfillmenttoolsEvent<>(
                    "ORDER_CREATED", "e1", "payload",
                    () -> acked.set(true), () -> nacked.set(true));

            // When
            handler.onEvent(event);

            // Then
            assertThat(methodCalled).isTrue();
            assertThat(acked).isFalse();
            assertThat(nacked).isFalse();
        }

        @Test
        void shouldInjectBothPayloadAndEvent() {
            // Given
            AtomicReference<String> receivedPayload = new AtomicReference<>();
            AtomicReference<FulfillmenttoolsEvent<?>> receivedEvent = new AtomicReference<>();
            Object listener = new Object() {
                @FulfillmenttoolsEventListener("ORDER_CREATED")
                public void handle(String payload, FulfillmenttoolsEvent<?> event) {
                    receivedPayload.set(payload);
                    receivedEvent.set(event);
                    event.ack();
                }
            };
            registerBean("listener", listener);
            FulfillmenttoolsEvent<String> event = new FulfillmenttoolsEvent<>(
                    "ORDER_CREATED", "e1", "the-order",
                    () -> {}, () -> {});

            // When
            handler.onEvent(event);

            // Then
            assertThat(receivedPayload).hasValue("the-order");
            assertThat(receivedEvent.get()).isSameAs(event);
        }
    }

    @Nested
    class WhenHandlerListensToMultipleEventTypes {

        @Test
        void shouldRouteEachEventTypeToHandler() {
            // Given
            AtomicReference<String> lastEventType = new AtomicReference<>();
            Object listener = new Object() {
                @FulfillmenttoolsEventListener({"PICK_JOB_CREATED", "PICK_JOB_UPDATED"})
                public void handle(Object payload) {
                    // payload capture not needed here
                }
            };
            registerBean("listener", listener);

            FulfillmenttoolsEvent<String> created = new FulfillmenttoolsEvent<>(
                    "PICK_JOB_CREATED", "e1", null, () -> lastEventType.set("PICK_JOB_CREATED"), () -> {});
            FulfillmenttoolsEvent<String> updated = new FulfillmenttoolsEvent<>(
                    "PICK_JOB_UPDATED", "e2", null, () -> lastEventType.set("PICK_JOB_UPDATED"), () -> {});

            // When / Then
            handler.onEvent(created);
            assertThat(lastEventType).hasValue("PICK_JOB_CREATED");

            handler.onEvent(updated);
            assertThat(lastEventType).hasValue("PICK_JOB_UPDATED");
        }
    }
}
