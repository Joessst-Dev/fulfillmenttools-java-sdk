package de.joesst.dev.fulfillmenttools.internal.reservations;

import de.joesst.dev.fulfillmenttools.reservations.Reservation;

import java.util.List;

record ReservationListResponse(List<Reservation> reservations, PageInfoDto pageInfo) {
    record PageInfoDto(String endCursor, Boolean hasNextPage) {}
}
