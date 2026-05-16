package de.joesst.dev.fulfillmenttools.facilityconnections;

import de.joesst.dev.fulfillmenttools.id.ConnectionId;
import de.joesst.dev.fulfillmenttools.id.FacilityId;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

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
        CustomAttributes customAttributes
) {

    /**
     * Returns a builder for constructing a {@link FacilityConnection}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link FacilityConnection}.
     */
    public static final class Builder {

        private ConnectionId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private FacilityId sourceFacilityRef;
        private ConnectionTarget target;
        private String carrierKey;
        private String carrierName;
        private List<ConnectionContext> context;
        private List<DeliveryCost> fallbackCosts;
        private List<NonDeliveryDaysPerCountry> nonDeliveryDays;
        private List<PackagingUnitsByContext> packagingUnitsByContexts;
        private CutoffTimes cutoffTimes;
        private CarrierTransitTime fallbackTransitTime;
        private CustomAttributes customAttributes;

        private Builder() {}

        /**
         * @param id unique {@link ConnectionId} of the connection
         * @return this builder
         */
        public Builder id(ConnectionId id) {
            this.id = id;
            return this;
        }

        /**
         * @param version optimistic-locking version number
         * @return this builder
         */
        public Builder version(Integer version) {
            this.version = version;
            return this;
        }

        /**
         * @param created creation timestamp
         * @return this builder
         */
        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        /**
         * @param lastModified last modification timestamp
         * @return this builder
         */
        public Builder lastModified(Instant lastModified) {
            this.lastModified = lastModified;
            return this;
        }

        /**
         * @param sourceFacilityRef {@link FacilityId} reference to the source facility
         * @return this builder
         */
        public Builder sourceFacilityRef(FacilityId sourceFacilityRef) {
            this.sourceFacilityRef = sourceFacilityRef;
            return this;
        }

        /**
         * @param target typed target of this connection
         * @return this builder
         */
        public Builder target(ConnectionTarget target) {
            this.target = target;
            return this;
        }

        /**
         * @param carrierKey carrier identifier key
         * @return this builder
         */
        public Builder carrierKey(String carrierKey) {
            this.carrierKey = carrierKey;
            return this;
        }

        /**
         * @param carrierName human-readable carrier name
         * @return this builder
         */
        public Builder carrierName(String carrierName) {
            this.carrierName = carrierName;
            return this;
        }

        /**
         * @param context scoping contexts that restrict when this connection is used
         * @return this builder
         */
        public Builder context(List<ConnectionContext> context) {
            this.context = context;
            return this;
        }

        /**
         * @param fallbackCosts delivery costs used when no packaging-unit-level cost matches
         * @return this builder
         */
        public Builder fallbackCosts(List<DeliveryCost> fallbackCosts) {
            this.fallbackCosts = fallbackCosts;
            return this;
        }

        /**
         * @param nonDeliveryDays country-level non-delivery day configuration
         * @return this builder
         */
        public Builder nonDeliveryDays(List<NonDeliveryDaysPerCountry> nonDeliveryDays) {
            this.nonDeliveryDays = nonDeliveryDays;
            return this;
        }

        /**
         * @param packagingUnitsByContexts context-dependent packaging unit mappings
         * @return this builder
         */
        public Builder packagingUnitsByContexts(List<PackagingUnitsByContext> packagingUnitsByContexts) {
            this.packagingUnitsByContexts = packagingUnitsByContexts;
            return this;
        }

        /**
         * @param cutoffTimes order cutoff schedule
         * @return this builder
         */
        public Builder cutoffTimes(CutoffTimes cutoffTimes) {
            this.cutoffTimes = cutoffTimes;
            return this;
        }

        /**
         * @param fallbackTransitTime transit time used when no packaging-unit-level time matches
         * @return this builder
         */
        public Builder fallbackTransitTime(CarrierTransitTime fallbackTransitTime) {
            this.fallbackTransitTime = fallbackTransitTime;
            return this;
        }

        /**
         * @param customAttributes free-form tenant-defined attributes
         * @return this builder
         */
        public Builder customAttributes(CustomAttributes customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        /**
         * Builds a {@link FacilityConnection}.
         *
         * @return a new instance
         */
        public FacilityConnection build() {
            return new FacilityConnection(
                    id, version, created, lastModified, sourceFacilityRef, target,
                    carrierKey, carrierName, context, fallbackCosts, nonDeliveryDays,
                    packagingUnitsByContexts, cutoffTimes, fallbackTransitTime, customAttributes);
        }
    }
}
