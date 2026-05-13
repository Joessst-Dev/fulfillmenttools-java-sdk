package de.joesst.dev.fulfillmenttools.health;

import java.util.List;

/**
 * Complete health information for the fulfillmenttools platform, including overall status and dependency details.
 *
 * @param status the overall health status of the platform.
 * @param dependencies the status of individual dependencies that the platform depends on.
 */
public record HealthResult(String status, List<HealthDependencyStatus> dependencies) {}
