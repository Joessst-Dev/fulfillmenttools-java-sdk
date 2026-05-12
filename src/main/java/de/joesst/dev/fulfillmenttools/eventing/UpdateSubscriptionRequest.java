package de.joesst.dev.fulfillmenttools.eventing;

public final class UpdateSubscriptionRequest {

    private final String callbackUrl;
    private final String status;

    private UpdateSubscriptionRequest(Builder builder) {
        this.callbackUrl = builder.callbackUrl;
        this.status = builder.status;
    }

    public String callbackUrl() { return callbackUrl; }
    public String status() { return status; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String callbackUrl;
        private String status;

        public Builder callbackUrl(String callbackUrl) { this.callbackUrl = callbackUrl; return this; }
        public Builder status(String status) { this.status = status; return this; }

        public UpdateSubscriptionRequest build() { return new UpdateSubscriptionRequest(this); }
    }
}
