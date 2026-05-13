package de.joesst.dev.fulfillmenttools.processes;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The most recent status of a specific domain entity tracked by a process.
 *
 * <p>All three fields are required per the OpenAPI spec.
 *
 * <p>Maps to the {@code LastDomainEntityStatusItem} schema in the fulfillmenttools OpenAPI
 * specification.
 *
 * @param domain   the type of domain entity
 * @param entityId the identifier of the domain entity
 * @param status   the current status of the entity
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record LastDomainEntityStatusItem(
        DomainType domain,
        String entityId,
        DomainStatus status
) {}
