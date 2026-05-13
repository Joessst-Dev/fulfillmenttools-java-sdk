# Storage Locations Client

The Storage Locations client manages warehouse storage locations. Query and manage where inventory is physically stored.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.StorageLocationId;

// Get a storage location for a facility
StorageLocation location = client.storageLocations().get(
    new FacilityId("fac-001"),
    new StorageLocationId("loc-001")
);
System.out.println("Zone: " + location.getZone());
```

## Listing Storage Locations

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;

Page<StorageLocation> page = client.storageLocations().list(
    new FacilityId("fac-001"),
    StorageLocationListRequest.builder()
        .size(50)
        .build()
);
```

## Creating and Managing Storage Locations

```java
StorageLocation location = client.storageLocations().create(
    new FacilityId("fac-001"),
    CreateStorageLocationRequest.builder()
        .zone("A1")
        .build()
);

// Update a storage location
StorageLocation updated = client.storageLocations().update(
    new FacilityId("fac-001"),
    new StorageLocationId("loc-001"),
    UpdateStorageLocationRequest.builder()
        .zone("A2")
        .build()
);

// Delete a storage location
client.storageLocations().delete(
    new FacilityId("fac-001"),
    new StorageLocationId("loc-001")
);
```

## API Reference

### get(FacilityId, StorageLocationId)

Get a storage location by ID.

**Parameters:**
- `facilityId: FacilityId` — The facility ID
- `storageLocationId: StorageLocationId` — The storage location ID

**Returns:** `StorageLocation`

**Throws:** `FulfillmenttoolsException` if not found

### getAsync(FacilityId, StorageLocationId)

Get a storage location asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The facility ID
- `storageLocationId: StorageLocationId` — The storage location ID

**Returns:** `CompletableFuture<StorageLocation>`

### list(FacilityId, StorageLocationListRequest)

List storage locations for a facility with pagination.

**Parameters:**
- `facilityId: FacilityId` — The facility ID
- `request: StorageLocationListRequest` — List request with filters and pagination

**Returns:** `Page<StorageLocation>`

**Throws:** `FulfillmenttoolsException` if the request fails

### listAsync(FacilityId, StorageLocationListRequest)

List storage locations asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The facility ID
- `request: StorageLocationListRequest` — List request with filters and pagination

**Returns:** `CompletableFuture<Page<StorageLocation>>`

### listAll(FacilityId, StorageLocationListRequest)

List all storage locations for a facility, automatically iterating through pages.

**Parameters:**
- `facilityId: FacilityId` — The facility ID
- `request: StorageLocationListRequest` — List request with filters

**Returns:** `Iterable<StorageLocation>`

### create(FacilityId, CreateStorageLocationRequest)

Create a new storage location in a facility.

**Parameters:**
- `facilityId: FacilityId` — The facility ID
- `request: CreateStorageLocationRequest` — Creation request

**Returns:** `StorageLocation`

**Throws:** `FulfillmenttoolsException` if the request fails

### createAsync(FacilityId, CreateStorageLocationRequest)

Create a storage location asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The facility ID
- `request: CreateStorageLocationRequest` — Creation request

**Returns:** `CompletableFuture<StorageLocation>`

### update(FacilityId, StorageLocationId, UpdateStorageLocationRequest)

Update an existing storage location.

**Parameters:**
- `facilityId: FacilityId` — The facility ID
- `storageLocationId: StorageLocationId` — The storage location ID
- `request: UpdateStorageLocationRequest` — Update request

**Returns:** `StorageLocation`

### updateAsync(FacilityId, StorageLocationId, UpdateStorageLocationRequest)

Update a storage location asynchronously.

**Returns:** `CompletableFuture<StorageLocation>`

### delete(FacilityId, StorageLocationId)

Delete a storage location.

**Parameters:**
- `facilityId: FacilityId` — The facility ID
- `storageLocationId: StorageLocationId` — The storage location ID

**Throws:** `FulfillmenttoolsException` if the request fails

### deleteAsync(FacilityId, StorageLocationId)

Delete a storage location asynchronously.

**Returns:** `CompletableFuture<Void>`
