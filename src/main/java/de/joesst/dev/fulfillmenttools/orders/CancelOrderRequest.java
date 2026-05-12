package de.joesst.dev.fulfillmenttools.orders;

import java.util.Objects;

public final class CancelOrderRequest {

    private final Integer version;
    private final String cancelationReasonId;

    private CancelOrderRequest(Builder builder) {
        this.version = Objects.requireNonNull(builder.version, "version must not be null");
        this.cancelationReasonId = builder.cancelationReasonId;
    }

    public Integer version() { return version; }
    public String cancelationReasonId() { return cancelationReasonId; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private Integer version;
        private String cancelationReasonId;

        public Builder version(Integer version) { this.version = version; return this; }
        public Builder cancelationReasonId(String cancelationReasonId) { this.cancelationReasonId = cancelationReasonId; return this; }

        public CancelOrderRequest build() { return new CancelOrderRequest(this); }
    }
}
