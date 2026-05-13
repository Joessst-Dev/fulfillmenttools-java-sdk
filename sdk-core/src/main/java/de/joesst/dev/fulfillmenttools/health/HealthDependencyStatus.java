package de.joesst.dev.fulfillmenttools.health;

/**
 * Health status of a single dependency within the fulfillmenttools platform.
 *
 * @param name the name of the dependency (e.g., "database", "cache", "queue").
 * @param status the status of the dependency (e.g., "UP", "DOWN", "DEGRADED").
 */
public record HealthDependencyStatus(String name, String status) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String name;
        private String status;

        public Builder name(String name) { this.name = name; return this; }
        public Builder status(String status) { this.status = status; return this; }

        public HealthDependencyStatus build() {
            return new HealthDependencyStatus(name, status);
        }
    }
}
