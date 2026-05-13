package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

/**
 * Shipping-specific delivery preferences for an order.
 *
 * <p>Maps to the {@code DeliveryPreferencesShipping} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param serviceLevel                The desired service level: {@code DELIVERY} (standard
 *                                    multi-day shipping) or {@code SAMEDAY}.
 * @param desiredDeliveryTime         Optional target delivery timestamp.
 * @param preferredCarriers           Optional list of preferred carrier keys (e.g. {@code DPD}).
 * @param preferredCarriersWithProduct Optional list of carriers with specific carrier products.
 * @param preselectedFacilities       Optional list of facilities pre-selected for fulfillment.
 * @param carrierProductCategory      Optional carrier product category filter.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DeliveryPreferencesShipping(
        String serviceLevel,
        Instant desiredDeliveryTime,
        List<String> preferredCarriers,
        List<PreferredCarrierWithProduct> preferredCarriersWithProduct,
        List<PreselectedFacility> preselectedFacilities,
        String carrierProductCategory
) {}
