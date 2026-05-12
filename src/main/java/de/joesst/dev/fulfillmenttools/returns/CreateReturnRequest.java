package de.joesst.dev.fulfillmenttools.returns;

import java.util.Objects;

public final class CreateReturnRequest {

    private final String facilityRef;
    private final String status;

    private CreateReturnRequest(Builder builder) {
        this.facilityRef = Objects.requireNonNull(builder.facilityRef, "facilityRef must not be null");
        this.status = builder.status;
    }

    public String facilityRef() { return facilityRef; }
    public String status() { return status; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String facilityRef;
        private String status;

        public Builder facilityRef(String facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder status(String status) { this.status = status; return this; }

        public CreateReturnRequest build() { return new CreateReturnRequest(this); }
    }
}
