package de.joesst.dev.fulfillmenttools.stocks;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.StockId;
import de.joesst.dev.fulfillmenttools.id.StorageLocationId;
import de.joesst.dev.fulfillmenttools.id.TenantArticleId;
import de.joesst.dev.fulfillmenttools.id.TenantFacilityId;
import de.joesst.dev.fulfillmenttools.id.TenantStockId;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Typed query builder for {@code POST /api/stocks/search}.
 *
 * <p>Example:
 * <pre>{@code
 * StockSearchQuery query = StockSearchQuery.builder()
 *     .tenantArticleIdIn(new TenantArticleId("art-1"), new TenantArticleId("art-2"))
 *     .facilityRefIn(new FacilityId("fac-1"))
 *     .valueGte(10)
 *     .build();
 * }</pre>
 *
 * <p>Logical operators ({@link Builder#and} / {@link Builder#or}) can be used to compose
 * compound queries from simpler ones.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class StockSearchQuery {

    private final Map<String, Object> id;
    private final Map<String, Object> tenantArticleId;
    private final Map<String, Object> tenantStockId;
    private final Map<String, Object> facilityRef;
    private final Map<String, Object> tenantFacilityId;
    private final Map<String, Object> locationRef;
    private final Map<String, Object> value;
    private final Map<String, Object> conditions;
    private final List<StockSearchQuery> and;
    private final List<StockSearchQuery> or;

    private StockSearchQuery(Builder b) {
        this.id = b.id;
        this.tenantArticleId = b.tenantArticleId;
        this.tenantStockId = b.tenantStockId;
        this.facilityRef = b.facilityRef;
        this.tenantFacilityId = b.tenantFacilityId;
        this.locationRef = b.locationRef;
        this.value = b.value;
        this.conditions = b.conditions;
        this.and = b.and;
        this.or = b.or;
    }

    /** Returns the id filter, or {@code null} if not set. */
    public Map<String, Object> id() { return id; }
    /** Returns the tenantArticleId filter, or {@code null} if not set. */
    public Map<String, Object> tenantArticleId() { return tenantArticleId; }
    /** Returns the tenantStockId filter, or {@code null} if not set. */
    public Map<String, Object> tenantStockId() { return tenantStockId; }
    /** Returns the facilityRef filter, or {@code null} if not set. */
    public Map<String, Object> facilityRef() { return facilityRef; }
    /** Returns the tenantFacilityId filter, or {@code null} if not set. */
    public Map<String, Object> tenantFacilityId() { return tenantFacilityId; }
    /** Returns the locationRef filter, or {@code null} if not set. */
    public Map<String, Object> locationRef() { return locationRef; }
    /** Returns the value filter, or {@code null} if not set. */
    public Map<String, Object> value() { return value; }
    /** Returns the conditions filter, or {@code null} if not set. */
    public Map<String, Object> conditions() { return conditions; }
    /** Returns the logical AND sub-queries, or {@code null} if not set. */
    public List<StockSearchQuery> and() { return and; }
    /** Returns the logical OR sub-queries, or {@code null} if not set. */
    public List<StockSearchQuery> or() { return or; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Map<String, Object> id;
        private Map<String, Object> tenantArticleId;
        private Map<String, Object> tenantStockId;
        private Map<String, Object> facilityRef;
        private Map<String, Object> tenantFacilityId;
        private Map<String, Object> locationRef;
        private Map<String, Object> value;
        private Map<String, Object> conditions;
        private List<StockSearchQuery> and;
        private List<StockSearchQuery> or;

        private Builder() {}

        // --- id ---

        /** Filters by stock ID equal to the given value. */
        public Builder idEq(StockId value) { id = Map.of("eq", value.value()); return this; }
        /** Filters by stock ID in the given values. */
        public Builder idIn(StockId... values) { id = Map.of("in", Arrays.stream(values).map(StockId::value).toList()); return this; }
        /** Filters by stock ID in the given values. */
        public Builder idIn(List<StockId> values) { id = Map.of("in", values.stream().map(StockId::value).toList()); return this; }
        /** Filters by stock ID not equal to the given value. */
        public Builder idNotEq(StockId value) { id = Map.of("notEq", value.value()); return this; }

        // --- tenantArticleId ---

        /** Filters by tenant article ID equal to the given value. */
        public Builder tenantArticleIdEq(TenantArticleId value) { tenantArticleId = Map.of("eq", value.value()); return this; }
        /** Filters by tenant article ID in the given values. */
        public Builder tenantArticleIdIn(TenantArticleId... values) { tenantArticleId = Map.of("in", Arrays.stream(values).map(TenantArticleId::value).toList()); return this; }
        /** Filters by tenant article ID in the given values. */
        public Builder tenantArticleIdIn(List<TenantArticleId> values) { tenantArticleId = Map.of("in", values.stream().map(TenantArticleId::value).toList()); return this; }
        /** Filters by tenant article ID not equal to the given value. */
        public Builder tenantArticleIdNotEq(TenantArticleId value) { tenantArticleId = Map.of("notEq", value.value()); return this; }

        // --- tenantStockId ---

        /** Filters by tenant stock ID equal to the given value. */
        public Builder tenantStockIdEq(TenantStockId value) { tenantStockId = Map.of("eq", value.value()); return this; }
        /** Filters by tenant stock ID in the given values. */
        public Builder tenantStockIdIn(TenantStockId... values) { tenantStockId = Map.of("in", Arrays.stream(values).map(TenantStockId::value).toList()); return this; }
        /** Filters by tenant stock ID in the given values. */
        public Builder tenantStockIdIn(List<TenantStockId> values) { tenantStockId = Map.of("in", values.stream().map(TenantStockId::value).toList()); return this; }

        // --- facilityRef ---

        /** Filters by facility reference equal to the given value. */
        public Builder facilityRefEq(FacilityId value) { facilityRef = Map.of("eq", value.value()); return this; }
        /** Filters by facility reference in the given values. */
        public Builder facilityRefIn(FacilityId... values) { facilityRef = Map.of("in", Arrays.stream(values).map(FacilityId::value).toList()); return this; }
        /** Filters by facility reference in the given values. */
        public Builder facilityRefIn(List<FacilityId> values) { facilityRef = Map.of("in", values.stream().map(FacilityId::value).toList()); return this; }
        /** Filters by facility reference not equal to the given value. */
        public Builder facilityRefNotEq(FacilityId value) { facilityRef = Map.of("notEq", value.value()); return this; }

        // --- tenantFacilityId ---

        /** Filters by tenant facility ID equal to the given value. */
        public Builder tenantFacilityIdEq(TenantFacilityId value) { tenantFacilityId = Map.of("eq", value.value()); return this; }
        /** Filters by tenant facility ID in the given values. */
        public Builder tenantFacilityIdIn(TenantFacilityId... values) { tenantFacilityId = Map.of("in", Arrays.stream(values).map(TenantFacilityId::value).toList()); return this; }
        /** Filters by tenant facility ID in the given values. */
        public Builder tenantFacilityIdIn(List<TenantFacilityId> values) { tenantFacilityId = Map.of("in", values.stream().map(TenantFacilityId::value).toList()); return this; }

        // --- locationRef ---

        /** Filters by storage location reference equal to the given value. */
        public Builder locationRefEq(StorageLocationId value) { locationRef = Map.of("eq", value.value()); return this; }
        /** Filters by storage location reference in the given values. */
        public Builder locationRefIn(StorageLocationId... values) { locationRef = Map.of("in", Arrays.stream(values).map(StorageLocationId::value).toList()); return this; }
        /** Filters by storage location reference in the given values. */
        public Builder locationRefIn(List<StorageLocationId> values) { locationRef = Map.of("in", values.stream().map(StorageLocationId::value).toList()); return this; }

        // --- value ---

        /** Filters by stock quantity equal to the given value. */
        public Builder valueEq(int v) { value = Map.of("eq", v); return this; }
        /** Filters by stock quantity greater than the given value. */
        public Builder valueGt(int v) { value = Map.of("gt", v); return this; }
        /** Filters by stock quantity greater than or equal to the given value. */
        public Builder valueGte(int v) { value = Map.of("gte", v); return this; }
        /** Filters by stock quantity less than the given value. */
        public Builder valueLt(int v) { value = Map.of("lt", v); return this; }
        /** Filters by stock quantity less than or equal to the given value. */
        public Builder valueLte(int v) { value = Map.of("lte", v); return this; }

        // --- conditions ---

        /** Filters by condition equal to the given value (e.g. {@code "DEFECTIVE"}). */
        public Builder conditionsContainsEq(String condition) { conditions = Map.of("contains", Map.of("eq", condition)); return this; }
        /** Filters by condition in the given values. */
        public Builder conditionsContainsIn(String... conditionValues) { conditions = Map.of("contains", Map.of("in", List.of(conditionValues))); return this; }

        // --- logical combinators ---

        /** Combines the given queries with logical AND. */
        public Builder and(StockSearchQuery... queries) { and = Arrays.asList(queries); return this; }
        /** Combines the given queries with logical AND. */
        public Builder and(List<StockSearchQuery> queries) { and = queries; return this; }
        /** Combines the given queries with logical OR. */
        public Builder or(StockSearchQuery... queries) { or = Arrays.asList(queries); return this; }
        /** Combines the given queries with logical OR. */
        public Builder or(List<StockSearchQuery> queries) { or = queries; return this; }

        public StockSearchQuery build() { return new StockSearchQuery(this); }
    }
}
