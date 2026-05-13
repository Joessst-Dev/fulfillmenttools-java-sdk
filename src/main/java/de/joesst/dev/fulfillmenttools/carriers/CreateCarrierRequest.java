package de.joesst.dev.fulfillmenttools.carriers;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Request to create a new carrier.
 */
public final class CreateCarrierRequest {

    private final String key;
    private final String name;
    private final String status;
    private final String logoUrl;
    private final String deliveryType;
    private final String lifecycle;
    private final Double defaultParcelWidthInCm;
    private final Double defaultParcelLengthInCm;
    private final Double defaultParcelHeightInCm;
    private final Double defaultParcelWeightInGram;
    private final Boolean productValueNeeded;
    private final Map<String, Object> credentials;
    private final List<ParcelLabelClassificationForCreation> parcelLabelClassifications;

    private CreateCarrierRequest(Builder builder) {
        this.key = Objects.requireNonNull(builder.key, "key must not be null");
        this.name = Objects.requireNonNull(builder.name, "name must not be null");
        this.status = builder.status;
        this.logoUrl = builder.logoUrl;
        this.deliveryType = builder.deliveryType;
        this.lifecycle = builder.lifecycle;
        this.defaultParcelWidthInCm = builder.defaultParcelWidthInCm;
        this.defaultParcelLengthInCm = builder.defaultParcelLengthInCm;
        this.defaultParcelHeightInCm = builder.defaultParcelHeightInCm;
        this.defaultParcelWeightInGram = builder.defaultParcelWeightInGram;
        this.productValueNeeded = builder.productValueNeeded;
        this.credentials = builder.credentials;
        this.parcelLabelClassifications = builder.parcelLabelClassifications;
    }

    /**
     * Returns the carrier key.
     * @return the carrier key
     */
    public String key() { return key; }

    /**
     * Returns the carrier name.
     * @return the carrier name
     */
    public String name() { return name; }

    /**
     * Returns the carrier status.
     * @return the carrier status
     */
    public String status() { return status; }

    /**
     * Returns the logo URL.
     * @return the logo URL
     */
    public String logoUrl() { return logoUrl; }

    /**
     * Returns the delivery type.
     * @return the delivery type
     */
    public String deliveryType() { return deliveryType; }

    /**
     * Returns the lifecycle state.
     * @return the lifecycle state
     */
    public String lifecycle() { return lifecycle; }

    /**
     * Returns the default parcel width in centimeters.
     * @return the default parcel width
     */
    public Double defaultParcelWidthInCm() { return defaultParcelWidthInCm; }

    /**
     * Returns the default parcel length in centimeters.
     * @return the default parcel length
     */
    public Double defaultParcelLengthInCm() { return defaultParcelLengthInCm; }

    /**
     * Returns the default parcel height in centimeters.
     * @return the default parcel height
     */
    public Double defaultParcelHeightInCm() { return defaultParcelHeightInCm; }

    /**
     * Returns the default parcel weight in grams.
     * @return the default parcel weight
     */
    public Double defaultParcelWeightInGram() { return defaultParcelWeightInGram; }

    /**
     * Returns whether product value is needed.
     * @return whether product value is needed
     */
    public Boolean productValueNeeded() { return productValueNeeded; }

    /**
     * Returns the carrier credentials.
     * @return the credentials map
     */
    public Map<String, Object> credentials() { return credentials; }

    /**
     * Returns the parcel label classifications.
     * @return the list of parcel label classifications
     */
    public List<ParcelLabelClassificationForCreation> parcelLabelClassifications() { return parcelLabelClassifications; }

    /**
     * Creates a new builder for CreateCarrierRequest.
     * @return a new builder instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for constructing CreateCarrierRequest instances.
     */
    public static final class Builder {

        /** Creates a new Builder instance. */
        public Builder() {}

        private String key;
        private String name;
        private String status;
        private String logoUrl;
        private String deliveryType;
        private String lifecycle;
        private Double defaultParcelWidthInCm;
        private Double defaultParcelLengthInCm;
        private Double defaultParcelHeightInCm;
        private Double defaultParcelWeightInGram;
        private Boolean productValueNeeded;
        private Map<String, Object> credentials;
        private List<ParcelLabelClassificationForCreation> parcelLabelClassifications;

        /**
         * Sets the carrier key.
         * @param key the carrier key
         * @return this builder
         */
        public Builder key(String key) { this.key = key; return this; }

        /**
         * Sets the carrier name.
         * @param name the carrier name
         * @return this builder
         */
        public Builder name(String name) { this.name = name; return this; }

        /**
         * Sets the carrier status.
         * @param status the carrier status
         * @return this builder
         */
        public Builder status(String status) { this.status = status; return this; }

        /**
         * Sets the logo URL.
         * @param logoUrl the logo URL
         * @return this builder
         */
        public Builder logoUrl(String logoUrl) { this.logoUrl = logoUrl; return this; }

        /**
         * Sets the delivery type.
         * @param deliveryType the delivery type
         * @return this builder
         */
        public Builder deliveryType(String deliveryType) { this.deliveryType = deliveryType; return this; }

        /**
         * Sets the lifecycle state.
         * @param lifecycle the lifecycle state
         * @return this builder
         */
        public Builder lifecycle(String lifecycle) { this.lifecycle = lifecycle; return this; }

        /**
         * Sets the default parcel width in centimeters.
         * @param v the default parcel width
         * @return this builder
         */
        public Builder defaultParcelWidthInCm(Double v) { this.defaultParcelWidthInCm = v; return this; }

        /**
         * Sets the default parcel length in centimeters.
         * @param v the default parcel length
         * @return this builder
         */
        public Builder defaultParcelLengthInCm(Double v) { this.defaultParcelLengthInCm = v; return this; }

        /**
         * Sets the default parcel height in centimeters.
         * @param v the default parcel height
         * @return this builder
         */
        public Builder defaultParcelHeightInCm(Double v) { this.defaultParcelHeightInCm = v; return this; }

        /**
         * Sets the default parcel weight in grams.
         * @param v the default parcel weight
         * @return this builder
         */
        public Builder defaultParcelWeightInGram(Double v) { this.defaultParcelWeightInGram = v; return this; }

        /**
         * Sets whether product value is needed.
         * @param v whether product value is needed
         * @return this builder
         */
        public Builder productValueNeeded(Boolean v) { this.productValueNeeded = v; return this; }

        /**
         * Sets the carrier credentials.
         * @param credentials the credentials map
         * @return this builder
         */
        public Builder credentials(Map<String, Object> credentials) { this.credentials = credentials; return this; }

        /**
         * Sets the parcel label classifications.
         * @param v the list of parcel label classifications
         * @return this builder
         */
        public Builder parcelLabelClassifications(List<ParcelLabelClassificationForCreation> v) { this.parcelLabelClassifications = v; return this; }

        /**
         * Builds and returns a CreateCarrierRequest instance.
         * @return a new CreateCarrierRequest
         */
        public CreateCarrierRequest build() { return new CreateCarrierRequest(this); }
    }
}
