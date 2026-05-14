# Returns Client

The Returns client provides access to return management operations in the fulfillmenttools platform. Handle and track product returns through your fulfillment network.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.ReturnId;
import de.joesst.dev.fulfillmenttools.returns.Return;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

// Get a return by ID
try {
    Return returnJob = client.returns().get(ReturnId.builder().value("ret-001").build());
    System.out.println("Status: " + returnJob.status());
    System.out.println("Short ID: " + returnJob.shortId());
} catch (NotFoundException e) {
    System.out.println("Return not found");
} catch (FulfillmenttoolsException e) {
    System.out.println("Request failed: " + e.getMessage());
}
```

## Listing Returns

List returns with pagination:

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.returns.ReturnListRequest;

Page<Return> page = client.returns().list(
    ReturnListRequest.builder()
        .size(50)
        .build()
);

for (Return returnJob : page.items()) {
    System.out.println(returnJob.id().value() + " — " + returnJob.status());
}
```

Iterate through all pages automatically:

```java
Iterable<Return> allReturns = client.returns().listAll(
    ReturnListRequest.builder()
        .size(100)
        .build()
);

for (Return returnJob : allReturns) {
    System.out.println(returnJob.id().value());
}
```

Manual pagination using `nextCursor()`:

```java
Page<Return> page = client.returns().list(ReturnListRequest.builder().size(20).build());

while (page.hasMore()) {
    page = client.returns().list(
        ReturnListRequest.builder()
            .size(20)
            .startAfterId(page.nextCursor())
            .build()
    );
    for (Return returnJob : page.items()) {
        System.out.println(returnJob.id().value());
    }
}
```

## Filtering Returns

`ReturnListRequest` supports several optional filters. Note that the facility filter field is `facilityId`, not `facilityRef`:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import java.util.List;

// Filter by facility and job status
Page<Return> openReturns = client.returns().list(
    ReturnListRequest.builder()
        .facilityId(FacilityId.builder().value("fac-001").build())
        .itemReturnJobStatus(List.of("OPEN", "IN_PROGRESS"))
        .size(50)
        .build()
);

// Filter by item-level return status
Page<Return> returns = client.returns().list(
    ReturnListRequest.builder()
        .itemReturnStatus(List.of("RETURNED"))
        .size(50)
        .build()
);

// Text search
Page<Return> searchResults = client.returns().list(
    ReturnListRequest.builder()
        .searchTerm("order-123")
        .size(20)
        .build()
);
```

## Creating a Return

`originFacilityRefs` and `status` are required fields:

```java
import de.joesst.dev.fulfillmenttools.returns.CreateReturnRequest;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import java.util.List;

Return created = client.returns().create(
    CreateReturnRequest.builder()
        .originFacilityRefs(List.of(FacilityId.builder().value("fac-001").build()))
        .status("OPEN")
        .build()
);
System.out.println("Created return: " + created.id().value());
```

## State Transitions

Returns move through a workflow lifecycle. Each transition requires the current `version` for optimistic locking. Transitions are mutually exclusive — always use the version from the most recent state of the return:

```java
Return returnJob = client.returns().get(ReturnId.builder().value("ret-001").build());
ReturnId id = returnJob.id();
int version = returnJob.version();

// Start processing the return
Return started = client.returns().start(id, version);

// Finish the return (uses the version returned by start())
Return finished = client.returns().finish(id, started.version());
```

Other available transitions (each called independently with the current version):

```java
// Restart a return that was previously finished or in progress
Return restarted = client.returns().restart(id, version);
```

## Async Usage

All methods have async variants returning `CompletableFuture`:

```java
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

CompletableFuture<Return> future = client.returns().getAsync(
    ReturnId.builder().value("ret-001").build()
);

future.whenComplete((returnJob, ex) -> {
    if (ex != null) {
        Throwable cause = ex instanceof CompletionException && ex.getCause() != null
            ? ex.getCause() : ex;
        System.out.println("Error: " + cause.getMessage());
    } else {
        System.out.println("Status: " + returnJob.status());
    }
});
```

## API Reference

### get(ReturnId)

Get a return by ID.

**Parameters:**
- `returnId: ReturnId` — The return identifier

**Returns:** `Return`

**Throws:** `NotFoundException` (404), `FulfillmenttoolsException` if the request fails

### getAsync(ReturnId)

Get a return by ID asynchronously.

**Parameters:**
- `returnId: ReturnId` — The return identifier

**Returns:** `CompletableFuture<Return>`

### list(ReturnListRequest)

List returns with pagination.

**Parameters:**
- `request: ReturnListRequest` — List request with filter and pagination parameters

**Returns:** `Page<Return>`

**Throws:** `FulfillmenttoolsException` if the request fails

### listAsync(ReturnListRequest)

List returns asynchronously.

**Parameters:**
- `request: ReturnListRequest` — List request

**Returns:** `CompletableFuture<Page<Return>>`

### listAll(ReturnListRequest)

List all returns, automatically iterating through pages.

**Parameters:**
- `request: ReturnListRequest` — List request

**Returns:** `Iterable<Return>`

### create(CreateReturnRequest)

Create a new return. `originFacilityRefs` and `status` are required.

**Parameters:**
- `request: CreateReturnRequest` — Create return request

**Returns:** `Return`

**Throws:** `FulfillmenttoolsException` if the request fails

### createAsync(CreateReturnRequest)

Create a new return asynchronously.

**Parameters:**
- `request: CreateReturnRequest` — Create return request

**Returns:** `CompletableFuture<Return>`

### start(ReturnId, int)

Start a return process.

**Parameters:**
- `returnId: ReturnId` — The return identifier
- `version: int` — Current version for optimistic locking

**Returns:** `Return`

**Throws:** `FulfillmenttoolsException` if the request fails or a version conflict occurs

### startAsync(ReturnId, int)

Start a return process asynchronously.

**Parameters:**
- `returnId: ReturnId` — The return identifier
- `version: int` — Current version for optimistic locking

**Returns:** `CompletableFuture<Return>`

### finish(ReturnId, int)

Finish a return process.

**Parameters:**
- `returnId: ReturnId` — The return identifier
- `version: int` — Current version for optimistic locking

**Returns:** `Return`

**Throws:** `FulfillmenttoolsException` if the request fails or a version conflict occurs

### finishAsync(ReturnId, int)

Finish a return process asynchronously.

**Parameters:**
- `returnId: ReturnId` — The return identifier
- `version: int` — Current version for optimistic locking

**Returns:** `CompletableFuture<Return>`

### restart(ReturnId, int)

Restart a return process.

**Parameters:**
- `returnId: ReturnId` — The return identifier
- `version: int` — Current version for optimistic locking

**Returns:** `Return`

**Throws:** `FulfillmenttoolsException` if the request fails or a version conflict occurs

### restartAsync(ReturnId, int)

Restart a return process asynchronously.

**Parameters:**
- `returnId: ReturnId` — The return identifier
- `version: int` — Current version for optimistic locking

**Returns:** `CompletableFuture<Return>`
