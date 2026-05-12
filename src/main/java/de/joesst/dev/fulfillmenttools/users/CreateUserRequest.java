package de.joesst.dev.fulfillmenttools.users;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class CreateUserRequest {

    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String locale;
    private final List<Map<String, Object>> assignedRoles;

    private CreateUserRequest(Builder builder) {
        this.username = Objects.requireNonNull(builder.username, "username must not be null");
        this.password = Objects.requireNonNull(builder.password, "password must not be null");
        this.firstName = Objects.requireNonNull(builder.firstName, "firstName must not be null");
        this.lastName = Objects.requireNonNull(builder.lastName, "lastName must not be null");
        this.email = builder.email;
        this.locale = builder.locale;
        this.assignedRoles = builder.assignedRoles;
    }

    public String username() { return username; }
    public String password() { return password; }
    public String firstName() { return firstName; }
    public String lastName() { return lastName; }
    public String email() { return email; }
    public String locale() { return locale; }
    public List<Map<String, Object>> assignedRoles() { return assignedRoles; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String username;
        private String password;
        private String firstName;
        private String lastName;
        private String email;
        private String locale;
        private List<Map<String, Object>> assignedRoles;

        public Builder username(String username) { this.username = username; return this; }
        public Builder password(String password) { this.password = password; return this; }
        public Builder firstName(String firstName) { this.firstName = firstName; return this; }
        public Builder lastName(String lastName) { this.lastName = lastName; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder locale(String locale) { this.locale = locale; return this; }
        public Builder assignedRoles(List<Map<String, Object>> assignedRoles) { this.assignedRoles = assignedRoles; return this; }

        public CreateUserRequest build() { return new CreateUserRequest(this); }
    }
}
