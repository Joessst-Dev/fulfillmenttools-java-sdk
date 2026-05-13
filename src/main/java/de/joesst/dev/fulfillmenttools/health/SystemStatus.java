package de.joesst.dev.fulfillmenttools.health;

/**
 * Overall system status of the fulfillmenttools platform.
 *
 * @param status the status of the system (e.g., "UP", "DOWN", "DEGRADED").
 */
public record SystemStatus(String status) {}
