package de.joesst.dev.fulfillmenttools.carriers;

import java.time.Instant;

/**
 * Represents a carrier (shipping provider) in fulfillmenttools.
 */
public record Carrier(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String key,
        String name,
        String status,
        Double defaultParcelWidthInCm,
        Double defaultParcelLengthInCm,
        Double defaultParcelHeightInCm,
        Double defaultParcelWeightInGram,
        String deliveryType,
        String lifecycle,
        String logoUrl,
        Boolean productValueNeeded
) {}
