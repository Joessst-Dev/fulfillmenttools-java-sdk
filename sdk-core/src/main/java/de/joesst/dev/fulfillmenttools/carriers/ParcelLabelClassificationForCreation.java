package de.joesst.dev.fulfillmenttools.carriers;

import java.util.Map;

/**
 * A parcel label classification sent in a {@link CreateCarrierRequest}.
 *
 * <p>Mirrors {@link ParcelLabelClassification} but omits the {@code name} field,
 * which is assigned by the platform and not accepted on creation.
 *
 * <p>Thread-safety: records are immutable and therefore unconditionally
 * thread-safe.
 *
 * @param nameLocalized locale-keyed display names (e.g. {@code {"en":"Standard","de":"Standard"}})
 * @param dimensions    physical parcel dimensions; required by the API
 * @param services      optional service flags for this classification
 */
public record ParcelLabelClassificationForCreation(
        Map<String, String> nameLocalized,
        ParcelDimensions dimensions,
        ParcelLabelClassificationServices services
) {}
