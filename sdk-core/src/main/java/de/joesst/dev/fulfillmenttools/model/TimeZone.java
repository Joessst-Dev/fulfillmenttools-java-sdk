package de.joesst.dev.fulfillmenttools.model;

/**
 * Time zone information for a facility or location.
 *
 * @param timeZoneId the IANA time zone identifier (e.g., "Europe/Berlin").
 * @param timeZoneName the human-readable name of the time zone.
 * @param offsetInSeconds the offset from UTC in seconds, or {@code null} if unknown.
 * @param source the source system or method used to determine this time zone information.
 */
public record TimeZone(String timeZoneId, String timeZoneName, Double offsetInSeconds, String source) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String timeZoneId;
        private String timeZoneName;
        private Double offsetInSeconds;
        private String source;

        public Builder timeZoneId(String timeZoneId) { this.timeZoneId = timeZoneId; return this; }
        public Builder timeZoneName(String timeZoneName) { this.timeZoneName = timeZoneName; return this; }
        public Builder offsetInSeconds(Double offsetInSeconds) { this.offsetInSeconds = offsetInSeconds; return this; }
        public Builder source(String source) { this.source = source; return this; }

        public TimeZone build() {
            return new TimeZone(timeZoneId, timeZoneName, offsetInSeconds, source);
        }
    }
}
