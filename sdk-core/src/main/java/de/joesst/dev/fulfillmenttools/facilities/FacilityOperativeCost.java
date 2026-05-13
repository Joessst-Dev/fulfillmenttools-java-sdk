package de.joesst.dev.fulfillmenttools.facilities;

/**
 * An operational cost estimate for a facility.
 *
 * @param value the cost amount as a scaled integer
 * @param currency ISO 4217 currency code (e.g. "EUR", "USD")
 * @param decimalPlaces number of decimal places for proper currency representation
 */
public record FacilityOperativeCost(Integer value, String currency, Integer decimalPlaces) {}
