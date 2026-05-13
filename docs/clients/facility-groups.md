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

## Creating and Managing Facility Groups

```java
FacilityGroup group = client.facilityGroups().create(
    CreateFacilityGroupRequest.builder()
        .name("Western Region")
        .build()
);

// Update a facility group
FacilityGroup updated = client.facilityGroups().update(
    new FacilityGroupId("fgrp-001"),
    UpdateFacilityGroupRequest.builder()
        .name("Western Region - Updated")
        .build()
);

// Add facilities to a group
FacilityGroup withFacilities = client.facilityGroups().addFacilities(
    new FacilityGroupId("fgrp-001"),
    Arrays.asList(
        new FacilityId("fac-001"),
        new FacilityId("fac-002")
    ),
    1  // current version
);

// Remove facilities from a group
FacilityGroup reduced = client.facilityGroups().removeFacilities(
    new FacilityGroupId("fgrp-001"),
    Arrays.asList(new FacilityId("fac-002")),
    2  // current version
);

// Delete a facility group
client.facilityGroups().delete(new FacilityGroupId("fgrp-001"));
```

## Searching Facility Groups

```java
Page<FacilityGroup> results = client.facilityGroups().search(
    FacilityGroupSearchRequest.builder()
        .name("Western")
        .build()
);
```

## API Reference

### get(FacilityGroupId)

Get a facility group by ID.

**Parameters:**
- `facilityGroupId: FacilityGroupId` — The facility group ID

**Returns:** `FacilityGroup`

**Throws:** `FulfillmenttoolsException` if not found

### getAsync(FacilityGroupId)

Get a facility group asynchronously.

**Parameters:**
- `facilityGroupId: FacilityGroupId` — The facility group ID

**Returns:** `CompletableFuture<FacilityGroup>`

### list(FacilityGroupListRequest)

List facility groups with pagination.

**Parameters:**
- `request: FacilityGroupListRequest` — List request with pagination

**Returns:** `Page<FacilityGroup>`

**Throws:** `FulfillmenttoolsException` if the request fails

### listAsync(FacilityGroupListRequest)

List facility groups asynchronously.

**Parameters:**
- `request: FacilityGroupListRequest` — List request with pagination

**Returns:** `CompletableFuture<Page<FacilityGroup>>`

### listAll(FacilityGroupListRequest)

List all facility groups, automatically iterating through pages.

**Parameters:**
- `request: FacilityGroupListRequest` — List request

**Returns:** `Iterable<FacilityGroup>`

### create(CreateFacilityGroupRequest)

Create a new facility group.

**Parameters:**
- `request: CreateFacilityGroupRequest` — Creation request

**Returns:** `FacilityGroup`

**Throws:** `FulfillmenttoolsException` if the request fails

### createAsync(CreateFacilityGroupRequest)

Create a facility group asynchronously.

**Parameters:**
- `request: CreateFacilityGroupRequest` — Creation request

**Returns:** `CompletableFuture<FacilityGroup>`

### update(FacilityGroupId, UpdateFacilityGroupRequest)

Update an existing facility group.

**Parameters:**
- `facilityGroupId: FacilityGroupId` — The facility group ID
- `request: UpdateFacilityGroupRequest` — Update request

**Returns:** `FacilityGroup`

**Throws:** `FulfillmenttoolsException` if the request fails

### updateAsync(FacilityGroupId, UpdateFacilityGroupRequest)

Update a facility group asynchronously.

**Parameters:**
- `facilityGroupId: FacilityGroupId` — The facility group ID
- `request: UpdateFacilityGroupRequest` — Update request

**Returns:** `CompletableFuture<FacilityGroup>`

### delete(FacilityGroupId)

Delete a facility group.

**Parameters:**
- `facilityGroupId: FacilityGroupId` — The facility group ID

**Throws:** `FulfillmenttoolsException` if the request fails

### deleteAsync(FacilityGroupId)

Delete a facility group asynchronously.

**Parameters:**
- `facilityGroupId: FacilityGroupId` — The facility group ID

**Returns:** `CompletableFuture<Void>`

### addFacilities(FacilityGroupId, List&lt;FacilityId&gt;, Integer)

Add facilities to a facility group.

**Parameters:**
- `facilityGroupId: FacilityGroupId` — The facility group ID
- `facilityIds: List<FacilityId>` — Facility IDs to add
- `version: Integer` — Current optimistic lock version

**Returns:** `FacilityGroup`

**Throws:** `FulfillmenttoolsException` if the request fails

### addFacilitiesAsync(FacilityGroupId, List&lt;FacilityId&gt;, Integer)

Add facilities to a group asynchronously.

**Returns:** `CompletableFuture<FacilityGroup>`

### removeFacilities(FacilityGroupId, List&lt;FacilityId&gt;, Integer)

Remove facilities from a facility group.

**Parameters:**
- `facilityGroupId: FacilityGroupId` — The facility group ID
- `facilityIds: List<FacilityId>` — Facility IDs to remove
- `version: Integer` — Current optimistic lock version

**Returns:** `FacilityGroup`

**Throws:** `FulfillmenttoolsException` if the request fails

### removeFacilitiesAsync(FacilityGroupId, List&lt;FacilityId&gt;, Integer)

Remove facilities from a group asynchronously.

**Returns:** `CompletableFuture<FacilityGroup>`

### search(FacilityGroupSearchRequest)

Search facility groups by criteria, returning one page of results.

**Parameters:**
- `request: FacilityGroupSearchRequest` — Search request with query and pagination

**Returns:** `Page<FacilityGroup>`

**Throws:** `FulfillmenttoolsException` if the request fails

### searchAsync(FacilityGroupSearchRequest)

Search facility groups asynchronously.

**Parameters:**
- `request: FacilityGroupSearchRequest` — Search request with query and pagination

**Returns:** `CompletableFuture<Page<FacilityGroup>>`

### searchAll(FacilityGroupSearchRequest)

Search all facility groups, automatically iterating through pages.

**Parameters:**
- `request: FacilityGroupSearchRequest` — Search request with query

**Returns:** `Iterable<FacilityGroup>`
