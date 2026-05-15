# Spring Boot Integration

The SDK provides a Spring Boot starter module for seamless integration with Spring applications. Automatic configuration and dependency injection out of the box.

## Adding the Starter

Add the starter to your Spring Boot 3.x project:

```kotlin
// build.gradle.kts
dependencies {
    implementation("io.github.joessst-dev:fulfillmenttools-java-sdk-springboot-starter:0.1.0-SNAPSHOT")
}
```

Or with Maven:

```xml
<dependency>
    <groupId>io.github.joessst-dev</groupId>
    <artifactId>fulfillmenttools-java-sdk-springboot-starter</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Configuration

Configure the SDK via `application.yml`:

```yaml
fulfillmenttools:
  project-id: ocff-abc123-pre
  api-key: ${FFT_API_KEY}
  username: ${FFT_USERNAME}
  password: ${FFT_PASSWORD}
```

Or `application.properties`:

```properties
fulfillmenttools.project-id=ocff-abc123-pre
fulfillmenttools.api-key=${FFT_API_KEY}
fulfillmenttools.username=${FFT_USERNAME}
fulfillmenttools.password=${FFT_PASSWORD}
```

## Using the Client

The starter automatically creates and configures a `FulfillmenttoolsClient` bean. Inject it into your services:

```java
import org.springframework.stereotype.Service;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.orders.Order;
import de.joesst.dev.fulfillmenttools.orders.OrderListRequest;
import de.joesst.dev.fulfillmenttools.id.OrderId;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrderService {
    private final FulfillmenttoolsClient client;

    public OrderService(FulfillmenttoolsClient client) {
        this.client = client;
    }

    public Order getOrder(String orderId) {
        return client.orders().get(OrderId.builder().value(orderId).build());
    }

    public List<Order> listOrders() {
        return StreamSupport.stream(
            client.orders()
                .listAll(OrderListRequest.builder().size(100).build())
                .spliterator(), false)
            .collect(Collectors.toList());
    }
}
```

## Custom Bean Configuration

Override the default bean by defining your own in a `@Configuration` class:

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.EmailPasswordCredentials;

@Configuration
public class FulfillmenttoolsConfiguration {

    @Bean
    public FulfillmenttoolsClient fulfillmenttoolsClient() {
        return FulfillmenttoolsClient.builder()
            .baseUrl("https://your-project.api.fulfillmenttools.com")
            .credentials(new EmailPasswordCredentials(
                System.getenv("FFT_USERNAME"),
                System.getenv("FFT_PASSWORD"),
                System.getenv("FFT_API_KEY")
            ))
            .retryMaxAttempts(5)
            .build();
    }
}
```

## Async in Spring Services

Use Spring's `@Async` with the SDK's async methods:

```java
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.orders.Order;
import de.joesst.dev.fulfillmenttools.id.OrderId;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncOrderService {
    private final FulfillmenttoolsClient client;

    public AsyncOrderService(FulfillmenttoolsClient client) {
        this.client = client;
    }

    @Async
    public CompletableFuture<Order> fetchOrder(String orderId) {
        return client.orders()
            .getAsync(OrderId.builder().value(orderId).build());
    }
}
```

## Testing

Mock the `FulfillmenttoolsClient` bean in tests. `Order` is an immutable record — build stub responses with `Order.builder()` and assert with record accessors:

```java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.orders.Order;
import de.joesst.dev.fulfillmenttools.orders.OrdersClient;
import de.joesst.dev.fulfillmenttools.id.OrderId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceTest {

    @MockBean
    private FulfillmenttoolsClient client;

    @Autowired
    private OrderService orderService;

    @Test
    public void testGetOrder() {
        OrdersClient orders = mock(OrdersClient.class);
        when(client.orders()).thenReturn(orders);

        Order expected = Order.builder()
            .id(OrderId.builder().value("ord-123").build())
            .status("OPEN")
            .build();
        when(orders.get(any())).thenReturn(expected);

        Order result = orderService.getOrder("ord-123");

        assertEquals("OPEN", result.status());
        verify(orders).get(any());
    }
}
```

## Receiving Events (Pub/Sub)

The starter includes a full Pub/Sub event-receiver pipeline. See [Spring Boot Eventing](./spring-boot-eventing) for the complete guide covering `@FulfillmenttoolsEventListener`, ack/nack conventions, available event types, and customization options.

## Environment Variables

For production deployments, use environment variables:

```bash
export FFT_PROJECT_ID="ocff-abc123-pre"
export FFT_API_KEY="your-api-key"
export FFT_USERNAME="your-email@example.com"
export FFT_PASSWORD="your-password"
```

Then reference them in your configuration:

```yaml
fulfillmenttools:
  project-id: ${FFT_PROJECT_ID}
  api-key: ${FFT_API_KEY}
  username: ${FFT_USERNAME}
  password: ${FFT_PASSWORD}
```
