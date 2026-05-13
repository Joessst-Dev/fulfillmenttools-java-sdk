package de.joesst.dev.fulfillmenttools.facilityconnections;

import java.util.List;

/**
 * Maps a set of packaging units to the delivery context in which they should be used.
 *
 * @param context        one or more context constraints that must all match for this mapping to apply
 * @param packagingUnits the packaging units available when the context matches
 */
public record PackagingUnitsByContext(List<PackagingUnitContext> context, List<PackagingUnit> packagingUnits) {}
