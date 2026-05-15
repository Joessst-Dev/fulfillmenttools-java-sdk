package de.joesst.dev.fulfillmenttools.spring.eventing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AnnotationDrivenEventHandlerTest {

    private AnnotationDrivenEventHandler handler;

    @BeforeEach
    void setUp() {
        handler = new AnnotationDrivenEventHandler();
    }

    private void registerBean(String name, Object bean) {
        registerBeans(Map.of(name, bean));
    }

    private void registerBeans(Map<String, Object> beans) {
        ConfigurableApplicationContext ctx = mock(ConfigurableApplicationContext.class);
        when(ctx.getBeanDefinitionNames()).thenReturn(beans.keySet().toArray(new String[0]));
        beans.forEach((name, bean) -> when(ctx.getBean(name)).thenReturn(bean));
        handler.setApplicationContext(ctx);
        handler.afterSingletonsInstantiated();
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

    @Nested
    class WhenHandlerMethodHasInvalidSignature {

        @Test
        void shouldThrowAtStartupWhenMethodHasMoreThanOneNonEventParam() {
            // Given: a method with two non-event parameters
            Object listener = new Object() {
                @FulfillmenttoolsEventListener("ORDER_CREATED")
                public void handle(String first, String second) {}
            };

            // When / Then: startup fails immediately, not at message time
            assertThatThrownBy(() -> registerBean("listener", listener))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("handle")
                    .hasMessageContaining("2 non-event parameters");
        }
    }

    @Nested
    class WhenMultipleHandlersAreRegisteredForSameEventType {

        @Test
        void shouldCallAllHandlersEachAckingSeparately_idempotencyEnforcedBySubscriberManager() {
            // Given: two separate beans both handle ORDER_CREATED
            List<String> callOrder = new ArrayList<>();
            Object listenerA = new Object() {
                @FulfillmenttoolsEventListener("ORDER_CREATED")
                public void handle(Object payload) {
                    callOrder.add("A");
                }
            };
            Object listenerB = new Object() {
                @FulfillmenttoolsEventListener("ORDER_CREATED")
                public void handle(Object payload) {
                    callOrder.add("B");
                }
            };
            registerBeans(Map.of("listenerA", listenerA, "listenerB", listenerB));

            AtomicInteger ackCount = new AtomicInteger(0);
            FulfillmenttoolsEvent<String> event = new FulfillmenttoolsEvent<>(
                    "ORDER_CREATED", "e1", "payload",
                    () -> ackCount.incrementAndGet(), () -> {});

            // When
            handler.onEvent(event);

            // Then: both handlers were called
            assertThat(callOrder).containsExactlyInAnyOrder("A", "B");
            // And: ack was called twice (idempotency is enforced by SubscriberManager, not here)
            assertThat(ackCount).hasValue(2);
        }
    }
}
