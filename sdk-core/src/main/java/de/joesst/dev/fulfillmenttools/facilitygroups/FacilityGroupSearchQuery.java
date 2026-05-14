package de.joesst.dev.fulfillmenttools.facilitygroups;

import de.joesst.dev.fulfillmenttools.id.FacilityId;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Typed builder for the query sent to POST /api/facilitygroups/search.
 *
 * <p>Example:
 * <pre>{@code
 * FacilityGroupSearchQuery query = FacilityGroupSearchQuery.builder()
 *     .tenantFacilityGroupIdEq("my-group")
 *     .facilityRefsContains(FacilityId.builder().value("fac-1").build())
 *     .build();
 * }</pre>
 */
public final class FacilityGroupSearchQuery {

    private final Map<String, Object> id;
    private final Map<String, Object> tenantFacilityGroupId;
    private final Map<String, Object> name;
    private final Map<String, Object> facilityRefs;
    private final List<FacilityGroupSearchQuery> and;
    private final List<FacilityGroupSearchQuery> or;

    /**
     * Internal constructor used by the builder.
     *
     * @param b the builder with configured values
     */
    private FacilityGroupSearchQuery(Builder b) {
        this.id = b.id;
        this.tenantFacilityGroupId = b.tenantFacilityGroupId;
        this.name = b.name;
        this.facilityRefs = b.facilityRefs;
        this.and = b.and;
        this.or = b.or;
    }

    /**
     * Returns the id filter condition.
     *
     * @return the id condition map
     */
    public Map<String, Object> id() { return id; }

    /**
     * Returns the tenantFacilityGroupId filter condition.
     *
     * @return the tenantFacilityGroupId condition map
     */
    public Map<String, Object> tenantFacilityGroupId() { return tenantFacilityGroupId; }

    /**
     * Returns the name filter condition.
     *
     * @return the name condition map
     */
    public Map<String, Object> name() { return name; }

    /**
     * Returns the facilityRefs filter condition.
     *
     * @return the facilityRefs condition map
     */
    public Map<String, Object> facilityRefs() { return facilityRefs; }

    /**
     * Returns the logical AND conditions.
     *
     * @return the list of AND queries
     */
    public List<FacilityGroupSearchQuery> and() { return and; }

    /**
     * Returns the logical OR conditions.
     *
     * @return the list of OR queries
     */
    public List<FacilityGroupSearchQuery> or() { return or; }

    /**
     * Creates a new builder for constructing FacilityGroupSearchQuery instances.
     *
     * @return a new Builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for constructing FacilityGroupSearchQuery with fluent condition methods.
     */
    public static final class Builder {
        private Builder() {}

        private Map<String, Object> id;
        private Map<String, Object> tenantFacilityGroupId;
        private Map<String, Object> name;
        private Map<String, Object> facilityRefs;
        private List<FacilityGroupSearchQuery> and;
        private List<FacilityGroupSearchQuery> or;

        // --- id ---

        /**
         * Filters by id equals the given value.
         *
         * @param value the id to match
         * @return this builder instance
         */
        public Builder idEq(String value) { id = Map.of("eq", value); return this; }

        /**
         * Filters by id in the given values.
         *
         * @param values the ids to match
         * @return this builder instance
         */
        public Builder idIn(String... values) { id = Map.of("in", List.of(values)); return this; }

        /**
         * Filters by id in the given values.
         *
         * @param values the ids to match
         * @return this builder instance
         */
        public Builder idIn(List<String> values) { id = Map.of("in", values); return this; }

        /**
         * Filters by id not equal to the given value.
         *
         * @param value the id to exclude
         * @return this builder instance
         */
        public Builder idNotEq(String value) { id = Map.of("notEq", value); return this; }

        // --- tenantFacilityGroupId ---

        /**
         * Filters by tenantFacilityGroupId equals the given value.
         *
         * @param value the tenantFacilityGroupId to match
         * @return this builder instance
         */
        public Builder tenantFacilityGroupIdEq(String value) { tenantFacilityGroupId = Map.of("eq", value); return this; }

        /**
         * Filters by tenantFacilityGroupId in the given values.
         *
         * @param values the tenantFacilityGroupIds to match
         * @return this builder instance
         */
        public Builder tenantFacilityGroupIdIn(String... values) { tenantFacilityGroupId = Map.of("in", List.of(values)); return this; }

        /**
         * Filters by tenantFacilityGroupId in the given values.
         *
         * @param values the tenantFacilityGroupIds to match
         * @return this builder instance
         */
        public Builder tenantFacilityGroupIdIn(List<String> values) { tenantFacilityGroupId = Map.of("in", values); return this; }

        /**
         * Filters by tenantFacilityGroupId not equal to the given value.
         *
         * @param value the tenantFacilityGroupId to exclude
         * @return this builder instance
         */
        public Builder tenantFacilityGroupIdNotEq(String value) { tenantFacilityGroupId = Map.of("notEq", value); return this; }

        // --- name (localized) ---

        /**
         * Filters by name equals the given value.
         *
         * @param value the name to match
         * @return this builder instance
         */
        public Builder nameEq(String value) { name = Map.of("eq", value); return this; }

        /**
         * Filters by name matching the given pattern.
         *
         * @param pattern the name pattern to match
         * @return this builder instance
         */
        public Builder nameLike(String pattern) { name = Map.of("like", pattern); return this; }

        /**
         * Filters by name in the given values.
         *
         * @param values the names to match
         * @return this builder instance
         */
        public Builder nameIn(String... values) { name = Map.of("in", List.of(values)); return this; }

        /**
         * Filters by name in the given values.
         *
         * @param values the names to match
         * @return this builder instance
         */
        public Builder nameIn(List<String> values) { name = Map.of("in", values); return this; }

        // --- facilityRefs ---

        /**
         * Filters by facilityRefs containing the given facility ID.
         *
         * @param value the facility ID to match
         * @return this builder instance
         */
        public Builder facilityRefsContains(FacilityId value) { facilityRefs = Map.of("contains", value.value()); return this; }

        /**
         * Filters by facilityRefs containing all of the given values.
         *
         * @param values the facility IDs to match
         * @return this builder instance
         */
        public Builder facilityRefsContainsAll(FacilityId... values) { facilityRefs = Map.of("containsAll", Arrays.stream(values).map(FacilityId::value).toList()); return this; }

        /**
         * Filters by facilityRefs containing all of the given values.
         *
         * @param values the facility IDs to match
         * @return this builder instance
         */
        public Builder facilityRefsContainsAll(List<FacilityId> values) { facilityRefs = Map.of("containsAll", values.stream().map(FacilityId::value).toList()); return this; }

        // --- logical combinators ---

        /**
         * Combines the given queries with logical AND.
         *
         * @param queries the queries to combine
         * @return this builder instance
         */
        public Builder and(FacilityGroupSearchQuery... queries) { and = Arrays.asList(queries); return this; }

        /**
         * Combines the given queries with logical AND.
         *
         * @param queries the queries to combine
         * @return this builder instance
         */
        public Builder and(List<FacilityGroupSearchQuery> queries) { and = queries; return this; }

        /**
         * Combines the given queries with logical OR.
         *
         * @param queries the queries to combine
         * @return this builder instance
         */
        public Builder or(FacilityGroupSearchQuery... queries) { or = Arrays.asList(queries); return this; }

        /**
         * Combines the given queries with logical OR.
         *
         * @param queries the queries to combine
         * @return this builder instance
         */
        public Builder or(List<FacilityGroupSearchQuery> queries) { or = queries; return this; }

        /**
         * Builds a new FacilityGroupSearchQuery with the configured conditions.
         *
         * @return a new FacilityGroupSearchQuery instance
         */
        public FacilityGroupSearchQuery build() { return new FacilityGroupSearchQuery(this); }
    }
}
