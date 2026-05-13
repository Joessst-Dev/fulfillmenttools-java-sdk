package de.joesst.dev.fulfillmenttools.facilities;

import java.time.Instant;

/**
 * Represents a period when a facility is closed and unavailable for fulfillment operations.
 *
 * @param date the date or start date of the closing period
 * @param reason human-readable reason for the closure
 * @param recurrence optional recurrence pattern (e.g. "WEEKLY", "MONTHLY", "YEARLY")
 */
public record ClosingDay(Instant date, String reason, String recurrence) {}
