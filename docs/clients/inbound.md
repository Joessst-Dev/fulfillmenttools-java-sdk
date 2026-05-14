# Inbound Client (Stow Jobs)

The Inbound client manages stow jobs — the operations that receive inbound stock and place it into storage locations. Stow jobs move through a lifecycle: `OPEN` → `IN_PROGRESS` → `DONE` (or `CANCELLED`/`PAUSED`).

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.StowJobId;
import de.joesst.dev.fulfillmenttools.inbound.StowJob;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

try {
    StowJob job = client.inbound().get(
        StowJobId.builder().value("stow-001").build()
    );
    System.out.println("Status: " + job.status());
    System.out.println("Facility: " + job.facilityRef().value());
    System.out.println("Short ID: " + job.shortId());
} catch (NotFoundException e) {
    System.out.println("Stow job not found");
} catch (FulfillmenttoolsException e) {
    System.out.println("Request failed: " + e.getMessage());
}
```

## Listing Stow Jobs

List stow jobs with optional filters:

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.inbound.StowJob;
import de.joesst.dev.fulfillmenttools.inbound.StowJobListRequest;
import java.util.List;

Page<StowJob> page = client.inbound().list(
    StowJobListRequest.builder()
        .size(50)
        .status(List.of("OPEN"))
        .build()
);

for (StowJob job : page.items()) {
    System.out.println(job.id().value() + " — " + job.status());
}
```

Iterate through all stow jobs automatically:

```java
import de.joesst.dev.fulfillmenttools.inbound.StowJob;
import de.joesst.dev.fulfillmenttools.inbound.StowJobListRequest;
import java.util.List;

Iterable<StowJob> allJobs = client.inbound().listAll(
    StowJobListRequest.builder()
        .size(100)
        .facilityRef(List.of("fac-001"))
        .build()
);

for (StowJob job : allJobs) {
    System.out.println(job.id().value() + " — " + job.status());
}
```

Manual pagination using `nextCursor()`:

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.inbound.StowJob;
import de.joesst.dev.fulfillmenttools.inbound.StowJobListRequest;

Page<StowJob> page = client.inbound().list(
    StowJobListRequest.builder().size(20).build()
);

while (page.hasMore()) {
    page = client.inbound().list(
        StowJobListRequest.builder()
            .size(20)
            .startAfterId(page.nextCursor())
            .build()
    );
    for (StowJob job : page.items()) {
        System.out.println(job.id().value());
    }
}
```

## Creating a Stow Job

`facilityRef`, `status`, and `stowLineItems` are required:

```java
import de.joesst.dev.fulfillmenttools.inbound.StowJob;
import de.joesst.dev.fulfillmenttools.inbound.CreateStowJobRequest;
import de.joesst.dev.fulfillmenttools.inbound.StowLineItemForCreation;
import de.joesst.dev.fulfillmenttools.inbound.StowLineItemArticle;
import de.joesst.dev.fulfillmenttools.inbound.StowLineItemTakeFrom;
import de.joesst.dev.fulfillmenttools.inbound.StowLineItemStowToForCreation;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.TenantArticleId;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;
import java.util.List;

try {
    StowJob job = client.inbound().create(
        CreateStowJobRequest.builder()
            .facilityRef(FacilityId.builder().value("fac-001").build())
            .status("OPEN")
            .stowLineItems(List.of(
                StowLineItemForCreation.builder()
                    .article(
                        StowLineItemArticle.builder()
                            .tenantArticleId(TenantArticleId.builder().value("SKU-12345").build())
                            .title("Blue Sneakers")
                            .build()
                    )
                    .build()
            ))
            .build()
    );
    System.out.println("Created stow job: " + job.id().value());
} catch (FulfillmenttoolsException e) {
    System.out.println("Creation failed: " + e.getMessage());
}
```

## Updating a Stow Job

`version` is required for optimistic locking:

```java
import de.joesst.dev.fulfillmenttools.id.StowJobId;
import de.joesst.dev.fulfillmenttools.inbound.StowJob;
import de.joesst.dev.fulfillmenttools.inbound.UpdateStowJobRequest;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

StowJob job = client.inbound().get(
    StowJobId.builder().value("stow-001").build()
);

StowJob updated = client.inbound().update(
    StowJobId.builder().value("stow-001").build(),
    UpdateStowJobRequest.builder()
        .version(job.version())
        .priority(5)
        .build()
);
System.out.println("Updated priority: " + updated.priority());
```

## Lifecycle Actions

All lifecycle methods take a `StowJobId` and the current `version` for optimistic locking.

**Start a stow job:**

```java
import de.joesst.dev.fulfillmenttools.id.StowJobId;
import de.joesst.dev.fulfillmenttools.inbound.StowJob;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

StowJob job = client.inbound().get(
    StowJobId.builder().value("stow-001").build()
);
StowJob started = client.inbound().start(
    StowJobId.builder().value("stow-001").build(),
    job.version()
);
System.out.println("Status: " + started.status());
```

**Pause, cancel, reopen, close:**

```java
import de.joesst.dev.fulfillmenttools.id.StowJobId;
import de.joesst.dev.fulfillmenttools.inbound.StowJob;

// Pause
StowJob paused = client.inbound().pause(
    StowJobId.builder().value("stow-001").build(), 2
);

// Cancel
StowJob cancelled = client.inbound().cancel(
    StowJobId.builder().value("stow-001").build(), 2
);

// Reopen (from CANCELLED back to OPEN)
StowJob reopened = client.inbound().reopen(
    StowJobId.builder().value("stow-001").build(), 3
);

// Close
StowJob closed = client.inbound().close(
    StowJobId.builder().value("stow-001").build(), 2
);
```

## Async Usage

All methods have async variants returning `CompletableFuture`:

```java
import de.joesst.dev.fulfillmenttools.id.StowJobId;
import de.joesst.dev.fulfillmenttools.inbound.StowJob;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

CompletableFuture<StowJob> future = client.inbound().getAsync(
    StowJobId.builder().value("stow-001").build()
);

future.whenComplete((job, ex) -> {
    if (ex != null) {
        Throwable cause = ex instanceof CompletionException && ex.getCause() != null
            ? ex.getCause() : ex;
        System.out.println("Error: " + cause.getMessage());
    } else {
        System.out.println("Status: " + job.status());
        System.out.println("Facility: " + job.facilityRef().value());
    }
});
```

## API Reference

### get(StowJobId)

Get a stow job by ID.

**Parameters:**
- `stowJobId: StowJobId` — The stow job identifier

**Returns:** `StowJob`

**Throws:** `NotFoundException` (404), `FulfillmenttoolsException` if the request fails

### getAsync(StowJobId)

Get a stow job by ID asynchronously.

**Parameters:**
- `stowJobId: StowJobId` — The stow job identifier

**Returns:** `CompletableFuture<StowJob>`

### list(StowJobListRequest)

List stow jobs with optional filtering and pagination. Filters include `status`, `facilityRef`, `tenantArticleId`, `locationRef`, `stockRef`, `shortId`, and `priority`.

**Parameters:**
- `request: StowJobListRequest` — List request with `size`, `startAfterId` cursor, `sort`, and optional filters

**Returns:** `Page<StowJob>`

**Throws:** `FulfillmenttoolsException` if the request fails

### listAsync(StowJobListRequest)

List stow jobs asynchronously.

**Parameters:**
- `request: StowJobListRequest` — List request

**Returns:** `CompletableFuture<Page<StowJob>>`

### listAll(StowJobListRequest)

List all stow jobs, automatically iterating through pages.

**Parameters:**
- `request: StowJobListRequest` — List request

**Returns:** `Iterable<StowJob>`

### create(CreateStowJobRequest)

Create a new stow job. `facilityRef`, `status`, and `stowLineItems` are required.

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

Update an existing stow job. `version` is required for optimistic locking. Updatable fields: `priority`, `targetTime`, `assignedUsers`, `customAttributes`.

**Parameters:**
- `stowJobId: StowJobId` — The stow job identifier
- `request: UpdateStowJobRequest` — Update request with current version and new values

**Returns:** `StowJob`

**Throws:** `FulfillmenttoolsException` if the request fails or a version conflict occurs

### updateAsync(StowJobId, UpdateStowJobRequest)

Update a stow job asynchronously.

**Parameters:**
- `stowJobId: StowJobId` — The stow job identifier
- `request: UpdateStowJobRequest` — Update request

**Returns:** `CompletableFuture<StowJob>`

### start(StowJobId, int)

Start a stow job (transition to `IN_PROGRESS`).

**Parameters:**
- `stowJobId: StowJobId` — The stow job identifier
- `version: int` — Current optimistic lock version

**Returns:** `StowJob`

**Throws:** `FulfillmenttoolsException` if the request fails

### startAsync(StowJobId, int)

Start a stow job asynchronously.

**Returns:** `CompletableFuture<StowJob>`

### pause(StowJobId, int)

Pause a stow job.

**Parameters:**
- `stowJobId: StowJobId` — The stow job identifier
- `version: int` — Current optimistic lock version

**Returns:** `StowJob`

### pauseAsync(StowJobId, int)

Pause a stow job asynchronously.

**Returns:** `CompletableFuture<StowJob>`

### cancel(StowJobId, int)

Cancel a stow job.

**Parameters:**
- `stowJobId: StowJobId` — The stow job identifier
- `version: int` — Current optimistic lock version

**Returns:** `StowJob`

### cancelAsync(StowJobId, int)

Cancel a stow job asynchronously.

**Returns:** `CompletableFuture<StowJob>`

### reopen(StowJobId, int)

Reopen a cancelled stow job (transition back to `OPEN`).

**Parameters:**
- `stowJobId: StowJobId` — The stow job identifier
- `version: int` — Current optimistic lock version

**Returns:** `StowJob`

### reopenAsync(StowJobId, int)

Reopen a stow job asynchronously.

**Returns:** `CompletableFuture<StowJob>`

### close(StowJobId, int)

Close a completed stow job.

**Parameters:**
- `stowJobId: StowJobId` — The stow job identifier
- `version: int` — Current optimistic lock version

**Returns:** `StowJob`

### closeAsync(StowJobId, int)

Close a stow job asynchronously.

**Returns:** `CompletableFuture<StowJob>`
