package de.joesst.dev.fulfillmenttools.tags;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * A search query for filtering tags used in {@link TagSearchRequest}.
 *
 * <p>Provides fluent builder methods to define matching criteria on tag ID and allowed values
 * using operators like equality, inequality, containment, and pattern matching. Queries can
 * be combined using logical AND/OR operators.
 *
 * <p>Thread-safety: immutable after construction; safe for concurrent use.
 */
public final class TagSearchQuery {

    private final Map<String, Object> id;
    private final Map<String, Object> allowedValues;
    private final List<TagSearchQuery> and;
    private final List<TagSearchQuery> or;

    private TagSearchQuery(Builder b) {
        this.id = b.id;
        this.allowedValues = b.allowedValues;
        this.and = b.and;
        this.or = b.or;
    }

    /**
     * Returns the ID matching condition as a map (e.g. {@code {eq: "value"}}).
     *
     * @return the ID condition, or {@code null} if not set
     */
    public Map<String, Object> id() { return id; }

    /**
     * Returns the allowed values matching condition as a map (e.g. {@code {in: ["A", "B"]}}).
     *
     * @return the allowed values condition, or {@code null} if not set
     */
    public Map<String, Object> allowedValues() { return allowedValues; }

    /**
     * Returns the list of queries to be combined with AND logic.
     *
     * @return the AND queries, or {@code null} if not set
     */
    public List<TagSearchQuery> and() { return and; }

    /**
     * Returns the list of queries to be combined with OR logic.
     *
     * @return the OR queries, or {@code null} if not set
     */
    public List<TagSearchQuery> or() { return or; }

    /**
     * Returns a new {@link Builder} for constructing a {@code TagSearchQuery}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link TagSearchQuery}.
     */
    public static final class Builder {

        /**
         * Creates a new Builder.
         */
        public Builder() {}

        private Map<String, Object> id;
        private Map<String, Object> allowedValues;
        private List<TagSearchQuery> and;
        private List<TagSearchQuery> or;

        /**
         * Matches tags with ID equal to the given value.
         *
         * @param value the tag ID
         * @return this builder
         */
        public Builder idEq(String value) { this.id = Map.of("eq", value); return this; }

        /**
         * Matches tags with ID not equal to the given value.
         *
         * @param value the tag ID
         * @return this builder
         */
        public Builder idNotEq(String value) { this.id = Map.of("notEq", value); return this; }

        /**
         * Matches tags with ID in the given set of values.
         *
         * @param values the tag IDs
         * @return this builder
         */
        public Builder idIn(String... values) { this.id = Map.of("in", Arrays.asList(values)); return this; }

        /**
         * Matches tags with ID matching the given pattern.
         *
         * @param pattern the pattern (e.g. {@code "tag*"})
         * @return this builder
         */
        public Builder idLike(String pattern) { this.id = Map.of("like", pattern); return this; }

        /**
         * Matches tags with an allowed value equal to the given value.
         *
         * @param value the allowed value
         * @return this builder
         */
        public Builder allowedValuesEq(String value) { this.allowedValues = Map.of("eq", value); return this; }

        /**
         * Matches tags with an allowed value not equal to the given value.
         *
         * @param value the allowed value
         * @return this builder
         */
        public Builder allowedValuesNotEq(String value) { this.allowedValues = Map.of("notEq", value); return this; }

        /**
         * Matches tags with an allowed value in the given set of values.
         *
         * @param values the allowed values
         * @return this builder
         */
        public Builder allowedValuesIn(String... values) { this.allowedValues = Map.of("in", Arrays.asList(values)); return this; }

        /**
         * Matches tags with an allowed value matching the given pattern.
         *
         * @param pattern the pattern (e.g. {@code "val*"})
         * @return this builder
         */
        public Builder allowedValuesLike(String pattern) { this.allowedValues = Map.of("like", pattern); return this; }

        /**
         * Combines multiple queries with AND logic.
         *
         * @param queries the queries to combine
         * @return this builder
         */
        public Builder and(TagSearchQuery... queries) { this.and = Arrays.asList(queries); return this; }

        /**
         * Combines multiple queries with OR logic.
         *
         * @param queries the queries to combine
         * @return this builder
         */
        public Builder or(TagSearchQuery... queries) { this.or = Arrays.asList(queries); return this; }

        /**
         * Builds the {@link TagSearchQuery}.
         *
         * @return a new {@code TagSearchQuery}
         */
        public TagSearchQuery build() { return new TagSearchQuery(this); }
    }
}
