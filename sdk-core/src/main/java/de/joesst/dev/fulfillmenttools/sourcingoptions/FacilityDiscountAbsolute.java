package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * An absolute (fixed-amount) discount, specified per currency.
 *
 * <p>Maps to the {@code FacilityDiscountAbsoluteForCreation} schema in the fulfillmenttools
 * OpenAPI specification.
 *
 * @param type   always {@code ABSOLUTE}
 * @param values the per-currency discount amounts
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record FacilityDiscountAbsolute(
        String type,
        List<FacilityDiscountAbsoluteElement> values
) implements FacilityDiscount {
}
