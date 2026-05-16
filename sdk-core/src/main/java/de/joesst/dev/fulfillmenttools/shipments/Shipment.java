package de.joesst.dev.fulfillmenttools.shipments;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.ShipmentId;
import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.orders.ConsumerAddress;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents a fulfillmenttools shipment, which groups parcels dispatched to a consumer
 * under a single carrier booking.
 *
 * <p>Received as the payload of {@code SHIPMENT_CREATED} and
 * {@code SHIPMENT_UPDATED} events.
 *
 * @param id the platform-generated {@link ShipmentId}
 * @param version the optimistic-locking version
 * @param created the timestamp when this shipment was created
 * @param lastModified the timestamp of the last modification
 * @param status the current lifecycle status
 * @param facilityRef the {@link FacilityId} of the dispatching facility
 * @param targetTime the target dispatch time
 * @param orderDate the date the originating order was placed
 * @param hasActiveCarrier whether a carrier is currently assigned to this shipment
 * @param carrierRef reference to the carrier
 * @param carrierProduct the carrier product code
 * @param processId reference to the operative process
 * @param tenantOrderId the tenant's external order reference
 * @param shortId a human-readable short identifier
 * @param postalAddress the consumer's postal address
 * @param targetAddress the delivery target address
 * @param anonymized whether this shipment has been anonymized for GDPR compliance
 * @param tags tags attached to this shipment
 * @param customAttributes tenant-defined key/value extension attributes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Shipment(
        ShipmentId id,
        Integer version,
        Instant created,
        Instant lastModified,
        ShipmentStatus status,
        FacilityId facilityRef,
        Instant targetTime,
        Instant orderDate,
        Boolean hasActiveCarrier,
        String carrierRef,
        String carrierProduct,
        String processId,
        String tenantOrderId,
        String shortId,
        ConsumerAddress postalAddress,
        ConsumerAddress targetAddress,
        Boolean anonymized,
        List<TagReference> tags,
        Map<String, Object> customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private ShipmentId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private ShipmentStatus status;
        private FacilityId facilityRef;
        private Instant targetTime;
        private Instant orderDate;
        private Boolean hasActiveCarrier;
        private String carrierRef;
        private String carrierProduct;
        private String processId;
        private String tenantOrderId;
        private String shortId;
        private ConsumerAddress postalAddress;
        private ConsumerAddress targetAddress;
        private Boolean anonymized;
        private List<TagReference> tags;
        private Map<String, Object> customAttributes;

        public Builder id(ShipmentId id) {
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

        public Builder status(ShipmentStatus status) {
            this.status = status;
            return this;
        }

        public Builder facilityRef(FacilityId facilityRef) {
            this.facilityRef = facilityRef;
            return this;
        }

        public Builder targetTime(Instant targetTime) {
            this.targetTime = targetTime;
            return this;
        }

        public Builder orderDate(Instant orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public Builder hasActiveCarrier(Boolean hasActiveCarrier) {
            this.hasActiveCarrier = hasActiveCarrier;
            return this;
        }

        public Builder carrierRef(String carrierRef) {
            this.carrierRef = carrierRef;
            return this;
        }

        public Builder carrierProduct(String carrierProduct) {
            this.carrierProduct = carrierProduct;
            return this;
        }

        public Builder processId(String processId) {
            this.processId = processId;
            return this;
        }

        public Builder tenantOrderId(String tenantOrderId) {
            this.tenantOrderId = tenantOrderId;
            return this;
        }

        public Builder shortId(String shortId) {
            this.shortId = shortId;
            return this;
        }

        public Builder postalAddress(ConsumerAddress postalAddress) {
            this.postalAddress = postalAddress;
            return this;
        }

        public Builder targetAddress(ConsumerAddress targetAddress) {
            this.targetAddress = targetAddress;
            return this;
        }

        public Builder anonymized(Boolean anonymized) {
            this.anonymized = anonymized;
            return this;
        }

        public Builder tags(List<TagReference> tags) {
            this.tags = tags;
            return this;
        }

        public Builder customAttributes(Map<String, Object> customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        public Shipment build() {
            return new Shipment(id, version, created, lastModified, status, facilityRef,
                    targetTime, orderDate, hasActiveCarrier, carrierRef, carrierProduct,
                    processId, tenantOrderId, shortId, postalAddress, targetAddress,
                    anonymized, tags, customAttributes);
        }
    }
}
