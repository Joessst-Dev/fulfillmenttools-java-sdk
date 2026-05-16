package de.joesst.dev.fulfillmenttools.expiryentities;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.ExpiryEntityId;

import java.time.Instant;

/**
 * Represents a fulfillmenttools expiry entity, which schedules a timed expiry trigger
 * against an operative process.
 *
 * <p>Received as the payload of {@code EXPIRY_ENTITY_CREATED}, {@code EXPIRY_ENTITY_UPDATED},
 * and {@code EXPIRY_ENTITY_EXPIRED} events.
 *
 * @param id the platform-generated {@link ExpiryEntityId}
 * @param version the optimistic-locking version
 * @param created the timestamp when this expiry entity was created
 * @param lastModified the timestamp of the last modification
 * @param processRef reference to the operative process this expiry entity is attached to
 * @param provisioningTime the time at which the expiry entity was provisioned
 * @param expiryTime the time at which the expiry trigger will fire
 * @param status the current lifecycle status
 * @param processVersion the version of the process at the time this entity was created
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExpiryEntity(
        ExpiryEntityId id,
        Integer version,
        Instant created,
        Instant lastModified,
        String processRef,
        Instant provisioningTime,
        Instant expiryTime,
        ExpiryEntityStatus status,
        Integer processVersion
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private ExpiryEntityId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private String processRef;
        private Instant provisioningTime;
        private Instant expiryTime;
        private ExpiryEntityStatus status;
        private Integer processVersion;

        public Builder id(ExpiryEntityId id) {
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

        public Builder provisioningTime(Instant provisioningTime) {
            this.provisioningTime = provisioningTime;
            return this;
        }

        public Builder expiryTime(Instant expiryTime) {
            this.expiryTime = expiryTime;
            return this;
        }

        public Builder status(ExpiryEntityStatus status) {
            this.status = status;
            return this;
        }

        public Builder processVersion(Integer processVersion) {
            this.processVersion = processVersion;
            return this;
        }

        public ExpiryEntity build() {
            return new ExpiryEntity(id, version, created, lastModified, processRef,
                    provisioningTime, expiryTime, status, processVersion);
        }
    }
}
