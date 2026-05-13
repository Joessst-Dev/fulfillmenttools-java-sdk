# Facility Connections Client

The Facility Connections client manages facility connection configurations. Manage integrations and connections between facilities.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.ConnectionId;

// Get a facility connection
FacilityConnection connection = client.facilityConnections().get(
    new FacilityId("fac-001"),
    new ConnectionId("conn-001")
);
System.out.println("Type: " + connection.getType());
```

## Listing Facility Connections

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;

Page<FacilityConnection> page = client.facilityConnections().list(
    new FacilityId("fac-001"),
    FacilityConnectionListRequest.builder()
        .size(50)
        .build()
);
```

## Creating and Managing Connections

```java
FacilityConnection connection = client.facilityConnections().create(
    new FacilityId("fac-001"),
    CreateFacilityConnectionRequest.builder()
        .carrier("UPS")
        .cutoffTime("18:00")
        .build()
);

// Update a connection
FacilityConnection updated = client.facilityConnections().update(
    new FacilityId("fac-001"),
    new ConnectionId("conn-001"),
    UpdateFacilityConnectionRequest.builder()
        .cutoffTime("17:00")
        .build()
);

// Delete a connection
client.facilityConnections().delete(
    new FacilityId("fac-001"),
    new ConnectionId("conn-001")
);
```

## API Reference

### get(FacilityId, ConnectionId)

Get a facility connection by ID.

**Parameters:**
- `facilityId: FacilityId` — The source facility ID
- `connectionId: ConnectionId` — The connection ID

**Returns:** `FacilityConnection`

**Throws:** `FulfillmenttoolsException` if not found

### getAsync(FacilityId, ConnectionId)

Get a facility connection asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The source facility ID
- `connectionId: ConnectionId` — The connection ID

**Returns:** `CompletableFuture<FacilityConnection>`

### list(FacilityId, FacilityConnectionListRequest)

List facility connections with optional filtering and pagination.

**Parameters:**
- `facilityId: FacilityId` — The source facility ID
- `request: FacilityConnectionListRequest` — List request with filters and pagination

**Returns:** `Page<FacilityConnection>`

**Throws:** `FulfillmenttoolsException` if a request error occurs

### listAsync(FacilityId, FacilityConnectionListRequest)

List facility connections asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The source facility ID
- `request: FacilityConnectionListRequest` — List request with filters and pagination

**Returns:** `CompletableFuture<Page<FacilityConnection>>`

### listAll(FacilityId, FacilityConnectionListRequest)

List all facility connections, automatically handling pagination.

**Parameters:**
- `facilityId: FacilityId` — The source facility ID
- `request: FacilityConnectionListRequest` — List request with filters

**Returns:** `Iterable<FacilityConnection>`

### create(FacilityId, CreateFacilityConnectionRequest)

Create a new facility connection.

**Parameters:**
- `facilityId: FacilityId` — The source facility ID
- `request: CreateFacilityConnectionRequest` — Connection creation payload

**Returns:** `FacilityConnection`

**Throws:** `FulfillmenttoolsException` if validation fails or request error occurs

### createAsync(FacilityId, CreateFacilityConnectionRequest)

Create a facility connection asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The source facility ID
- `request: CreateFacilityConnectionRequest` — Connection creation payload

**Returns:** `CompletableFuture<FacilityConnection>`

### update(FacilityId, ConnectionId, UpdateFacilityConnectionRequest)

Update an existing facility connection with partial changes.

**Parameters:**
- `facilityId: FacilityId` — The source facility ID
- `connectionId: ConnectionId` — The connection ID to update
- `request: UpdateFacilityConnectionRequest` — Update payload with new field values

**Returns:** `FacilityConnection`

**Throws:** `FulfillmenttoolsException` if not found or request error occurs

### updateAsync(FacilityId, ConnectionId, UpdateFacilityConnectionRequest)

Update a facility connection asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The source facility ID
- `connectionId: ConnectionId` — The connection ID to update
- `request: UpdateFacilityConnectionRequest` — Update payload with new field values

**Returns:** `CompletableFuture<FacilityConnection>`

### delete(FacilityId, ConnectionId)

Delete a facility connection.

**Parameters:**
- `facilityId: FacilityId` — The source facility ID
- `connectionId: ConnectionId` — The connection ID to delete

**Throws:** `FulfillmenttoolsException` if not found or request error occurs

### deleteAsync(FacilityId, ConnectionId)

Delete a facility connection asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The source facility ID
- `connectionId: ConnectionId` — The connection ID to delete

**Returns:** `CompletableFuture<Void>`
