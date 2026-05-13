# Pack Jobs Client

The Pack Jobs client provides access to packing operations. Manage and monitor pack jobs throughout your fulfillment workflow.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.PackJobId;

// Get a pack job
PackJob job = client.packing().get(new PackJobId("packjob-001"));
System.out.println("Status: " + job.getStatus());
```

## Listing Pack Jobs

```java
Page<PackJob> page = client.packing().list(
    PackJobListRequest.builder()
        .size(50)
        .build()
);
```

## API Reference

### get(PackJobId)

Get a pack job by ID.

**Parameters:**
- `packJobId: PackJobId` — The pack job identifier

**Returns:** `PackJob`

**Throws:** `FulfillmenttoolsException` on request failure

### getAsync(PackJobId)

Get a pack job by ID asynchronously.

**Parameters:**
- `packJobId: PackJobId` — The pack job identifier

**Returns:** `CompletableFuture<PackJob>`

### list(PackJobListRequest)

List pack jobs with pagination.

**Parameters:**
- `request: PackJobListRequest` — List request with filter and pagination parameters

**Returns:** `Page<PackJob>`

**Throws:** `FulfillmenttoolsException` on request failure

### listAsync(PackJobListRequest)

List pack jobs asynchronously.

**Parameters:**
- `request: PackJobListRequest` — List request

**Returns:** `CompletableFuture<Page<PackJob>>`

### listAll(PackJobListRequest)

List all pack jobs, automatically iterating through pages.

**Parameters:**
- `request: PackJobListRequest` — List request

**Returns:** `Iterable<PackJob>`

### update(PackJobId, UpdatePackJobRequest)

Update a pack job with new values.

**Parameters:**
- `packJobId: PackJobId` — The pack job identifier
- `request: UpdatePackJobRequest` — Update request with new values and version for optimistic locking

**Returns:** `PackJob`

**Throws:** `FulfillmenttoolsException` on request failure or version conflict

### updateAsync(PackJobId, UpdatePackJobRequest)

Update a pack job asynchronously.

**Parameters:**
- `packJobId: PackJobId` — The pack job identifier
- `request: UpdatePackJobRequest` — Update request

**Returns:** `CompletableFuture<PackJob>`
