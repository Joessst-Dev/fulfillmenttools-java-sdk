package de.joesst.dev.fulfillmenttools.reservations;

import de.joesst.dev.fulfillmenttools.id.ReservationId;
import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

/**
 * Client for managing reservations in the fulfillmenttools reservations module.
 *
 * <p>Provides synchronous and asynchronous operations to retrieve and list stock
 * reservations created for orders.
 */
public interface ReservationsClient {

    /**
     * Retrieves a single reservation by ID.
     *
     * @param reservationId the reservation ID
     * @return the reservation
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    Reservation get(ReservationId reservationId);

    /**
     * Retrieves a single reservation by ID asynchronously.
     *
     * @param reservationId the reservation ID
     * @return a {@code CompletableFuture} that resolves to the reservation
     */
    CompletableFuture<Reservation> getAsync(ReservationId reservationId);

    /**
     * Lists reservations, returning one page of results.
     *
     * @param request the list request with pagination
     * @return a page of reservations
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    Page<Reservation> list(ReservationListRequest request);

    /**
     * Lists reservations asynchronously, returning one page of results.
     *
     * @param request the list request with pagination
     * @return a {@code CompletableFuture} that resolves to a page of reservations
     */
    CompletableFuture<Page<Reservation>> listAsync(ReservationListRequest request);

    /**
     * Lists all reservations by automatically iterating through pages.
     *
     * @param request the list request
     * @return an {@code Iterable} over all reservations
     */
    Iterable<Reservation> listAll(ReservationListRequest request);
}
