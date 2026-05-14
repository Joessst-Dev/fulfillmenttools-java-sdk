package de.joesst.dev.fulfillmenttools.health;

import java.util.List;

/**
 * Complete health information for the fulfillmenttools platform, including overall status and dependency details.
 *
 * @param status the overall health status of the platform.
 * @param dependencies the status of individual dependencies that the platform depends on.
 */
public record HealthResult(String status, List<HealthDependencyStatus> dependencies) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String status;
        private List<HealthDependencyStatus> dependencies;

        public Builder status(String status) { this.status = status; return this; }
        public Builder dependencies(List<HealthDependencyStatus> dependencies) { this.dependencies = dependencies; return this; }

        public HealthResult build() {
            return new HealthResult(status, dependencies);
        }
    }
}
