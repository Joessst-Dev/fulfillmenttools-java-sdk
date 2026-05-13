# Storage Locations Client

The Storage Locations client manages warehouse storage locations. Query and manage where inventory is physically stored.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.StorageLocationId;

// Get a storage location
StorageLocation location = client.storageLocations().get(new StorageLocationId("loc-001"));
System.out.println("Zone: " + location.getZone());
```

## Listing Storage Locations

```java
Page<StorageLocation> page = client.storageLocations().list(
    StorageLocationListRequest.builder()
        .size(50)
        .build()
);
```

## API Reference

### get(StorageLocationId)

Get a storage location by ID.

**Returns:** `StorageLocation`

### list(StorageLocationListRequest)

List storage locations with pagination.

**Returns:** `Page<StorageLocation>`

### listAll(StorageLocationListRequest)

List all storage locations.

**Returns:** `Iterable<StorageLocation>`
