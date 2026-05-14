package de.joesst.dev.fulfillmenttools.users;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * A role assignment with optional context limitations.
 *
 * <p>{@code UserRole} is used within {@link CustomClaims} (deprecated) and in the
 * deprecated {@code roles} field of a user creation request.
 *
 * @param name               the role name; required
 * @param context            context limitations scoping this role (preferred field)
 * @param contextLimitations context limitations scoping this role (deprecated; prefer {@code context})
 * @param facilities         legacy list of facility IDs (deprecated)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserRole(
        String name,
        List<ContextLimitation> context,
        List<ContextLimitation> contextLimitations,
        List<String> facilities
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String name;
        private List<ContextLimitation> context;
        private List<ContextLimitation> contextLimitations;
        private List<String> facilities;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder context(List<ContextLimitation> context) {
            this.context = context;
            return this;
        }

        public Builder contextLimitations(List<ContextLimitation> contextLimitations) {
            this.contextLimitations = contextLimitations;
            return this;
        }

        public Builder facilities(List<String> facilities) {
            this.facilities = facilities;
            return this;
        }

        public UserRole build() {
            return new UserRole(name, context, contextLimitations, facilities);
        }
    }
}
