package de.joesst.dev.fulfillmenttools.facilities;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Typed builder for facility search queries with support for nested field filters and logical operators.
 *
 * <p>Constructs complex searches using a fluent API with predefined filter methods for common fields
 * (e.g., {@code statusIn()}, {@code nameLike()}, {@code cityEq()}) and generic pass-through methods for
 * nested objects ({@code contact()}, {@code services()}, {@code tags()}).
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

    /**
     * Returns the facility ID filter.
     * @return the ID filter, or {@code null} if not set
     */
    public Map<String, Object> id() { return id; }

    /**
     * Returns the facility name filter.
     * @return the name filter, or {@code null} if not set
     */
    public Map<String, Object> name() { return name; }

    /**
     * Returns the facility status filter.
     * @return the status filter, or {@code null} if not set
     */
    public Map<String, Object> status() { return status; }

    /**
     * Returns the facility type filter.
     * @return the type filter, or {@code null} if not set
     */
    public Map<String, Object> type() { return type; }

    /**
     * Returns the facility location type filter.
     * @return the location type filter, or {@code null} if not set
     */
    public Map<String, Object> locationType() { return locationType; }

    /**
     * Returns the tenant facility ID filter.
     * @return the tenant facility ID filter, or {@code null} if not set
     */
    public Map<String, Object> tenantFacilityId() { return tenantFacilityId; }

    /**
     * Returns the facility address filter.
     * @return the address filter, or {@code null} if not set
     */
    public Map<String, Object> address() { return address; }

    /**
     * Returns the facility contact filter.
     * @return the contact filter, or {@code null} if not set
     */
    public Map<String, Object> contact() { return contact; }

    /**
     * Returns the referenced entities filter.
     * @return the referenced filter, or {@code null} if not set
     */
    public Map<String, Object> referenced() { return referenced; }

    /**
     * Returns the facility services filter.
     * @return the services filter, or {@code null} if not set
     */
    public Map<String, Object> services() { return services; }

    /**
     * Returns the facility tags filter.
     * @return the tags filter, or {@code null} if not set
     */
    public Map<String, Object> tags() { return tags; }

    /**
     * Returns the AND-combined sub-queries.
     * @return the AND queries, or {@code null} if not set
     */
    public List<FacilitySearchQuery> and() { return and; }

    /**
     * Returns the OR-combined sub-queries.
     * @return the OR queries, or {@code null} if not set
     */
    public List<FacilitySearchQuery> or() { return or; }

    /**
     * Returns a new builder for constructing a {@code FacilitySearchQuery}.
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /** Builder for {@code FacilitySearchQuery}. */
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

        /** Creates a new Builder instance. */
        public Builder() {}

        /**
         * Sets an equality filter on facility ID.
         * @param value the value
         * @return this builder
         */
        public Builder idEq(String value) { id = Map.of("eq", value); return this; }

        /**
         * Sets an in-list filter on facility ID.
         * @param values the values
         * @return this builder
         */
        public Builder idIn(String... values) { id = Map.of("in", List.of(values)); return this; }

        /**
         * Sets an in-list filter on facility ID.
         * @param values the values
         * @return this builder
         */
        public Builder idIn(List<String> values) { id = Map.of("in", values); return this; }

        /**
         * Sets a not-equal filter on facility ID.
         * @param value the value
         * @return this builder
         */
        public Builder idNotEq(String value) { id = Map.of("notEq", value); return this; }

        /**
         * Sets an equality filter on facility name.
         * @param value the value
         * @return this builder
         */
        public Builder nameEq(String value) { name = Map.of("eq", value); return this; }

        /**
         * Sets a pattern filter on facility name.
         * @param pattern the pattern
         * @return this builder
         */
        public Builder nameLike(String pattern) { name = Map.of("like", pattern); return this; }

        /**
         * Sets an in-list filter on facility name.
         * @param values the values
         * @return this builder
         */
        public Builder nameIn(String... values) { name = Map.of("in", List.of(values)); return this; }

        /**
         * Sets an in-list filter on facility name.
         * @param values the values
         * @return this builder
         */
        public Builder nameIn(List<String> values) { name = Map.of("in", values); return this; }

        /**
         * Sets a not-equal filter on facility name.
         * @param value the value
         * @return this builder
         */
        public Builder nameNotEq(String value) { name = Map.of("notEq", value); return this; }

        /**
         * Sets an equality filter on facility status.
         * @param value the value
         * @return this builder
         */
        public Builder statusEq(String value) { status = Map.of("eq", value); return this; }

        /**
         * Sets an in-list filter on facility status.
         * @param values the values
         * @return this builder
         */
        public Builder statusIn(String... values) { status = Map.of("in", List.of(values)); return this; }

        /**
         * Sets an in-list filter on facility status.
         * @param values the values
         * @return this builder
         */
        public Builder statusIn(List<String> values) { status = Map.of("in", values); return this; }

        /**
         * Sets a not-equal filter on facility status.
         * @param value the value
         * @return this builder
         */
        public Builder statusNotEq(String value) { status = Map.of("notEq", value); return this; }

        /**
         * Sets an equality filter on facility type.
         * @param value the value
         * @return this builder
         */
        public Builder typeEq(String value) { type = Map.of("eq", value); return this; }

        /**
         * Sets an in-list filter on facility type.
         * @param values the values
         * @return this builder
         */
        public Builder typeIn(String... values) { type = Map.of("in", List.of(values)); return this; }

        /**
         * Sets an in-list filter on facility type.
         * @param values the values
         * @return this builder
         */
        public Builder typeIn(List<String> values) { type = Map.of("in", values); return this; }

        /**
         * Sets a not-equal filter on facility type.
         * @param value the value
         * @return this builder
         */
        public Builder typeNotEq(String value) { type = Map.of("notEq", value); return this; }

        /**
         * Sets an equality filter on location type.
         * @param value the value
         * @return this builder
         */
        public Builder locationTypeEq(String value) { locationType = Map.of("eq", value); return this; }

        /**
         * Sets an in-list filter on location type.
         * @param values the values
         * @return this builder
         */
        public Builder locationTypeIn(String... values) { locationType = Map.of("in", List.of(values)); return this; }

        /**
         * Sets an in-list filter on location type.
         * @param values the values
         * @return this builder
         */
        public Builder locationTypeIn(List<String> values) { locationType = Map.of("in", values); return this; }

        /**
         * Sets a not-equal filter on location type.
         * @param value the value
         * @return this builder
         */
        public Builder locationTypeNotEq(String value) { locationType = Map.of("notEq", value); return this; }

        /**
         * Sets an equality filter on tenant facility ID.
         * @param value the value
         * @return this builder
         */
        public Builder tenantFacilityIdEq(String value) { tenantFacilityId = Map.of("eq", value); return this; }

        /**
         * Sets an in-list filter on tenant facility ID.
         * @param values the values
         * @return this builder
         */
        public Builder tenantFacilityIdIn(String... values) { tenantFacilityId = Map.of("in", List.of(values)); return this; }

        /**
         * Sets an in-list filter on tenant facility ID.
         * @param values the values
         * @return this builder
         */
        public Builder tenantFacilityIdIn(List<String> values) { tenantFacilityId = Map.of("in", values); return this; }

        /**
         * Sets a not-equal filter on tenant facility ID.
         * @param value the value
         * @return this builder
         */
        public Builder tenantFacilityIdNotEq(String value) { tenantFacilityId = Map.of("notEq", value); return this; }

        /**
         * Sets an equality filter on city.
         * @param value the value
         * @return this builder
         */
        public Builder cityEq(String value) { return addr("city", Map.of("eq", value)); }

        /**
         * Sets a pattern filter on city.
         * @param pattern the pattern
         * @return this builder
         */
        public Builder cityLike(String pattern) { return addr("city", Map.of("like", pattern)); }

        /**
         * Sets an equality filter on country.
         * @param value the value
         * @return this builder
         */
        public Builder countryEq(String value) { return addr("country", Map.of("eq", value)); }

        /**
         * Sets a pattern filter on country.
         * @param pattern the pattern
         * @return this builder
         */
        public Builder countryLike(String pattern) { return addr("country", Map.of("like", pattern)); }

        /**
         * Sets an equality filter on postal code.
         * @param value the value
         * @return this builder
         */
        public Builder postalCodeEq(String value) { return addr("postalCode", Map.of("eq", value)); }

        /**
         * Sets a pattern filter on postal code.
         * @param pattern the pattern
         * @return this builder
         */
        public Builder postalCodeLike(String pattern) { return addr("postalCode", Map.of("like", pattern)); }

        /**
         * Sets an equality filter on street.
         * @param value the value
         * @return this builder
         */
        public Builder streetEq(String value) { return addr("street", Map.of("eq", value)); }

        /**
         * Sets an equality filter on company name.
         * @param value the value
         * @return this builder
         */
        public Builder companyNameEq(String value) { return addr("companyName", Map.of("eq", value)); }

        /**
         * Sets an equality filter on province.
         * @param value the value
         * @return this builder
         */
        public Builder provinceEq(String value) { return addr("province", Map.of("eq", value)); }

        /**
         * Sets a nested filter on contact.
         * @param filter the filter
         * @return this builder
         */
        public Builder contact(Map<String, Object> filter) { contact = filter; return this; }

        /**
         * Sets a nested filter on referenced entities.
         * @param filter the filter
         * @return this builder
         */
        public Builder referenced(Map<String, Object> filter) { referenced = filter; return this; }

        /**
         * Sets a nested filter on services.
         * @param filter the filter
         * @return this builder
         */
        public Builder services(Map<String, Object> filter) { services = filter; return this; }

        /**
         * Sets a nested filter on tags.
         * @param filter the filter
         * @return this builder
         */
        public Builder tags(Map<String, Object> filter) { tags = filter; return this; }

        /**
         * Combines sub-queries with AND.
         * @param queries the queries
         * @return this builder
         */
        public Builder and(FacilitySearchQuery... queries) { and = Arrays.asList(queries); return this; }

        /**
         * Combines sub-queries with AND.
         * @param queries the queries
         * @return this builder
         */
        public Builder and(List<FacilitySearchQuery> queries) { and = queries; return this; }

        /**
         * Combines sub-queries with OR.
         * @param queries the queries
         * @return this builder
         */
        public Builder or(FacilitySearchQuery... queries) { or = Arrays.asList(queries); return this; }

        /**
         * Combines sub-queries with OR.
         * @param queries the queries
         * @return this builder
         */
        public Builder or(List<FacilitySearchQuery> queries) { or = queries; return this; }

        /**
         * Builds and returns a new {@code FacilitySearchQuery}.
         * @return the built query
         */
        public FacilitySearchQuery build() { return new FacilitySearchQuery(this); }

        private Builder addr(String field, Object filter) {
            if (address == null) address = new LinkedHashMap<>();
            address.put(field, filter);
            return this;
        }
    }
}
