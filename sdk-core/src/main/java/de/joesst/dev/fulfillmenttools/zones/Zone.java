package de.joesst.dev.fulfillmenttools.zones;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.ZoneId;

import java.util.Map;

/**
 * Represents a fulfillmenttools zone within a facility.
 *
 * <p>Zones partition a facility's physical space for routing and stock organisation purposes.
 * Received as the payload of {@code ZONE_CREATED} and {@code ZONE_DELETED} events.
 *
 * @param id the platform-generated {@link ZoneId}
 * @param facilityRef the {@link FacilityId} of the facility this zone belongs to
 * @param version the optimistic-locking version
 * @param name the human-readable name of the zone
 * @param score the routing score assigned to this zone
 * @param customAttributes tenant-defined key/value extension attributes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Zone(
        ZoneId id,
        FacilityId facilityRef,
        Integer version,
        String name,
        Double score,
        Map<String, Object> customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private ZoneId id;
        private FacilityId facilityRef;
        private Integer version;
        private String name;
        private Double score;
        private Map<String, Object> customAttributes;

        public Builder id(ZoneId id) {
            this.id = id;
            return this;
        }

        public Builder facilityRef(FacilityId facilityRef) {
            this.facilityRef = facilityRef;
            return this;
        }

        public Builder version(Integer version) {
            this.version = version;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder score(Double score) {
            this.score = score;
            return this;
        }

        public Builder customAttributes(Map<String, Object> customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        public Zone build() {
            return new Zone(id, facilityRef, version, name, score, customAttributes);
        }
    }
}
