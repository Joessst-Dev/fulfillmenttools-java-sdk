package de.joesst.dev.fulfillmenttools.tags;

import java.time.Instant;
import java.util.List;

/**
 * Represents a tag used to organize and categorize orders and items.
 *
 * <p>Maps to the {@code Tag} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id             Server-assigned unique identifier for this tag.
 * @param version        Optimistic-locking version counter.
 * @param created        Timestamp when this tag was created.
 * @param lastModified   Timestamp when this tag was last modified.
 * @param allowedValues  List of valid values that can be assigned to this tag.
 */
public record Tag(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        List<String> allowedValues
) {}
