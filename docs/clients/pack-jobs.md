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

**Returns:** `PackJob`

### list(PackJobListRequest)

List pack jobs with pagination.

**Returns:** `Page<PackJob>`

### listAll(PackJobListRequest)

List all pack jobs.

**Returns:** `Iterable<PackJob>`
