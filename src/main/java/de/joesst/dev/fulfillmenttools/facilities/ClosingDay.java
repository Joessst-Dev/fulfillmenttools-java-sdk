package de.joesst.dev.fulfillmenttools.facilities;

import java.time.Instant;

public record ClosingDay(Instant date, String reason, String recurrence) {}
