# Pick Jobs Client

The Pick Jobs client provides access to picking operations in the fulfillmenttools platform. A pick job represents the fulfilment task to pick articles for an order at a facility.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.PickJobId;
import de.joesst.dev.fulfillmenttools.pickjobs.PickJob;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

// Get a pick job by ID
try {
    PickJob job = client.pickJobs().get(PickJobId.builder().value("pjob-001").build());
    System.out.println("Status: " + job.status());
    System.out.println("Facility: " + job.facilityRef().value());
} catch (NotFoundException e) {
    System.out.println("Pick job not found");
} catch (FulfillmenttoolsException e) {
    System.out.println("Request failed: " + e.getMessage());
}
```

## Listing Pick Jobs

List pick jobs with pagination:

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.pickjobs.PickJobListRequest;

Page<PickJob> page = client.pickJobs().list(
    PickJobListRequest.builder()
        .size(50)
        .build()
);

for (PickJob job : page.items()) {
    System.out.println(job.id().value() + " — " + job.status());
}
```

Iterate through all pages automatically:

```java
Iterable<PickJob> allJobs = client.pickJobs().listAll(
    PickJobListRequest.builder()
        .size(100)
        .build()
);

for (PickJob job : allJobs) {
    System.out.println(job.id().value());
}
```

Manual pagination using `nextCursor()`:

```java
Page<PickJob> page = client.pickJobs().list(PickJobListRequest.builder().size(20).build());

while (page.hasMore()) {
    page = client.pickJobs().list(
        PickJobListRequest.builder()
            .size(20)
            .startAfterId(page.nextCursor())
            .build()
    );
    for (PickJob job : page.items()) {
        System.out.println(job.id().value());
    }
}
```

## Filtering Pick Jobs

`PickJobListRequest` supports many optional filters:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import java.util.List;

// Filter by facility and status
Page<PickJob> openJobs = client.pickJobs().list(
    PickJobListRequest.builder()
        .facilityRef(FacilityId.builder().value("fac-001").build())
        .status(List.of("OPEN", "IN_PROGRESS"))
        .size(50)
        .build()
);

// Filter by channel and sort order
Page<PickJob> shippingJobs = client.pickJobs().list(
    PickJobListRequest.builder()
        .channel("SHIPPING")
        .orderBy("created,desc")
        .size(50)
        .build()
);

// Text search across order metadata
Page<PickJob> searchResults = client.pickJobs().list(
    PickJobListRequest.builder()
        .searchTerm("Smith")
        .size(20)
        .build()
);
```

## Updating a Pick Job

```java
import de.joesst.dev.fulfillmenttools.pickjobs.UpdatePickJobRequest;

PickJob job = client.pickJobs().get(PickJobId.builder().value("pjob-001").build());

PickJob updated = client.pickJobs().update(
    PickJobId.builder().value("pjob-001").build(),
    UpdatePickJobRequest.builder()
        .version(job.version())
        .status("IN_PROGRESS")
        .build()
);
System.out.println("Updated status: " + updated.status());
```

## State Transitions

Pick jobs move through a workflow lifecycle. Each transition requires the current `version` for optimistic locking. Transitions are mutually exclusive — always use the version from the most recent state of the job:

```java
PickJob job = client.pickJobs().get(PickJobId.builder().value("pjob-001").build());
PickJobId id = job.id();
int version = job.version();

// Start picking — use the version from the current job state
PickJob started = client.pickJobs().start(id, version);

// Pause an in-progress job — use the version returned by start()
PickJob paused = client.pickJobs().pause(id, started.version());
```

Other available transitions (each called independently with the current version):

```java
// Abort a job
PickJob aborted = client.pickJobs().abort(id, version);

// Reset a job to allow restarting from the beginning
PickJob reset = client.pickJobs().reset(id, version);

// Restart a job
PickJob restarted = client.pickJobs().restart(id, version);

// Mark a job as obsolete
PickJob obsoleted = client.pickJobs().obsolete(id, version);
```

## Async Usage

All methods have async variants returning `CompletableFuture`:

```java
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

CompletableFuture<PickJob> future = client.pickJobs().getAsync(
    PickJobId.builder().value("pjob-001").build()
);

future.whenComplete((job, ex) -> {
    if (ex != null) {
        Throwable cause = ex instanceof CompletionException && ex.getCause() != null
            ? ex.getCause() : ex;
        System.out.println("Error: " + cause.getMessage());
    } else {
        System.out.println("Status: " + job.status());
    }
});
```

## API Reference

### get(PickJobId)

Get a pick job by ID.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier

**Returns:** `PickJob`

**Throws:** `NotFoundException` (404), `FulfillmenttoolsException` if the request fails

### getAsync(PickJobId)

Get a pick job by ID asynchronously.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier

**Returns:** `CompletableFuture<PickJob>`

### list(PickJobListRequest)

List pick jobs with pagination.

**Parameters:**
- `request: PickJobListRequest` — List request with filter and pagination parameters

**Returns:** `Page<PickJob>`

**Throws:** `FulfillmenttoolsException` if the request fails

### listAsync(PickJobListRequest)

List pick jobs asynchronously.

**Parameters:**
- `request: PickJobListRequest` — List request

**Returns:** `CompletableFuture<Page<PickJob>>`

### listAll(PickJobListRequest)

List all pick jobs, automatically iterating through pages.

**Parameters:**
- `request: PickJobListRequest` — List request

**Returns:** `Iterable<PickJob>`

### update(PickJobId, UpdatePickJobRequest)

Update a pick job. The `version` field in the request is required for optimistic locking.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier
- `request: UpdatePickJobRequest` — Update request with new values and current version

**Returns:** `PickJob`

**Throws:** `FulfillmenttoolsException` if the request fails or a version conflict occurs

### updateAsync(PickJobId, UpdatePickJobRequest)

Update a pick job asynchronously.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier
- `request: UpdatePickJobRequest` — Update request

**Returns:** `CompletableFuture<PickJob>`

### abort(PickJobId, int)

Transition a pick job to the aborted state.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier
- `version: int` — Current version for optimistic locking

**Returns:** `PickJob`

**Throws:** `FulfillmenttoolsException` if the request fails or a version conflict occurs

### abortAsync(PickJobId, int)

Transition a pick job to the aborted state asynchronously.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier
- `version: int` — Current version for optimistic locking

**Returns:** `CompletableFuture<PickJob>`

### restart(PickJobId, int)

Transition a pick job to the restarted state.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier
- `version: int` — Current version for optimistic locking

**Returns:** `PickJob`

**Throws:** `FulfillmenttoolsException` if the request fails or a version conflict occurs

### restartAsync(PickJobId, int)

Transition a pick job to the restarted state asynchronously.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier
- `version: int` — Current version for optimistic locking

**Returns:** `CompletableFuture<PickJob>`

### reset(PickJobId, int)

Transition a pick job to the reset state, allowing the job to be restarted from the beginning.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier
- `version: int` — Current version for optimistic locking

**Returns:** `PickJob`

**Throws:** `FulfillmenttoolsException` if the request fails or a version conflict occurs

### resetAsync(PickJobId, int)

Transition a pick job to the reset state asynchronously.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier
- `version: int` — Current version for optimistic locking

**Returns:** `CompletableFuture<PickJob>`

### obsolete(PickJobId, int)

Transition a pick job to the obsolete state.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier
- `version: int` — Current version for optimistic locking

**Returns:** `PickJob`

**Throws:** `FulfillmenttoolsException` if the request fails or a version conflict occurs

### obsoleteAsync(PickJobId, int)

Transition a pick job to the obsolete state asynchronously.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier
- `version: int` — Current version for optimistic locking

**Returns:** `CompletableFuture<PickJob>`

### start(PickJobId, int)

Transition a pick job to the started state.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier
- `version: int` — Current version for optimistic locking

**Returns:** `PickJob`

**Throws:** `FulfillmenttoolsException` if the request fails or a version conflict occurs

### startAsync(PickJobId, int)

Transition a pick job to the started state asynchronously.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier
- `version: int` — Current version for optimistic locking

**Returns:** `CompletableFuture<PickJob>`

### pause(PickJobId, int)

Transition a pick job to the paused state.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier
- `version: int` — Current version for optimistic locking

**Returns:** `PickJob`

**Throws:** `FulfillmenttoolsException` if the request fails or a version conflict occurs

### pauseAsync(PickJobId, int)

Transition a pick job to the paused state asynchronously.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier
- `version: int` — Current version for optimistic locking

**Returns:** `CompletableFuture<PickJob>`
