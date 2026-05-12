package de.joesst.dev.fulfillmenttools.health;

import java.util.List;

public record HealthResult(String status, List<HealthDependencyStatus> dependencies) {}
