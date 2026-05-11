package de.joesst.dev.fulfillmenttools.pickjobs;

public final class UpdatePickJobRequest {

    private final String status;

    private UpdatePickJobRequest(Builder builder) {
        this.status = builder.status;
    }

    public String status() { return status; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String status;

        public Builder status(String status) { this.status = status; return this; }

        public UpdatePickJobRequest build() { return new UpdatePickJobRequest(this); }
    }
}
