# Health Client

The Health client provides system health and status checks. Monitor the health of the fulfillmenttools platform.

## Basic Usage

```java
// Check system health
HealthStatus status = client.health().check();
System.out.println("Status: " + status.getStatus());
System.out.println("Timestamp: " + status.getTimestamp());
```

## Async Health Checks

```java
CompletableFuture<HealthStatus> futureStatus = client.health().checkAsync();
futureStatus.thenAccept(status -> {
    System.out.println("System is " + status.getStatus());
});
```

## API Reference

### check()

Get the current system health status.

**Returns:** `HealthStatus`

**Throws:** `FulfillmenttoolsException` if the health check fails

### checkAsync()

Get the system health status asynchronously.

**Returns:** `CompletableFuture<HealthStatus>`
