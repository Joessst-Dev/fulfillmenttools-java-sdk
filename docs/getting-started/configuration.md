# Configuration

Configure the `FulfillmenttoolsClient` with the builder API to customize HTTP behavior, retries, and JSON serialization.

## Builder Options

### Base URL

The API endpoint base URL (required).

```java
FulfillmenttoolsClient client = FulfillmenttoolsClient.builder()
    .baseUrl("https://your-project.api.fulfillmenttools.com")
    .credentials(...)
    .build();
```

### Credentials

Provide `EmailPasswordCredentials` or a custom `TokenProvider` (required).

```java
FulfillmenttoolsClient client = FulfillmenttoolsClient.builder()
    .baseUrl("...")
    .credentials(new EmailPasswordCredentials(email, password, apiKey))
    .build();
```

### Retry Configuration

Set the maximum number of total attempts (default: 3, meaning up to 2 retries).

```java
FulfillmenttoolsClient client = FulfillmenttoolsClient.builder()
    .baseUrl("...")
    .credentials(...)
    .retryMaxAttempts(5)  // 1 initial + 4 retries
    .build();
```

The SDK uses exponential backoff for retries on transient failures (5xx, timeouts).

### Custom HTTP Transport

Override the default JDK HTTP transport (useful for testing or custom interceptors):

```java
FulfillmenttoolsClient client = FulfillmenttoolsClient.builder()
    .baseUrl("...")
    .credentials(...)
    .httpTransport(customTransport)
    .build();
```

### Custom Object Mapper

Override the default Jackson `ObjectMapper` for JSON serialization:

```java
ObjectMapper mapper = new ObjectMapper()
    .registerModule(new JavaTimeModule());

FulfillmenttoolsClient client = FulfillmenttoolsClient.builder()
    .baseUrl("...")
    .credentials(...)
    .objectMapper(mapper)
    .build();
```

## Spring Boot Configuration

When using the starter module, configure via `application.yml`:

```yaml
fulfillmenttools:
  project-id: ocff-abc123-pre
  api-key: ${FFT_API_KEY}
  username: ${FFT_USERNAME}
  password: ${FFT_PASSWORD}
  retry-max-attempts: 3
```

The `FulfillmenttoolsClient` bean is automatically created and injected:

```java
@Service
public class OrderService {
    private final FulfillmenttoolsClient client;

    public OrderService(FulfillmenttoolsClient client) {
        this.client = client;
    }

    public Order getOrder(String orderId) {
        return client.orders().get(new OrderId(orderId));
    }
}
```
