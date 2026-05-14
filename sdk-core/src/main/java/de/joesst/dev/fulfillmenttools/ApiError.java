package de.joesst.dev.fulfillmenttools;

/**
 * Structured error details returned by the fulfillmenttools API.
 *
 * @param description A detailed error message describing what went wrong.
 * @param summary A human-readable error summary.
 * @param requestVersion The request format version used in the error response.
 * @param version The error response format version.
 */
public record ApiError(
        String description,
        String summary,
        int requestVersion,
        int version
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String description;
        private String summary;
        private int requestVersion;
        private int version;

        public Builder description(String description) { this.description = description; return this; }
        public Builder summary(String summary) { this.summary = summary; return this; }
        public Builder requestVersion(int requestVersion) { this.requestVersion = requestVersion; return this; }
        public Builder version(int version) { this.version = version; return this; }

        public ApiError build() {
            return new ApiError(description, summary, requestVersion, version);
        }
    }
}
