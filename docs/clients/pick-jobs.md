# Pick Jobs Client

The Pick Jobs client provides access to picking operations in the fulfillmenttools platform. Manage and monitor pick jobs across your facilities.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.PickJobId;

// Get a pick job
PickJob job = client.pickJobs().get(new PickJobId("pjob-001"));
System.out.println("Status: " + job.getStatus());
```

## Listing Pick Jobs

```java
Page<PickJob> page = client.pickJobs().list(
    PickJobListRequest.builder()
        .size(50)
        .build()
);
```

## API Reference

### get(PickJobId)

Get a pick job by ID.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier

**Returns:** `PickJob`

**Throws:** `FulfillmenttoolsException` on request failure

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

**Throws:** `FulfillmenttoolsException` on request failure

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

Update a pick job with new values.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier
- `request: UpdatePickJobRequest` — Update request with new values and version for optimistic locking

**Returns:** `PickJob`

**Throws:** `FulfillmenttoolsException` on request failure or version conflict

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
- `version: int` — Current version of the pick job (for optimistic locking)

**Returns:** `PickJob`

**Throws:** `FulfillmenttoolsException` on request failure or version conflict

### abortAsync(PickJobId, int)

Transition a pick job to the aborted state asynchronously.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier
- `version: int` — Current version of the pick job

**Returns:** `CompletableFuture<PickJob>`

### restart(PickJobId, int)

Transition a pick job to the restarted state.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier
- `version: int` — Current version of the pick job

**Returns:** `PickJob`

**Throws:** `FulfillmenttoolsException` on request failure or version conflict

### restartAsync(PickJobId, int)

Transition a pick job to the restarted state asynchronously.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier
- `version: int` — Current version of the pick job

**Returns:** `CompletableFuture<PickJob>`

### reset(PickJobId, int)

Transition a pick job to the reset state, allowing the job to be restarted from the beginning.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier
- `version: int` — Current version of the pick job

**Returns:** `PickJob`

**Throws:** `FulfillmenttoolsException` on request failure or version conflict

### resetAsync(PickJobId, int)

Transition a pick job to the reset state asynchronously.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier
- `version: int` — Current version of the pick job

**Returns:** `CompletableFuture<PickJob>`

### obsolete(PickJobId, int)

Transition a pick job to the obsolete state.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier
- `version: int` — Current version of the pick job

**Returns:** `PickJob`

**Throws:** `FulfillmenttoolsException` on request failure or version conflict

### obsoleteAsync(PickJobId, int)

Transition a pick job to the obsolete state asynchronously.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier
- `version: int` — Current version of the pick job

**Returns:** `CompletableFuture<PickJob>`

### start(PickJobId, int)

Transition a pick job to the started state.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier
- `version: int` — Current version of the pick job

**Returns:** `PickJob`

**Throws:** `FulfillmenttoolsException` on request failure or version conflict

### startAsync(PickJobId, int)

Transition a pick job to the started state asynchronously.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier
- `version: int` — Current version of the pick job

**Returns:** `CompletableFuture<PickJob>`

### pause(PickJobId, int)

Transition a pick job to the paused state.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier
- `version: int` — Current version of the pick job

**Returns:** `PickJob`

**Throws:** `FulfillmenttoolsException` on request failure or version conflict

### pauseAsync(PickJobId, int)

Transition a pick job to the paused state asynchronously.

**Parameters:**
- `pickJobId: PickJobId` — The pick job identifier
- `version: int` — Current version of the pick job

**Returns:** `CompletableFuture<PickJob>`
