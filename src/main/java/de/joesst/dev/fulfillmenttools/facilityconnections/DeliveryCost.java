package de.joesst.dev.fulfillmenttools.facilityconnections;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A monetary delivery cost, optionally refined by decimal precision and a cost coefficient.
 *
 * @param value                    the cost amount as a scaled integer
 * @param currency                 ISO 4217 currency code (e.g. "EUR")
 * @param decimalPlaces            optional number of decimal places; {@code null} defaults to the currency's standard
 * @param deliveryCostCoefficient  optional per-unit cost coefficient; {@code null} if not applicable
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DeliveryCost(
        Integer value,
        String currency,
        Integer decimalPlaces,
        DeliveryCostCoefficient deliveryCostCoefficient
) {}
