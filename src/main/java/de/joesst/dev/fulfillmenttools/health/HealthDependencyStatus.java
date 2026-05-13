package de.joesst.dev.fulfillmenttools.health;

/**
 * Health status of a single dependency within the fulfillmenttools platform.
 *
 * @param name the name of the dependency (e.g., "database", "cache", "queue").
 * @param status the status of the dependency (e.g., "UP", "DOWN", "DEGRADED").
 */
public record HealthDependencyStatus(String name, String status) {}
