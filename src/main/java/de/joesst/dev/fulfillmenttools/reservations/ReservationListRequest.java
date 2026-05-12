package de.joesst.dev.fulfillmenttools.reservations;

public final class ReservationListRequest {

    private final Integer size;
    private final String after;

    private ReservationListRequest(Builder builder) {
        this.size = builder.size;
        this.after = builder.after;
    }

    public Integer size() { return size; }
    public String after() { return after; }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.after = this.after;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Integer size;
        private String after;

        public Builder size(Integer size) { this.size = size; return this; }
        public Builder after(String after) { this.after = after; return this; }

        public ReservationListRequest build() { return new ReservationListRequest(this); }
    }
}
