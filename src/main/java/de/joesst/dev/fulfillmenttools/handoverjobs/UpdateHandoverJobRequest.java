package de.joesst.dev.fulfillmenttools.handoverjobs;

public final class UpdateHandoverJobRequest {

    private final String status;

    private UpdateHandoverJobRequest(Builder builder) {
        this.status = builder.status;
    }

    public String status() { return status; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String status;

        public Builder status(String status) { this.status = status; return this; }

        public UpdateHandoverJobRequest build() { return new UpdateHandoverJobRequest(this); }
    }
}
