# Facility Groups Client

The Facility Groups client manages facility groupings — organize facilities into logical groups for routing, reporting, and operational management.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.FacilityGroupId;
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroup;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

try {
    FacilityGroup group = client.facilityGroups().get(
        FacilityGroupId.builder().value("fgrp-001").build()
    );
    System.out.println("Name: " + group.name());
    System.out.println("Tenant ID: " + group.tenantFacilityGroupId());
    System.out.println("Facilities: " + group.facilityRefs().size());
} catch (NotFoundException e) {
    System.out.println("Facility group not found");
} catch (FulfillmenttoolsException e) {
    System.out.println("Request failed: " + e.getMessage());
}
```

## Listing Facility Groups

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroup;
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroupListRequest;

Page<FacilityGroup> page = client.facilityGroups().list(
    FacilityGroupListRequest.builder()
        .size(50)
        .build()
);

for (FacilityGroup group : page.items()) {
    System.out.println(group.id().value() + " — " + group.name());
}
```

Manual pagination using `nextCursor()`:

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroup;
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroupListRequest;

Page<FacilityGroup> page = client.facilityGroups().list(
    FacilityGroupListRequest.builder().size(20).build()
);

while (page.hasMore()) {
    page = client.facilityGroups().list(
        FacilityGroupListRequest.builder()
            .size(20)
            .startAfterId(page.nextCursor())
            .build()
    );
    for (FacilityGroup group : page.items()) {
        System.out.println(group.id().value());
    }
}
```

Iterate through all facility groups automatically:

```java
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroup;
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroupListRequest;

Iterable<FacilityGroup> allGroups = client.facilityGroups().listAll(
    FacilityGroupListRequest.builder()
        .size(100)
        .build()
);

for (FacilityGroup group : allGroups) {
    System.out.println(group.id().value() + " — " + group.name());
}
```

## Creating a Facility Group

`tenantFacilityGroupId`, `facilityRefs`, and `nameLocalized` are required:

```java
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroup;
import de.joesst.dev.fulfillmenttools.facilitygroups.CreateFacilityGroupRequest;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;
import java.util.List;
import java.util.Map;

try {
    FacilityGroup group = client.facilityGroups().create(
        CreateFacilityGroupRequest.builder()
            .tenantFacilityGroupId("western-region")
            .facilityRefs(List.of("fac-001", "fac-002"))
            .nameLocalized(Map.of("en_US", "Western Region"))
            .build()
    );
    System.out.println("Created: " + group.id().value());
} catch (FulfillmenttoolsException e) {
    System.out.println("Creation failed: " + e.getMessage());
}
```

## Updating a Facility Group

`version` is required for optimistic locking:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityGroupId;
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroup;
import de.joesst.dev.fulfillmenttools.facilitygroups.UpdateFacilityGroupRequest;
import java.util.Map;

FacilityGroup group = client.facilityGroups().get(
    FacilityGroupId.builder().value("fgrp-001").build()
);

FacilityGroup updated = client.facilityGroups().update(
    FacilityGroupId.builder().value("fgrp-001").build(),
    UpdateFacilityGroupRequest.builder()
        .version(group.version())
        .nameLocalized(Map.of("en_US", "Western Region - Updated"))
        .build()
);
System.out.println("Updated name: " + updated.name());
```

## Managing Facilities in a Group

Add facilities to a group using their `FacilityId`s and the current group version:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityGroupId;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroup;
import java.util.List;

FacilityGroup group = client.facilityGroups().get(
    FacilityGroupId.builder().value("fgrp-001").build()
);

FacilityGroup withFacilities = client.facilityGroups().addFacilities(
    FacilityGroupId.builder().value("fgrp-001").build(),
    List.of(
        FacilityId.builder().value("fac-003").build(),
        FacilityId.builder().value("fac-004").build()
    ),
    group.version()
);
System.out.println("Facilities: " + withFacilities.facilityRefs().size());
```

Remove facilities from a group:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityGroupId;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroup;
import java.util.List;

FacilityGroup current = client.facilityGroups().get(
    FacilityGroupId.builder().value("fgrp-001").build()
);

FacilityGroup reduced = client.facilityGroups().removeFacilities(
    FacilityGroupId.builder().value("fgrp-001").build(),
    List.of(
        FacilityId.builder().value("fac-004").build()
    ),
    current.version()
);
```

## Deleting a Facility Group

```java
import de.joesst.dev.fulfillmenttools.id.FacilityGroupId;

client.facilityGroups().delete(
    FacilityGroupId.builder().value("fgrp-001").build()
);
```

## Searching Facility Groups

`search` uses a `FacilityGroupSearchQuery` to filter by name, tenant ID, or facility references:

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroup;
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroupSearchRequest;
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroupSearchQuery;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

try {
    Page<FacilityGroup> results = client.facilityGroups().search(
        FacilityGroupSearchRequest.builder()
            .query(
                FacilityGroupSearchQuery.builder()
                    .nameLike("Western%")
                    .build()
            )
            .size(50)
            .build()
    );

    for (FacilityGroup group : results.items()) {
        System.out.println(group.id().value() + " — " + group.name());
    }
} catch (FulfillmenttoolsException e) {
    System.out.println("Search failed: " + e.getMessage());
}
```

Search by facility reference:

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroup;
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroupSearchRequest;
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroupSearchQuery;

Page<FacilityGroup> results = client.facilityGroups().search(
    FacilityGroupSearchRequest.builder()
        .query(
            FacilityGroupSearchQuery.builder()
                .facilityRefsContains("fac-001")
                .build()
        )
        .build()
);
```

Iterate through all matching search results automatically:

```java
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroup;
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroupSearchRequest;
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroupSearchQuery;

Iterable<FacilityGroup> allResults = client.facilityGroups().searchAll(
    FacilityGroupSearchRequest.builder()
        .query(
            FacilityGroupSearchQuery.builder()
                .nameLike("Western%")
                .build()
        )
        .size(100)
        .build()
);

for (FacilityGroup group : allResults) {
    System.out.println(group.id().value());
}
```

## Async Usage

All methods have async variants returning `CompletableFuture`:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityGroupId;
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroup;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

CompletableFuture<FacilityGroup> future = client.facilityGroups().getAsync(
    FacilityGroupId.builder().value("fgrp-001").build()
);

future.whenComplete((group, ex) -> {
    if (ex != null) {
        Throwable cause = ex instanceof CompletionException && ex.getCause() != null
            ? ex.getCause() : ex;
        System.out.println("Error: " + cause.getMessage());
    } else {
        System.out.println("Name: " + group.name());
        System.out.println("Facilities: " + group.facilityRefs().size());
    }
});
```

## API Reference

### get(FacilityGroupId)

Get a facility group by ID.

**Parameters:**
- `facilityGroupId: FacilityGroupId` — The facility group identifier

**Returns:** `FacilityGroup`

**Throws:** `NotFoundException` (404), `FulfillmenttoolsException` if the request fails

### getAsync(FacilityGroupId)

Get a facility group by ID asynchronously.

**Parameters:**
- `facilityGroupId: FacilityGroupId` — The facility group identifier

**Returns:** `CompletableFuture<FacilityGroup>`

### list(FacilityGroupListRequest)

List facility groups with pagination.

**Parameters:**
- `request: FacilityGroupListRequest` — List request with `size` and `startAfterId` cursor

**Returns:** `Page<FacilityGroup>`

**Throws:** `FulfillmenttoolsException` if the request fails

### listAsync(FacilityGroupListRequest)

List facility groups asynchronously.

**Parameters:**
- `request: FacilityGroupListRequest` — List request

**Returns:** `CompletableFuture<Page<FacilityGroup>>`

### listAll(FacilityGroupListRequest)

List all facility groups, automatically iterating through pages.

**Parameters:**
- `request: FacilityGroupListRequest` — List request

**Returns:** `Iterable<FacilityGroup>`

### create(CreateFacilityGroupRequest)

Create a new facility group. `tenantFacilityGroupId`, `facilityRefs`, and `nameLocalized` are required.

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

Update an existing facility group. `version` is required for optimistic locking. Other updatable fields: `tenantFacilityGroupId`, `facilityRefs`, `nameLocalized`, `customAttributes`.

**Parameters:**
- `facilityGroupId: FacilityGroupId` — The facility group identifier
- `request: UpdateFacilityGroupRequest` — Update request with current version and new values

**Returns:** `FacilityGroup`

**Throws:** `FulfillmenttoolsException` if the request fails or a version conflict occurs

### updateAsync(FacilityGroupId, UpdateFacilityGroupRequest)

Update a facility group asynchronously.

**Parameters:**
- `facilityGroupId: FacilityGroupId` — The facility group identifier
- `request: UpdateFacilityGroupRequest` — Update request

**Returns:** `CompletableFuture<FacilityGroup>`

### delete(FacilityGroupId)

Delete a facility group.

**Parameters:**
- `facilityGroupId: FacilityGroupId` — The facility group identifier

**Returns:** `void`

**Throws:** `FulfillmenttoolsException` if the request fails

### deleteAsync(FacilityGroupId)

Delete a facility group asynchronously.

**Parameters:**
- `facilityGroupId: FacilityGroupId` — The facility group identifier

**Returns:** `CompletableFuture<Void>`

### addFacilities(FacilityGroupId, List&lt;FacilityId&gt;, Integer)

Add facilities to a facility group.

**Parameters:**
- `facilityGroupId: FacilityGroupId` — The facility group identifier
- `facilityIds: List<FacilityId>` — Facility IDs to add
- `version: Integer` — Current optimistic lock version (pass `null` to skip version check)

**Returns:** `FacilityGroup`

**Throws:** `FulfillmenttoolsException` if the request fails

### addFacilitiesAsync(FacilityGroupId, List&lt;FacilityId&gt;, Integer)

Add facilities to a facility group asynchronously.

**Parameters:**
- `facilityGroupId: FacilityGroupId` — The facility group identifier
- `facilityIds: List<FacilityId>` — Facility IDs to add
- `version: Integer` — Current optimistic lock version

**Returns:** `CompletableFuture<FacilityGroup>`

### removeFacilities(FacilityGroupId, List&lt;FacilityId&gt;, Integer)

Remove facilities from a facility group.

**Parameters:**
- `facilityGroupId: FacilityGroupId` — The facility group identifier
- `facilityIds: List<FacilityId>` — Facility IDs to remove
- `version: Integer` — Current optimistic lock version (pass `null` to skip version check)

**Returns:** `FacilityGroup`

**Throws:** `FulfillmenttoolsException` if the request fails

### removeFacilitiesAsync(FacilityGroupId, List&lt;FacilityId&gt;, Integer)

Remove facilities from a facility group asynchronously.

**Parameters:**
- `facilityGroupId: FacilityGroupId` — The facility group identifier
- `facilityIds: List<FacilityId>` — Facility IDs to remove
- `version: Integer` — Current optimistic lock version

**Returns:** `CompletableFuture<FacilityGroup>`

### search(FacilityGroupSearchRequest)

Search facility groups using a structured query.

**Parameters:**
- `request: FacilityGroupSearchRequest` — Search request with optional `query`, `size`, `after`, and `before` cursors

**Returns:** `Page<FacilityGroup>`

**Throws:** `FulfillmenttoolsException` if the request fails

### searchAsync(FacilityGroupSearchRequest)

Search facility groups asynchronously.

**Parameters:**
- `request: FacilityGroupSearchRequest` — Search request

**Returns:** `CompletableFuture<Page<FacilityGroup>>`

### searchAll(FacilityGroupSearchRequest)

Search all facility groups matching a query, automatically iterating through pages.

**Parameters:**
- `request: FacilityGroupSearchRequest` — Search request

**Returns:** `Iterable<FacilityGroup>`

## FacilityGroupSearchQuery Filter Methods

| Method | Description |
|--------|-------------|
| `idEq(String)` | Match exact group ID |
| `idIn(String...)` | Match any of the given group IDs |
| `idNotEq(String)` | Exclude the given group ID |
| `tenantFacilityGroupIdEq(String)` | Match exact tenant group ID |
| `tenantFacilityGroupIdIn(String...)` | Match any of the given tenant group IDs |
| `nameEq(String)` | Match exact name |
| `nameLike(String)` | Match name by pattern |
| `nameIn(String...)` | Match any of the given names |
| `facilityRefsContains(String)` | Group contains the given facility ref |
| `facilityRefsContainsAll(String...)` | Group contains all of the given facility refs |
| `and(FacilityGroupSearchQuery...)` | Combine multiple queries with AND logic |
| `or(FacilityGroupSearchQuery...)` | Combine multiple queries with OR logic |
