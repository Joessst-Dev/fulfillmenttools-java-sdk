package de.joesst.dev.fulfillmenttools.packjobs;

import java.util.List;

/**
 * A stripped-down view of a packing source container attached to a pack job.
 *
 * <p>Maps to the {@code StrippedPackingSourceContainer} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                          Unique identifier of the source container.
 * @param codes                       Barcodes or scan codes that identify the container.
 * @param loadUnitRef                 Deprecated — use {@code previousModuleContainerInfo} instead.
 * @param previousModuleContainerInfo Information about the container from which this one was created.
 */
public record StrippedPackingSourceContainer(
        String id,
        List<String> codes,
        String loadUnitRef,
        PreviousModuleContainerInfo previousModuleContainerInfo
) {

    /**
     * Information about the container from which this packing source container was derived.
     *
     * <p>Maps to the {@code PreviousModuleContainerInfo} schema.
     *
     * @param containerRef The id of the previous container.
     * @param type         The type of the previous container: {@code LOAD_UNIT} or
     *                     {@code SERVICE_CONTAINER}.
     */
    public record PreviousModuleContainerInfo(
            String containerRef,
            String type
    ) {}
}
