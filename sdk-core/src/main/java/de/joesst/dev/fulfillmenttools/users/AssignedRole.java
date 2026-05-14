package de.joesst.dev.fulfillmenttools.users;

import java.util.List;

/**
 * A role reference assigned to a user, optionally scoped by context limitations.
 *
 * @param ref                the role reference identifier; required
 * @param context            context limitations that scope this role assignment (preferred field)
 * @param contextLimitations context limitations that scope this role assignment
 *                           (deprecated; prefer {@code context})
 */
public record AssignedRole(
        String ref,
        List<ContextLimitation> context,
        List<ContextLimitation> contextLimitations
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String ref;
        private List<ContextLimitation> context;
        private List<ContextLimitation> contextLimitations;

        public Builder ref(String ref) {
            this.ref = ref;
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

        public AssignedRole build() {
            return new AssignedRole(ref, context, contextLimitations);
        }
    }
}
