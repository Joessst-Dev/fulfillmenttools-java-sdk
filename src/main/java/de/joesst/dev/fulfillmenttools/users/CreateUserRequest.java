package de.joesst.dev.fulfillmenttools.users;

import java.util.List;
import java.util.Objects;

/**
 * Request object for creating a new fulfillmenttools user.
 *
 * <p>Construct via the fluent builder:
 * <pre>{@code
 * CreateUserRequest request = CreateUserRequest.builder()
 *     .username("alice")
 *     .password("secret123")
 *     .firstName("Alice")
 *     .lastName("Smith")
 *     .email("alice@example.com")
 *     .assignedRoles(List.of(new AssignedRole("role-ref", null, null)))
 *     .build();
 * }</pre>
 */
public final class CreateUserRequest {

    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String locale;
    private final List<AssignedRole> assignedRoles;

    private CreateUserRequest(Builder builder) {
        this.username = Objects.requireNonNull(builder.username, "username must not be null");
        this.password = Objects.requireNonNull(builder.password, "password must not be null");
        this.firstName = Objects.requireNonNull(builder.firstName, "firstName must not be null");
        this.lastName = Objects.requireNonNull(builder.lastName, "lastName must not be null");
        this.email = builder.email;
        this.locale = builder.locale;
        this.assignedRoles = builder.assignedRoles;
    }

    /** @return the login username */
    public String username() { return username; }

    /** @return the initial password */
    public String password() { return password; }

    /** @return the user's given name */
    public String firstName() { return firstName; }

    /** @return the user's family name */
    public String lastName() { return lastName; }

    /** @return the user's email address, or {@code null} if not provided */
    public String email() { return email; }

    /** @return the user's preferred locale, or {@code null} if not provided */
    public String locale() { return locale; }

    /** @return the role assignments for the new user, or {@code null} if not provided */
    public List<AssignedRole> assignedRoles() { return assignedRoles; }

    /** @return a new {@link Builder} */
    public static Builder builder() { return new Builder(); }

    /** Fluent builder for {@link CreateUserRequest}. */
    public static final class Builder {

        private String username;
        private String password;
        private String firstName;
        private String lastName;
        private String email;
        private String locale;
        private List<AssignedRole> assignedRoles;

        /** @param username the login username; required */
        public Builder username(String username) { this.username = username; return this; }

        /** @param password the initial password; required */
        public Builder password(String password) { this.password = password; return this; }

        /** @param firstName the user's given name; required */
        public Builder firstName(String firstName) { this.firstName = firstName; return this; }

        /** @param lastName the user's family name; required */
        public Builder lastName(String lastName) { this.lastName = lastName; return this; }

        /** @param email the user's email address */
        public Builder email(String email) { this.email = email; return this; }

        /** @param locale the user's preferred locale */
        public Builder locale(String locale) { this.locale = locale; return this; }

        /** @param assignedRoles the role assignments for the new user */
        public Builder assignedRoles(List<AssignedRole> assignedRoles) { this.assignedRoles = assignedRoles; return this; }

        /**
         * @return a validated {@link CreateUserRequest}
         * @throws NullPointerException if any required field is not set
         */
        public CreateUserRequest build() { return new CreateUserRequest(this); }
    }
}
