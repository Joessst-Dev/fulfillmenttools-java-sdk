package de.joesst.dev.fulfillmenttools.reservations;

import de.joesst.dev.fulfillmenttools.model.Page;

public interface ReservationsClient {

    Reservation get(String reservationId);

    Page<Reservation> list(ReservationListRequest request);

    Iterable<Reservation> listAll(ReservationListRequest request);

    Reservation create(CreateReservationRequest request);

    void delete(String reservationId);
}
