package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * A preferred carrier combined with a specific carrier product and optional carrier services.
 *
 * <p>Maps to the {@code PreferredCarrierWithProduct} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param carrierKey      The carrier identifier key (e.g. {@code DPD}).
 * @param carrierProduct  Optional carrier product (e.g. {@code WORLDWIDE}).
 * @param carrierServices Optional list of carrier service identifiers.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PreferredCarrierWithProduct(
        String carrierKey,
        String carrierProduct,
        List<String> carrierServices
) {}
