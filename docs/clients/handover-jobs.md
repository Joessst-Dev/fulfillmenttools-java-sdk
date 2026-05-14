# Handover Jobs Client

The Handover Jobs client manages handover operations in the fulfillmenttools platform. A handover job represents the task of handing over picked articles to a customer or shipping carrier.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.HandoverJobId;
import de.joesst.dev.fulfillmenttools.handoverjobs.HandoverJob;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

// Get a handover job by ID
try {
    HandoverJob job = client.handoverJobs().get(HandoverJobId.builder().value("hojob-001").build());
    System.out.println("Status: " + job.status());
    System.out.println("Channel: " + job.channel());
} catch (NotFoundException e) {
    System.out.println("Handover job not found");
} catch (FulfillmenttoolsException e) {
    System.out.println("Request failed: " + e.getMessage());
}
```

## Listing Handover Jobs

List handover jobs with pagination:

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.handoverjobs.HandoverJobListRequest;

Page<HandoverJob> page = client.handoverJobs().list(
    HandoverJobListRequest.builder()
        .size(50)
        .build()
);

for (HandoverJob job : page.items()) {
    System.out.println(job.id().value() + " — " + job.status());
}
```

Iterate through all pages automatically:

```java
Iterable<HandoverJob> allJobs = client.handoverJobs().listAll(
    HandoverJobListRequest.builder()
        .size(100)
        .build()
);

for (HandoverJob job : allJobs) {
    System.out.println(job.id().value());
}
```

Manual pagination using `nextCursor()`:

```java
Page<HandoverJob> page = client.handoverJobs().list(HandoverJobListRequest.builder().size(20).build());

while (page.hasMore()) {
    page = client.handoverJobs().list(
        HandoverJobListRequest.builder()
            .size(20)
            .startAfterId(page.nextCursor())
            .build()
    );
    for (HandoverJob job : page.items()) {
        System.out.println(job.id().value());
    }
}
```

## Filtering Handover Jobs

`HandoverJobListRequest` supports many optional filters:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import java.util.List;

// Filter by facility and status
Page<HandoverJob> openJobs = client.handoverJobs().list(
    HandoverJobListRequest.builder()
        .facilityRef(FacilityId.builder().value("fac-001").build())
        .status(List.of("OPEN", "IN_PROGRESS"))
        .size(50)
        .build()
);

// Filter by delivery channel and shipment reference
Page<HandoverJob> collectJobs = client.handoverJobs().list(
    HandoverJobListRequest.builder()
        .channel("COLLECT")
        .shipmentRef("SHIP-12345")
        .size(50)
        .build()
);

// Text search across order metadata
Page<HandoverJob> searchResults = client.handoverJobs().list(
    HandoverJobListRequest.builder()
        .searchTerm("Smith")
        .size(20)
        .build()
);
```

## Updating a Handover Job

```java
import de.joesst.dev.fulfillmenttools.handoverjobs.UpdateHandoverJobRequest;

HandoverJob job = client.handoverJobs().get(HandoverJobId.builder().value("hojob-001").build());

HandoverJob updated = client.handoverJobs().update(
    HandoverJobId.builder().value("hojob-001").build(),
    UpdateHandoverJobRequest.builder()
        .version(job.version())
        .status("IN_PROGRESS")
        .build()
);
System.out.println("Updated status: " + updated.status());
```

## Cancelling a Handover Job

Cancelling requires the current `version` for optimistic locking and a reason string:

```java
HandoverJob job = client.handoverJobs().get(HandoverJobId.builder().value("hojob-001").build());

HandoverJob cancelled = client.handoverJobs().cancel(
    job.id(),
    job.version(),
    "Customer cancelled order"
);
System.out.println("Cancel reason: " + cancelled.cancelReason());
```

## Async Usage

All methods have async variants returning `CompletableFuture`:

```java
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

CompletableFuture<HandoverJob> future = client.handoverJobs().getAsync(
    HandoverJobId.builder().value("hojob-001").build()
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

### get(HandoverJobId)

Get a handover job by ID.

**Parameters:**
- `handoverJobId: HandoverJobId` — The handover job identifier

**Returns:** `HandoverJob`

**Throws:** `NotFoundException` (404), `FulfillmenttoolsException` if the request fails

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

**Throws:** `FulfillmenttoolsException` if the request fails

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

Update a handover job. The `version` field in the request is required for optimistic locking.

**Parameters:**
- `handoverJobId: HandoverJobId` — The handover job identifier
- `request: UpdateHandoverJobRequest` — Update request with new values and current version

**Returns:** `HandoverJob`

**Throws:** `FulfillmenttoolsException` if the request fails or a version conflict occurs

### updateAsync(HandoverJobId, UpdateHandoverJobRequest)

Update a handover job asynchronously.

**Parameters:**
- `handoverJobId: HandoverJobId` — The handover job identifier
- `request: UpdateHandoverJobRequest` — Update request

**Returns:** `CompletableFuture<HandoverJob>`

### cancel(HandoverJobId, int, String)

Cancel a handover job with a reason.

**Parameters:**
- `handoverJobId: HandoverJobId` — The handover job identifier
- `version: int` — Current version for optimistic locking
- `cancelReason: String` — The reason for cancellation

**Returns:** `HandoverJob`

**Throws:** `FulfillmenttoolsException` if the request fails or a version conflict occurs

### cancelAsync(HandoverJobId, int, String)

Cancel a handover job asynchronously.

**Parameters:**
- `handoverJobId: HandoverJobId` — The handover job identifier
- `version: int` — Current version for optimistic locking
- `cancelReason: String` — The reason for cancellation

**Returns:** `CompletableFuture<HandoverJob>`
