# Facility Connections Client

The Facility Connections client manages facility connection configurations. Manage integrations and connections between facilities.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.FacilityConnectionId;

// Get a facility connection
FacilityConnection connection = client.facilityConnections().get(new FacilityConnectionId("fcon-001"));
System.out.println("Type: " + connection.getType());
```

## Listing Facility Connections

```java
Page<FacilityConnection> page = client.facilityConnections().list(
    FacilityConnectionListRequest.builder()
        .size(50)
        .build()
);
```

## API Reference

### get(FacilityConnectionId)

Get a facility connection by ID.

**Returns:** `FacilityConnection`

### list(FacilityConnectionListRequest)

List facility connections with pagination.

**Returns:** `Page<FacilityConnection>`

### listAll(FacilityConnectionListRequest)

List all facility connections.

**Returns:** `Iterable<FacilityConnection>`
