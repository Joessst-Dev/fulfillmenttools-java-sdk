package de.joesst.dev.fulfillmenttools.facilitydiscounts;

import de.joesst.dev.fulfillmenttools.id.FacilityDiscountId;
import de.joesst.dev.fulfillmenttools.id.FacilityId;

import java.time.Instant;
import java.util.List;

/**
 * Represents a discount applied to a facility.
 *
 * @param id the discount's unique {@link FacilityDiscountId}
 * @param version the current version of the resource
 * @param created the timestamp when the discount was created
 * @param lastModified the timestamp when the discount was last updated
 * @param facilityRef the {@link FacilityId} reference to the facility this discount applies to
 * @param type the type of discount
 * @param priority the priority of this discount relative to other discounts
 * @param discount the discount value specification (absolute or relative)
 * @param context the context conditions under which this discount applies
 */
public record FacilityDiscount(
        FacilityDiscountId id,
        Integer version,
        Instant created,
        Instant lastModified,
        FacilityId facilityRef,
        String type,
        Integer priority,
        FacilityDiscountValue discount,
        List<FacilityDiscountContext> context
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private FacilityDiscountId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private FacilityId facilityRef;
        private String type;
        private Integer priority;
        private FacilityDiscountValue discount;
        private List<FacilityDiscountContext> context;

        public Builder id(FacilityDiscountId id) {
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

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder priority(Integer priority) {
            this.priority = priority;
            return this;
        }

        public Builder discount(FacilityDiscountValue discount) {
            this.discount = discount;
            return this;
        }

        public Builder context(List<FacilityDiscountContext> context) {
            this.context = context;
            return this;
        }

        public FacilityDiscount build() {
            return new FacilityDiscount(id, version, created, lastModified, facilityRef, type,
                    priority, discount, context);
        }
    }
}
