package de.joesst.dev.fulfillmenttools.inbound;

import java.util.Objects;

public final class CreateStowJobRequest {

    private final String facilityRef;

    private CreateStowJobRequest(Builder builder) {
        this.facilityRef = Objects.requireNonNull(builder.facilityRef, "facilityRef must not be null");
    }

    public String facilityRef() { return facilityRef; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String facilityRef;

        public Builder facilityRef(String facilityRef) { this.facilityRef = facilityRef; return this; }

        public CreateStowJobRequest build() { return new CreateStowJobRequest(this); }
    }
}
