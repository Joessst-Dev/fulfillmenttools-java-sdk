package de.joesst.dev.fulfillmenttools.inbound;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

/**
 * A reason for an external stock change operation, as sent in a create or update request.
 *
 * <p>Each reason can either be a standalone reason (supply {@code reasonLocalized}) or a
 * reference to a preconfigured reason (supply {@code id} and/or {@code tenantReasonId}).
 * When both {@code id} and {@code tenantReasonId} are specified, they must match.
 * {@code reasonLocalized} cannot be combined with {@code id} or {@code tenantReasonId}.
 *
 * <p>Maps to the {@code ExternalStockChangeReasonInput} schema in the fulfillmenttools
 * OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param reasonLocalized Localized reason texts keyed by locale (e.g. {@code en_US}).
 *                        Mutually exclusive with {@code id}/{@code tenantReasonId}.
 * @param id              ID of a preconfigured reason.
 * @param tenantReasonId  Tenant-defined identifier for a preconfigured reason.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExternalStockChangeReasonInput(
        Map<String, String> reasonLocalized,
        String id,
        String tenantReasonId
) {}
