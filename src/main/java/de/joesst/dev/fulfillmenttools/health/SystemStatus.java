package de.joesst.dev.fulfillmenttools.health;

import java.util.Map;

public record SystemStatus(String status, String version, Map<String, Object> components) {}
