package de.joesst.dev.fulfillmenttools.processes;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    public Map<String, Object> facilityRefs() { return facilityRefs; }
    public Map<String, Object> status() { return status; }
    public Map<String, Object> operativeStatus() { return operativeStatus; }
    public Map<String, Object> domsStatus() { return domsStatus; }
    public Map<String, Object> returnStatus() { return returnStatus; }
    public Map<String, Object> orderRef() { return orderRef; }
    public Map<String, Object> processId() { return processId; }
    public Map<String, Object> created() { return created; }
    public Map<String, Object> documentRefs() { return documentRefs; }
    public Map<String, Object> externalActionRefs() { return externalActionRefs; }
    public Map<String, Object> flatRefs() { return flatRefs; }
    public Map<String, Object> handoverJobRefs() { return handoverJobRefs; }
    public Map<String, Object> itemReturnJobsRef() { return itemReturnJobsRef; }
    public Map<String, Object> packJobRefs() { return packJobRefs; }
    public Map<String, Object> pickJobRefs() { return pickJobRefs; }
    public Map<String, Object> returnRefs() { return returnRefs; }
    public Map<String, Object> routingPlanRefs() { return routingPlanRefs; }
    public Map<String, Object> serviceJobRefs() { return serviceJobRefs; }
    public Map<String, Object> shipmentRefs() { return shipmentRefs; }
    public Map<String, Object> searchTerm() { return searchTerm; }
    public List<ProcessSearchQuery> and() { return and; }
    public List<ProcessSearchQuery> or() { return or; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

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

        // --- facilityRefs ---
        public Builder facilityRefsHasAny(String... values) { facilityRefs = Map.of("hasAny", List.of(values)); return this; }
        public Builder facilityRefsHasAny(List<String> values) { facilityRefs = Map.of("hasAny", values); return this; }

        // --- status ---
        public Builder statusEq(String value) { status = Map.of("eq", value); return this; }
        public Builder statusIn(String... values) { status = Map.of("in", List.of(values)); return this; }
        public Builder statusIn(List<String> values) { status = Map.of("in", values); return this; }
        public Builder statusNotIn(List<String> values) { status = Map.of("notIn", values); return this; }

        // --- operativeStatus ---
        public Builder operativeStatusEq(String value) { operativeStatus = Map.of("eq", value); return this; }
        public Builder operativeStatusIn(String... values) { operativeStatus = Map.of("in", List.of(values)); return this; }
        public Builder operativeStatusIn(List<String> values) { operativeStatus = Map.of("in", values); return this; }

        // --- domsStatus ---
        public Builder domsStatusEq(String value) { domsStatus = Map.of("eq", value); return this; }
        public Builder domsStatusIn(List<String> values) { domsStatus = Map.of("in", values); return this; }

        // --- returnStatus ---
        public Builder returnStatusEq(String value) { returnStatus = Map.of("eq", value); return this; }
        public Builder returnStatusIn(List<String> values) { returnStatus = Map.of("in", values); return this; }

        // --- orderRef ---
        public Builder orderRefEq(String value) { orderRef = Map.of("eq", value); return this; }
        public Builder orderRefIn(List<String> values) { orderRef = Map.of("in", values); return this; }

        // --- processId ---
        public Builder processIdEq(String value) { processId = Map.of("eq", value); return this; }
        public Builder processIdLike(String pattern) { processId = Map.of("like", pattern); return this; }
        public Builder processIdIn(List<String> values) { processId = Map.of("in", values); return this; }

        // --- created ---
        public Builder createdGte(Instant value) { created = Map.of("gte", value.toString()); return this; }
        public Builder createdLte(Instant value) { created = Map.of("lte", value.toString()); return this; }
        public Builder createdBetween(Instant from, Instant to) {
            created = Map.of("gte", from.toString(), "lte", to.toString()); return this;
        }

        // --- ref list filters ---
        public Builder documentRefsHasAny(List<String> values) { documentRefs = Map.of("hasAny", values); return this; }
        public Builder externalActionRefsHasAny(List<String> values) { externalActionRefs = Map.of("hasAny", values); return this; }
        public Builder flatRefsHasAny(List<String> values) { flatRefs = Map.of("hasAny", values); return this; }
        public Builder handoverJobRefsHasAny(List<String> values) { handoverJobRefs = Map.of("hasAny", values); return this; }
        public Builder itemReturnJobsRefHasAny(List<String> values) { itemReturnJobsRef = Map.of("hasAny", values); return this; }
        public Builder packJobRefsHasAny(List<String> values) { packJobRefs = Map.of("hasAny", values); return this; }
        public Builder pickJobRefsHasAny(List<String> values) { pickJobRefs = Map.of("hasAny", values); return this; }
        public Builder returnRefsHasAny(List<String> values) { returnRefs = Map.of("hasAny", values); return this; }
        public Builder routingPlanRefsHasAny(List<String> values) { routingPlanRefs = Map.of("hasAny", values); return this; }
        public Builder serviceJobRefsHasAny(List<String> values) { serviceJobRefs = Map.of("hasAny", values); return this; }
        public Builder shipmentRefsHasAny(List<String> values) { shipmentRefs = Map.of("hasAny", values); return this; }

        // --- searchTerm ---
        public Builder searchTermEq(String value) { searchTerm = Map.of("eq", value); return this; }

        // --- logical combinators ---
        public Builder and(ProcessSearchQuery... queries) { and = Arrays.asList(queries); return this; }
        public Builder and(List<ProcessSearchQuery> queries) { and = queries; return this; }
        public Builder or(ProcessSearchQuery... queries) { or = Arrays.asList(queries); return this; }
        public Builder or(List<ProcessSearchQuery> queries) { or = queries; return this; }

        public ProcessSearchQuery build() { return new ProcessSearchQuery(this); }
    }
}
