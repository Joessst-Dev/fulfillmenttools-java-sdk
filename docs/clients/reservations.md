# Reservations Client

The Reservations client provides read access to stock reservations in the fulfillmenttools platform. Reservations represent quantities of an article held for a specific order at a facility.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.ReservationId;
import de.joesst.dev.fulfillmenttools.reservations.Reservation;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

// Get a reservation by ID
try {
    Reservation reservation = client.reservations().get(ReservationId.builder().value("res-001").build());
    System.out.println("Article: " + reservation.tenantArticleId().value());
    System.out.println("Quantity: " + reservation.quantity());
    System.out.println("Facility: " + reservation.facilityRef().value());
} catch (NotFoundException e) {
    System.out.println("Reservation not found");
} catch (FulfillmenttoolsException e) {
    System.out.println("Request failed: " + e.getMessage());
}
```

## Listing Reservations

List reservations with pagination. Note that `ReservationListRequest` uses `after` as the pagination cursor field (not `startAfterId`):

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.reservations.ReservationListRequest;

Page<Reservation> page = client.reservations().list(
    ReservationListRequest.builder()
        .size(50)
        .build()
);

for (Reservation reservation : page.items()) {
    System.out.println(reservation.id().value() + " — qty: " + reservation.quantity());
}
```

Iterate through all pages automatically:

```java
Iterable<Reservation> allReservations = client.reservations().listAll(
    ReservationListRequest.builder()
        .size(100)
        .build()
);

for (Reservation reservation : allReservations) {
    System.out.println(reservation.tenantArticleId().value());
}
```

Manual pagination using `nextCursor()`:

```java
Page<Reservation> page = client.reservations().list(ReservationListRequest.builder().size(20).build());

while (page.hasMore()) {
    page = client.reservations().list(
        ReservationListRequest.builder()
            .size(20)
            .after(page.nextCursor())
            .build()
    );
    for (Reservation reservation : page.items()) {
        System.out.println(reservation.id().value());
    }
}
```

## Async Usage

All methods have async variants returning `CompletableFuture`:

```java
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

CompletableFuture<Reservation> future = client.reservations().getAsync(
    ReservationId.builder().value("res-001").build()
);

future.whenComplete((reservation, ex) -> {
    if (ex != null) {
        Throwable cause = ex instanceof CompletionException && ex.getCause() != null
            ? ex.getCause() : ex;
        System.out.println("Error: " + cause.getMessage());
    } else {
        System.out.println("Quantity: " + reservation.quantity());
    }
});
```

## API Reference

### get(ReservationId)

Get a reservation by ID.

**Parameters:**
- `reservationId: ReservationId` — The reservation identifier

**Returns:** `Reservation`

**Throws:** `NotFoundException` (404), `FulfillmenttoolsException` if the request fails

### getAsync(ReservationId)

Get a reservation asynchronously.

**Parameters:**
- `reservationId: ReservationId` — The reservation identifier

**Returns:** `CompletableFuture<Reservation>`

### list(ReservationListRequest)

List reservations with pagination.

**Parameters:**
- `request: ReservationListRequest` — List request with `size` and `after` cursor

**Returns:** `Page<Reservation>`

**Throws:** `FulfillmenttoolsException` if the request fails

### listAsync(ReservationListRequest)

List reservations asynchronously with pagination.

**Parameters:**
- `request: ReservationListRequest` — List request with `size` and `after` cursor

**Returns:** `CompletableFuture<Page<Reservation>>`

### listAll(ReservationListRequest)

List all reservations, automatically iterating through pages.

**Parameters:**
- `request: ReservationListRequest` — List request

**Returns:** `Iterable<Reservation>`
