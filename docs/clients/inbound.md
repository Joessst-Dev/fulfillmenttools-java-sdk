# Inbound Client (Stow Jobs)

The Inbound client manages inbound stock operations and stow jobs. Handle receiving and placing stock into storage locations.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.StowJobId;

// Get a stow job
StowJob job = client.inbound().get(new StowJobId("stow-001"));
System.out.println("Status: " + job.getStatus());
```

## Listing Stow Jobs

```java
Page<StowJob> page = client.inbound().list(
    StowJobListRequest.builder()
        .size(50)
        .build()
);
```

Iterate through all stow jobs:

```java
Iterable<StowJob> allJobs = client.inbound().listAll(
    StowJobListRequest.builder()
        .size(100)
        .build()
);
```

## Managing Stow Jobs

Create a stow job:

```java
StowJob job = client.inbound().create(
    CreateStowJobRequest.builder()
        .facilityId(new FacilityId("fac-001"))
        .build()
);
```

Update a stow job:

```java
StowJob updated = client.inbound().update(
    new StowJobId("stow-001"),
    UpdateStowJobRequest.builder()
        .status("ACTIVE")
        .build()
);
```

Start a stow job:

```java
StowJob started = client.inbound().start(
    new StowJobId("stow-001"),
    1  // version for optimistic locking
);
```

Close a stow job:

```java
StowJob closed = client.inbound().close(
    new StowJobId("stow-001"),
    2  // current version
);
```

## API Reference

### get(StowJobId)

Get a stow job by ID.

**Parameters:**
- `stowJobId: StowJobId` — The stow job identifier

**Returns:** `StowJob`

### getAsync(StowJobId)

Get a stow job asynchronously.

**Parameters:**
- `stowJobId: StowJobId` — The stow job identifier

**Returns:** `CompletableFuture<StowJob>`

### list(StowJobListRequest)

List stow jobs with pagination.

**Parameters:**
- `request: StowJobListRequest` — List request with filters and pagination

**Returns:** `Page<StowJob>`

### listAsync(StowJobListRequest)

List stow jobs asynchronously.

**Parameters:**
- `request: StowJobListRequest` — List request with filters and pagination

**Returns:** `CompletableFuture<Page<StowJob>>`

### listAll(StowJobListRequest)

List all stow jobs, automatically iterating through pages.

**Parameters:**
- `request: StowJobListRequest` — List request with filters

**Returns:** `Iterable<StowJob>`

### create(CreateStowJobRequest)

Create a new stow job.

**Parameters:**
- `request: CreateStowJobRequest` — Creation request

**Returns:** `StowJob`

**Throws:** `FulfillmenttoolsException` if the request fails

### createAsync(CreateStowJobRequest)

Create a new stow job asynchronously.

**Parameters:**
- `request: CreateStowJobRequest` — Creation request

**Returns:** `CompletableFuture<StowJob>`

### update(StowJobId, UpdateStowJobRequest)

Update an existing stow job.

**Parameters:**
- `stowJobId: StowJobId` — The stow job ID to update
- `request: UpdateStowJobRequest` — Update request

**Returns:** `StowJob`

### updateAsync(StowJobId, UpdateStowJobRequest)

Update a stow job asynchronously.

**Parameters:**
- `stowJobId: StowJobId` — The stow job ID to update
- `request: UpdateStowJobRequest` — Update request

**Returns:** `CompletableFuture<StowJob>`

### start(StowJobId, int)

Start a stow job.

**Parameters:**
- `stowJobId: StowJobId` — The stow job ID
- `version: int` — Current optimistic lock version

**Returns:** `StowJob`

### startAsync(StowJobId, int)

Start a stow job asynchronously.

**Parameters:**
- `stowJobId: StowJobId` — The stow job ID
- `version: int` — Current optimistic lock version

**Returns:** `CompletableFuture<StowJob>`

### pause(StowJobId, int)

Pause a stow job.

**Parameters:**
- `stowJobId: StowJobId` — The stow job ID
- `version: int` — Current optimistic lock version

**Returns:** `StowJob`

### pauseAsync(StowJobId, int)

Pause a stow job asynchronously.

**Returns:** `CompletableFuture<StowJob>`

### cancel(StowJobId, int)

Cancel a stow job.

**Parameters:**
- `stowJobId: StowJobId` — The stow job ID
- `version: int` — Current optimistic lock version

**Returns:** `StowJob`

### cancelAsync(StowJobId, int)

Cancel a stow job asynchronously.

**Returns:** `CompletableFuture<StowJob>`

### reopen(StowJobId, int)

Reopen a stow job.

**Parameters:**
- `stowJobId: StowJobId` — The stow job ID
- `version: int` — Current optimistic lock version

**Returns:** `StowJob`

### reopenAsync(StowJobId, int)

Reopen a stow job asynchronously.

**Returns:** `CompletableFuture<StowJob>`

### close(StowJobId, int)

Close a stow job.

**Parameters:**
- `stowJobId: StowJobId` — The stow job ID
- `version: int` — Current optimistic lock version

**Returns:** `StowJob`

### closeAsync(StowJobId, int)

Close a stow job asynchronously.

**Returns:** `CompletableFuture<StowJob>`
