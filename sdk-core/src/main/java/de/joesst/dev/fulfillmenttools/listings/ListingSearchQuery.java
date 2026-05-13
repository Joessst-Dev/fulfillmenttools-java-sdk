package de.joesst.dev.fulfillmenttools.listings;

import de.joesst.dev.fulfillmenttools.id.TagId;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Typed builder for the query sent to POST /api/listings/search.
 *
 * <p>Example:
 * <pre>{@code
 * ListingSearchQuery query = ListingSearchQuery.builder()
 *     .statusEq("ACTIVE")
 *     .facilityRefEq("fac-1")
 *     .tenantArticleIdIn("art-1", "art-2")
 *     .build();
 * }</pre>
 */
public final class ListingSearchQuery {

    private final Map<String, Object> id;
    private final Map<String, Object> tenantArticleId;
    private final Map<String, Object> facilityRef;
    private final Map<String, Object> measurementUnitKey;
    private final Map<String, Object> status;
    private final Map<String, Object> outOfStockBehaviour;
    private final Map<String, Object> price;
    private final Map<String, Object> weight;
    private final Map<String, Object> created;
    private final Map<String, Object> categoryRefs;
    private final Map<String, Object> availabilityTimeframe;
    private final Map<String, Object> tags;
    private final Map<String, Object> customAttributes;
    private final List<ListingSearchQuery> and;
    private final List<ListingSearchQuery> or;

    private ListingSearchQuery(Builder b) {
        this.id = b.id;
        this.tenantArticleId = b.tenantArticleId;
        this.facilityRef = b.facilityRef;
        this.measurementUnitKey = b.measurementUnitKey;
        this.status = b.status;
        this.outOfStockBehaviour = b.outOfStockBehaviour;
        this.price = b.price;
        this.weight = b.weight;
        this.created = b.created;
        this.categoryRefs = b.categoryRefs;
        this.availabilityTimeframe = b.availabilityTimeframe;
        this.tags = b.tags;
        this.customAttributes = b.customAttributes;
        this.and = b.and;
        this.or = b.or;
    }

    /**
     * Returns the listing ID filter.
     * @return the ID filter, or {@code null} if not set
     */
    public Map<String, Object> id() { return id; }

    /**
     * Returns the tenant article ID filter.
     * @return the tenant article ID filter, or {@code null} if not set
     */
    public Map<String, Object> tenantArticleId() { return tenantArticleId; }

    /**
     * Returns the facility reference filter.
     * @return the facility reference filter, or {@code null} if not set
     */
    public Map<String, Object> facilityRef() { return facilityRef; }

    /**
     * Returns the measurement unit key filter.
     * @return the measurement unit key filter, or {@code null} if not set
     */
    public Map<String, Object> measurementUnitKey() { return measurementUnitKey; }

    /**
     * Returns the listing status filter.
     * @return the status filter, or {@code null} if not set
     */
    public Map<String, Object> status() { return status; }

    /**
     * Returns the out-of-stock behaviour filter.
     * @return the out-of-stock behaviour filter, or {@code null} if not set
     */
    public Map<String, Object> outOfStockBehaviour() { return outOfStockBehaviour; }

    /**
     * Returns the price filter.
     * @return the price filter, or {@code null} if not set
     */
    public Map<String, Object> price() { return price; }

    /**
     * Returns the weight filter.
     * @return the weight filter, or {@code null} if not set
     */
    public Map<String, Object> weight() { return weight; }

    /**
     * Returns the creation date filter.
     * @return the created filter, or {@code null} if not set
     */
    public Map<String, Object> created() { return created; }

    /**
     * Returns the category references filter.
     * @return the category references filter, or {@code null} if not set
     */
    public Map<String, Object> categoryRefs() { return categoryRefs; }

    /**
     * Returns the availability timeframe filter.
     * @return the availability timeframe filter, or {@code null} if not set
     */
    public Map<String, Object> availabilityTimeframe() { return availabilityTimeframe; }

    /**
     * Returns the tags filter.
     * @return the tags filter, or {@code null} if not set
     */
    public Map<String, Object> tags() { return tags; }

    /**
     * Returns the custom attributes filter.
     * @return the custom attributes filter, or {@code null} if not set
     */
    public Map<String, Object> customAttributes() { return customAttributes; }

    /**
     * Returns the AND-combined sub-queries.
     * @return the AND queries, or {@code null} if not set
     */
    public List<ListingSearchQuery> and() { return and; }

    /**
     * Returns the OR-combined sub-queries.
     * @return the OR queries, or {@code null} if not set
     */
    public List<ListingSearchQuery> or() { return or; }

    /**
     * Returns a new builder for constructing a {@code ListingSearchQuery}.
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /** Builder for {@code ListingSearchQuery}. */
    public static final class Builder {

        private Map<String, Object> id;
        private Map<String, Object> tenantArticleId;
        private Map<String, Object> facilityRef;
        private Map<String, Object> measurementUnitKey;
        private Map<String, Object> status;
        private Map<String, Object> outOfStockBehaviour;
        private Map<String, Object> price;
        private Map<String, Object> weight;
        private Map<String, Object> created;
        private Map<String, Object> categoryRefs;
        private Map<String, Object> availabilityTimeframe;
        private Map<String, Object> tags;
        private Map<String, Object> customAttributes;
        private List<ListingSearchQuery> and;
        private List<ListingSearchQuery> or;

        /** Creates a new Builder instance. */
        public Builder() {}

        /**
         * Sets an equality filter on listing ID.
         * @param value the value
         * @return this builder
         */
        public Builder idEq(String value) { id = Map.of("eq", value); return this; }

        /**
         * Sets a not-equal filter on listing ID.
         * @param value the value
         * @return this builder
         */
        public Builder idNotEq(String value) { id = Map.of("notEq", value); return this; }

        /**
         * Sets an in-list filter on listing ID.
         * @param values the values
         * @return this builder
         */
        public Builder idIn(String... values) { id = Map.of("in", List.of(values)); return this; }

        /**
         * Sets an in-list filter on listing ID.
         * @param values the values
         * @return this builder
         */
        public Builder idIn(List<String> values) { id = Map.of("in", values); return this; }

        /**
         * Sets a not-in-list filter on listing ID.
         * @param values the values
         * @return this builder
         */
        public Builder idNotIn(List<String> values) { id = Map.of("notIn", values); return this; }

        /**
         * Sets an equality filter on tenant article ID.
         * @param value the value
         * @return this builder
         */
        public Builder tenantArticleIdEq(String value) { tenantArticleId = Map.of("eq", value); return this; }

        /**
         * Sets a not-equal filter on tenant article ID.
         * @param value the value
         * @return this builder
         */
        public Builder tenantArticleIdNotEq(String value) { tenantArticleId = Map.of("notEq", value); return this; }

        /**
         * Sets an in-list filter on tenant article ID.
         * @param values the values
         * @return this builder
         */
        public Builder tenantArticleIdIn(String... values) { tenantArticleId = Map.of("in", List.of(values)); return this; }

        /**
         * Sets an in-list filter on tenant article ID.
         * @param values the values
         * @return this builder
         */
        public Builder tenantArticleIdIn(List<String> values) { tenantArticleId = Map.of("in", values); return this; }

        /**
         * Sets a not-in-list filter on tenant article ID.
         * @param values the values
         * @return this builder
         */
        public Builder tenantArticleIdNotIn(List<String> values) { tenantArticleId = Map.of("notIn", values); return this; }

        /**
         * Sets an equality filter on facility reference.
         * @param value the value
         * @return this builder
         */
        public Builder facilityRefEq(String value) { facilityRef = Map.of("eq", value); return this; }

        /**
         * Sets a not-equal filter on facility reference.
         * @param value the value
         * @return this builder
         */
        public Builder facilityRefNotEq(String value) { facilityRef = Map.of("notEq", value); return this; }

        /**
         * Sets an in-list filter on facility reference.
         * @param values the values
         * @return this builder
         */
        public Builder facilityRefIn(String... values) { facilityRef = Map.of("in", List.of(values)); return this; }

        /**
         * Sets an in-list filter on facility reference.
         * @param values the values
         * @return this builder
         */
        public Builder facilityRefIn(List<String> values) { facilityRef = Map.of("in", values); return this; }

        /**
         * Sets an equality filter on measurement unit key.
         * @param value the value
         * @return this builder
         */
        public Builder measurementUnitKeyEq(String value) { measurementUnitKey = Map.of("eq", value); return this; }

        /**
         * Sets an in-list filter on measurement unit key.
         * @param values the values
         * @return this builder
         */
        public Builder measurementUnitKeyIn(String... values) { measurementUnitKey = Map.of("in", List.of(values)); return this; }

        /**
         * Sets an in-list filter on measurement unit key.
         * @param values the values
         * @return this builder
         */
        public Builder measurementUnitKeyIn(List<String> values) { measurementUnitKey = Map.of("in", values); return this; }

        /**
         * Sets an equality filter on listing status (e.g. {@code ACTIVE}, {@code INACTIVE}).
         * @param value the value
         * @return this builder
         */
        public Builder statusEq(String value) { status = Map.of("eq", value); return this; }

        /**
         * Sets a not-equal filter on listing status.
         * @param value the value
         * @return this builder
         */
        public Builder statusNotEq(String value) { status = Map.of("notEq", value); return this; }

        /**
         * Sets an in-list filter on listing status.
         * @param values the values
         * @return this builder
         */
        public Builder statusIn(String... values) { status = Map.of("in", List.of(values)); return this; }

        /**
         * Sets an in-list filter on listing status.
         * @param values the values
         * @return this builder
         */
        public Builder statusIn(List<String> values) { status = Map.of("in", values); return this; }

        /**
         * Sets an equality filter on out-of-stock behaviour.
         * @param value the value
         * @return this builder
         */
        public Builder outOfStockBehaviourEq(String value) { outOfStockBehaviour = Map.of("eq", value); return this; }

        /**
         * Sets a not-equal filter on out-of-stock behaviour.
         * @param value the value
         * @return this builder
         */
        public Builder outOfStockBehaviourNotEq(String value) { outOfStockBehaviour = Map.of("notEq", value); return this; }

        /**
         * Sets an in-list filter on out-of-stock behaviour.
         * @param values the values
         * @return this builder
         */
        public Builder outOfStockBehaviourIn(String... values) { outOfStockBehaviour = Map.of("in", List.of(values)); return this; }

        /**
         * Sets an in-list filter on out-of-stock behaviour.
         * @param values the values
         * @return this builder
         */
        public Builder outOfStockBehaviourIn(List<String> values) { outOfStockBehaviour = Map.of("in", values); return this; }

        /**
         * Sets an equality filter on price.
         * @param value the value
         * @return this builder
         */
        public Builder priceEq(Number value) { price = Map.of("eq", value); return this; }

        /**
         * Sets a greater-than filter on price.
         * @param value the value
         * @return this builder
         */
        public Builder priceGt(Number value) { price = Map.of("gt", value); return this; }

        /**
         * Sets a greater-than-or-equal filter on price.
         * @param value the value
         * @return this builder
         */
        public Builder priceGte(Number value) { price = Map.of("gte", value); return this; }

        /**
         * Sets a less-than filter on price.
         * @param value the value
         * @return this builder
         */
        public Builder priceLt(Number value) { price = Map.of("lt", value); return this; }

        /**
         * Sets a less-than-or-equal filter on price.
         * @param value the value
         * @return this builder
         */
        public Builder priceLte(Number value) { price = Map.of("lte", value); return this; }

        /**
         * Sets an equality filter on weight.
         * @param value the value
         * @return this builder
         */
        public Builder weightEq(Number value) { weight = Map.of("eq", value); return this; }

        /**
         * Sets a greater-than filter on weight.
         * @param value the value
         * @return this builder
         */
        public Builder weightGt(Number value) { weight = Map.of("gt", value); return this; }

        /**
         * Sets a greater-than-or-equal filter on weight.
         * @param value the value
         * @return this builder
         */
        public Builder weightGte(Number value) { weight = Map.of("gte", value); return this; }

        /**
         * Sets a less-than filter on weight.
         * @param value the value
         * @return this builder
         */
        public Builder weightLt(Number value) { weight = Map.of("lt", value); return this; }

        /**
         * Sets a less-than-or-equal filter on weight.
         * @param value the value
         * @return this builder
         */
        public Builder weightLte(Number value) { weight = Map.of("lte", value); return this; }

        /**
         * Sets an equality filter on creation date.
         * @param isoDateTime the ISO 8601 date-time string
         * @return this builder
         */
        public Builder createdEq(String isoDateTime) { created = Map.of("eq", isoDateTime); return this; }

        /**
         * Sets a greater-than filter on creation date.
         * @param isoDateTime the ISO 8601 date-time string
         * @return this builder
         */
        public Builder createdGt(String isoDateTime) { created = Map.of("gt", isoDateTime); return this; }

        /**
         * Sets a greater-than-or-equal filter on creation date.
         * @param isoDateTime the ISO 8601 date-time string
         * @return this builder
         */
        public Builder createdGte(String isoDateTime) { created = Map.of("gte", isoDateTime); return this; }

        /**
         * Sets a less-than filter on creation date.
         * @param isoDateTime the ISO 8601 date-time string
         * @return this builder
         */
        public Builder createdLt(String isoDateTime) { created = Map.of("lt", isoDateTime); return this; }

        /**
         * Sets a less-than-or-equal filter on creation date.
         * @param isoDateTime the ISO 8601 date-time string
         * @return this builder
         */
        public Builder createdLte(String isoDateTime) { created = Map.of("lte", isoDateTime); return this; }

        /**
         * Sets a contains filter on category references.
         * @param value the category reference value
         * @return this builder
         */
        public Builder categoryRefsContains(String value) { categoryRefs = Map.of("contains", Map.of("eq", value)); return this; }

        /**
         * Sets a greater-than-or-equal filter on availability timeframe start.
         * @param isoDateTime the ISO 8601 date-time string
         * @return this builder
         */
        public Builder availabilityTimeframeStartGte(String isoDateTime) {
            availabilityTimeframe = Map.of("start", Map.of("gte", isoDateTime));
            return this;
        }

        /**
         * Sets a less-than-or-equal filter on availability timeframe start.
         * @param isoDateTime the ISO 8601 date-time string
         * @return this builder
         */
        public Builder availabilityTimeframeStartLte(String isoDateTime) {
            availabilityTimeframe = Map.of("start", Map.of("lte", isoDateTime));
            return this;
        }

        /**
         * Sets a filter matching listings whose tags contain the given tag ID.
         * @param tagId the tag ID to match
         * @return this builder
         */
        public Builder tagsContainId(TagId tagId) {
            tags = Map.of("contains", Map.of("id", Map.of("eq", tagId.value())));
            return this;
        }

        /**
         * Sets a filter matching listings whose tags contain the given tag value.
         * @param tagValue the tag value to match
         * @return this builder
         */
        public Builder tagsContainValue(String tagValue) {
            tags = Map.of("contains", Map.of("value", Map.of("eq", tagValue)));
            return this;
        }

        /**
         * Adds a filter on a specific custom attribute key.
         * @param key the custom attribute key
         * @param filter the filter expression for the attribute
         * @return this builder
         */
        public Builder customAttribute(String key, Object filter) {
            if (customAttributes == null) customAttributes = new LinkedHashMap<>();
            customAttributes.put(key, filter);
            return this;
        }

        /**
         * Combines sub-queries with AND.
         * @param queries the queries
         * @return this builder
         */
        public Builder and(ListingSearchQuery... queries) { and = Arrays.asList(queries); return this; }

        /**
         * Combines sub-queries with AND.
         * @param queries the queries
         * @return this builder
         */
        public Builder and(List<ListingSearchQuery> queries) { and = queries; return this; }

        /**
         * Combines sub-queries with OR.
         * @param queries the queries
         * @return this builder
         */
        public Builder or(ListingSearchQuery... queries) { or = Arrays.asList(queries); return this; }

        /**
         * Combines sub-queries with OR.
         * @param queries the queries
         * @return this builder
         */
        public Builder or(List<ListingSearchQuery> queries) { or = queries; return this; }

        /**
         * Builds and returns a new {@code ListingSearchQuery}.
         * @return the built query
         */
        public ListingSearchQuery build() { return new ListingSearchQuery(this); }
    }
}
