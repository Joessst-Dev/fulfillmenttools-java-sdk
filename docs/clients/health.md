# Health Client

The Health client provides system health and status checks. Monitor the health of the fulfillmenttools platform.

## Basic Usage

```java
// Get detailed health information
HealthResult healthResult = client.health().health();
System.out.println("Status: " + healthResult.getStatus());

// Get overall system status
SystemStatus status = client.health().status();
System.out.println("System Status: " + status.getStatus());
```

## Async Health Checks

```java
CompletableFuture<HealthResult> futureHealth = client.health().healthAsync();
futureHealth.thenAccept(result -> {
    System.out.println("System is " + result.getStatus());
});

CompletableFuture<SystemStatus> futureStatus = client.health().statusAsync();
futureStatus.thenAccept(status -> {
    System.out.println("System is " + status.getStatus());
});
```

## API Reference

### health()

Retrieve detailed health information including the status of all dependencies.

**Returns:** `HealthResult`

**Throws:** `FulfillmenttoolsException` if the health check request fails

### healthAsync()

Retrieve health information asynchronously.

**Returns:** `CompletableFuture<HealthResult>`

### status()

Retrieve the overall system status of the fulfillmenttools platform.

**Returns:** `SystemStatus`

**Throws:** `FulfillmenttoolsException` if the status check request fails

### statusAsync()

Retrieve the overall system status asynchronously.

**Returns:** `CompletableFuture<SystemStatus>`
