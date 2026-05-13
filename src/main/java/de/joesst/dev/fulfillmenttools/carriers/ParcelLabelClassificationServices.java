package de.joesst.dev.fulfillmenttools.carriers;

/**
 * Service flags that apply to a parcel label classification.
 *
 * <p>Thread-safety: records are immutable and therefore unconditionally
 * thread-safe.
 *
 * @param bulkyGoods {@code true} when this classification applies to bulky-goods
 *                   shipments; defaults to {@code false} in the API
 */
public record ParcelLabelClassificationServices(
        Boolean bulkyGoods
) {}
