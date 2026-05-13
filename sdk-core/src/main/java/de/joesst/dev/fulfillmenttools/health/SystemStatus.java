package de.joesst.dev.fulfillmenttools.health;

/**
 * Overall system status of the fulfillmenttools platform.
 *
 * @param status the status of the system (e.g., "UP", "DOWN", "DEGRADED").
 */
public record SystemStatus(String status) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String status;

        public Builder status(String status) { this.status = status; return this; }

        public SystemStatus build() {
            return new SystemStatus(status);
        }
    }
}
