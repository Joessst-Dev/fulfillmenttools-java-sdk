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
) {}
