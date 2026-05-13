package de.joesst.dev.fulfillmenttools.carriers;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    public String key() { return key; }
    public String name() { return name; }
    public String status() { return status; }
    public String logoUrl() { return logoUrl; }
    public String deliveryType() { return deliveryType; }
    public String lifecycle() { return lifecycle; }
    public Double defaultParcelWidthInCm() { return defaultParcelWidthInCm; }
    public Double defaultParcelLengthInCm() { return defaultParcelLengthInCm; }
    public Double defaultParcelHeightInCm() { return defaultParcelHeightInCm; }
    public Double defaultParcelWeightInGram() { return defaultParcelWeightInGram; }
    public Boolean productValueNeeded() { return productValueNeeded; }
    public Map<String, Object> credentials() { return credentials; }
    public List<ParcelLabelClassificationForCreation> parcelLabelClassifications() { return parcelLabelClassifications; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

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

        public Builder key(String key) { this.key = key; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder logoUrl(String logoUrl) { this.logoUrl = logoUrl; return this; }
        public Builder deliveryType(String deliveryType) { this.deliveryType = deliveryType; return this; }
        public Builder lifecycle(String lifecycle) { this.lifecycle = lifecycle; return this; }
        public Builder defaultParcelWidthInCm(Double v) { this.defaultParcelWidthInCm = v; return this; }
        public Builder defaultParcelLengthInCm(Double v) { this.defaultParcelLengthInCm = v; return this; }
        public Builder defaultParcelHeightInCm(Double v) { this.defaultParcelHeightInCm = v; return this; }
        public Builder defaultParcelWeightInGram(Double v) { this.defaultParcelWeightInGram = v; return this; }
        public Builder productValueNeeded(Boolean v) { this.productValueNeeded = v; return this; }
        public Builder credentials(Map<String, Object> credentials) { this.credentials = credentials; return this; }
        public Builder parcelLabelClassifications(List<ParcelLabelClassificationForCreation> v) { this.parcelLabelClassifications = v; return this; }

        public CreateCarrierRequest build() { return new CreateCarrierRequest(this); }
    }
}
