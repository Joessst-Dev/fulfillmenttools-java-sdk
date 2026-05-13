# Handover Jobs Client

The Handover Jobs client manages handover operations between facilities or to carriers in your fulfillment network.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.HandoverJobId;

// Get a handover job
HandoverJob job = client.handoverJobs().get(new HandoverJobId("hojob-001"));
System.out.println("Status: " + job.getStatus());
```

## Listing Handover Jobs

```java
Page<HandoverJob> page = client.handoverJobs().list(
    HandoverJobListRequest.builder()
        .size(50)
        .build()
);
```

## API Reference

### get(HandoverJobId)

Get a handover job by ID.

**Parameters:**
- `handoverJobId: HandoverJobId` — The handover job identifier

**Returns:** `HandoverJob`

**Throws:** `FulfillmenttoolsException` on request failure

### getAsync(HandoverJobId)

Get a handover job by ID asynchronously.

**Parameters:**
- `handoverJobId: HandoverJobId` — The handover job identifier

**Returns:** `CompletableFuture<HandoverJob>`

### list(HandoverJobListRequest)

List handover jobs with pagination.

**Parameters:**
- `request: HandoverJobListRequest` — List request with filter and pagination parameters

**Returns:** `Page<HandoverJob>`

**Throws:** `FulfillmenttoolsException` on request failure

### listAsync(HandoverJobListRequest)

List handover jobs asynchronously.

**Parameters:**
- `request: HandoverJobListRequest` — List request

**Returns:** `CompletableFuture<Page<HandoverJob>>`

### listAll(HandoverJobListRequest)

List all handover jobs, automatically iterating through pages.

**Parameters:**
- `request: HandoverJobListRequest` — List request

**Returns:** `Iterable<HandoverJob>`

### update(HandoverJobId, UpdateHandoverJobRequest)

Update a handover job with new values.

**Parameters:**
- `handoverJobId: HandoverJobId` — The handover job identifier
- `request: UpdateHandoverJobRequest` — Update request with new values and version for optimistic locking

**Returns:** `HandoverJob`

**Throws:** `FulfillmenttoolsException` on request failure or version conflict

### updateAsync(HandoverJobId, UpdateHandoverJobRequest)

Update a handover job asynchronously.

**Parameters:**
- `handoverJobId: HandoverJobId` — The handover job identifier
- `request: UpdateHandoverJobRequest` — Update request

**Returns:** `CompletableFuture<HandoverJob>`

### cancel(HandoverJobId, int, String)

Cancel a handover job with a cancellation reason.

**Parameters:**
- `handoverJobId: HandoverJobId` — The handover job identifier
- `version: int` — Current version of the handover job (for optimistic locking)
- `cancelReason: String` — The reason for cancellation

**Returns:** `HandoverJob`

**Throws:** `FulfillmenttoolsException` on request failure or version conflict

### cancelAsync(HandoverJobId, int, String)

Cancel a handover job asynchronously with a cancellation reason.

**Parameters:**
- `handoverJobId: HandoverJobId` — The handover job identifier
- `version: int` — Current version of the handover job
- `cancelReason: String` — The reason for cancellation

**Returns:** `CompletableFuture<HandoverJob>`
