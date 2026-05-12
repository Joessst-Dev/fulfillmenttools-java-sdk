package de.joesst.dev.fulfillmenttools.users;

import java.util.Objects;

public final class CreateUserRequest {

    private final String email;
    private final String firstName;
    private final String lastName;

    private CreateUserRequest(Builder builder) {
        this.email = Objects.requireNonNull(builder.email, "email must not be null");
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
    }

    public String email() { return email; }
    public String firstName() { return firstName; }
    public String lastName() { return lastName; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String email;
        private String firstName;
        private String lastName;

        public Builder email(String email) { this.email = email; return this; }
        public Builder firstName(String firstName) { this.firstName = firstName; return this; }
        public Builder lastName(String lastName) { this.lastName = lastName; return this; }

        public CreateUserRequest build() { return new CreateUserRequest(this); }
    }
}
