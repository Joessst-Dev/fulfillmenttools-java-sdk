package de.joesst.dev.fulfillmenttools.facilitygroups;

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
 *     .facilityRefsContains("fac-1")
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

    private FacilityGroupSearchQuery(Builder b) {
        this.id = b.id;
        this.tenantFacilityGroupId = b.tenantFacilityGroupId;
        this.name = b.name;
        this.facilityRefs = b.facilityRefs;
        this.and = b.and;
        this.or = b.or;
    }

    public Map<String, Object> id() { return id; }
    public Map<String, Object> tenantFacilityGroupId() { return tenantFacilityGroupId; }
    public Map<String, Object> name() { return name; }
    public Map<String, Object> facilityRefs() { return facilityRefs; }
    public List<FacilityGroupSearchQuery> and() { return and; }
    public List<FacilityGroupSearchQuery> or() { return or; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Map<String, Object> id;
        private Map<String, Object> tenantFacilityGroupId;
        private Map<String, Object> name;
        private Map<String, Object> facilityRefs;
        private List<FacilityGroupSearchQuery> and;
        private List<FacilityGroupSearchQuery> or;

        // --- id ---

        public Builder idEq(String value) { id = Map.of("eq", value); return this; }
        public Builder idIn(String... values) { id = Map.of("in", List.of(values)); return this; }
        public Builder idIn(List<String> values) { id = Map.of("in", values); return this; }
        public Builder idNotEq(String value) { id = Map.of("notEq", value); return this; }

        // --- tenantFacilityGroupId ---

        public Builder tenantFacilityGroupIdEq(String value) { tenantFacilityGroupId = Map.of("eq", value); return this; }
        public Builder tenantFacilityGroupIdIn(String... values) { tenantFacilityGroupId = Map.of("in", List.of(values)); return this; }
        public Builder tenantFacilityGroupIdIn(List<String> values) { tenantFacilityGroupId = Map.of("in", values); return this; }
        public Builder tenantFacilityGroupIdNotEq(String value) { tenantFacilityGroupId = Map.of("notEq", value); return this; }

        // --- name (localized) ---

        public Builder nameEq(String value) { name = Map.of("eq", value); return this; }
        public Builder nameLike(String pattern) { name = Map.of("like", pattern); return this; }
        public Builder nameIn(String... values) { name = Map.of("in", List.of(values)); return this; }
        public Builder nameIn(List<String> values) { name = Map.of("in", values); return this; }

        // --- facilityRefs ---

        public Builder facilityRefsContains(String value) { facilityRefs = Map.of("contains", value); return this; }
        public Builder facilityRefsContainsAll(String... values) { facilityRefs = Map.of("containsAll", List.of(values)); return this; }
        public Builder facilityRefsContainsAll(List<String> values) { facilityRefs = Map.of("containsAll", values); return this; }

        // --- logical combinators ---

        public Builder and(FacilityGroupSearchQuery... queries) { and = Arrays.asList(queries); return this; }
        public Builder and(List<FacilityGroupSearchQuery> queries) { and = queries; return this; }
        public Builder or(FacilityGroupSearchQuery... queries) { or = Arrays.asList(queries); return this; }
        public Builder or(List<FacilityGroupSearchQuery> queries) { or = queries; return this; }

        public FacilityGroupSearchQuery build() { return new FacilityGroupSearchQuery(this); }
    }
}
