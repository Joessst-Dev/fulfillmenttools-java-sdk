package de.joesst.dev.fulfillmenttools.carriers;

import de.joesst.dev.fulfillmenttools.id.CarrierId;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents a carrier in the fulfillmenttools platform.
 *
 * @param id the carrier's unique {@link CarrierId}
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
        CarrierId id,
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
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private CarrierId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private String key;
        private String name;
        private String status;
        private Double defaultParcelWidthInCm;
        private Double defaultParcelLengthInCm;
        private Double defaultParcelHeightInCm;
        private Double defaultParcelWeightInGram;
        private String deliveryType;
        private String lifecycle;
        private String logoUrl;
        private Boolean productValueNeeded;
        private Map<String, Object> credentials;
        private List<ParcelLabelClassification> parcelLabelClassifications;

        private Builder() {}

        public Builder id(CarrierId id) { this.id = id; return this; }
        public Builder version(Integer version) { this.version = version; return this; }
        public Builder created(Instant created) { this.created = created; return this; }
        public Builder lastModified(Instant lastModified) { this.lastModified = lastModified; return this; }
        public Builder key(String key) { this.key = key; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder defaultParcelWidthInCm(Double defaultParcelWidthInCm) { this.defaultParcelWidthInCm = defaultParcelWidthInCm; return this; }
        public Builder defaultParcelLengthInCm(Double defaultParcelLengthInCm) { this.defaultParcelLengthInCm = defaultParcelLengthInCm; return this; }
        public Builder defaultParcelHeightInCm(Double defaultParcelHeightInCm) { this.defaultParcelHeightInCm = defaultParcelHeightInCm; return this; }
        public Builder defaultParcelWeightInGram(Double defaultParcelWeightInGram) { this.defaultParcelWeightInGram = defaultParcelWeightInGram; return this; }
        public Builder deliveryType(String deliveryType) { this.deliveryType = deliveryType; return this; }
        public Builder lifecycle(String lifecycle) { this.lifecycle = lifecycle; return this; }
        public Builder logoUrl(String logoUrl) { this.logoUrl = logoUrl; return this; }
        public Builder productValueNeeded(Boolean productValueNeeded) { this.productValueNeeded = productValueNeeded; return this; }
        public Builder credentials(Map<String, Object> credentials) { this.credentials = credentials; return this; }
        public Builder parcelLabelClassifications(List<ParcelLabelClassification> parcelLabelClassifications) { this.parcelLabelClassifications = parcelLabelClassifications; return this; }

        public Carrier build() {
            return new Carrier(id, version, created, lastModified, key, name, status, defaultParcelWidthInCm, defaultParcelLengthInCm, defaultParcelHeightInCm, defaultParcelWeightInGram, deliveryType, lifecycle, logoUrl, productValueNeeded, credentials, parcelLabelClassifications);
        }
    }
}
