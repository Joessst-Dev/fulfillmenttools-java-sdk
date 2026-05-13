# Facility Groups Client

The Facility Groups client manages facility groupings. Organize facilities into logical groups for operational management.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.FacilityGroupId;

// Get a facility group
FacilityGroup group = client.facilityGroups().get(new FacilityGroupId("fgrp-001"));
System.out.println("Name: " + group.getName());
```

## Listing Facility Groups

```java
Page<FacilityGroup> page = client.facilityGroups().list(
    FacilityGroupListRequest.builder()
        .size(50)
        .build()
);
```

## API Reference

### get(FacilityGroupId)

Get a facility group by ID.

**Returns:** `FacilityGroup`

### list(FacilityGroupListRequest)

List facility groups with pagination.

**Returns:** `Page<FacilityGroup>`

### listAll(FacilityGroupListRequest)

List all facility groups.

**Returns:** `Iterable<FacilityGroup>`
