package de.joesst.dev.fulfillmenttools.inbound;

import java.util.List;
import java.util.Map;

/**
 * Additional information about a stock entry being created during an inbound stow operation.
 * Only applicable when the stow-from type is {@code UNREGISTERED}.
 *
 * <p>Maps to the {@code StowJobStockInformation} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param properties  Tracking properties for the stock, such as expiry dates. These describe
 *                    physical, identifiable properties of the stock — not metadata
 *                    (use {@code customAttributes} for that).
 * @param traitConfig Trait configuration entries that define which operational actions
 *                    the stock is available for.
 */
public record StowJobStockInformation(
        Map<String, String> properties,
        List<StorageLocationTraitConfigEntry> traitConfig
) {}
