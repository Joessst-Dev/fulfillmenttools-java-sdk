package de.joesst.dev.fulfillmenttools.parcels;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.facilities.FacilityAddress;
import de.joesst.dev.fulfillmenttools.id.CarrierId;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.ParcelId;
import de.joesst.dev.fulfillmenttools.orders.ConsumerAddress;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents a fulfillmenttools parcel, which tracks the physical shipment of goods
 * from a facility to a consumer via a carrier.
 *
 * <p>Received as the payload of {@code PARCEL_CARRIER_ACKNOWLEDGED},
 * {@code PARCEL_CARRIER_CREATED}, {@code PARCEL_CARRIER_FAILED},
 * {@code PARCEL_CARRIER_REQUESTED}, and
 * {@code PARCEL_TRACK_AND_TRACE_STATUS_UPDATED} events.
 *
 * @param id the platform-generated {@link ParcelId}
 * @param version the optimistic-locking version
 * @param created the timestamp when this parcel was created
 * @param lastModified the timestamp of the last modification
 * @param status the current lifecycle status
 * @param carrierRef the {@link CarrierId} of the carrier handling this parcel
 * @param carrierProduct the carrier's product or service code
 * @param processRef reference to the operative process this parcel belongs to
 * @param facilityRef the {@link FacilityId} of the dispatching facility
 * @param sender the sender address (facility address)
 * @param recipient the recipient consumer address
 * @param loadUnitRefs references to the load units (containers) in this parcel
 * @param shipmentRef reference to the shipment this parcel belongs to
 * @param tenantOrderId the tenant's external order reference
 * @param tenantParcelId the tenant's external parcel identifier
 * @param shortId a human-readable short identifier
 * @param anonymized whether this parcel has been anonymized for GDPR compliance
 * @param carrierInformation raw carrier-specific information
 * @param customAttributes tenant-defined key/value extension attributes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Parcel(
        ParcelId id,
        Integer version,
        Instant created,
        Instant lastModified,
        ParcelStatus status,
        CarrierId carrierRef,
        String carrierProduct,
        String processRef,
        FacilityId facilityRef,
        FacilityAddress sender,
        ConsumerAddress recipient,
        List<String> loadUnitRefs,
        String shipmentRef,
        String tenantOrderId,
        String tenantParcelId,
        String shortId,
        Boolean anonymized,
        Map<String, Object> carrierInformation,
        CustomAttributes customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private ParcelId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private ParcelStatus status;
        private CarrierId carrierRef;
        private String carrierProduct;
        private String processRef;
        private FacilityId facilityRef;
        private FacilityAddress sender;
        private ConsumerAddress recipient;
        private List<String> loadUnitRefs;
        private String shipmentRef;
        private String tenantOrderId;
        private String tenantParcelId;
        private String shortId;
        private Boolean anonymized;
        private Map<String, Object> carrierInformation;
        private CustomAttributes customAttributes;

        public Builder id(ParcelId id) {
            this.id = id;
            return this;
        }

        public Builder version(Integer version) {
            this.version = version;
            return this;
        }

        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        public Builder lastModified(Instant lastModified) {
            this.lastModified = lastModified;
            return this;
        }

        public Builder status(ParcelStatus status) {
            this.status = status;
            return this;
        }

        public Builder carrierRef(CarrierId carrierRef) {
            this.carrierRef = carrierRef;
            return this;
        }

        public Builder carrierProduct(String carrierProduct) {
            this.carrierProduct = carrierProduct;
            return this;
        }

        public Builder processRef(String processRef) {
            this.processRef = processRef;
            return this;
        }

        public Builder facilityRef(FacilityId facilityRef) {
            this.facilityRef = facilityRef;
            return this;
        }

        public Builder sender(FacilityAddress sender) {
            this.sender = sender;
            return this;
        }

        public Builder recipient(ConsumerAddress recipient) {
            this.recipient = recipient;
            return this;
        }

        public Builder loadUnitRefs(List<String> loadUnitRefs) {
            this.loadUnitRefs = loadUnitRefs;
            return this;
        }

        public Builder shipmentRef(String shipmentRef) {
            this.shipmentRef = shipmentRef;
            return this;
        }

        public Builder tenantOrderId(String tenantOrderId) {
            this.tenantOrderId = tenantOrderId;
            return this;
        }

        public Builder tenantParcelId(String tenantParcelId) {
            this.tenantParcelId = tenantParcelId;
            return this;
        }

        public Builder shortId(String shortId) {
            this.shortId = shortId;
            return this;
        }

        public Builder anonymized(Boolean anonymized) {
            this.anonymized = anonymized;
            return this;
        }

        public Builder carrierInformation(Map<String, Object> carrierInformation) {
            this.carrierInformation = carrierInformation;
            return this;
        }

        public Builder customAttributes(CustomAttributes customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        public Parcel build() {
            return new Parcel(id, version, created, lastModified, status, carrierRef, carrierProduct,
                    processRef, facilityRef, sender, recipient, loadUnitRefs, shipmentRef,
                    tenantOrderId, tenantParcelId, shortId, anonymized, carrierInformation,
                    customAttributes);
        }
    }
}
