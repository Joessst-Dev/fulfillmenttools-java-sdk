package de.joesst.dev.fulfillmenttools.users;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class UpdateUserRequest {

    private final Integer version;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final String locale;
    private final List<Map<String, Object>> assignedRoles;
    private final Map<String, Object> customAttributes;

    private UpdateUserRequest(Builder builder) {
        this.version = Objects.requireNonNull(builder.version, "version must not be null");
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.password = builder.password;
        this.locale = builder.locale;
        this.assignedRoles = builder.assignedRoles;
        this.customAttributes = builder.customAttributes;
    }

    public Integer version() { return version; }
    public String firstName() { return firstName; }
    public String lastName() { return lastName; }
    public String email() { return email; }
    public String password() { return password; }
    public String locale() { return locale; }
    public List<Map<String, Object>> assignedRoles() { return assignedRoles; }
    public Map<String, Object> customAttributes() { return customAttributes; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Integer version;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String locale;
        private List<Map<String, Object>> assignedRoles;
        private Map<String, Object> customAttributes;

        public Builder version(Integer version) { this.version = version; return this; }
        public Builder firstName(String firstName) { this.firstName = firstName; return this; }
        public Builder lastName(String lastName) { this.lastName = lastName; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder password(String password) { this.password = password; return this; }
        public Builder locale(String locale) { this.locale = locale; return this; }
        public Builder assignedRoles(List<Map<String, Object>> assignedRoles) { this.assignedRoles = assignedRoles; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public UpdateUserRequest build() { return new UpdateUserRequest(this); }
    }
}
