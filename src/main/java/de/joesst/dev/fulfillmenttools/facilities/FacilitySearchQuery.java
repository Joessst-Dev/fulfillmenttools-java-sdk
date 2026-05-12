package de.joesst.dev.fulfillmenttools.facilities;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Typed builder for the FacilitySearchQuery sent to POST /api/facilities/search.
 *
 * <p>Example:
 * <pre>{@code
 * FacilitySearchQuery query = FacilitySearchQuery.builder()
 *     .statusIn("ONLINE", "SUSPENDED")
 *     .nameLike("Berlin*")
 *     .cityEq("Berlin")
 *     .build();
 * }</pre>
 */
public final class FacilitySearchQuery {

    private final Map<String, Object> id;
    private final Map<String, Object> name;
    private final Map<String, Object> status;
    private final Map<String, Object> type;
    private final Map<String, Object> locationType;
    private final Map<String, Object> tenantFacilityId;
    private final Map<String, Object> address;
    private final Map<String, Object> contact;
    private final Map<String, Object> referenced;
    private final Map<String, Object> services;
    private final Map<String, Object> tags;
    private final List<FacilitySearchQuery> and;
    private final List<FacilitySearchQuery> or;

    private FacilitySearchQuery(Builder b) {
        this.id = b.id;
        this.name = b.name;
        this.status = b.status;
        this.type = b.type;
        this.locationType = b.locationType;
        this.tenantFacilityId = b.tenantFacilityId;
        this.address = b.address;
        this.contact = b.contact;
        this.referenced = b.referenced;
        this.services = b.services;
        this.tags = b.tags;
        this.and = b.and;
        this.or = b.or;
    }

    public Map<String, Object> id() { return id; }
    public Map<String, Object> name() { return name; }
    public Map<String, Object> status() { return status; }
    public Map<String, Object> type() { return type; }
    public Map<String, Object> locationType() { return locationType; }
    public Map<String, Object> tenantFacilityId() { return tenantFacilityId; }
    public Map<String, Object> address() { return address; }
    public Map<String, Object> contact() { return contact; }
    public Map<String, Object> referenced() { return referenced; }
    public Map<String, Object> services() { return services; }
    public Map<String, Object> tags() { return tags; }
    public List<FacilitySearchQuery> and() { return and; }
    public List<FacilitySearchQuery> or() { return or; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Map<String, Object> id;
        private Map<String, Object> name;
        private Map<String, Object> status;
        private Map<String, Object> type;
        private Map<String, Object> locationType;
        private Map<String, Object> tenantFacilityId;
        private Map<String, Object> address;
        private Map<String, Object> contact;
        private Map<String, Object> referenced;
        private Map<String, Object> services;
        private Map<String, Object> tags;
        private List<FacilitySearchQuery> and;
        private List<FacilitySearchQuery> or;

        // --- id ---

        public Builder idEq(String value) { id = Map.of("eq", value); return this; }
        public Builder idIn(String... values) { id = Map.of("in", List.of(values)); return this; }
        public Builder idIn(List<String> values) { id = Map.of("in", values); return this; }
        public Builder idNotEq(String value) { id = Map.of("notEq", value); return this; }

        // --- name (supports like) ---

        public Builder nameEq(String value) { name = Map.of("eq", value); return this; }
        public Builder nameLike(String pattern) { name = Map.of("like", pattern); return this; }
        public Builder nameIn(String... values) { name = Map.of("in", List.of(values)); return this; }
        public Builder nameIn(List<String> values) { name = Map.of("in", values); return this; }
        public Builder nameNotEq(String value) { name = Map.of("notEq", value); return this; }

        // --- status: ONLINE | SUSPENDED | OFFLINE ---

        public Builder statusEq(String value) { status = Map.of("eq", value); return this; }
        public Builder statusIn(String... values) { status = Map.of("in", List.of(values)); return this; }
        public Builder statusIn(List<String> values) { status = Map.of("in", values); return this; }
        public Builder statusNotEq(String value) { status = Map.of("notEq", value); return this; }

        // --- type: MANAGED_FACILITY | SUPPLIER ---

        public Builder typeEq(String value) { type = Map.of("eq", value); return this; }
        public Builder typeIn(String... values) { type = Map.of("in", List.of(values)); return this; }
        public Builder typeIn(List<String> values) { type = Map.of("in", values); return this; }
        public Builder typeNotEq(String value) { type = Map.of("notEq", value); return this; }

        // --- locationType: STORE | WAREHOUSE | EXTERNAL ---

        public Builder locationTypeEq(String value) { locationType = Map.of("eq", value); return this; }
        public Builder locationTypeIn(String... values) { locationType = Map.of("in", List.of(values)); return this; }
        public Builder locationTypeIn(List<String> values) { locationType = Map.of("in", values); return this; }
        public Builder locationTypeNotEq(String value) { locationType = Map.of("notEq", value); return this; }

        // --- tenantFacilityId ---

        public Builder tenantFacilityIdEq(String value) { tenantFacilityId = Map.of("eq", value); return this; }
        public Builder tenantFacilityIdIn(String... values) { tenantFacilityId = Map.of("in", List.of(values)); return this; }
        public Builder tenantFacilityIdIn(List<String> values) { tenantFacilityId = Map.of("in", values); return this; }
        public Builder tenantFacilityIdNotEq(String value) { tenantFacilityId = Map.of("notEq", value); return this; }

        // --- address sub-filters ---

        public Builder cityEq(String value) { return addr("city", Map.of("eq", value)); }
        public Builder cityLike(String pattern) { return addr("city", Map.of("like", pattern)); }
        public Builder countryEq(String value) { return addr("country", Map.of("eq", value)); }
        public Builder countryLike(String pattern) { return addr("country", Map.of("like", pattern)); }
        public Builder postalCodeEq(String value) { return addr("postalCode", Map.of("eq", value)); }
        public Builder postalCodeLike(String pattern) { return addr("postalCode", Map.of("like", pattern)); }
        public Builder streetEq(String value) { return addr("street", Map.of("eq", value)); }
        public Builder companyNameEq(String value) { return addr("companyName", Map.of("eq", value)); }
        public Builder provinceEq(String value) { return addr("province", Map.of("eq", value)); }

        // --- contact, referenced, services, tags (pass-through nested filters) ---

        public Builder contact(Map<String, Object> filter) { contact = filter; return this; }
        public Builder referenced(Map<String, Object> filter) { referenced = filter; return this; }
        public Builder services(Map<String, Object> filter) { services = filter; return this; }
        public Builder tags(Map<String, Object> filter) { tags = filter; return this; }

        // --- logical combinators ---

        public Builder and(FacilitySearchQuery... queries) { and = Arrays.asList(queries); return this; }
        public Builder and(List<FacilitySearchQuery> queries) { and = queries; return this; }
        public Builder or(FacilitySearchQuery... queries) { or = Arrays.asList(queries); return this; }
        public Builder or(List<FacilitySearchQuery> queries) { or = queries; return this; }

        public FacilitySearchQuery build() { return new FacilitySearchQuery(this); }

        private Builder addr(String field, Object filter) {
            if (address == null) address = new LinkedHashMap<>();
            address.put(field, filter);
            return this;
        }
    }
}
