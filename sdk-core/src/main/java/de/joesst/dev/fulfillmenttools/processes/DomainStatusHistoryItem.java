package de.joesst.dev.fulfillmenttools.processes;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

/**
 * A single entry in the domain-status change history of a process.
 *
 * <p>{@code timestamp} and {@code domain} and {@code domainEntityProcessStatus} and
 * {@code domainRef} are required per the OpenAPI spec; {@code statusChangeReasonKey} is optional.
 *
 * <p>Maps to the {@code DomainStatusHistoryItem} schema in the fulfillmenttools OpenAPI
 * specification.
 *
 * @param timestamp                the instant at which the status change occurred
 * @param domain                   the type of domain entity that changed status
 * @param domainEntityProcessStatus the new status value
 * @param domainRef                the identifier of the domain entity
 * @param statusChangeReasonKey    optional key describing the reason for the status change
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DomainStatusHistoryItem(
        Instant timestamp,
        DomainType domain,
        DomainStatus domainEntityProcessStatus,
        String domainRef,
        String statusChangeReasonKey
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private Instant timestamp;
        private DomainType domain;
        private DomainStatus domainEntityProcessStatus;
        private String domainRef;
        private String statusChangeReasonKey;

        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder domain(DomainType domain) {
            this.domain = domain;
            return this;
        }

        public Builder domainEntityProcessStatus(DomainStatus domainEntityProcessStatus) {
            this.domainEntityProcessStatus = domainEntityProcessStatus;
            return this;
        }

        public Builder domainRef(String domainRef) {
            this.domainRef = domainRef;
            return this;
        }

        public Builder statusChangeReasonKey(String statusChangeReasonKey) {
            this.statusChangeReasonKey = statusChangeReasonKey;
            return this;
        }

        public DomainStatusHistoryItem build() {
            return new DomainStatusHistoryItem(timestamp, domain, domainEntityProcessStatus, domainRef, statusChangeReasonKey);
        }
    }
}
