package de.joesst.dev.fulfillmenttools.inbound;

import java.util.Map;

/**
 * The reason for an external stock change operation as returned by the API.
 *
 * <p>Maps to the {@code ExternalStockChangeReasonOutput} schema in the fulfillmenttools
 * OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param reasonLocalized Localized reason texts, keyed by locale (e.g. {@code en_US}).
 * @param tenantReasonId  Tenant-defined identifier for a preconfigured reason.
 */
public record ExternalStockChangeReason(
        Map<String, String> reasonLocalized,
        String tenantReasonId
) {}
