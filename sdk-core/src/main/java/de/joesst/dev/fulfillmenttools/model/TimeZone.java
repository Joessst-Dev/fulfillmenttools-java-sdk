package de.joesst.dev.fulfillmenttools.model;

/**
 * Time zone information for a facility or location.
 *
 * @param timeZoneId the IANA time zone identifier (e.g., "Europe/Berlin").
 * @param timeZoneName the human-readable name of the time zone.
 * @param offsetInSeconds the offset from UTC in seconds, or {@code null} if unknown.
 * @param source the source system or method used to determine this time zone information.
 */
public record TimeZone(String timeZoneId, String timeZoneName, Double offsetInSeconds, String source) {}
