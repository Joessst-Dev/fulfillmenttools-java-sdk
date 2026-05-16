package de.joesst.dev.fulfillmenttools.inbound;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.InboundDeliveryId;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * Represents a fulfillmenttools inbound delivery (called {@code InboundProcess} in the API),
 * which tracks the receipt of goods into a facility from a supplier.
 *
 * <p>Received as the payload of {@code INBOUND_DELIVERY_FINISHED},
 * {@code INBOUND_DELIVERY_ON_HOLD}, {@code INBOUND_DELIVERY_RECEIVED}, and
 * {@code INBOUND_DELIVERY_RELEASED} events.
 *
 * @param id the platform-generated {@link InboundDeliveryId}
 * @param version the optimistic-locking version
 * @param created the timestamp when this inbound delivery was created
 * @param lastModified the timestamp of the last modification
 * @param facilityRef the {@link FacilityId} of the receiving facility
 * @param inboundDate the expected inbound date(s)
 * @param origin origin / supplier information
 * @param status the current lifecycle status
 * @param onHold whether this inbound delivery is currently on hold
 * @param anonymized whether this inbound delivery has been anonymized for GDPR compliance
 * @param scannableCodes scannable codes associated with this delivery
 * @param receipts receipts recorded against this delivery
 * @param tenantInboundProcessId the tenant's external inbound process identifier
 * @param customAttributes tenant-defined key/value extension attributes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record InboundDelivery(
        InboundDeliveryId id,
        Integer version,
        Instant created,
        Instant lastModified,
        FacilityId facilityRef,
        List<Instant> inboundDate,
        List<InboundProcessOrigin> origin,
        InboundDeliveryStatus status,
        Boolean onHold,
        Boolean anonymized,
        List<String> scannableCodes,
        List<InboundReceipt> receipts,
        String tenantInboundProcessId,
        CustomAttributes customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private InboundDeliveryId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private FacilityId facilityRef;
        private List<Instant> inboundDate;
        private List<InboundProcessOrigin> origin;
        private InboundDeliveryStatus status;
        private Boolean onHold;
        private Boolean anonymized;
        private List<String> scannableCodes;
        private List<InboundReceipt> receipts;
        private String tenantInboundProcessId;
        private CustomAttributes customAttributes;

        public Builder id(InboundDeliveryId id) {
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

        public Builder facilityRef(FacilityId facilityRef) {
            this.facilityRef = facilityRef;
            return this;
        }

        public Builder inboundDate(List<Instant> inboundDate) {
            this.inboundDate = inboundDate;
            return this;
        }

        public Builder origin(List<InboundProcessOrigin> origin) {
            this.origin = origin;
            return this;
        }

        public Builder status(InboundDeliveryStatus status) {
            this.status = status;
            return this;
        }

        public Builder onHold(Boolean onHold) {
            this.onHold = onHold;
            return this;
        }

        public Builder anonymized(Boolean anonymized) {
            this.anonymized = anonymized;
            return this;
        }

        public Builder scannableCodes(List<String> scannableCodes) {
            this.scannableCodes = scannableCodes;
            return this;
        }

        public Builder receipts(List<InboundReceipt> receipts) {
            this.receipts = receipts;
            return this;
        }

        public Builder tenantInboundProcessId(String tenantInboundProcessId) {
            this.tenantInboundProcessId = tenantInboundProcessId;
            return this;
        }

        public Builder customAttributes(CustomAttributes customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        public InboundDelivery build() {
            return new InboundDelivery(id, version, created, lastModified, facilityRef, inboundDate,
                    origin, status, onHold, anonymized, scannableCodes, receipts,
                    tenantInboundProcessId, customAttributes);
        }
    }
}
