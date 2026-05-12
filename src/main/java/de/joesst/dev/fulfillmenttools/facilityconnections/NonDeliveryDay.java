package de.joesst.dev.fulfillmenttools.facilityconnections;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

/**
 * A single non-delivery day entry.
 *
 * @param nonDeliveryDay  the calendar date on which no delivery takes place
 * @param nonDeliveryType optional type of non-delivery event; defaults to {@code SINGLE} when absent
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record NonDeliveryDay(LocalDate nonDeliveryDay, String nonDeliveryType) {}
