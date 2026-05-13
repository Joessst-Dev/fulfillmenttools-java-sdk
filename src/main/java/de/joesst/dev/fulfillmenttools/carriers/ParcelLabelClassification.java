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
) {}
