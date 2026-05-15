# Spring Boot Eventing

The Spring Boot starter includes a GCP Pub/Sub event-receiver pipeline that delivers typed, deserialized platform events to plain Spring beans. No manual subscriber management, no raw JSON parsing — annotate a method and the SDK handles the rest.

## Prerequisites

- Spring Boot 3.x
- GCP Pub/Sub subscription(s) already configured to receive fulfillmenttools events (see [Eventing Client](../clients/eventing) for creating subscriptions)
- GCP credentials configured for your runtime environment — Application Default Credentials (ADC), Workload Identity, or an explicit service account key via `spring.cloud.gcp.credentials.location`
- The `spring-cloud-gcp-pubsub` library on the classpath (pulled in automatically as a transitive dependency of the starter)

## Setup

### 1. Add the starter

```kotlin
// build.gradle.kts
dependencies {
    implementation("io.github.joessst-dev:fulfillmenttools-java-sdk-springboot-starter:0.1.0-SNAPSHOT")
}
```

```xml
<!-- pom.xml -->
<dependency>
    <groupId>io.github.joessst-dev</groupId>
    <artifactId>fulfillmenttools-java-sdk-springboot-starter</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

### 2. Configure credentials and subscriptions

```yaml
# application.yml
fulfillmenttools:
  project-id: ocff-abc123-pre
  api-key: ${FFT_API_KEY}
  username: ${FFT_USERNAME}
  password: ${FFT_PASSWORD}
  eventing:
    subscriptions:
      - projects/my-gcp-project/subscriptions/fulfillmenttools-orders
      - projects/my-gcp-project/subscriptions/fulfillmenttools-picking
```

Each entry is the fully-qualified GCP Pub/Sub subscription name (`projects/{project}/subscriptions/{name}`). At least one subscription must be listed for the subscriber manager to start — if the list is empty the eventing pipeline is not activated.

## Basic Usage

### Listening to an event

Annotate any Spring bean method with `@FulfillmenttoolsEventListener` and specify one or more event type strings:

```java
import de.joesst.dev.fulfillmenttools.orders.Order;
import de.joesst.dev.fulfillmenttools.spring.eventing.FulfillmenttoolsEventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventHandler {

    @FulfillmenttoolsEventListener("ORDER_CREATED")
    public void onOrderCreated(Order order) {
        System.out.println("New order: " + order.id().value());
    }
}
```

The SDK deserializes the incoming Pub/Sub message into the registered entity class for that event type, calls your method, and then **automatically acknowledges** the message on success.

If your method throws an exception, the SDK logs the error and **automatically nack**s the message, triggering Pub/Sub redelivery.

### Multiple event types on one method

Pass an array of event type strings to handle several events in a single method:

```java
import de.joesst.dev.fulfillmenttools.pickjobs.PickJob;
import de.joesst.dev.fulfillmenttools.spring.eventing.FulfillmenttoolsEventListener;
import org.springframework.stereotype.Component;

@Component
public class PickJobEventHandler {

    @FulfillmenttoolsEventListener({"PICK_JOB_CREATED", "PICK_JOB_PICKING_COMMENCED", "PICK_JOB_PICKING_FINISHED"})
    public void onPickJobStateChange(PickJob pickJob) {
        System.out.println("Pick job " + pickJob.id().value() + " updated");
    }
}
```

## Explicit Ack / Nack

For fine-grained control — for example when you hand off processing to another thread — add a `FulfillmenttoolsEvent<?>` parameter. The SDK then **does not auto-ack or auto-nack**; the responsibility falls entirely on your code.

```java
import de.joesst.dev.fulfillmenttools.orders.Order;
import de.joesst.dev.fulfillmenttools.spring.eventing.FulfillmenttoolsEvent;
import de.joesst.dev.fulfillmenttools.spring.eventing.FulfillmenttoolsEventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventHandler {

    @FulfillmenttoolsEventListener("ORDER_CREATED")
    public void onOrderCreated(Order order, FulfillmenttoolsEvent<?> event) {
        try {
            processOrder(order);
            event.ack();
        } catch (Exception e) {
            event.nack(); // triggers redelivery
        }
    }
}
```

`FulfillmenttoolsEvent<?>` exposes:

| Method | Description |
|--------|-------------|
| `eventType()` | Event type string, e.g. `"ORDER_CREATED"` |
| `eventId()` | Unique identifier of this event instance |
| `payload()` | Deserialized entity object |
| `ack()` | Acknowledge — message will not be redelivered |
| `nack()` | Negative-acknowledge — Pub/Sub will redeliver |

> **Ack/nack is idempotent.** Calling `ack()` or `nack()` more than once for the same message is safe — only the first call takes effect.

### Async processing

If you want to acknowledge quickly and process asynchronously, ack before handing off:

```java
import de.joesst.dev.fulfillmenttools.orders.Order;
import de.joesst.dev.fulfillmenttools.spring.eventing.FulfillmenttoolsEvent;
import de.joesst.dev.fulfillmenttools.spring.eventing.FulfillmenttoolsEventListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.stereotype.Component;

@Component
public class OrderEventHandler {

    private final ExecutorService executor = Executors.newCachedThreadPool();

    @FulfillmenttoolsEventListener("ORDER_CREATED")
    public void onOrderCreated(Order order, FulfillmenttoolsEvent<?> event) {
        event.ack();  // release the Pub/Sub lease immediately
        executor.submit(() -> processOrder(order));
    }
}
```

Note that nacking after an early ack is not possible — once acknowledged the message will not be redelivered regardless of what happens during processing.

## Available Event Types

The following event types are pre-registered with their corresponding entity classes. Pass the event type string to `@FulfillmenttoolsEventListener` and declare the matching entity class as the method parameter type.

> If an event type is not in this list, its payload is still delivered — deserialized as `Map<String, Object>` rather than a typed entity. You can cast `event.payload()` to `Map<String, Object>` and inspect the raw fields, or call `registry.register(...)` to add a typed mapping (see [Registering Additional Event Types](#registering-additional-event-types)).

### Orders

| Event Type | Entity Class |
|------------|-------------|
| `ORDER_CREATED` | `Order` |
| `ORDER_MODIFIED` | `Order` |
| `ORDER_CANCELLED` | `Order` |
| `ORDER_CANCELLED_BY_EXPIRY` | `Order` |
| `ORDER_FORCE_CANCELLED` | `Order` |
| `ORDER_UNLOCKED` | `Order` |

### Pick Jobs

| Event Type | Entity Class |
|------------|-------------|
| `PICK_JOB_CREATED` | `PickJob` |
| `PICK_JOB_PICKING_COMMENCED` | `PickJob` |
| `PICK_JOB_PICKING_FINISHED` | `PickJob` |
| `PICK_JOB_PICKING_PAUSED` | `PickJob` |
| `PICK_JOB_PICK_LINE_PICKED` | `PickJob` |
| `PICK_JOB_ABORTED` | `PickJob` |
| `PICK_JOB_CANCELED` | `PickJob` |
| `PICK_JOB_REROUTED` | `PickJob` |
| `PICK_JOB_RESET` | `PickJob` |
| `PICK_JOB_OPENED` | `PickJob` |
| `PICKING_COMPLETED` | `PickJob` |

### Pack Jobs

| Event Type | Entity Class |
|------------|-------------|
| `PACK_JOB_CREATED` | `PackJob` |
| `PACK_JOB_UPDATED` | `PackJob` |
| `PACK_JOB_CLOSED` | `PackJob` |
| `PACK_JOB_CANCELED` | `PackJob` |

### Handover Jobs

| Event Type | Entity Class |
|------------|-------------|
| `HANDOVERJOB_CREATED` | `HandoverJob` |
| `HANDOVERJOB_HANDED_OVER` | `HandoverJob` |
| `HANDOVERJOB_REVERTED` | `HandoverJob` |
| `HANDOVERJOB_CANCELED` | `HandoverJob` |

### Returns

| Event Type | Entity Class |
|------------|-------------|
| `RETURN_CREATED` | `Return` |
| `RETURN_CLAIMED` | `Return` |
| `RETURN_CLOSED` | `Return` |
| `RETURN_CANCELED` | `Return` |
| `RETURN_UPDATED` | `Return` |

### Facilities

| Event Type | Entity Class |
|------------|-------------|
| `FACILITY_CREATED` | `Facility` |
| `FACILITY_DELETED` | `Facility` |
| `FACILITY_UPDATED` | `Facility` |
| `FACILITY_SUSPENDED` | `Facility` |
| `FACILITY_WENT_OFFLINE` | `Facility` |
| `FACILITY_WENT_ONLINE` | `Facility` |
| `FACILITY_GROUP_CREATED` | `FacilityGroup` |
| `FACILITY_GROUP_DELETED` | `FacilityGroup` |
| `FACILITY_GROUP_UPDATED` | `FacilityGroup` |

### Inventory

| Event Type | Entity Class |
|------------|-------------|
| `INVENTORY_STOCKS_CREATED` | `StockItem` |
| `INVENTORY_STOCKS_DELETED` | `StockItem` |
| `INVENTORY_STOCKS_VALUE_CHANGED` | `StockItem` |
| `INVENTORY_STOCKS_LOCATION_CHANGED` | `StockItem` |
| `INVENTORY_RESERVATIONS_CREATED` | `Reservation` |
| `INVENTORY_RESERVATIONS_DELETED` | `Reservation` |

### Storage Locations

| Event Type | Entity Class |
|------------|-------------|
| `STORAGE_LOCATIONS_CREATED` | `StorageLocation` |
| `STORAGE_LOCATIONS_DELETED` | `StorageLocation` |
| `STORAGE_LOCATIONS_INFORMATION_CHANGED` | `StorageLocation` |

### Routing

| Event Type | Entity Class |
|------------|-------------|
| `ROUTING_PLAN_ROUTED` | `RoutingPlan` |
| `ROUTING_PLAN_NOT_ROUTABLE` | `RoutingPlan` |
| `ROUTING_PLAN_CANCELLED` | `RoutingPlan` |
| `ROUTING_PLAN_SPLITTED` | `RoutingPlan` |
| `ROUTING_PLAN_FALLBACK` | `RoutingPlan` |
| `ROUTING_PLAN_WAITING` | `RoutingPlan` |
| `ROUTING_PLAN_REROUTEPLAN_CREATED` | `RoutingPlan` |
| `UPCOMING_TIME_TRIGGERED_REROUTE` | `RoutingPlan` |

### Stow Jobs (Inbound)

| Event Type | Entity Class |
|------------|-------------|
| `STOW_JOB_CREATED` | `StowJob` |
| `STOW_JOB_COMMENCED` | `StowJob` |
| `STOW_JOB_PAUSED` | `StowJob` |
| `STOW_JOB_OPENED` | `StowJob` |
| `STOW_JOB_LINE_ITEMS_STOWED` | `StowJob` |
| `STOW_JOB_CLOSED` | `StowJob` |
| `STOW_JOB_CANCELED` | `StowJob` |

### Users

| Event Type | Entity Class |
|------------|-------------|
| `USER_CREATED` | `User` |
| `USER_DELETED` | `User` |
| `USER_UPDATED` | `User` |

### External Actions

| Event Type | Entity Class |
|------------|-------------|
| `EXTERNAL_ACTION_EXECUTED` | `ExternalAction` |

## Customization

### Registering Additional Event Types

If your platform tenant publishes event types not in the default registry, register them at startup:

```java
import de.joesst.dev.fulfillmenttools.spring.eventing.FulfillmenttoolsEventTypeRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventingConfiguration {

    @Bean
    public FulfillmenttoolsEventTypeRegistry fulfillmenttoolsEventTypeRegistry() {
        FulfillmenttoolsEventTypeRegistry registry = new FulfillmenttoolsEventTypeRegistry();
        registry.register("MY_CUSTOM_EVENT", MyCustomEntity.class);
        return registry;
    }
}
```

Declaring your own `FulfillmenttoolsEventTypeRegistry` bean replaces the auto-configured default. Call `new FulfillmenttoolsEventTypeRegistry()` first to keep the built-in mappings, then add your own on top.

### Custom ObjectMapper

The SDK registers a dedicated `ObjectMapper` bean named `fulfillmenttoolsObjectMapper` for event deserialization. To use your own configuration instead, declare a bean with that exact name.

> **When overriding, replicate the SDK's defaults** — omitting them will break deserialization of entity records and ISO timestamps. Start from the snippet below and add your own modules on top:

```java
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventingConfiguration {

    @Bean("fulfillmenttoolsObjectMapper")
    public ObjectMapper fulfillmenttoolsObjectMapper() {
        return new ObjectMapper()
            .registerModule(new JavaTimeModule())
            // chain additional modules here, e.g.: .registerModule(new ParameterNamesModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }
}
```

This bean name is isolated — it does not affect other `ObjectMapper` beans in your application context.

### Full Control: Custom Event Handler

For advanced routing logic — logging, metrics, conditional processing, or multi-tenancy — replace the entire handler by declaring a `FulfillmenttoolsEventHandler` bean. The annotation-driven default backs off automatically via `@ConditionalOnMissingBean`.

```java
import de.joesst.dev.fulfillmenttools.orders.Order;
import de.joesst.dev.fulfillmenttools.pickjobs.PickJob;
import de.joesst.dev.fulfillmenttools.spring.eventing.FulfillmenttoolsEventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventingConfiguration {

    @Bean
    public FulfillmenttoolsEventHandler fulfillmenttoolsEventHandler() {
        return event -> switch (event.eventType()) {
            case "ORDER_CREATED" -> {
                handleOrder((Order) event.payload());
                event.ack();
            }
            case "PICK_JOB_CREATED" -> {
                handlePickJob((PickJob) event.payload());
                event.ack();
            }
            default -> {
                // acknowledge anything you don't handle to avoid redelivery loops
                event.ack();
            }
        };
    }
}
```

> When using a custom handler, ack/nack responsibility is entirely yours. Always ack or nack every event — including ones you don't handle — otherwise messages will be held until their Pub/Sub ack deadline expires and will be redelivered.

## Operational Considerations

### Unregistered event types

If a message arrives for an event type that has no registered handler, the SDK logs a warning and auto-acknowledges the message:

```
WARN  - No @FulfillmenttoolsEventListener registered for event type 'SOME_UNKNOWN_EVENT', auto-acking
```

This prevents dead-letter queue build-up when a subscription receives event types your application does not handle. To suppress the warning for known-but-intentionally-unhandled types, register a no-op handler or filter at the subscription level in your GCP configuration.

### Startup validation

Method signatures are validated when the Spring context starts. A method annotated with `@FulfillmenttoolsEventListener` that has more than one non-event parameter causes the context to fail to start with a descriptive `IllegalStateException`:

```
IllegalStateException: @FulfillmenttoolsEventListener method
'com.example.MyHandler#handle' has 2 non-event parameters; at most 1 is supported
```

Valid signatures are:
- `void handle(EntityType payload)` — payload only, auto-ack
- `void handle(EntityType payload, FulfillmenttoolsEvent<?> event)` — explicit ack/nack
- `void handle(FulfillmenttoolsEvent<?> event)` — event only, explicit ack/nack

The event-only form is useful when you need full event metadata (`eventType()`, `eventId()`) but want to handle the payload generically via `event.payload()`:

```java
@FulfillmenttoolsEventListener({"ORDER_CREATED", "ORDER_MODIFIED", "ORDER_CANCELLED"})
public void onAnyOrderEvent(FulfillmenttoolsEvent<?> event) {
    log.info("Received {} for event {}", event.eventType(), event.eventId());
    Order order = (Order) event.payload();
    // process order...
    event.ack();
}
```

### Thread model

Handler methods are invoked on GCP Pub/Sub subscriber threads. The number of concurrent threads is determined by the `PubSubTemplate` / `Subscriber` configuration. For the payload-only convention the thread is held until your method returns; for the explicit-ack convention the thread is released as soon as `onEvent` returns regardless of whether `ack()`/`nack()` have been called.

If your handler performs slow I/O (database writes, downstream HTTP calls), consider acknowledging early and handing off to a thread pool to avoid holding Pub/Sub flow-control leases.

### Malformed or undeserializable messages

If a Pub/Sub message cannot be parsed as JSON, or if its payload cannot be deserialized into the registered entity class, the SDK logs an error and nacks the message:

```
ERROR - Failed to process fulfillmenttools event from 'projects/.../subscriptions/...': ...
```

The message is redelivered until its retry policy is exhausted and it moves to the dead-letter queue. If you see this error, check:
- The subscription is pointed at the correct fulfillmenttools Pub/Sub topic
- The entity class registered for that event type in `FulfillmenttoolsEventTypeRegistry` matches the actual payload shape

### Shutdown

`FulfillmenttoolsSubscriberManager` implements Spring's `SmartLifecycle`. On context close all GCP subscribers are stopped gracefully: each subscriber receives `stopAsync()` first (in parallel), then the manager waits up to 5 seconds per subscriber for `awaitTerminated` to complete. Messages already in-flight may be redelivered after shutdown if they were not yet acknowledged.

## Testing

### Unit-testing a handler method

Handler methods are plain Java methods — test them directly without Spring. No ack setup is needed; ack is handled by the SDK after your method returns, not inside your method.

```java
import de.joesst.dev.fulfillmenttools.id.OrderId;
import de.joesst.dev.fulfillmenttools.orders.Order;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class OrderEventHandlerTest {

    @Test
    void shouldDelegateToOrderService() {
        // Given
        OrderService orderService = mock(OrderService.class);
        OrderEventHandler handler = new OrderEventHandler(orderService);
        Order order = Order.builder()
            .id(OrderId.builder().value("ord-42").build())
            .build();

        // When — call the handler method directly, just like any plain Java method
        // (the SDK calls ack after the method returns; you do not call it here)
        handler.onOrderCreated(order);

        // Then
        verify(orderService).processNewOrder(order);
    }
}
```

### Integration-testing with ApplicationContextRunner

Use Spring Boot's `ApplicationContextRunner` to verify auto-configuration wiring without starting a real GCP Pub/Sub connection:

```java
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import de.joesst.dev.fulfillmenttools.spring.eventing.FulfillmenttoolsEventDispatcher;
import de.joesst.dev.fulfillmenttools.spring.eventing.FulfillmenttoolsEventingAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class EventingAutoConfigurationTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(FulfillmenttoolsEventingAutoConfiguration.class));

    @Test
    void shouldRegisterDispatcherWhenPubSubPresent() {
        runner.withBean(PubSubTemplate.class, () -> mock(PubSubTemplate.class))
            .run(context -> {
                assertThat(context).hasSingleBean(FulfillmenttoolsEventDispatcher.class);
            });
    }
}
```

### Testing the full dispatch pipeline

To drive an event end-to-end through the dispatcher and assert both that ack was called and that the handler received the right payload, register a capturing `FulfillmenttoolsEventHandler` in a `@TestConfiguration`. It replaces the annotation-driven default via `@ConditionalOnMissingBean`:

```java
import de.joesst.dev.fulfillmenttools.orders.Order;
import de.joesst.dev.fulfillmenttools.spring.eventing.FulfillmenttoolsEvent;
import de.joesst.dev.fulfillmenttools.spring.eventing.FulfillmenttoolsEventDispatcher;
import de.joesst.dev.fulfillmenttools.spring.eventing.FulfillmenttoolsEventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderEventHandlerIntegrationTest {

    static final AtomicReference<FulfillmenttoolsEvent<?>> captured = new AtomicReference<>();

    @TestConfiguration
    static class Config {
        @Bean
        FulfillmenttoolsEventHandler capturingHandler() {
            return event -> {
                captured.set(event);
                event.ack();
            };
        }
    }

    @Autowired
    FulfillmenttoolsEventDispatcher dispatcher;

    @BeforeEach
    void reset() {
        captured.set(null);
    }

    @Test
    void shouldRouteOrderCreatedToHandler() {
        // Given
        AtomicBoolean acked = new AtomicBoolean(false);
        byte[] message = """
            {"eventId":"e1","event":"ORDER_CREATED","payload":{"id":{"value":"ord-1"}}}
            """.getBytes();

        // When
        dispatcher.dispatch(message, () -> acked.set(true), () -> {});

        // Then: message was acknowledged
        assertThat(acked).isTrue();

        // And: handler received the correctly typed and deserialized payload
        FulfillmenttoolsEvent<?> event = captured.get();
        assertThat(event.eventType()).isEqualTo("ORDER_CREATED");
        assertThat(event.eventId()).isEqualTo("e1");
        assertThat(event.payload()).isInstanceOf(Order.class);
        assertThat(((Order) event.payload()).id().value()).isEqualTo("ord-1");
    }
}
```
