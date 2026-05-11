package de.joesst.dev.fulfillmenttools.reservations;

import java.util.Objects;

public final class CreateReservationRequest {

    private final String facilityRef;
    private final String tenantArticleId;
    private final int quantity;

    private CreateReservationRequest(Builder builder) {
        this.facilityRef = Objects.requireNonNull(builder.facilityRef, "facilityRef must not be null");
        this.tenantArticleId = Objects.requireNonNull(builder.tenantArticleId, "tenantArticleId must not be null");
        this.quantity = builder.quantity;
    }

    public String facilityRef() { return facilityRef; }
    public String tenantArticleId() { return tenantArticleId; }
    public int quantity() { return quantity; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String facilityRef;
        private String tenantArticleId;
        private int quantity;

        public Builder facilityRef(String facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder tenantArticleId(String tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }
        public Builder quantity(int quantity) { this.quantity = quantity; return this; }

        public CreateReservationRequest build() { return new CreateReservationRequest(this); }
    }
}
