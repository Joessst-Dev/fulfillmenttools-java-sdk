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

**Returns:** `Carrier`

### list(CarrierListRequest)

List carriers with pagination.

**Returns:** `Page<Carrier>`

### listAll(CarrierListRequest)

List all carriers.

**Returns:** `Iterable<Carrier>`
