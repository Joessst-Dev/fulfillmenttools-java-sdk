package de.joesst.dev.fulfillmenttools.returns;

public final class UpdateReturnRequest {

    private final String status;

    private UpdateReturnRequest(Builder builder) {
        this.status = builder.status;
    }

    public String status() { return status; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String status;

        public Builder status(String status) { this.status = status; return this; }

        public UpdateReturnRequest build() { return new UpdateReturnRequest(this); }
    }
}
