package de.joesst.dev.fulfillmenttools.servicejobs;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.ServiceJobId;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * Represents a fulfillmenttools service job, which models a configurable operative task
 * performed within a facility (e.g. gift wrapping, assembly, inspection).
 *
 * <p>Received as the payload of {@code SERVICE_JOB_CREATED} and
 * {@code SERVICE_JOB_FINISHED} events.
 *
 * @param id the platform-generated {@link ServiceJobId}
 * @param version the optimistic-locking version
 * @param created the timestamp when this service job was created
 * @param lastModified the timestamp of the last modification
 * @param processRef reference to the operative process this service job belongs to
 * @param facilityRef the {@link FacilityId} of the facility executing this service job
 * @param status the current lifecycle status
 * @param name the human-readable name
 * @param nameLocalized the localized names
 * @param customServiceRef reference to the custom service definition
 * @param targetTime the target completion time
 * @param lineItems the line items requiring service
 * @param shortId a human-readable short identifier
 * @param tenantOrderId the tenant's external order reference
 * @param description a human-readable description
 * @param descriptionLocalized the localized descriptions
 * @param anonymized whether this service job has been anonymized for GDPR compliance
 * @param executionTimeInMin the estimated execution time in minutes
 * @param customAttributes tenant-defined key/value extension attributes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ServiceJob(
        ServiceJobId id,
        Integer version,
        Instant created,
        Instant lastModified,
        String processRef,
        FacilityId facilityRef,
        ServiceJobStatus status,
        String name,
        Map<String, String> nameLocalized,
        String customServiceRef,
        Instant targetTime,
        List<ServiceJobLineItem> lineItems,
        String shortId,
        String tenantOrderId,
        String description,
        Map<String, String> descriptionLocalized,
        Boolean anonymized,
        Integer executionTimeInMin,
        CustomAttributes customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private ServiceJobId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private String processRef;
        private FacilityId facilityRef;
        private ServiceJobStatus status;
        private String name;
        private Map<String, String> nameLocalized;
        private String customServiceRef;
        private Instant targetTime;
        private List<ServiceJobLineItem> lineItems;
        private String shortId;
        private String tenantOrderId;
        private String description;
        private Map<String, String> descriptionLocalized;
        private Boolean anonymized;
        private Integer executionTimeInMin;
        private CustomAttributes customAttributes;

        public Builder id(ServiceJobId id) {
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

        public Builder processRef(String processRef) {
            this.processRef = processRef;
            return this;
        }

        public Builder facilityRef(FacilityId facilityRef) {
            this.facilityRef = facilityRef;
            return this;
        }

        public Builder status(ServiceJobStatus status) {
            this.status = status;
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

        public Builder customServiceRef(String customServiceRef) {
            this.customServiceRef = customServiceRef;
            return this;
        }

        public Builder targetTime(Instant targetTime) {
            this.targetTime = targetTime;
            return this;
        }

        public Builder lineItems(List<ServiceJobLineItem> lineItems) {
            this.lineItems = lineItems;
            return this;
        }

        public Builder shortId(String shortId) {
            this.shortId = shortId;
            return this;
        }

        public Builder tenantOrderId(String tenantOrderId) {
            this.tenantOrderId = tenantOrderId;
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

        public Builder anonymized(Boolean anonymized) {
            this.anonymized = anonymized;
            return this;
        }

        public Builder executionTimeInMin(Integer executionTimeInMin) {
            this.executionTimeInMin = executionTimeInMin;
            return this;
        }

        public Builder customAttributes(CustomAttributes customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        public ServiceJob build() {
            return new ServiceJob(id, version, created, lastModified, processRef, facilityRef,
                    status, name, nameLocalized, customServiceRef, targetTime, lineItems,
                    shortId, tenantOrderId, description, descriptionLocalized, anonymized,
                    executionTimeInMin, customAttributes);
        }
    }
}
