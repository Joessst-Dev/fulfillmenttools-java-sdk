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

**Returns:** `PickJob`

### list(PickJobListRequest)

List pick jobs with pagination.

**Returns:** `Page<PickJob>`

### listAll(PickJobListRequest)

List all pick jobs.

**Returns:** `Iterable<PickJob>`
