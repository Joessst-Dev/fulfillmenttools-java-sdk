package de.joesst.dev.fulfillmenttools.pickjobs;

import java.util.List;

/**
 * Identifies the physical storage location from which an article should be picked.
 *
 * <p>Maps to the {@code Location} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param locationRef    The id of the location.
 * @param scannableCodes Barcodes that may be scanned at this location.
 */
public record PickJobStockLocation(
        String locationRef,
        List<String> scannableCodes
) {}
