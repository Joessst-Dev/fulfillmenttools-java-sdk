package de.joesst.dev.fulfillmenttools.facilityconnections;

import de.joesst.dev.fulfillmenttools.id.ConnectionId;
import de.joesst.dev.fulfillmenttools.id.FacilityId;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents a facility connection returned by the fulfillmenttools API.
 *
 * <p>A facility connection defines how a source facility is linked to a carrier and
 * an optional target (customer, managed facility, or supplier), together with all
 * associated routing rules such as cutoff times, packaging units, and delivery costs.
 *
 * @param id                       unique {@link ConnectionId} of the connection
 * @param version                  optimistic-locking version number
 * @param created                  creation timestamp
 * @param lastModified             last modification timestamp
 * @param sourceFacilityRef        {@link FacilityId} reference to the source facility
 * @param target                   typed target of this connection
 * @param carrierKey               carrier identifier key
 * @param carrierName              human-readable carrier name
 * @param context                  scoping contexts that restrict when this connection is used
 * @param fallbackCosts            delivery costs used when no packaging-unit-level cost matches
 * @param nonDeliveryDays          country-level non-delivery day configuration
 * @param packagingUnitsByContexts context-dependent packaging unit mappings
 * @param cutoffTimes              order cutoff schedule
 * @param fallbackTransitTime      transit time used when no packaging-unit-level time matches
 * @param customAttributes         free-form tenant-defined attributes
 */
public record FacilityConnection(
        ConnectionId id,
        Integer version,
        Instant created,
        Instant lastModified,
        FacilityId sourceFacilityRef,
        ConnectionTarget target,
        String carrierKey,
        String carrierName,
        List<ConnectionContext> context,
        List<DeliveryCost> fallbackCosts,
        List<NonDeliveryDaysPerCountry> nonDeliveryDays,
        List<PackagingUnitsByContext> packagingUnitsByContexts,
        CutoffTimes cutoffTimes,
        CarrierTransitTime fallbackTransitTime,
        Map<String, Object> customAttributes
) {}
