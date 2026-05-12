package de.joesst.dev.fulfillmenttools.facilityconnections;

import java.util.List;

/**
 * Non-delivery day configuration for a specific province within a country.
 *
 * @param province                    province or state code
 * @param nonDeliveryDays             explicit non-delivery dates for this province
 * @param recurringNonDeliveryWeekdays weekdays on which delivery never occurs in this province
 */
public record NonDeliveryDaysPerProvince(
        String province,
        List<NonDeliveryDay> nonDeliveryDays,
        List<WeekDay> recurringNonDeliveryWeekdays
) {}
