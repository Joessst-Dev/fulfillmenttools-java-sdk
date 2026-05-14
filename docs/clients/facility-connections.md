# Facility Connections Client

The Facility Connections client manages facility connection configurations — define how a source facility links to a carrier and a typed target (customer, managed facility, or supplier), along with routing rules such as cutoff times and delivery costs.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.ConnectionId;
import de.joesst.dev.fulfillmenttools.facilityconnections.FacilityConnection;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

try {
    FacilityConnection connection = client.facilityConnections().get(
        FacilityId.builder().value("fac-001").build(),
        ConnectionId.builder().value("conn-001").build()
    );
    System.out.println("Carrier key: " + connection.carrierKey());
    System.out.println("Carrier name: " + connection.carrierName());
    System.out.println("Version: " + connection.version());
} catch (NotFoundException e) {
    System.out.println("Connection not found");
} catch (FulfillmenttoolsException e) {
    System.out.println("Request failed: " + e.getMessage());
}
```

## Listing Facility Connections

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.facilityconnections.FacilityConnection;
import de.joesst.dev.fulfillmenttools.facilityconnections.FacilityConnectionListRequest;

Page<FacilityConnection> page = client.facilityConnections().list(
    FacilityId.builder().value("fac-001").build(),
    FacilityConnectionListRequest.builder()
        .size(50)
        .build()
);

for (FacilityConnection connection : page.items()) {
    System.out.println(connection.id().value() + " — " + connection.carrierKey());
}
```

Filter by target facility reference:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.facilityconnections.FacilityConnection;
import de.joesst.dev.fulfillmenttools.facilityconnections.FacilityConnectionListRequest;

Page<FacilityConnection> page = client.facilityConnections().list(
    FacilityId.builder().value("fac-001").build(),
    FacilityConnectionListRequest.builder()
        .targetFacilityRef("fac-456")
        .build()
);
```

Iterate through all facility connections automatically:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.facilityconnections.FacilityConnection;
import de.joesst.dev.fulfillmenttools.facilityconnections.FacilityConnectionListRequest;

Iterable<FacilityConnection> allConnections = client.facilityConnections().listAll(
    FacilityId.builder().value("fac-001").build(),
    FacilityConnectionListRequest.builder()
        .size(100)
        .build()
);

for (FacilityConnection connection : allConnections) {
    System.out.println(connection.id().value() + " — " + connection.carrierKey());
}
```

## Connection Targets

Every connection has a typed `target` that describes its destination. There are three variants, each constructed with a static factory method.

### Customer target

A connection whose target is an end customer:

```java
import de.joesst.dev.fulfillmenttools.facilityconnections.ConnectionTarget;

ConnectionTarget target = ConnectionTarget.Customer.of();
```

### Managed facility target

A connection pointing to another managed facility:

```java
import de.joesst.dev.fulfillmenttools.facilityconnections.ConnectionTarget;

ConnectionTarget target = ConnectionTarget.ManagedFacility.of("fac-456");
```

With facility and group exclusions:

```java
import de.joesst.dev.fulfillmenttools.facilityconnections.ConnectionTarget;
import java.util.List;

ConnectionTarget target = ConnectionTarget.ManagedFacility.of(
    "fac-456",
    List.of("fac-blacklisted"),
    List.of("fgrp-blacklisted")
);
```

### Supplier target

A connection pointing to a supplier facility:

```java
import de.joesst.dev.fulfillmenttools.facilityconnections.ConnectionTarget;

ConnectionTarget target = ConnectionTarget.Supplier.of("fac-789");
```

With exclusions:

```java
import de.joesst.dev.fulfillmenttools.facilityconnections.ConnectionTarget;
import java.util.List;

ConnectionTarget target = ConnectionTarget.Supplier.of(
    "fac-789",
    List.of("fac-excluded"),
    null
);
```

### Inspecting target type at runtime

`ConnectionTarget` is a sealed interface — use pattern matching to handle each variant:

```java
import de.joesst.dev.fulfillmenttools.facilityconnections.ConnectionTarget;
import de.joesst.dev.fulfillmenttools.facilityconnections.FacilityConnection;

FacilityConnection connection = client.facilityConnections().get(
    FacilityId.builder().value("fac-001").build(),
    ConnectionId.builder().value("conn-001").build()
);

switch (connection.target()) {
    case ConnectionTarget.Customer c ->
        System.out.println("Ships to customer");
    case ConnectionTarget.ManagedFacility mf ->
        System.out.println("Ships to facility: " + mf.facilityRef());
    case ConnectionTarget.Supplier s ->
        System.out.println("Ships to supplier: " + s.facilityRef());
}
```

## Creating a Facility Connection

`target` is the only required field:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.facilityconnections.FacilityConnection;
import de.joesst.dev.fulfillmenttools.facilityconnections.CreateFacilityConnectionRequest;
import de.joesst.dev.fulfillmenttools.facilityconnections.ConnectionTarget;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

try {
    FacilityConnection connection = client.facilityConnections().create(
        FacilityId.builder().value("fac-001").build(),
        CreateFacilityConnectionRequest.builder()
            .target(ConnectionTarget.Customer.of())
            .carrierKey("DHL")
            .build()
    );
    System.out.println("Created: " + connection.id().value());
} catch (FulfillmenttoolsException e) {
    System.out.println("Creation failed: " + e.getMessage());
}
```

### Fully-configured connection

A complete example showing all optional routing fields:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.facilityconnections.*;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;
import java.time.LocalDate;
import java.util.List;

try {
    FacilityConnection connection = client.facilityConnections().create(
        FacilityId.builder().value("fac-001").build(),
        CreateFacilityConnectionRequest.builder()
            .target(ConnectionTarget.Customer.of())
            .carrierKey("DHL")
            .carrierName("DHL Express")
            .fallbackTransitTime(
                CarrierTransitTime.builder()
                    .minTransitDays(1)
                    .maxTransitDays(3)
                    .build()
            )
            .fallbackCosts(List.of(
                DeliveryCost.builder()
                    .value(599)
                    .currency("EUR")
                    .decimalPlaces(2)
                    .build()
            ))
            .cutoffTimes(
                CutoffTimes.builder()
                    .weekdays(List.of(
                        CutoffTimesWeekDay.builder()
                            .day(WeekDay.MONDAY)
                            .cutoffConfigurations(List.of(
                                CutoffConfiguration.builder().time("14:00").build(),
                                CutoffConfiguration.builder().time("18:00").capacity(50.0).build()
                            ))
                            .build(),
                        CutoffTimesWeekDay.builder()
                            .day(WeekDay.FRIDAY)
                            .cutoffConfigurations(List.of(
                                CutoffConfiguration.builder().time("12:00").build()
                            ))
                            .build()
                    ))
                    .overwrites(List.of(
                        CutoffTimesOverwrite.builder()
                            .date(LocalDate.of(2025, 12, 24))
                            .cutoffConfigurations(List.of(
                                CutoffConfiguration.builder().time("10:00").build()
                            ))
                            .build()
                    ))
                    .build()
            )
            .nonDeliveryDays(List.of(
                NonDeliveryDaysPerCountry.builder()
                    .country("DE")
                    .recurringNonDeliveryWeekdays(List.of(WeekDay.SUNDAY))
                    .nonDeliveryDays(List.of(
                        NonDeliveryDay.builder()
                            .nonDeliveryDay(LocalDate.of(2025, 12, 25))
                            .build()
                    ))
                    .build()
            ))
            .build()
    );
    System.out.println("Created: " + connection.id().value());
} catch (FulfillmenttoolsException e) {
    System.out.println("Creation failed: " + e.getMessage());
}
```

## Configuring Cutoff Times

`CutoffTimes` combines a regular weekday schedule with optional date-specific overrides:

```java
import de.joesst.dev.fulfillmenttools.facilityconnections.*;
import java.time.LocalDate;
import java.util.List;

CutoffTimes cutoffTimes = CutoffTimes.builder()
    .weekdays(List.of(
        CutoffTimesWeekDay.builder()
            .day(WeekDay.MONDAY)
            .cutoffConfigurations(List.of(
                CutoffConfiguration.builder().time("14:00").build(),
                CutoffConfiguration.builder().time("18:00").capacity(50.0).build()
            ))
            .build(),
        CutoffTimesWeekDay.builder()
            .day(WeekDay.TUESDAY)
            .cutoffConfigurations(List.of(
                CutoffConfiguration.builder().time("14:00").build()
            ))
            .build(),
        CutoffTimesWeekDay.builder()
            .day(WeekDay.WEDNESDAY)
            .cutoffConfigurations(List.of(
                CutoffConfiguration.builder().time("14:00").build()
            ))
            .build(),
        CutoffTimesWeekDay.builder()
            .day(WeekDay.THURSDAY)
            .cutoffConfigurations(List.of(
                CutoffConfiguration.builder().time("14:00").build()
            ))
            .build(),
        CutoffTimesWeekDay.builder()
            .day(WeekDay.FRIDAY)
            .cutoffConfigurations(List.of(
                CutoffConfiguration.builder().time("12:00").build()
            ))
            .build()
    ))
    .overwrites(List.of(
        CutoffTimesOverwrite.builder()
            .date(LocalDate.of(2025, 12, 24))
            .cutoffConfigurations(List.of(
                CutoffConfiguration.builder().time("10:00").build()
            ))
            .build()
    ))
    .build();
```

`CutoffConfiguration.time` uses `HH:mm` format. The optional `capacity` caps the number of orders accepted before that cutoff.

## Configuring Non-Delivery Days

`NonDeliveryDaysPerCountry` lets you define per-country blackout dates and recurring non-delivery weekdays:

```java
import de.joesst.dev.fulfillmenttools.facilityconnections.*;
import java.time.LocalDate;
import java.util.List;

List<NonDeliveryDaysPerCountry> nonDeliveryDays = List.of(
    NonDeliveryDaysPerCountry.builder()
        .country("DE")
        .recurringNonDeliveryWeekdays(List.of(WeekDay.SATURDAY, WeekDay.SUNDAY))
        .nonDeliveryDays(List.of(
            NonDeliveryDay.builder()
                .nonDeliveryDay(LocalDate.of(2025, 12, 25))
                .build(),
            NonDeliveryDay.builder()
                .nonDeliveryDay(LocalDate.of(2025, 12, 26))
                .build()
        ))
        .build(),
    NonDeliveryDaysPerCountry.builder()
        .country("AT")
        .recurringNonDeliveryWeekdays(List.of(WeekDay.SUNDAY))
        .build()
);
```

## Configuring Connection Context

`context` scopes a connection to a subset of entities — use it to activate a connection only for specific facilities, facility groups, categories, or tags:

```java
import de.joesst.dev.fulfillmenttools.facilityconnections.*;
import java.util.List;

List<ConnectionContext> context = List.of(
    ConnectionContext.builder()
        .type(ConnectionContextType.FACILITY_GROUP)
        .values(List.of("fgrp-west", "fgrp-north"))
        .build(),
    ConnectionContext.builder()
        .type(ConnectionContextType.CATEGORY)
        .values(List.of("electronics"))
        .operator(ContextOperator.NOT)
        .build()
);
```

The `ContextOperator.NOT` operator negates the match — the second context above excludes the `electronics` category.

## Updating a Facility Connection

`version` and `target` are both required:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.ConnectionId;
import de.joesst.dev.fulfillmenttools.facilityconnections.FacilityConnection;
import de.joesst.dev.fulfillmenttools.facilityconnections.UpdateFacilityConnectionRequest;
import de.joesst.dev.fulfillmenttools.facilityconnections.ConnectionTarget;

FacilityConnection connection = client.facilityConnections().get(
    FacilityId.builder().value("fac-001").build(),
    ConnectionId.builder().value("conn-001").build()
);

FacilityConnection updated = client.facilityConnections().update(
    FacilityId.builder().value("fac-001").build(),
    ConnectionId.builder().value("conn-001").build(),
    UpdateFacilityConnectionRequest.builder()
        .version(connection.version())
        .target(ConnectionTarget.Customer.of())
        .carrierKey("FedEx")
        .build()
);
System.out.println("Updated carrier: " + updated.carrierKey());
```

## Deleting a Facility Connection

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.ConnectionId;

client.facilityConnections().delete(
    FacilityId.builder().value("fac-001").build(),
    ConnectionId.builder().value("conn-001").build()
);
```

## Async Usage

All methods have async variants returning `CompletableFuture`:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.ConnectionId;
import de.joesst.dev.fulfillmenttools.facilityconnections.FacilityConnection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

CompletableFuture<FacilityConnection> future = client.facilityConnections().getAsync(
    FacilityId.builder().value("fac-001").build(),
    ConnectionId.builder().value("conn-001").build()
);

future.whenComplete((connection, ex) -> {
    if (ex != null) {
        Throwable cause = ex instanceof CompletionException && ex.getCause() != null
            ? ex.getCause() : ex;
        System.out.println("Error: " + cause.getMessage());
    } else {
        System.out.println("Carrier key: " + connection.carrierKey());
        System.out.println("Version: " + connection.version());
    }
});
```

## API Reference

### get(FacilityId, ConnectionId)

Get a facility connection by ID.

**Parameters:**
- `facilityId: FacilityId` — The source facility identifier
- `connectionId: ConnectionId` — The connection identifier

**Returns:** `FacilityConnection`

**Throws:** `NotFoundException` (404), `FulfillmenttoolsException` if the request fails

### getAsync(FacilityId, ConnectionId)

Get a facility connection by ID asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The source facility identifier
- `connectionId: ConnectionId` — The connection identifier

**Returns:** `CompletableFuture<FacilityConnection>`

### list(FacilityId, FacilityConnectionListRequest)

List facility connections with optional filtering and pagination. Filters include `targetFacilityRef`.

**Parameters:**
- `facilityId: FacilityId` — The source facility identifier
- `request: FacilityConnectionListRequest` — List request with `size`, `startAfterId`, and optional filters

**Returns:** `Page<FacilityConnection>`

**Throws:** `FulfillmenttoolsException` if the request fails

### listAsync(FacilityId, FacilityConnectionListRequest)

List facility connections asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The source facility identifier
- `request: FacilityConnectionListRequest` — List request

**Returns:** `CompletableFuture<Page<FacilityConnection>>`

### listAll(FacilityId, FacilityConnectionListRequest)

List all facility connections, automatically iterating through pages.

**Parameters:**
- `facilityId: FacilityId` — The source facility identifier
- `request: FacilityConnectionListRequest` — List request

**Returns:** `Iterable<FacilityConnection>`

### create(FacilityId, CreateFacilityConnectionRequest)

Create a new facility connection. `target` is the only required field.

**Parameters:**
- `facilityId: FacilityId` — The source facility identifier
- `request: CreateFacilityConnectionRequest` — Creation request. Optional fields: `carrierKey`, `carrierName`, `context`, `fallbackCosts`, `nonDeliveryDays`, `packagingUnitsByContexts`, `cutoffTimes`, `fallbackTransitTime`, `customAttributes`

**Returns:** `FacilityConnection`

**Throws:** `FulfillmenttoolsException` if the request fails

### createAsync(FacilityId, CreateFacilityConnectionRequest)

Create a facility connection asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The source facility identifier
- `request: CreateFacilityConnectionRequest` — Creation request

**Returns:** `CompletableFuture<FacilityConnection>`

### update(FacilityId, ConnectionId, UpdateFacilityConnectionRequest)

Update an existing facility connection. `version` and `target` are both required. Other updatable fields: `carrierKey`, `carrierName`, `context`, `fallbackCosts`, `nonDeliveryDays`, `packagingUnitsByContexts`, `cutoffTimes`, `fallbackTransitTime`, `customAttributes`.

**Parameters:**
- `facilityId: FacilityId` — The source facility identifier
- `connectionId: ConnectionId` — The connection identifier
- `request: UpdateFacilityConnectionRequest` — Update request with current version, target, and new values

**Returns:** `FacilityConnection`

**Throws:** `FulfillmenttoolsException` if the request fails or a version conflict occurs

### updateAsync(FacilityId, ConnectionId, UpdateFacilityConnectionRequest)

Update a facility connection asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The source facility identifier
- `connectionId: ConnectionId` — The connection identifier
- `request: UpdateFacilityConnectionRequest` — Update request

**Returns:** `CompletableFuture<FacilityConnection>`

### delete(FacilityId, ConnectionId)

Delete a facility connection.

**Parameters:**
- `facilityId: FacilityId` — The source facility identifier
- `connectionId: ConnectionId` — The connection identifier

**Returns:** `void`

**Throws:** `FulfillmenttoolsException` if the request fails

### deleteAsync(FacilityId, ConnectionId)

Delete a facility connection asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The source facility identifier
- `connectionId: ConnectionId` — The connection identifier

**Returns:** `CompletableFuture<Void>`

## ConnectionTarget Variants

| Variant | Factory | Type discriminator |
|---------|---------|-------------------|
| `ConnectionTarget.Customer` | `Customer.of()` | `"CUSTOMER"` |
| `ConnectionTarget.ManagedFacility` | `ManagedFacility.of(facilityRef)` | `"MANAGED_FACILITY"` |
| `ConnectionTarget.Supplier` | `Supplier.of(facilityRef)` | `"SUPPLIER"` |

`ManagedFacility` and `Supplier` also accept exclusion lists:
`ManagedFacility.of(facilityRef, excludedFacilityRefs, excludedFacilityGroupRefs)`

## ConnectionContextType Values

| Value | Scopes connection to |
|-------|---------------------|
| `FACILITY` | Specific facility IDs |
| `FACILITY_GROUP` | Specific facility group IDs |
| `CATEGORY` | Product categories |
| `TAG_REFERENCE` | Tag references |
