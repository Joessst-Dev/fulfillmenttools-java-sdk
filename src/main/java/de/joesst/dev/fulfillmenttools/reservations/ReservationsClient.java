package de.joesst.dev.fulfillmenttools.reservations;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

public interface ReservationsClient {

    Reservation get(String reservationId);
    CompletableFuture<Reservation> getAsync(String reservationId);

    Page<Reservation> list(ReservationListRequest request);
    CompletableFuture<Page<Reservation>> listAsync(ReservationListRequest request);

    Iterable<Reservation> listAll(ReservationListRequest request);
}
