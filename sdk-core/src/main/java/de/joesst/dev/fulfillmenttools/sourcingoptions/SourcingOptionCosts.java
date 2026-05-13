package de.joesst.dev.fulfillmenttools.sourcingoptions;

/**
 * Aggregated cost breakdown for a sourcing option.
 *
 * <p>Maps to the {@code SourcingOptionCosts} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param currency The ISO 4217 currency code (e.g. {@code EUR}).
 * @param total    Total cost value.
 */
public record SourcingOptionCosts(
        String currency,
        Double total
) {}
