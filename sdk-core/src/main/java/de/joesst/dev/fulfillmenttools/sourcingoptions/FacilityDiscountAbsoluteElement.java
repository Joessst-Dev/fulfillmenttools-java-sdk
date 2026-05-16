package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A single currency-denominated element of an absolute discount.
 *
 * <p>Maps to the {@code FacilityDiscountAbsoluteElement} schema in the fulfillmenttools
 * OpenAPI specification.
 *
 * @param currency      the ISO 4217 currency code (e.g. {@code EUR})
 * @param decimalPlaces number of decimal places for the currency
 * @param value         discount value in the smallest subunit (e.g. cents)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record FacilityDiscountAbsoluteElement(
        String currency,
        Integer decimalPlaces,
        Integer value
) {
}
