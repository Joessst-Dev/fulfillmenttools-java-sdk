package de.joesst.dev.fulfillmenttools.tags;

import java.time.Instant;
import java.util.List;

public record Tag(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        List<String> allowedValues
) {}
