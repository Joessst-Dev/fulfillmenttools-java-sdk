package de.joesst.dev.fulfillmenttools.listings;

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

    public Map<String, Object> id() { return id; }
    public Map<String, Object> tenantArticleId() { return tenantArticleId; }
    public Map<String, Object> facilityRef() { return facilityRef; }
    public Map<String, Object> measurementUnitKey() { return measurementUnitKey; }
    public Map<String, Object> status() { return status; }
    public Map<String, Object> outOfStockBehaviour() { return outOfStockBehaviour; }
    public Map<String, Object> price() { return price; }
    public Map<String, Object> weight() { return weight; }
    public Map<String, Object> created() { return created; }
    public Map<String, Object> categoryRefs() { return categoryRefs; }
    public Map<String, Object> availabilityTimeframe() { return availabilityTimeframe; }
    public Map<String, Object> tags() { return tags; }
    public Map<String, Object> customAttributes() { return customAttributes; }
    public List<ListingSearchQuery> and() { return and; }
    public List<ListingSearchQuery> or() { return or; }

    public static Builder builder() { return new Builder(); }

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

        // --- id ---
        public Builder idEq(String value) { id = Map.of("eq", value); return this; }
        public Builder idNotEq(String value) { id = Map.of("notEq", value); return this; }
        public Builder idIn(String... values) { id = Map.of("in", List.of(values)); return this; }
        public Builder idIn(List<String> values) { id = Map.of("in", values); return this; }
        public Builder idNotIn(List<String> values) { id = Map.of("notIn", values); return this; }

        // --- tenantArticleId ---
        public Builder tenantArticleIdEq(String value) { tenantArticleId = Map.of("eq", value); return this; }
        public Builder tenantArticleIdNotEq(String value) { tenantArticleId = Map.of("notEq", value); return this; }
        public Builder tenantArticleIdIn(String... values) { tenantArticleId = Map.of("in", List.of(values)); return this; }
        public Builder tenantArticleIdIn(List<String> values) { tenantArticleId = Map.of("in", values); return this; }
        public Builder tenantArticleIdNotIn(List<String> values) { tenantArticleId = Map.of("notIn", values); return this; }

        // --- facilityRef ---
        public Builder facilityRefEq(String value) { facilityRef = Map.of("eq", value); return this; }
        public Builder facilityRefNotEq(String value) { facilityRef = Map.of("notEq", value); return this; }
        public Builder facilityRefIn(String... values) { facilityRef = Map.of("in", List.of(values)); return this; }
        public Builder facilityRefIn(List<String> values) { facilityRef = Map.of("in", values); return this; }

        // --- measurementUnitKey ---
        public Builder measurementUnitKeyEq(String value) { measurementUnitKey = Map.of("eq", value); return this; }
        public Builder measurementUnitKeyIn(String... values) { measurementUnitKey = Map.of("in", List.of(values)); return this; }
        public Builder measurementUnitKeyIn(List<String> values) { measurementUnitKey = Map.of("in", values); return this; }

        // --- status: ACTIVE | INACTIVE ---
        public Builder statusEq(String value) { status = Map.of("eq", value); return this; }
        public Builder statusNotEq(String value) { status = Map.of("notEq", value); return this; }
        public Builder statusIn(String... values) { status = Map.of("in", List.of(values)); return this; }
        public Builder statusIn(List<String> values) { status = Map.of("in", values); return this; }

        // --- outOfStockBehaviour: NONE | BACKORDER | PREORDER | RESTOCK | PREORDER_AND_RESTOCK ---
        public Builder outOfStockBehaviourEq(String value) { outOfStockBehaviour = Map.of("eq", value); return this; }
        public Builder outOfStockBehaviourNotEq(String value) { outOfStockBehaviour = Map.of("notEq", value); return this; }
        public Builder outOfStockBehaviourIn(String... values) { outOfStockBehaviour = Map.of("in", List.of(values)); return this; }
        public Builder outOfStockBehaviourIn(List<String> values) { outOfStockBehaviour = Map.of("in", values); return this; }

        // --- price (NumberFilter) ---
        public Builder priceEq(Number value) { price = Map.of("eq", value); return this; }
        public Builder priceGt(Number value) { price = Map.of("gt", value); return this; }
        public Builder priceGte(Number value) { price = Map.of("gte", value); return this; }
        public Builder priceLt(Number value) { price = Map.of("lt", value); return this; }
        public Builder priceLte(Number value) { price = Map.of("lte", value); return this; }

        // --- weight (NumberFilter) ---
        public Builder weightEq(Number value) { weight = Map.of("eq", value); return this; }
        public Builder weightGt(Number value) { weight = Map.of("gt", value); return this; }
        public Builder weightGte(Number value) { weight = Map.of("gte", value); return this; }
        public Builder weightLt(Number value) { weight = Map.of("lt", value); return this; }
        public Builder weightLte(Number value) { weight = Map.of("lte", value); return this; }

        // --- created (DateFilter) ---
        public Builder createdEq(String isoDateTime) { created = Map.of("eq", isoDateTime); return this; }
        public Builder createdGt(String isoDateTime) { created = Map.of("gt", isoDateTime); return this; }
        public Builder createdGte(String isoDateTime) { created = Map.of("gte", isoDateTime); return this; }
        public Builder createdLt(String isoDateTime) { created = Map.of("lt", isoDateTime); return this; }
        public Builder createdLte(String isoDateTime) { created = Map.of("lte", isoDateTime); return this; }

        // --- categoryRefs (StringListFilter — contains) ---
        public Builder categoryRefsContains(String value) { categoryRefs = Map.of("contains", Map.of("eq", value)); return this; }

        // --- availabilityTimeframe ---
        public Builder availabilityTimeframeStartGte(String isoDateTime) {
            availabilityTimeframe = Map.of("start", Map.of("gte", isoDateTime));
            return this;
        }
        public Builder availabilityTimeframeStartLte(String isoDateTime) {
            availabilityTimeframe = Map.of("start", Map.of("lte", isoDateTime));
            return this;
        }

        // --- tags (ListingTagsListFilter) ---
        public Builder tagsContainId(String tagId) {
            tags = Map.of("contains", Map.of("id", Map.of("eq", tagId)));
            return this;
        }
        public Builder tagsContainValue(String tagValue) {
            tags = Map.of("contains", Map.of("value", Map.of("eq", tagValue)));
            return this;
        }

        // --- customAttributes ---
        public Builder customAttribute(String key, Object filter) {
            if (customAttributes == null) customAttributes = new LinkedHashMap<>();
            customAttributes.put(key, filter);
            return this;
        }

        // --- logical combinators ---
        public Builder and(ListingSearchQuery... queries) { and = Arrays.asList(queries); return this; }
        public Builder and(List<ListingSearchQuery> queries) { and = queries; return this; }
        public Builder or(ListingSearchQuery... queries) { or = Arrays.asList(queries); return this; }
        public Builder or(List<ListingSearchQuery> queries) { or = queries; return this; }

        public ListingSearchQuery build() { return new ListingSearchQuery(this); }
    }
}
