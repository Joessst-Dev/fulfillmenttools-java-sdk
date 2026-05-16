package de.joesst.dev.fulfillmenttools.facilitygroups;

import de.joesst.dev.fulfillmenttools.id.FacilityGroupId;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents a facility group in the fulfillmenttools platform.
 *
 * @param id the facility group's unique {@link FacilityGroupId}
 * @param version the current version of the resource
 * @param created the timestamp when the facility group was created
 * @param lastModified the timestamp when the facility group was last updated
 * @param tenantFacilityGroupId the tenant-scoped identifier for this group
 * @param facilityRefs the list of {@link FacilityId} references in this group
 * @param nameLocalized the localized names of the group by language code
 * @param name the default name of the group
 * @param customAttributes additional attributes as a key-value map
 */
public record FacilityGroup(
        FacilityGroupId id,
        Integer version,
        Instant created,
        Instant lastModified,
        String tenantFacilityGroupId,
        List<FacilityId> facilityRefs,
        Map<String, String> nameLocalized,
        String name,
        CustomAttributes customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private FacilityGroupId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private String tenantFacilityGroupId;
        private List<FacilityId> facilityRefs;
        private Map<String, String> nameLocalized;
        private String name;
        private CustomAttributes customAttributes;

        public Builder id(FacilityGroupId id) {
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

        public Builder tenantFacilityGroupId(String tenantFacilityGroupId) {
            this.tenantFacilityGroupId = tenantFacilityGroupId;
            return this;
        }

        public Builder facilityRefs(List<FacilityId> facilityRefs) {
            this.facilityRefs = facilityRefs;
            return this;
        }

        public Builder nameLocalized(Map<String, String> nameLocalized) {
            this.nameLocalized = nameLocalized;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder customAttributes(CustomAttributes customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        public FacilityGroup build() {
            return new FacilityGroup(id, version, created, lastModified, tenantFacilityGroupId,
                    facilityRefs, nameLocalized, name, customAttributes);
        }
    }
}
