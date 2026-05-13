package de.joesst.dev.fulfillmenttools.users;

import java.util.List;
import java.util.Objects;

/**
 * Request object for updating an existing fulfillmenttools user.
 *
 * <p>Construct via the fluent builder:
 * <pre>{@code
 * UpdateUserRequest request = UpdateUserRequest.builder()
 *     .version(2)
 *     .firstName("Bob")
 *     .assignedRoles(List.of(new AssignedRole("role-ref", null, null)))
 *     .build();
 * }</pre>
 *
 * <p>Only non-null fields are included in the update action sent to the API.
 */
public final class UpdateUserRequest {

    private final Integer version;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final String locale;
    private final List<AssignedRole> assignedRoles;

    private UpdateUserRequest(Builder builder) {
        this.version = Objects.requireNonNull(builder.version, "version must not be null");
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.password = builder.password;
        this.locale = builder.locale;
        this.assignedRoles = builder.assignedRoles;
    }

    /**
     * Returns the optimistic-locking version number; required.
     *
     * @return the optimistic-locking version number
     */
    public Integer version() { return version; }

    /**
     * Returns the new given name.
     *
     * @return the new given name, or {@code null} if not being updated
     */
    public String firstName() { return firstName; }

    /**
     * Returns the new family name.
     *
     * @return the new family name, or {@code null} if not being updated
     */
    public String lastName() { return lastName; }

    /**
     * Returns the new email address.
     *
     * @return the new email address, or {@code null} if not being updated
     */
    public String email() { return email; }

    /**
     * Returns the new password.
     *
     * @return the new password, or {@code null} if not being updated
     */
    public String password() { return password; }

    /**
     * Returns the new preferred locale.
     *
     * @return the new preferred locale, or {@code null} if not being updated
     */
    public String locale() { return locale; }

    /**
     * Returns the new role assignments.
     *
     * @return the new role assignments, or {@code null} if not being updated
     */
    public List<AssignedRole> assignedRoles() { return assignedRoles; }

    /**
     * Returns a new builder for constructing {@code UpdateUserRequest} instances.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link UpdateUserRequest}.
     */
    public static final class Builder {

        /**
         * Creates a new Builder.
         */
        public Builder() {}

        private Integer version;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String locale;
        private List<AssignedRole> assignedRoles;

        /**
         * Sets the optimistic-locking version; required.
         *
         * @param version the optimistic-locking version
         * @return this builder
         */
        public Builder version(Integer version) { this.version = version; return this; }

        /**
         * Sets the new given name.
         *
         * @param firstName the new given name
         * @return this builder
         */
        public Builder firstName(String firstName) { this.firstName = firstName; return this; }

        /**
         * Sets the new family name.
         *
         * @param lastName the new family name
         * @return this builder
         */
        public Builder lastName(String lastName) { this.lastName = lastName; return this; }

        /**
         * Sets the new email address.
         *
         * @param email the new email address
         * @return this builder
         */
        public Builder email(String email) { this.email = email; return this; }

        /**
         * Sets the new password.
         *
         * @param password the new password
         * @return this builder
         */
        public Builder password(String password) { this.password = password; return this; }

        /**
         * Sets the new preferred locale.
         *
         * @param locale the new preferred locale
         * @return this builder
         */
        public Builder locale(String locale) { this.locale = locale; return this; }

        /**
         * Sets the new role assignments.
         *
         * @param assignedRoles the new role assignments
         * @return this builder
         */
        public Builder assignedRoles(List<AssignedRole> assignedRoles) { this.assignedRoles = assignedRoles; return this; }

        /**
         * Builds a validated {@link UpdateUserRequest}.
         *
         * @return a validated {@link UpdateUserRequest}
         * @throws NullPointerException if {@code version} is not set
         */
        public UpdateUserRequest build() { return new UpdateUserRequest(this); }
    }
}
