package de.joesst.dev.fulfillmenttools.processes;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Represents a search query for filtering operative processes.
 *
 * Use the builder to construct queries with various filter criteria.
 */
public final class ProcessSearchQuery {

    private final Map<String, Object> facilityRefs;
    private final Map<String, Object> status;
    private final Map<String, Object> operativeStatus;
    private final Map<String, Object> domsStatus;
    private final Map<String, Object> returnStatus;
    private final Map<String, Object> orderRef;
    private final Map<String, Object> processId;
    private final Map<String, Object> created;
    private final Map<String, Object> documentRefs;
    private final Map<String, Object> externalActionRefs;
    private final Map<String, Object> flatRefs;
    private final Map<String, Object> handoverJobRefs;
    private final Map<String, Object> itemReturnJobsRef;
    private final Map<String, Object> packJobRefs;
    private final Map<String, Object> pickJobRefs;
    private final Map<String, Object> returnRefs;
    private final Map<String, Object> routingPlanRefs;
    private final Map<String, Object> serviceJobRefs;
    private final Map<String, Object> shipmentRefs;
    private final Map<String, Object> searchTerm;
    private final List<ProcessSearchQuery> and;
    private final List<ProcessSearchQuery> or;

    private ProcessSearchQuery(Builder b) {
        this.facilityRefs = b.facilityRefs;
        this.status = b.status;
        this.operativeStatus = b.operativeStatus;
        this.domsStatus = b.domsStatus;
        this.returnStatus = b.returnStatus;
        this.orderRef = b.orderRef;
        this.processId = b.processId;
        this.created = b.created;
        this.documentRefs = b.documentRefs;
        this.externalActionRefs = b.externalActionRefs;
        this.flatRefs = b.flatRefs;
        this.handoverJobRefs = b.handoverJobRefs;
        this.itemReturnJobsRef = b.itemReturnJobsRef;
        this.packJobRefs = b.packJobRefs;
        this.pickJobRefs = b.pickJobRefs;
        this.returnRefs = b.returnRefs;
        this.routingPlanRefs = b.routingPlanRefs;
        this.serviceJobRefs = b.serviceJobRefs;
        this.shipmentRefs = b.shipmentRefs;
        this.searchTerm = b.searchTerm;
        this.and = b.and;
        this.or = b.or;
    }

    /**
     * Returns the facility references filter.
     *
     * @return the facility references filter map, or null if not set
     */
    public Map<String, Object> facilityRefs() { return facilityRefs; }

    /**
     * Returns the status filter.
     *
     * @return the status filter map, or null if not set
     */
    public Map<String, Object> status() { return status; }

    /**
     * Returns the operative status filter.
     *
     * @return the operative status filter map, or null if not set
     */
    public Map<String, Object> operativeStatus() { return operativeStatus; }

    /**
     * Returns the DOMS status filter.
     *
     * @return the DOMS status filter map, or null if not set
     */
    public Map<String, Object> domsStatus() { return domsStatus; }

    /**
     * Returns the return status filter.
     *
     * @return the return status filter map, or null if not set
     */
    public Map<String, Object> returnStatus() { return returnStatus; }

    /**
     * Returns the order reference filter.
     *
     * @return the order reference filter map, or null if not set
     */
    public Map<String, Object> orderRef() { return orderRef; }

    /**
     * Returns the process ID filter.
     *
     * @return the process ID filter map, or null if not set
     */
    public Map<String, Object> processId() { return processId; }

    /**
     * Returns the creation date filter.
     *
     * @return the creation date filter map, or null if not set
     */
    public Map<String, Object> created() { return created; }

    /**
     * Returns the document references filter.
     *
     * @return the document references filter map, or null if not set
     */
    public Map<String, Object> documentRefs() { return documentRefs; }

    /**
     * Returns the external action references filter.
     *
     * @return the external action references filter map, or null if not set
     */
    public Map<String, Object> externalActionRefs() { return externalActionRefs; }

    /**
     * Returns the flat references filter.
     *
     * @return the flat references filter map, or null if not set
     */
    public Map<String, Object> flatRefs() { return flatRefs; }

    /**
     * Returns the handover job references filter.
     *
     * @return the handover job references filter map, or null if not set
     */
    public Map<String, Object> handoverJobRefs() { return handoverJobRefs; }

    /**
     * Returns the item return job references filter.
     *
     * @return the item return job references filter map, or null if not set
     */
    public Map<String, Object> itemReturnJobsRef() { return itemReturnJobsRef; }

    /**
     * Returns the pack job references filter.
     *
     * @return the pack job references filter map, or null if not set
     */
    public Map<String, Object> packJobRefs() { return packJobRefs; }

    /**
     * Returns the pick job references filter.
     *
     * @return the pick job references filter map, or null if not set
     */
    public Map<String, Object> pickJobRefs() { return pickJobRefs; }

    /**
     * Returns the return references filter.
     *
     * @return the return references filter map, or null if not set
     */
    public Map<String, Object> returnRefs() { return returnRefs; }

    /**
     * Returns the routing plan references filter.
     *
     * @return the routing plan references filter map, or null if not set
     */
    public Map<String, Object> routingPlanRefs() { return routingPlanRefs; }

    /**
     * Returns the service job references filter.
     *
     * @return the service job references filter map, or null if not set
     */
    public Map<String, Object> serviceJobRefs() { return serviceJobRefs; }

    /**
     * Returns the shipment references filter.
     *
     * @return the shipment references filter map, or null if not set
     */
    public Map<String, Object> shipmentRefs() { return shipmentRefs; }

    /**
     * Returns the search term filter.
     *
     * @return the search term filter map, or null if not set
     */
    public Map<String, Object> searchTerm() { return searchTerm; }

    /**
     * Returns the AND combined queries.
     *
     * @return the AND queries list, or null if not set
     */
    public List<ProcessSearchQuery> and() { return and; }

    /**
     * Returns the OR combined queries.
     *
     * @return the OR queries list, or null if not set
     */
    public List<ProcessSearchQuery> or() { return or; }

    /**
     * Creates a new builder for constructing a {@code ProcessSearchQuery}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@link ProcessSearchQuery}.
     */
    public static final class Builder {

        /** Creates a new Builder. */
        public Builder() {}

        private Map<String, Object> facilityRefs;
        private Map<String, Object> status;
        private Map<String, Object> operativeStatus;
        private Map<String, Object> domsStatus;
        private Map<String, Object> returnStatus;
        private Map<String, Object> orderRef;
        private Map<String, Object> processId;
        private Map<String, Object> created;
        private Map<String, Object> documentRefs;
        private Map<String, Object> externalActionRefs;
        private Map<String, Object> flatRefs;
        private Map<String, Object> handoverJobRefs;
        private Map<String, Object> itemReturnJobsRef;
        private Map<String, Object> packJobRefs;
        private Map<String, Object> pickJobRefs;
        private Map<String, Object> returnRefs;
        private Map<String, Object> routingPlanRefs;
        private Map<String, Object> serviceJobRefs;
        private Map<String, Object> shipmentRefs;
        private Map<String, Object> searchTerm;
        private List<ProcessSearchQuery> and;
        private List<ProcessSearchQuery> or;

        /**
         * Filters by facility references containing any of the given values.
         *
         * @param values the facility references
         * @return this builder
         */
        public Builder facilityRefsHasAny(String... values) { facilityRefs = Map.of("hasAny", List.of(values)); return this; }

        /**
         * Filters by facility references containing any of the given values.
         *
         * @param values the facility references
         * @return this builder
         */
        public Builder facilityRefsHasAny(List<String> values) { facilityRefs = Map.of("hasAny", values); return this; }

        /**
         * Filters by status equal to the given value.
         *
         * @param value the status value
         * @return this builder
         */
        public Builder statusEq(String value) { status = Map.of("eq", value); return this; }

        /**
         * Filters by status in the given values.
         *
         * @param values the status values
         * @return this builder
         */
        public Builder statusIn(String... values) { status = Map.of("in", List.of(values)); return this; }

        /**
         * Filters by status in the given values.
         *
         * @param values the status values
         * @return this builder
         */
        public Builder statusIn(List<String> values) { status = Map.of("in", values); return this; }

        /**
         * Filters by status not in the given values.
         *
         * @param values the status values to exclude
         * @return this builder
         */
        public Builder statusNotIn(List<String> values) { status = Map.of("notIn", values); return this; }

        /**
         * Filters by operative status equal to the given value.
         *
         * @param value the operative status value
         * @return this builder
         */
        public Builder operativeStatusEq(String value) { operativeStatus = Map.of("eq", value); return this; }

        /**
         * Filters by operative status in the given values.
         *
         * @param values the operative status values
         * @return this builder
         */
        public Builder operativeStatusIn(String... values) { operativeStatus = Map.of("in", List.of(values)); return this; }

        /**
         * Filters by operative status in the given values.
         *
         * @param values the operative status values
         * @return this builder
         */
        public Builder operativeStatusIn(List<String> values) { operativeStatus = Map.of("in", values); return this; }

        /**
         * Filters by DOMS status equal to the given value.
         *
         * @param value the DOMS status value
         * @return this builder
         */
        public Builder domsStatusEq(String value) { domsStatus = Map.of("eq", value); return this; }

        /**
         * Filters by DOMS status in the given values.
         *
         * @param values the DOMS status values
         * @return this builder
         */
        public Builder domsStatusIn(List<String> values) { domsStatus = Map.of("in", values); return this; }

        /**
         * Filters by return status equal to the given value.
         *
         * @param value the return status value
         * @return this builder
         */
        public Builder returnStatusEq(String value) { returnStatus = Map.of("eq", value); return this; }

        /**
         * Filters by return status in the given values.
         *
         * @param values the return status values
         * @return this builder
         */
        public Builder returnStatusIn(List<String> values) { returnStatus = Map.of("in", values); return this; }

        /**
         * Filters by order reference equal to the given value.
         *
         * @param value the order reference
         * @return this builder
         */
        public Builder orderRefEq(String value) { orderRef = Map.of("eq", value); return this; }

        /**
         * Filters by order reference in the given values.
         *
         * @param values the order references
         * @return this builder
         */
        public Builder orderRefIn(List<String> values) { orderRef = Map.of("in", values); return this; }

        /**
         * Filters by process ID equal to the given value.
         *
         * @param value the process ID
         * @return this builder
         */
        public Builder processIdEq(String value) { processId = Map.of("eq", value); return this; }

        /**
         * Filters by process ID matching the given pattern.
         *
         * @param pattern the pattern
         * @return this builder
         */
        public Builder processIdLike(String pattern) { processId = Map.of("like", pattern); return this; }

        /**
         * Filters by process ID in the given values.
         *
         * @param values the process IDs
         * @return this builder
         */
        public Builder processIdIn(List<String> values) { processId = Map.of("in", values); return this; }

        /**
         * Filters by creation date greater than or equal to the given instant.
         *
         * @param value the instant
         * @return this builder
         */
        public Builder createdGte(Instant value) { created = Map.of("gte", value.toString()); return this; }

        /**
         * Filters by creation date less than or equal to the given instant.
         *
         * @param value the instant
         * @return this builder
         */
        public Builder createdLte(Instant value) { created = Map.of("lte", value.toString()); return this; }

        /**
         * Filters by creation date between the given instants.
         *
         * @param from the start instant
         * @param to the end instant
         * @return this builder
         */
        public Builder createdBetween(Instant from, Instant to) {
            created = Map.of("gte", from.toString(), "lte", to.toString()); return this;
        }

        /**
         * Filters by document references containing any of the given values.
         *
         * @param values the document references
         * @return this builder
         */
        public Builder documentRefsHasAny(List<String> values) { documentRefs = Map.of("hasAny", values); return this; }

        /**
         * Filters by external action references containing any of the given values.
         *
         * @param values the external action references
         * @return this builder
         */
        public Builder externalActionRefsHasAny(List<String> values) { externalActionRefs = Map.of("hasAny", values); return this; }

        /**
         * Filters by flat references containing any of the given values.
         *
         * @param values the flat references
         * @return this builder
         */
        public Builder flatRefsHasAny(List<String> values) { flatRefs = Map.of("hasAny", values); return this; }

        /**
         * Filters by handover job references containing any of the given values.
         *
         * @param values the handover job references
         * @return this builder
         */
        public Builder handoverJobRefsHasAny(List<String> values) { handoverJobRefs = Map.of("hasAny", values); return this; }

        /**
         * Filters by item return job references containing any of the given values.
         *
         * @param values the item return job references
         * @return this builder
         */
        public Builder itemReturnJobsRefHasAny(List<String> values) { itemReturnJobsRef = Map.of("hasAny", values); return this; }

        /**
         * Filters by pack job references containing any of the given values.
         *
         * @param values the pack job references
         * @return this builder
         */
        public Builder packJobRefsHasAny(List<String> values) { packJobRefs = Map.of("hasAny", values); return this; }

        /**
         * Filters by pick job references containing any of the given values.
         *
         * @param values the pick job references
         * @return this builder
         */
        public Builder pickJobRefsHasAny(List<String> values) { pickJobRefs = Map.of("hasAny", values); return this; }

        /**
         * Filters by return references containing any of the given values.
         *
         * @param values the return references
         * @return this builder
         */
        public Builder returnRefsHasAny(List<String> values) { returnRefs = Map.of("hasAny", values); return this; }

        /**
         * Filters by routing plan references containing any of the given values.
         *
         * @param values the routing plan references
         * @return this builder
         */
        public Builder routingPlanRefsHasAny(List<String> values) { routingPlanRefs = Map.of("hasAny", values); return this; }

        /**
         * Filters by service job references containing any of the given values.
         *
         * @param values the service job references
         * @return this builder
         */
        public Builder serviceJobRefsHasAny(List<String> values) { serviceJobRefs = Map.of("hasAny", values); return this; }

        /**
         * Filters by shipment references containing any of the given values.
         *
         * @param values the shipment references
         * @return this builder
         */
        public Builder shipmentRefsHasAny(List<String> values) { shipmentRefs = Map.of("hasAny", values); return this; }

        /**
         * Filters by search term equal to the given value.
         *
         * @param value the search term
         * @return this builder
         */
        public Builder searchTermEq(String value) { searchTerm = Map.of("eq", value); return this; }

        /**
         * Combines multiple queries with AND logic.
         *
         * @param queries the queries to combine
         * @return this builder
         */
        public Builder and(ProcessSearchQuery... queries) { and = Arrays.asList(queries); return this; }

        /**
         * Combines multiple queries with AND logic.
         *
         * @param queries the queries to combine
         * @return this builder
         */
        public Builder and(List<ProcessSearchQuery> queries) { and = queries; return this; }

        /**
         * Combines multiple queries with OR logic.
         *
         * @param queries the queries to combine
         * @return this builder
         */
        public Builder or(ProcessSearchQuery... queries) { or = Arrays.asList(queries); return this; }

        /**
         * Combines multiple queries with OR logic.
         *
         * @param queries the queries to combine
         * @return this builder
         */
        public Builder or(List<ProcessSearchQuery> queries) { or = queries; return this; }

        /**
         * Builds the {@link ProcessSearchQuery}.
         *
         * @return a new query
         */
        public ProcessSearchQuery build() { return new ProcessSearchQuery(this); }
    }
}
