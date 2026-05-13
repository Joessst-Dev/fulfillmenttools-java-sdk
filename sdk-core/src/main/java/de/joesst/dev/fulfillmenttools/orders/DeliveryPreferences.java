package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

/**
 * Delivery preferences that determine how and where an order should be fulfilled.
 *
 * <p>Maps to the {@code DeliveryPreferences} schema in the fulfillmenttools OpenAPI spec.
 * Exactly one of {@code shipping} or {@code collect} should be provided to specify the
 * fulfillment channel.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param shipping               Configuration for ship-to-customer delivery.
 * @param collect                Configuration for click-and-collect delivery. At most one entry.
 * @param targetTime             The time by which the delivery result is expected.
 * @param sourcingOptionRefs     Optional references to pre-computed sourcing options.
 * @param supplyingFacilities    Deprecated: supply facilities at the collect level instead.
 * @param reservationPreferences Preferences controlling how stock reservations are scheduled.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DeliveryPreferences(
        DeliveryPreferencesShipping shipping,
        List<CollectDelivery> collect,
        Instant targetTime,
        List<String> sourcingOptionRefs,
        List<String> supplyingFacilities,
        DeliveryReservationPreferences reservationPreferences
) {}
