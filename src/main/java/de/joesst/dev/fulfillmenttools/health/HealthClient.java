package de.joesst.dev.fulfillmenttools.health;

import java.util.concurrent.CompletableFuture;

public interface HealthClient {

    HealthStatus health();

    CompletableFuture<HealthStatus> healthAsync();

    SystemStatus status();

    CompletableFuture<SystemStatus> statusAsync();
}
