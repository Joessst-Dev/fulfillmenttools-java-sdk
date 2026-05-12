# fulfillmenttools Java SDK

> **Disclaimer:** This is an independent, community-maintained open source project and is **not** officially developed, maintained, or endorsed by fulfillmenttools GmbH or any of its affiliates. Use at your own risk. For official SDKs and support, refer to the fulfillmenttools documentation and support channels.

A lightweight Java 21 client library for the fulfillmenttools cloud-based Order Management and Fulfillment platform API. Zero framework dependencies — use it with any Java application.

[![Java](https://img.shields.io/badge/Java-21+-orange)](https://www.oracle.com/java/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## What is fulfillmenttools?

fulfillmenttools is a cloud platform for managing orders, facilities, inventory, picking, and returns. This SDK provides a fluent, type-safe Java interface to its REST API, handling authentication and token management transparently.

## Quick Start

### Installation

The SDK is published to GitHub Packages. You'll need a GitHub token to install it.

**Gradle (Kotlin DSL):**

```kotlin
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/Joessst-Dev/fulfillmenttools-java-sdk")
        credentials {
            username = System.getenv("GITHUB_ACTOR")
            password = System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    implementation("de.joesst.dev:fulfillmenttools-java-sdk:0.1.0-SNAPSHOT")
}
```

**Maven:**

```xml
<repository>
  <id>github</id>
  <url>https://maven.pkg.github.com/Joessst-Dev/fulfillmenttools-java-sdk</url>
</repository>

<dependency>
  <groupId>de.joesst.dev</groupId>
  <artifactId>fulfillmenttools-java-sdk</artifactId>
  <version>0.1.0-SNAPSHOT</version>
</dependency>
```

### Basic Usage

All interactions start with a `FulfillmenttoolsClient`, instantiated with your API credentials and base URL.

**Authenticate and create a client:**

```java
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.EmailPasswordCredentials;

FulfillmenttoolsClient client = FulfillmenttoolsClient.builder()
    .baseUrl("https://<projectId>.api.fulfillmenttools.com")
    .credentials(new EmailPasswordCredentials(
        "user@example.com",
        "password",
        "apiKey"
    ))
    .build();
```

The SDK handles token acquisition, caching, and proactive refresh automatically — you never touch tokens directly.

## Core Concepts

### Resource Clients

The main client provides access to specialized clients for each resource type:

| Method | Interface | Operations |
|--------|-----------|------------|
| `orders()` | `OrdersClient` | get, list, create, update, delete |
| `facilities()` | `FacilitiesClient` | get, list, create, update, delete |
| `stocks()` | `StocksClient` | list |
| `pickJobs()` | `PickJobsClient` | get, list, update |
| `reservations()` | `ReservationsClient` | get, list, create, delete |

All operations except `listAll` have `*Async` variants returning `CompletableFuture<T>`.

### Authentication

The SDK uses Google Identity Toolkit (Firebase Auth) under the hood. Provide your credentials once when creating the client:

```java
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.EmailPasswordCredentials;

var credentials = new EmailPasswordCredentials(
    email,     // Your fulfillmenttools user email
    password,  // Your password
    apiKey     // Your API key (from fulfillmenttools console)
);

var client = FulfillmenttoolsClient.builder()
    .baseUrl("https://<projectId>.api.fulfillmenttools.com")
    .credentials(credentials)
    .build();
```

Tokens are cached and refreshed automatically before expiration. No refresh logic needed in your code.

## Usage Examples

### Fetch a Single Order (Sync)

```java
var order = client.orders().get("order-123");

System.out.println("Order status: " + order.status());
```

### Fetch an Order Asynchronously

```java
client.orders()
    .getAsync("order-123")
    .thenAccept(order -> {
        System.out.println("Order: " + order.status());
    })
    .exceptionally(throwable -> {
        System.err.println("Failed to fetch order: " + throwable.getMessage());
        return null;
    });
```

### List Orders with Pagination

```java
import de.joesst.dev.fulfillmenttools.orders.OrderListRequest;

var request = OrderListRequest.builder().size(25).build();

var page = client.orders().list(request);

for (var order : page.items()) {
    System.out.println(order.id());
}

if (page.hasMore()) {
    var nextRequest = request.toBuilder().startAfterId(page.nextCursor()).build();
    var nextPage = client.orders().list(nextRequest);
    // Process next page...
}
```

### Iterate All Orders (Auto-Pagination)

Use `listAll()` for a lazy, automatic pagination loop:

```java
import de.joesst.dev.fulfillmenttools.orders.OrderListRequest;

var request = OrderListRequest.builder().size(100).build();

for (var order : client.orders().listAll(request)) {
    System.out.println("Order: " + order.id());
}
```

The SDK fetches pages on demand as you iterate — no manual cursor tracking needed.

### Create an Order

```java
import de.joesst.dev.fulfillmenttools.orders.CreateOrderRequest;

var newOrder = client.orders().create(
    CreateOrderRequest.builder()
        .tenantOrderId("my-order-001")
        .build()
);

System.out.println("Created order: " + newOrder.id());
```

### Error Handling

All SDK exceptions extend `FulfillmenttoolsException` (unchecked). Handle specific errors with type-safe catches:

```java
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.ValidationException;
import de.joesst.dev.fulfillmenttools.RateLimitException;

try {
    var order = client.orders().get("order-123");
} catch (NotFoundException e) {
    System.err.println("Order not found");
} catch (ValidationException e) {
    System.err.println("Invalid request: " + e.getMessage());
} catch (RateLimitException e) {
    String wait = e.retryAfter().map(d -> d.toMillis() + "ms").orElse("unknown");
    System.err.println("Rate limited. Retry after: " + wait);
} catch (FulfillmenttoolsException e) {
    System.err.println("API error: " + e.getMessage());
}
```

**Exception types:**

- `NotFoundException` — 404 (resource not found)
- `ValidationException` — 400/422 (invalid request data)
- `AuthenticationException` — 401 (invalid credentials)
- `AuthorizationException` — 403 (permission denied)
- `ConflictException` — 409 (conflict, e.g., duplicate)
- `RateLimitException` — 429 (rate limited; check `retryAfter()`)
- `ServerException` — 5xx (server error)
- `TransportException` — I/O failure (network, connection timeout)

### Facilities and Stocks

```java
import de.joesst.dev.fulfillmenttools.facilities.FacilityListRequest;
import de.joesst.dev.fulfillmenttools.stocks.StockListRequest;

// List facilities
var facilities = client.facilities().list(FacilityListRequest.builder().build());

for (var facility : facilities.items()) {
    System.out.println("Facility: " + facility.name());
}

// List stocks for a specific facility
var stocks = client.stocks().list(
    StockListRequest.builder().facilityRef("fac-1").build()
);

for (var stock : stocks.items()) {
    System.out.println("Article: " + stock.tenantArticleId() + ", Qty: " + stock.quantity());
}
```

## API Reference

Full API documentation is available via Javadoc. Build the project to generate:

```bash
./gradlew javadoc
```

Javadoc HTML is output to `build/docs/javadoc/`.

## Building & Contributing

### Requirements

- Java 21 or later
- Gradle 9

### Build

```bash
./gradlew build
```

This compiles, runs all tests, and generates Javadoc.

### Run Tests

```bash
# All tests
./gradlew test

# Single test class
./gradlew test --tests "fully.qualified.ClassName"

# Single test method
./gradlew test --tests "fully.qualified.ClassName.methodName"
```

The SDK includes 121 tests covering all client operations with WireMock for HTTP mocking.

### Code Style

The project uses standard Java conventions. Format your code before committing.

## Dependencies

The SDK has minimal runtime dependencies:

- **Jackson** — JSON serialization (databind + JSR310 modules)
- **SLF4J** — logging facade (no implementation required; users provide their own)

No Spring, CDI, HTTP client frameworks, or other opinionated dependencies — integrate it into any Java application.

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.

## Support & Issues

Found a bug? Have a question? Open an issue on [GitHub](https://github.com/Joessst-Dev/fulfillmenttools-java-sdk/issues).

For fulfillmenttools platform documentation, visit [fulfillmenttools.de](https://fulfillmenttools.de).
