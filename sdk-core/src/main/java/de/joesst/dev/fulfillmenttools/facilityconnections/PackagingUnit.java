package de.joesst.dev.fulfillmenttools.facilityconnections;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Describes a single packaging unit (box, envelope, pallet, etc.) that can be used
 * by a carrier connection.
 *
 * @param name                  human-readable name of this packaging unit; required
 * @param priority              selection priority — lower values are preferred; required
 * @param packagingUnitRef      optional carrier-side reference identifier
 * @param tenantPackagingUnitId optional tenant-defined identifier
 * @param maxItemQuantity       optional maximum number of items that fit in this unit
 * @param dimensions            optional physical dimensions and weight limit
 * @param prices                optional list of delivery costs associated with this unit
 * @param transitTime           optional carrier transit time for shipments using this unit
 * @param volumeBufferInPercent optional percentage of volume reserved as a buffer
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PackagingUnit(
        String name,
        Integer priority,
        String packagingUnitRef,
        String tenantPackagingUnitId,
        Integer maxItemQuantity,
        PackagingUnitDimensions dimensions,
        List<DeliveryCost> prices,
        CarrierTransitTime transitTime,
        Integer volumeBufferInPercent
) {}
