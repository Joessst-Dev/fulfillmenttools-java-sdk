package de.joesst.dev.fulfillmenttools.packjobs;

public final class UpdatePackJobRequest {

    private final String status;

    private UpdatePackJobRequest(Builder builder) {
        this.status = builder.status;
    }

    public String status() { return status; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String status;

        public Builder status(String status) { this.status = status; return this; }

        public UpdatePackJobRequest build() { return new UpdatePackJobRequest(this); }
    }
}
