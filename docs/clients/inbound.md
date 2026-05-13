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
    InboundListRequest.builder()
        .size(50)
        .build()
);
```

Iterate through all stow jobs:

```java
Iterable<StowJob> allJobs = client.inbound().listAll(
    InboundListRequest.builder()
        .size(100)
        .build()
);
```

## API Reference

### get(StowJobId)

Get a stow job by ID.

**Returns:** `StowJob`

### list(InboundListRequest)

List stow jobs with pagination.

**Returns:** `Page<StowJob>`

### listAll(InboundListRequest)

List all stow jobs.

**Returns:** `Iterable<StowJob>`
