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

    /**
     * Returns the login username.
     * @return the username; never {@code null}
     */
    public String username() { return username; }

    /**
     * Returns the initial password.
     * @return the password; never {@code null}
     */
    public String password() { return password; }

    /**
     * Returns the user's given name.
     * @return the first name; never {@code null}
     */
    public String firstName() { return firstName; }

    /**
     * Returns the user's family name.
     * @return the last name; never {@code null}
     */
    public String lastName() { return lastName; }

    /**
     * Returns the user's email address.
     * @return the email, or {@code null} if not provided
     */
    public String email() { return email; }

    /**
     * Returns the user's preferred locale.
     * @return the locale, or {@code null} if not provided
     */
    public String locale() { return locale; }

    /**
     * Returns the role assignments for the new user.
     * @return the assigned roles, or {@code null} if not provided
     */
    public List<AssignedRole> assignedRoles() { return assignedRoles; }

    /**
     * Creates a new builder for constructing a {@link CreateUserRequest}.
     * @return a new builder instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link CreateUserRequest}.
     */
    public static final class Builder {

        /** Creates a new Builder instance. */
        public Builder() {}

        private String username;
        private String password;
        private String firstName;
        private String lastName;
        private String email;
        private String locale;
        private List<AssignedRole> assignedRoles;

        /**
         * Sets the login username (required).
         * @param username the login username
         * @return this builder
         */
        public Builder username(String username) { this.username = username; return this; }

        /**
         * Sets the initial password (required).
         * @param password the initial password
         * @return this builder
         */
        public Builder password(String password) { this.password = password; return this; }

        /**
         * Sets the user's given name (required).
         * @param firstName the given name
         * @return this builder
         */
        public Builder firstName(String firstName) { this.firstName = firstName; return this; }

        /**
         * Sets the user's family name (required).
         * @param lastName the family name
         * @return this builder
         */
        public Builder lastName(String lastName) { this.lastName = lastName; return this; }

        /**
         * Sets the user's email address.
         * @param email the email address
         * @return this builder
         */
        public Builder email(String email) { this.email = email; return this; }

        /**
         * Sets the user's preferred locale.
         * @param locale the preferred locale
         * @return this builder
         */
        public Builder locale(String locale) { this.locale = locale; return this; }

        /**
         * Sets the role assignments for the new user.
         * @param assignedRoles the role assignments
         * @return this builder
         */
        public Builder assignedRoles(List<AssignedRole> assignedRoles) { this.assignedRoles = assignedRoles; return this; }

        /**
         * Builds and returns a validated {@link CreateUserRequest}.
         * @return a new request instance
         * @throws NullPointerException if any required field is not set
         */
        public CreateUserRequest build() { return new CreateUserRequest(this); }
    }
}
