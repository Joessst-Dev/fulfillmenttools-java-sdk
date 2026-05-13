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

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String id;
        private List<String> codes;
        private String loadUnitRef;
        private PreviousModuleContainerInfo previousModuleContainerInfo;

        public Builder id(String id) { this.id = id; return this; }
        public Builder codes(List<String> codes) { this.codes = codes; return this; }
        public Builder loadUnitRef(String loadUnitRef) { this.loadUnitRef = loadUnitRef; return this; }
        public Builder previousModuleContainerInfo(PreviousModuleContainerInfo previousModuleContainerInfo) { this.previousModuleContainerInfo = previousModuleContainerInfo; return this; }

        public StrippedPackingSourceContainer build() {
            return new StrippedPackingSourceContainer(id, codes, loadUnitRef, previousModuleContainerInfo);
        }
    }

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
    ) {

        public static Builder builder() {
            return new Builder();
        }

        public static final class Builder {
            private Builder() {}

            private String containerRef;
            private String type;

            public Builder containerRef(String containerRef) { this.containerRef = containerRef; return this; }
            public Builder type(String type) { this.type = type; return this; }

            public PreviousModuleContainerInfo build() {
                return new PreviousModuleContainerInfo(containerRef, type);
            }
        }
    }
}
