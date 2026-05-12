package de.joesst.dev.fulfillmenttools.health;

import java.util.concurrent.CompletableFuture;

public interface HealthClient {

    HealthResult health();

    CompletableFuture<HealthResult> healthAsync();

    SystemStatus status();

    CompletableFuture<SystemStatus> statusAsync();
}
