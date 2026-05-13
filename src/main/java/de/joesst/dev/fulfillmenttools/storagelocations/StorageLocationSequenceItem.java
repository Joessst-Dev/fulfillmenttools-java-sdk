package de.joesst.dev.fulfillmenttools.storagelocations;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Represents a single entry in a storage location's running sequence, describing the
 * order in which locations are visited during picking or restow operations.
 *
 * <p>Maps to {@code StorageLocationSequenceItem} in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param type                       The sequence type. Known values: {@code PICKING_SEQUENCE},
 *                                   {@code RESTOW_SEQUENCE}.
 * @param nextStorageLocationRef     Reference to the next storage location in the sequence,
 *                                   or {@code null} if this is the last entry.
 * @param previousStorageLocationRef Reference to the previous storage location in the sequence,
 *                                   or {@code null} if this is the first entry.
 * @param score                      Deprecated ordering score. May be {@code null}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StorageLocationSequenceItem(
        String type,
        String nextStorageLocationRef,
        String previousStorageLocationRef,
        Double score
) {}
