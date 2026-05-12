package de.joesst.dev.fulfillmenttools.facilityconnections;

/**
 * A coefficient applied to delivery cost calculations, expressed per measurement unit.
 *
 * @param value           the coefficient value
 * @param measurementUnit the unit of measure the coefficient is relative to (e.g. "kg")
 */
public record DeliveryCostCoefficient(Double value, String measurementUnit) {}
