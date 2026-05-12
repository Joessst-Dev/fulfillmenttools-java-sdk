package de.joesst.dev.fulfillmenttools.orders;

import java.time.Instant;

public record OrderUpdateDetail(
        Instant created,
        OrderUpdateDetailChanges changes,
        String comment,
        String user
) {}
