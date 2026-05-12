package de.joesst.dev.fulfillmenttools.internal.orders;

import java.time.Instant;

record OrderUnlockActionBody(String name, Integer version, Instant targetTime) {
    OrderUnlockActionBody(Integer version, Instant targetTime) {
        this("UNLOCK", version, targetTime);
    }
}
