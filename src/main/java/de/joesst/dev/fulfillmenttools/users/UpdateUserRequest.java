package de.joesst.dev.fulfillmenttools.users;

public final class UpdateUserRequest {

    private final String firstName;
    private final String lastName;
    private final String status;

    private UpdateUserRequest(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.status = builder.status;
    }

    public String firstName() { return firstName; }
    public String lastName() { return lastName; }
    public String status() { return status; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String firstName;
        private String lastName;
        private String status;

        public Builder firstName(String firstName) { this.firstName = firstName; return this; }
        public Builder lastName(String lastName) { this.lastName = lastName; return this; }
        public Builder status(String status) { this.status = status; return this; }

        public UpdateUserRequest build() { return new UpdateUserRequest(this); }
    }
}
