package de.joesst.dev.fulfillmenttools.carriers;

import java.util.Map;
import java.util.Objects;

public final class UpdateCarrierRequest {

    private final Integer version;
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

    private UpdateCarrierRequest(Builder builder) {
        this.version = Objects.requireNonNull(builder.version, "version must not be null");
        this.name = builder.name;
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
    }

    public Integer version() { return version; }
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

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Integer version;
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

        public Builder version(Integer version) { this.version = version; return this; }
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

        public UpdateCarrierRequest build() { return new UpdateCarrierRequest(this); }
    }
}
