# Carriers Client

The Carriers client provides full CRUD access to carrier management in the fulfillmenttools platform. Carriers represent shipping providers configured for your fulfillment network.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.CarrierId;
import de.joesst.dev.fulfillmenttools.carriers.Carrier;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

// Get a carrier by ID
try {
    Carrier carrier = client.carriers().get(CarrierId.builder().value("car-001").build());
    System.out.println("Name: " + carrier.name());
    System.out.println("Key: " + carrier.key());
    System.out.println("Status: " + carrier.status());
} catch (NotFoundException e) {
    System.out.println("Carrier not found");
} catch (FulfillmenttoolsException e) {
    System.out.println("Request failed: " + e.getMessage());
}
```

## Listing Carriers

List carriers with pagination:

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.carriers.CarrierListRequest;

Page<Carrier> page = client.carriers().list(
    CarrierListRequest.builder()
        .size(50)
        .build()
);

for (Carrier carrier : page.items()) {
    System.out.println(carrier.id().value() + " — " + carrier.name());
}
```

Iterate through all carriers automatically:

```java
Iterable<Carrier> allCarriers = client.carriers().listAll(
    CarrierListRequest.builder()
        .size(100)
        .build()
);

for (Carrier carrier : allCarriers) {
    System.out.println(carrier.name());
}
```

Manual pagination using `nextCursor()`:

```java
Page<Carrier> page = client.carriers().list(CarrierListRequest.builder().size(20).build());

while (page.hasMore()) {
    page = client.carriers().list(
        CarrierListRequest.builder()
            .size(20)
            .startAfterId(page.nextCursor())
            .build()
    );
    for (Carrier carrier : page.items()) {
        System.out.println(carrier.id().value());
    }
}
```

## Creating a Carrier

`key` and `name` are required fields:

```java
import de.joesst.dev.fulfillmenttools.carriers.CreateCarrierRequest;

Carrier created = client.carriers().create(
    CreateCarrierRequest.builder()
        .key("DHL")
        .name("DHL Parcel")
        .build()
);
System.out.println("Created carrier: " + created.id().value());
```

## Updating a Carrier

`version` is required for optimistic locking:

```java
import de.joesst.dev.fulfillmenttools.carriers.UpdateCarrierRequest;

Carrier carrier = client.carriers().get(CarrierId.builder().value("car-001").build());

Carrier updated = client.carriers().update(
    CarrierId.builder().value("car-001").build(),
    UpdateCarrierRequest.builder()
        .version(carrier.version())
        .name("DHL Express")
        .build()
);
System.out.println("Updated name: " + updated.name());
```

## Deleting a Carrier

```java
client.carriers().delete(CarrierId.builder().value("car-001").build());
```

## Async Usage

All methods have async variants returning `CompletableFuture`:

```java
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

CompletableFuture<Carrier> future = client.carriers().getAsync(
    CarrierId.builder().value("car-001").build()
);

future.whenComplete((carrier, ex) -> {
    if (ex != null) {
        Throwable cause = ex instanceof CompletionException && ex.getCause() != null
            ? ex.getCause() : ex;
        System.out.println("Error: " + cause.getMessage());
    } else {
        System.out.println("Name: " + carrier.name());
    }
});
```

## API Reference

### get(CarrierId)

Get a carrier by ID.

**Parameters:**
- `carrierId: CarrierId` — The carrier identifier

**Returns:** `Carrier`

**Throws:** `NotFoundException` (404), `FulfillmenttoolsException` if the request fails

### getAsync(CarrierId)

Get a carrier by ID asynchronously.

**Parameters:**
- `carrierId: CarrierId` — The carrier identifier

**Returns:** `CompletableFuture<Carrier>`

### list(CarrierListRequest)

List carriers with pagination.

**Parameters:**
- `request: CarrierListRequest` — List request with `size` and `startAfterId` cursor

**Returns:** `Page<Carrier>`

**Throws:** `FulfillmenttoolsException` if the request fails

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

Create a new carrier. `key` and `name` are required.

**Parameters:**
- `request: CreateCarrierRequest` — Create carrier request

**Returns:** `Carrier`

**Throws:** `FulfillmenttoolsException` if the request fails

### createAsync(CreateCarrierRequest)

Create a new carrier asynchronously.

**Parameters:**
- `request: CreateCarrierRequest` — Create carrier request

**Returns:** `CompletableFuture<Carrier>`

### update(CarrierId, UpdateCarrierRequest)

Update an existing carrier. The `version` field is required for optimistic locking.

**Parameters:**
- `carrierId: CarrierId` — The carrier identifier
- `request: UpdateCarrierRequest` — Update request with new values and current version

**Returns:** `Carrier`

**Throws:** `FulfillmenttoolsException` if the request fails or a version conflict occurs

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

**Throws:** `FulfillmenttoolsException` if the request fails

### deleteAsync(CarrierId)

Delete a carrier by ID asynchronously.

**Parameters:**
- `carrierId: CarrierId` — The carrier identifier

**Returns:** `CompletableFuture<Void>`
