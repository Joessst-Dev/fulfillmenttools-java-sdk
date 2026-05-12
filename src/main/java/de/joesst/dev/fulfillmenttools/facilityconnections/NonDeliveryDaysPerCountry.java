package de.joesst.dev.fulfillmenttools.facilityconnections;

import java.util.List;

/**
 * Non-delivery day configuration for a country, optionally refined per province.
 * Corresponds to {@code NonDeliveryDaysPerCountryAndProvince} in the OpenAPI spec.
 *
 * @param country                     ISO 3166-1 alpha-2 country code
 * @param nonDeliveryDays             explicit non-delivery dates that apply country-wide
 * @param nonDeliveryDaysPerProvince  province-level overrides
 * @param recurringNonDeliveryWeekdays weekdays on which delivery never occurs in this country
 */
public record NonDeliveryDaysPerCountry(
        String country,
        List<NonDeliveryDay> nonDeliveryDays,
        List<NonDeliveryDaysPerProvince> nonDeliveryDaysPerProvince,
        List<WeekDay> recurringNonDeliveryWeekdays
) {}
