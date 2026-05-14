# Health Client

The Health client checks the health of the fulfillmenttools platform. `health()` returns detailed per-dependency status; `status()` returns the overall system status.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.health.HealthResult;
import de.joesst.dev.fulfillmenttools.health.HealthDependencyStatus;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

try {
    HealthResult result = client.health().health();
    System.out.println("Status: " + result.status());
    for (HealthDependencyStatus dep : result.dependencies()) {
        System.out.println(dep.name() + ": " + dep.status());
    }
} catch (FulfillmenttoolsException e) {
    System.out.println("Health check failed: " + e.getMessage());
}
```

## Overall System Status

```java
import de.joesst.dev.fulfillmenttools.health.SystemStatus;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

try {
    SystemStatus status = client.health().status();
    System.out.println("System: " + status.status());
} catch (FulfillmenttoolsException e) {
    System.out.println("Status check failed: " + e.getMessage());
}
```

## Async Usage

All methods have async variants returning `CompletableFuture`:

```java
import de.joesst.dev.fulfillmenttools.health.HealthResult;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

CompletableFuture<HealthResult> future = client.health().healthAsync();

future.whenComplete((result, ex) -> {
    if (ex != null) {
        Throwable cause = ex instanceof CompletionException && ex.getCause() != null
            ? ex.getCause() : ex;
        System.out.println("Error: " + cause.getMessage());
    } else {
        System.out.println("Status: " + result.status());
        result.dependencies().forEach(dep ->
            System.out.println(dep.name() + ": " + dep.status())
        );
    }
});
```

```java
import de.joesst.dev.fulfillmenttools.health.SystemStatus;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

CompletableFuture<SystemStatus> future = client.health().statusAsync();

future.whenComplete((status, ex) -> {
    if (ex != null) {
        Throwable cause = ex instanceof CompletionException && ex.getCause() != null
            ? ex.getCause() : ex;
        System.out.println("Error: " + cause.getMessage());
    } else {
        System.out.println("System: " + status.status());
    }
});
```

## API Reference

### health()

Retrieve detailed health information including the status of all dependencies.

**Returns:** `HealthResult` — contains `status()` (overall) and `dependencies()` (list of `HealthDependencyStatus`)

**Throws:** `FulfillmenttoolsException` if the request fails

### healthAsync()

Retrieve detailed health information asynchronously.

**Returns:** `CompletableFuture<HealthResult>`

### status()

Retrieve the overall system status.

**Returns:** `SystemStatus` — contains `status()` (e.g. `"UP"`, `"DOWN"`, `"DEGRADED"`)

**Throws:** `FulfillmenttoolsException` if the request fails

### statusAsync()

Retrieve the overall system status asynchronously.

**Returns:** `CompletableFuture<SystemStatus>`
