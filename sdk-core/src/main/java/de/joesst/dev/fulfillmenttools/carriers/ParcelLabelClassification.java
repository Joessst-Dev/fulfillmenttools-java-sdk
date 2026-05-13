package de.joesst.dev.fulfillmenttools.carriers;

import java.util.Map;

/**
 * A parcel label classification returned by the fulfillmenttools Carriers API.
 *
 * <p>Represents one entry in the {@code parcelLabelClassifications} array of a
 * {@link Carrier}. The {@code nameLocalized} and {@code dimensions} fields are
 * required by the API; {@code services} and {@code name} are optional.
 *
 * <p>Thread-safety: records are immutable and therefore unconditionally
 * thread-safe.
 *
 * @param nameLocalized locale-keyed display names (e.g. {@code {"en":"Standard","de":"Standard"}})
 * @param dimensions    physical parcel dimensions; required by the API
 * @param services      optional service flags for this classification
 * @param name          internal name assigned by the platform
 */
public record ParcelLabelClassification(
        Map<String, String> nameLocalized,
        ParcelDimensions dimensions,
        ParcelLabelClassificationServices services,
        String name
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private Map<String, String> nameLocalized;
        private ParcelDimensions dimensions;
        private ParcelLabelClassificationServices services;
        private String name;

        public Builder nameLocalized(Map<String, String> nameLocalized) { this.nameLocalized = nameLocalized; return this; }
        public Builder dimensions(ParcelDimensions dimensions) { this.dimensions = dimensions; return this; }
        public Builder services(ParcelLabelClassificationServices services) { this.services = services; return this; }
        public Builder name(String name) { this.name = name; return this; }

        public ParcelLabelClassification build() {
            return new ParcelLabelClassification(nameLocalized, dimensions, services, name);
        }
    }
}
