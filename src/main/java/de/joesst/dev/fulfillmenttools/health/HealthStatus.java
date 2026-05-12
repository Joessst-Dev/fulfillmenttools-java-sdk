package de.joesst.dev.fulfillmenttools.health;

import java.util.Map;

public record HealthStatus(String status, Map<String, Object> details) {}
