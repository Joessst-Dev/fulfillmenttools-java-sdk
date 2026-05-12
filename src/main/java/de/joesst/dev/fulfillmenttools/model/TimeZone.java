package de.joesst.dev.fulfillmenttools.model;

public record TimeZone(String timeZoneId, String timeZoneName, Double offsetInSeconds, String source) {}
