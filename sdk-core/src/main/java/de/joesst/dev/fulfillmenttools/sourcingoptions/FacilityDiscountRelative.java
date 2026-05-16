package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A relative (percentage-based) discount.
 *
 * <p>Maps to the {@code FacilityDiscountRelative} schema in the fulfillmenttools OpenAPI spec.
 *
 * @param type  always {@code RELATIVE}
 * @param value the discount percentage (e.g. {@code 7.1} for 7.1 %)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record FacilityDiscountRelative(
        String type,
        Double value
) implements FacilityDiscount {
}
