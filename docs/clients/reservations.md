# Reservations Client

The Reservations client manages inventory reservations. Reserve stock for orders and manage reservation lifecycles.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.ReservationId;

// Get a reservation
Reservation reservation = client.reservations().get(new ReservationId("res-001"));
System.out.println("Status: " + reservation.getStatus());
```

## Listing Reservations

```java
Page<Reservation> page = client.reservations().list(
    ReservationListRequest.builder()
        .size(50)
        .build()
);
```

Iterate through all reservations:

```java
Iterable<Reservation> allReservations = client.reservations().listAll(
    ReservationListRequest.builder()
        .size(100)
        .build()
);
```

## API Reference

### get(ReservationId)

Get a reservation by ID.

**Parameters:**
- `reservationId: ReservationId` — The reservation ID

**Returns:** `Reservation`

**Throws:** `FulfillmenttoolsException` if not found

### getAsync(ReservationId)

Get a reservation asynchronously.

**Parameters:**
- `reservationId: ReservationId` — The reservation ID

**Returns:** `CompletableFuture<Reservation>`

### list(ReservationListRequest)

List reservations with pagination.

**Parameters:**
- `request: ReservationListRequest` — List request with pagination

**Returns:** `Page<Reservation>`

**Throws:** `FulfillmenttoolsException` if the request fails

### listAsync(ReservationListRequest)

List reservations asynchronously with pagination.

**Parameters:**
- `request: ReservationListRequest` — List request with pagination

**Returns:** `CompletableFuture<Page<Reservation>>`

### listAll(ReservationListRequest)

List all reservations, automatically iterating through pages.

**Parameters:**
- `request: ReservationListRequest` — List request

**Returns:** `Iterable<Reservation>`
