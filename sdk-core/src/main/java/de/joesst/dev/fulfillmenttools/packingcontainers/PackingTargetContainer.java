package de.joesst.dev.fulfillmenttools.packingcontainers;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.PackingTargetContainerId;

import java.util.List;
import java.util.Map;

/**
 * Represents a fulfillmenttools packing target container, which groups packed line items
 * within a pack job into a single physical or virtual container.
 *
 * <p>Received as the payload of {@code PACKING_TARGET_CONTAINER_CREATED_EVENT},
 * {@code PACKING_TARGET_CONTAINER_UPDATED_EVENT}, and
 * {@code PACKING_TARGET_CONTAINER_DELETED_EVENT} events.
 *
 * @param id the platform-generated {@link PackingTargetContainerId}
 * @param version the optimistic-locking version
 * @param facilityRef the {@link FacilityId} of the facility this container belongs to
 * @param packJobRef reference to the pack job this container belongs to
 * @param type the container type (physical or virtual)
 * @param name the human-readable container name
 * @param nameLocalized the localized container names
 * @param description a human-readable description
 * @param descriptionLocalized the localized descriptions
 * @param lineItems the packed line items within this container
 * @param shortId a human-readable short identifier
 * @param weightLimitInG the maximum weight capacity in grams
 * @param customAttributes tenant-defined key/value extension attributes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PackingTargetContainer(
        PackingTargetContainerId id,
        Integer version,
        FacilityId facilityRef,
        String packJobRef,
        PackingTargetContainerType type,
        String name,
        Map<String, String> nameLocalized,
        String description,
        Map<String, String> descriptionLocalized,
        List<PackingTargetContainerLineItem> lineItems,
        String shortId,
        Double weightLimitInG,
        Map<String, Object> customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private PackingTargetContainerId id;
        private Integer version;
        private FacilityId facilityRef;
        private String packJobRef;
        private PackingTargetContainerType type;
        private String name;
        private Map<String, String> nameLocalized;
        private String description;
        private Map<String, String> descriptionLocalized;
        private List<PackingTargetContainerLineItem> lineItems;
        private String shortId;
        private Double weightLimitInG;
        private Map<String, Object> customAttributes;

        public Builder id(PackingTargetContainerId id) {
            this.id = id;
            return this;
        }

        public Builder version(Integer version) {
            this.version = version;
            return this;
        }

        public Builder facilityRef(FacilityId facilityRef) {
            this.facilityRef = facilityRef;
            return this;
        }

        public Builder packJobRef(String packJobRef) {
            this.packJobRef = packJobRef;
            return this;
        }

        public Builder type(PackingTargetContainerType type) {
            this.type = type;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder nameLocalized(Map<String, String> nameLocalized) {
            this.nameLocalized = nameLocalized;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder descriptionLocalized(Map<String, String> descriptionLocalized) {
            this.descriptionLocalized = descriptionLocalized;
            return this;
        }

        public Builder lineItems(List<PackingTargetContainerLineItem> lineItems) {
            this.lineItems = lineItems;
            return this;
        }

        public Builder shortId(String shortId) {
            this.shortId = shortId;
            return this;
        }

        public Builder weightLimitInG(Double weightLimitInG) {
            this.weightLimitInG = weightLimitInG;
            return this;
        }

        public Builder customAttributes(Map<String, Object> customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        public PackingTargetContainer build() {
            return new PackingTargetContainer(id, version, facilityRef, packJobRef, type,
                    name, nameLocalized, description, descriptionLocalized, lineItems,
                    shortId, weightLimitInG, customAttributes);
        }
    }
}
