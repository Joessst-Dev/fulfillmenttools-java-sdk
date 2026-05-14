# Pack Jobs Client

The Pack Jobs client provides access to packing operations in the fulfillmenttools platform. A pack job represents the task of consolidating picked articles into parcels for shipment or collection.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.PackJobId;
import de.joesst.dev.fulfillmenttools.packjobs.PackJob;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

// Get a pack job by ID
try {
    PackJob job = client.packing().get(PackJobId.builder().value("packjob-001").build());
    System.out.println("Status: " + job.status());
    System.out.println("Facility: " + job.facilityRef().value());
} catch (NotFoundException e) {
    System.out.println("Pack job not found");
} catch (FulfillmenttoolsException e) {
    System.out.println("Request failed: " + e.getMessage());
}
```

## Listing Pack Jobs

List pack jobs with pagination:

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.packjobs.PackJobListRequest;

Page<PackJob> page = client.packing().list(
    PackJobListRequest.builder()
        .size(50)
        .build()
);

for (PackJob job : page.items()) {
    System.out.println(job.id().value() + " — " + job.status());
}
```

Iterate through all pages automatically:

```java
Iterable<PackJob> allJobs = client.packing().listAll(
    PackJobListRequest.builder()
        .size(100)
        .build()
);

for (PackJob job : allJobs) {
    System.out.println(job.id().value());
}
```

Manual pagination using `nextCursor()`:

```java
Page<PackJob> page = client.packing().list(PackJobListRequest.builder().size(20).build());

while (page.hasMore()) {
    page = client.packing().list(
        PackJobListRequest.builder()
            .size(20)
            .startAfterId(page.nextCursor())
            .build()
    );
    for (PackJob job : page.items()) {
        System.out.println(job.id().value());
    }
}
```

## Filtering Pack Jobs

`PackJobListRequest` supports many optional filters:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import java.util.List;

// Filter by facility and status
Page<PackJob> openJobs = client.packing().list(
    PackJobListRequest.builder()
        .facilityRef(FacilityId.builder().value("fac-001").build())
        .status(List.of("OPEN", "IN_PROGRESS"))
        .size(50)
        .build()
);

// Filter by delivery channel and sort order
Page<PackJob> shippingJobs = client.packing().list(
    PackJobListRequest.builder()
        .channel("SHIPPING")
        .orderBy("created,desc")
        .size(50)
        .build()
);

// Text search and filter by associated pick job
import de.joesst.dev.fulfillmenttools.id.PickJobId;

Page<PackJob> results = client.packing().list(
    PackJobListRequest.builder()
        .pickJobRef(PickJobId.builder().value("pjob-001").build())
        .size(20)
        .build()
);
```

## Updating a Pack Job

```java
import de.joesst.dev.fulfillmenttools.packjobs.UpdatePackJobRequest;

PackJob job = client.packing().get(PackJobId.builder().value("packjob-001").build());

PackJob updated = client.packing().update(
    PackJobId.builder().value("packjob-001").build(),
    UpdatePackJobRequest.builder()
        .version(job.version())
        .status("IN_PROGRESS")
        .build()
);
System.out.println("Updated status: " + updated.status());
```

## Async Usage

All methods have async variants returning `CompletableFuture`:

```java
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

CompletableFuture<PackJob> future = client.packing().getAsync(
    PackJobId.builder().value("packjob-001").build()
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

### get(PackJobId)

Get a pack job by ID.

**Parameters:**
- `packJobId: PackJobId` — The pack job identifier

**Returns:** `PackJob`

**Throws:** `NotFoundException` (404), `FulfillmenttoolsException` if the request fails

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

**Throws:** `FulfillmenttoolsException` if the request fails

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

Update a pack job. The `version` field in the request is required for optimistic locking.

**Parameters:**
- `packJobId: PackJobId` — The pack job identifier
- `request: UpdatePackJobRequest` — Update request with new values and current version

**Returns:** `PackJob`

**Throws:** `FulfillmenttoolsException` if the request fails or a version conflict occurs

### updateAsync(PackJobId, UpdatePackJobRequest)

Update a pack job asynchronously.

**Parameters:**
- `packJobId: PackJobId` — The pack job identifier
- `request: UpdatePackJobRequest` — Update request

**Returns:** `CompletableFuture<PackJob>`
