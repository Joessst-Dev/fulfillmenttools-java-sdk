package de.joesst.dev.fulfillmenttools.reservations;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.ReservationId;
import de.joesst.dev.fulfillmenttools.id.TenantArticleId;

import java.time.Instant;
import java.util.Map;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * Represents a stock reservation in fulfillmenttools.
 *
 * <p>Maps to the {@code Reservation} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id               unique reservation identifier; required
 * @param version          optimistic-concurrency version; required
 * @param created          creation timestamp in UTC
 * @param lastModified     last-modification timestamp in UTC
 * @param facilityRef      reference to the facility holding the stock; required
 * @param tenantArticleId  tenant-specific article identifier; required
 * @param quantity         reserved quantity; required
 * @param host             the host system that owns this reservation; required
 * @param relatedRefs      cross-domain entity references; required
 * @param customAttributes arbitrary key-value metadata provided by the tenant;
 *                         kept as {@code Map<String,Object>} because the spec defines
 *                         this field as a free-form {@code object}
 */
public record Reservation(
        ReservationId id,
        Integer version,
        Instant created,
        Instant lastModified,
        FacilityId facilityRef,
        TenantArticleId tenantArticleId,
        int quantity,
        ReservationHost host,
        RelatedRefs relatedRefs,
        CustomAttributes customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private ReservationId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private FacilityId facilityRef;
        private TenantArticleId tenantArticleId;
        private int quantity;
        private ReservationHost host;
        private RelatedRefs relatedRefs;
        private CustomAttributes customAttributes;

        public Builder id(ReservationId id) {
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

        public Builder tenantArticleId(TenantArticleId tenantArticleId) {
            this.tenantArticleId = tenantArticleId;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder host(ReservationHost host) {
            this.host = host;
            return this;
        }

        public Builder relatedRefs(RelatedRefs relatedRefs) {
            this.relatedRefs = relatedRefs;
            return this;
        }

        public Builder customAttributes(CustomAttributes customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        public Reservation build() {
            return new Reservation(id, version, created, lastModified, facilityRef, tenantArticleId,
                    quantity, host, relatedRefs, customAttributes);
        }
    }
}
