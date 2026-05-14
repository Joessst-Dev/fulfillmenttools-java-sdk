# Storage Locations Client

The Storage Locations client manages physical storage locations (aisles, racks, bins) within a facility. All operations are scoped to a specific facility.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.StorageLocationId;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocation;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

try {
    StorageLocation location = client.storageLocations().get(
        FacilityId.builder().value("fac-001").build(),
        StorageLocationId.builder().value("loc-001").build()
    );
    System.out.println("Name: " + location.name());
    System.out.println("Type: " + location.type());
    System.out.println("Zone: " + location.zoneName());
} catch (NotFoundException e) {
    System.out.println("Storage location not found");
} catch (FulfillmenttoolsException e) {
    System.out.println("Request failed: " + e.getMessage());
}
```

## Listing Storage Locations

List storage locations for a facility with optional filters:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocation;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocationListRequest;

Page<StorageLocation> page = client.storageLocations().list(
    FacilityId.builder().value("fac-001").build(),
    StorageLocationListRequest.builder()
        .size(50)
        .build()
);

for (StorageLocation location : page.items()) {
    System.out.println(location.id().value() + " — " + location.name());
}
```

Filter by scannable code:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocation;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocationListRequest;

Page<StorageLocation> page = client.storageLocations().list(
    FacilityId.builder().value("fac-001").build(),
    StorageLocationListRequest.builder()
        .scannableCode("A1-001")
        .build()
);
```

Iterate through all storage locations automatically:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocation;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocationListRequest;

Iterable<StorageLocation> allLocations = client.storageLocations().listAll(
    FacilityId.builder().value("fac-001").build(),
    StorageLocationListRequest.builder()
        .size(100)
        .build()
);

for (StorageLocation location : allLocations) {
    System.out.println(location.id().value() + " — " + location.name());
}
```

## Creating a Storage Location

`name`, `type`, `scannableCodes`, and `runningSequences` are required:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocation;
import de.joesst.dev.fulfillmenttools.storagelocations.CreateStorageLocationRequest;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocationSequenceItem;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;
import java.util.List;

try {
    StorageLocation location = client.storageLocations().create(
        FacilityId.builder().value("fac-001").build(),
        CreateStorageLocationRequest.builder()
            .name("Aisle 1")
            .type("AISLE")
            .scannableCodes(List.of("A1"))
            .runningSequences(List.<StorageLocationSequenceItem>of())
            .zoneName("Zone A")
            .build()
    );
    System.out.println("Created: " + location.id().value());
} catch (FulfillmenttoolsException e) {
    System.out.println("Creation failed: " + e.getMessage());
}
```

## Updating a Storage Location

`version` is required for optimistic locking:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.StorageLocationId;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocation;
import de.joesst.dev.fulfillmenttools.storagelocations.UpdateStorageLocationRequest;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

StorageLocation location = client.storageLocations().get(
    FacilityId.builder().value("fac-001").build(),
    StorageLocationId.builder().value("loc-001").build()
);

StorageLocation updated = client.storageLocations().update(
    FacilityId.builder().value("fac-001").build(),
    StorageLocationId.builder().value("loc-001").build(),
    UpdateStorageLocationRequest.builder()
        .version(location.version())
        .name("Aisle 1A")
        .build()
);
System.out.println("Updated name: " + updated.name());
```

## Deleting a Storage Location

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.StorageLocationId;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

client.storageLocations().delete(
    FacilityId.builder().value("fac-001").build(),
    StorageLocationId.builder().value("loc-001").build()
);
```

## Async Usage

All methods have async variants returning `CompletableFuture`:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.StorageLocationId;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocation;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

CompletableFuture<StorageLocation> future = client.storageLocations().getAsync(
    FacilityId.builder().value("fac-001").build(),
    StorageLocationId.builder().value("loc-001").build()
);

future.whenComplete((location, ex) -> {
    if (ex != null) {
        Throwable cause = ex instanceof CompletionException && ex.getCause() != null
            ? ex.getCause() : ex;
        System.out.println("Error: " + cause.getMessage());
    } else {
        System.out.println("Name: " + location.name());
        System.out.println("Type: " + location.type());
    }
});
```

## API Reference

### get(FacilityId, StorageLocationId)

Get a storage location by ID.

**Parameters:**
- `facilityId: FacilityId` — The facility identifier
- `storageLocationId: StorageLocationId` — The storage location identifier

**Returns:** `StorageLocation`

**Throws:** `NotFoundException` (404), `FulfillmenttoolsException` if the request fails

### getAsync(FacilityId, StorageLocationId)

Get a storage location by ID asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The facility identifier
- `storageLocationId: StorageLocationId` — The storage location identifier

**Returns:** `CompletableFuture<StorageLocation>`

### list(FacilityId, StorageLocationListRequest)

List storage locations for a facility with pagination. Filters include `scannableCode`.

**Parameters:**
- `facilityId: FacilityId` — The facility identifier
- `request: StorageLocationListRequest` — List request with `size`, `startAfterId`, and optional filters

**Returns:** `Page<StorageLocation>`

**Throws:** `FulfillmenttoolsException` if the request fails

### listAsync(FacilityId, StorageLocationListRequest)

List storage locations asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The facility identifier
- `request: StorageLocationListRequest` — List request

**Returns:** `CompletableFuture<Page<StorageLocation>>`

### listAll(FacilityId, StorageLocationListRequest)

List all storage locations for a facility, automatically iterating through pages.

**Parameters:**
- `facilityId: FacilityId` — The facility identifier
- `request: StorageLocationListRequest` — List request

**Returns:** `Iterable<StorageLocation>`

### create(FacilityId, CreateStorageLocationRequest)

Create a new storage location in a facility. `name`, `type`, `scannableCodes`, and `runningSequences` are required.

**Parameters:**
- `facilityId: FacilityId` — The facility identifier
- `request: CreateStorageLocationRequest` — Creation request

**Returns:** `StorageLocation`

**Throws:** `FulfillmenttoolsException` if the request fails

### createAsync(FacilityId, CreateStorageLocationRequest)

Create a storage location asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The facility identifier
- `request: CreateStorageLocationRequest` — Creation request

**Returns:** `CompletableFuture<StorageLocation>`

### update(FacilityId, StorageLocationId, UpdateStorageLocationRequest)

Update an existing storage location. `version` is required for optimistic locking.

**Parameters:**
- `facilityId: FacilityId` — The facility identifier
- `storageLocationId: StorageLocationId` — The storage location identifier
- `request: UpdateStorageLocationRequest` — Update request with current version and new values

**Returns:** `StorageLocation`

**Throws:** `FulfillmenttoolsException` if the request fails or a version conflict occurs

### updateAsync(FacilityId, StorageLocationId, UpdateStorageLocationRequest)

Update a storage location asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The facility identifier
- `storageLocationId: StorageLocationId` — The storage location identifier
- `request: UpdateStorageLocationRequest` — Update request

**Returns:** `CompletableFuture<StorageLocation>`

### delete(FacilityId, StorageLocationId)

Delete a storage location.

**Parameters:**
- `facilityId: FacilityId` — The facility identifier
- `storageLocationId: StorageLocationId` — The storage location identifier

**Returns:** `void`

**Throws:** `FulfillmenttoolsException` if the request fails

### deleteAsync(FacilityId, StorageLocationId)

Delete a storage location asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The facility identifier
- `storageLocationId: StorageLocationId` — The storage location identifier

**Returns:** `CompletableFuture<Void>`
