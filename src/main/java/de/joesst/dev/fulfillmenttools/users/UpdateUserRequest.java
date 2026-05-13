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

    /** @return the optimistic-locking version number; required */
    public Integer version() { return version; }

    /** @return the new given name, or {@code null} if not being updated */
    public String firstName() { return firstName; }

    /** @return the new family name, or {@code null} if not being updated */
    public String lastName() { return lastName; }

    /** @return the new email address, or {@code null} if not being updated */
    public String email() { return email; }

    /** @return the new password, or {@code null} if not being updated */
    public String password() { return password; }

    /** @return the new preferred locale, or {@code null} if not being updated */
    public String locale() { return locale; }

    /** @return the new role assignments, or {@code null} if not being updated */
    public List<AssignedRole> assignedRoles() { return assignedRoles; }

    /** @return a new {@link Builder} */
    public static Builder builder() { return new Builder(); }

    /** Fluent builder for {@link UpdateUserRequest}. */
    public static final class Builder {

        private Integer version;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String locale;
        private List<AssignedRole> assignedRoles;

        /** @param version the optimistic-locking version; required */
        public Builder version(Integer version) { this.version = version; return this; }

        /** @param firstName the new given name */
        public Builder firstName(String firstName) { this.firstName = firstName; return this; }

        /** @param lastName the new family name */
        public Builder lastName(String lastName) { this.lastName = lastName; return this; }

        /** @param email the new email address */
        public Builder email(String email) { this.email = email; return this; }

        /** @param password the new password */
        public Builder password(String password) { this.password = password; return this; }

        /** @param locale the new preferred locale */
        public Builder locale(String locale) { this.locale = locale; return this; }

        /** @param assignedRoles the new role assignments */
        public Builder assignedRoles(List<AssignedRole> assignedRoles) { this.assignedRoles = assignedRoles; return this; }

        /**
         * @return a validated {@link UpdateUserRequest}
         * @throws NullPointerException if {@code version} is not set
         */
        public UpdateUserRequest build() { return new UpdateUserRequest(this); }
    }
}
