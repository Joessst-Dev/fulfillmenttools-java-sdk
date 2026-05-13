# Carriers Client

The Carriers client provides access to carrier information and management. Query and manage shipping carriers in your network.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.CarrierId;

// Get carrier information
Carrier carrier = client.carriers().get(new CarrierId("car-001"));
System.out.println("Name: " + carrier.getName());
```

## Listing Carriers

```java
Page<Carrier> page = client.carriers().list(
    CarrierListRequest.builder()
        .size(50)
        .build()
);
```

Iterate through all carriers:

```java
Iterable<Carrier> allCarriers = client.carriers().listAll(
    CarrierListRequest.builder()
        .size(100)
        .build()
);
```

## API Reference

### get(CarrierId)

Get carrier information by ID.

**Parameters:**
- `carrierId: CarrierId` — The carrier identifier

**Returns:** `Carrier`

**Throws:** `FulfillmenttoolsException` on request failure

### getAsync(CarrierId)

Get carrier information by ID asynchronously.

**Parameters:**
- `carrierId: CarrierId` — The carrier identifier

**Returns:** `CompletableFuture<Carrier>`

### list(CarrierListRequest)

List carriers with pagination.

**Parameters:**
- `request: CarrierListRequest` — List request with pagination parameters

**Returns:** `Page<Carrier>`

**Throws:** `FulfillmenttoolsException` on request failure

### listAsync(CarrierListRequest)

List carriers asynchronously.

**Parameters:**
- `request: CarrierListRequest` — List request

**Returns:** `CompletableFuture<Page<Carrier>>`

### listAll(CarrierListRequest)

List all carriers, automatically iterating through pages.

**Parameters:**
- `request: CarrierListRequest` — List request

**Returns:** `Iterable<Carrier>`

### create(CreateCarrierRequest)

Create a new carrier.

**Parameters:**
- `request: CreateCarrierRequest` — Create carrier request

**Returns:** `Carrier`

**Throws:** `FulfillmenttoolsException` on request failure

### createAsync(CreateCarrierRequest)

Create a new carrier asynchronously.

**Parameters:**
- `request: CreateCarrierRequest` — Create carrier request

**Returns:** `CompletableFuture<Carrier>`

### update(CarrierId, UpdateCarrierRequest)

Update an existing carrier.

**Parameters:**
- `carrierId: CarrierId` — The carrier identifier
- `request: UpdateCarrierRequest` — Update request with new values

**Returns:** `Carrier`

**Throws:** `FulfillmenttoolsException` on request failure

### updateAsync(CarrierId, UpdateCarrierRequest)

Update an existing carrier asynchronously.

**Parameters:**
- `carrierId: CarrierId` — The carrier identifier
- `request: UpdateCarrierRequest` — Update request

**Returns:** `CompletableFuture<Carrier>`

### delete(CarrierId)

Delete a carrier by ID.

**Parameters:**
- `carrierId: CarrierId` — The carrier identifier

**Returns:** `void`

**Throws:** `FulfillmenttoolsException` on request failure

### deleteAsync(CarrierId)

Delete a carrier by ID asynchronously.

**Parameters:**
- `carrierId: CarrierId` — The carrier identifier

**Returns:** `CompletableFuture<Void>`
