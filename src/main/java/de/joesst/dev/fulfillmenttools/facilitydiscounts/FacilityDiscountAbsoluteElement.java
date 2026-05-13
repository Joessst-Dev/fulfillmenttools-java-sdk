package de.joesst.dev.fulfillmenttools.facilitydiscounts;

/**
 * Represents an absolute discount element with a specific currency and precision.
 *
 * @param value the discount amount
 * @param currency the currency code (e.g., USD, EUR)
 * @param decimalPlaces the number of decimal places for the currency
 */
public record FacilityDiscountAbsoluteElement(Integer value, String currency, Integer decimalPlaces) {}
