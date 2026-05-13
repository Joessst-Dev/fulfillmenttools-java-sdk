package de.joesst.dev.fulfillmenttools.carriers;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents a carrier in the fulfillmenttools platform.
 *
 * @param id the carrier's unique identifier
 * @param version the current version of the carrier entity
 * @param created the timestamp when the carrier was created
 * @param lastModified the timestamp of the last modification
 * @param key the carrier's key
 * @param name the carrier's display name
 * @param status the carrier's status
 * @param defaultParcelWidthInCm the default parcel width in centimeters
 * @param defaultParcelLengthInCm the default parcel length in centimeters
 * @param defaultParcelHeightInCm the default parcel height in centimeters
 * @param defaultParcelWeightInGram the default parcel weight in grams
 * @param deliveryType the type of delivery
 * @param lifecycle the lifecycle state of the carrier
 * @param logoUrl the URL of the carrier's logo
 * @param productValueNeeded whether product value is needed for shipments
 * @param credentials authentication or configuration credentials for the carrier
 * @param parcelLabelClassifications list of available parcel label classifications
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
        Boolean productValueNeeded,
        Map<String, Object> credentials,
        List<ParcelLabelClassification> parcelLabelClassifications
) {}
