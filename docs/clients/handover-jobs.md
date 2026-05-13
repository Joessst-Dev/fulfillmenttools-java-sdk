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

**Returns:** `HandoverJob`

### list(HandoverJobListRequest)

List handover jobs with pagination.

**Returns:** `Page<HandoverJob>`

### listAll(HandoverJobListRequest)

List all handover jobs.

**Returns:** `Iterable<HandoverJob>`
