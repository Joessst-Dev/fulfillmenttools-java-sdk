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
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String name;
        private Double penalty;
        private Double score;

        public Builder name(String name) { this.name = name; return this; }
        public Builder penalty(Double penalty) { this.penalty = penalty; return this; }
        public Builder score(Double score) { this.score = score; return this; }

        public SourcingOptionRatingResult build() {
            return new SourcingOptionRatingResult(name, penalty, score);
        }
    }
}
