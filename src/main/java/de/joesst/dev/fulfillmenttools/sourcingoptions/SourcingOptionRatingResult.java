package de.joesst.dev.fulfillmenttools.sourcingoptions;

/**
 * A single rating criterion result contributing to the overall penalty score of a sourcing option.
 *
 * <p>Maps to the {@code SourcingOptionRatingResult} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param name    The name of the rating criterion (e.g. {@code DISTANCE}, {@code COST}).
 * @param penalty The penalty value assigned by this criterion.
 * @param score   The raw score before penalty transformation.
 */
public record SourcingOptionRatingResult(
        String name,
        Double penalty,
        Double score
) {}
