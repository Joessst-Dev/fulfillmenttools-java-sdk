package de.joesst.dev.fulfillmenttools.inbound;

public final class UpdateStowJobRequest {

    private final String status;

    private UpdateStowJobRequest(Builder builder) {
        this.status = builder.status;
    }

    public String status() { return status; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String status;

        public Builder status(String status) { this.status = status; return this; }

        public UpdateStowJobRequest build() { return new UpdateStowJobRequest(this); }
    }
}
