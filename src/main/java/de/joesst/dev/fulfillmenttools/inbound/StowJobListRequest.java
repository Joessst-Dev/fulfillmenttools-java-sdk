package de.joesst.dev.fulfillmenttools.inbound;

import java.util.List;

public final class StowJobListRequest {

    private final Integer size;
    private final String startAfterId;
    private final String sort;
    private final List<String> facilityRef;
    private final List<String> status;
    private final List<String> tenantArticleId;
    private final List<String> locationRef;
    private final List<String> stockRef;
    private final List<String> shortId;
    private final List<Integer> priority;

    private StowJobListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
        this.sort = builder.sort;
        this.facilityRef = builder.facilityRef;
        this.status = builder.status;
        this.tenantArticleId = builder.tenantArticleId;
        this.locationRef = builder.locationRef;
        this.stockRef = builder.stockRef;
        this.shortId = builder.shortId;
        this.priority = builder.priority;
    }

    public Integer size() { return size; }
    public String startAfterId() { return startAfterId; }
    public String sort() { return sort; }
    public List<String> facilityRef() { return facilityRef; }
    public List<String> status() { return status; }
    public List<String> tenantArticleId() { return tenantArticleId; }
    public List<String> locationRef() { return locationRef; }
    public List<String> stockRef() { return stockRef; }
    public List<String> shortId() { return shortId; }
    public List<Integer> priority() { return priority; }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        b.sort = this.sort;
        b.facilityRef = this.facilityRef;
        b.status = this.status;
        b.tenantArticleId = this.tenantArticleId;
        b.locationRef = this.locationRef;
        b.stockRef = this.stockRef;
        b.shortId = this.shortId;
        b.priority = this.priority;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Integer size;
        private String startAfterId;
        private String sort;
        private List<String> facilityRef;
        private List<String> status;
        private List<String> tenantArticleId;
        private List<String> locationRef;
        private List<String> stockRef;
        private List<String> shortId;
        private List<Integer> priority;

        public Builder size(Integer size) { this.size = size; return this; }
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }
        public Builder sort(String sort) { this.sort = sort; return this; }
        public Builder facilityRef(List<String> facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder status(List<String> status) { this.status = status; return this; }
        public Builder tenantArticleId(List<String> tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }
        public Builder locationRef(List<String> locationRef) { this.locationRef = locationRef; return this; }
        public Builder stockRef(List<String> stockRef) { this.stockRef = stockRef; return this; }
        public Builder shortId(List<String> shortId) { this.shortId = shortId; return this; }
        public Builder priority(List<Integer> priority) { this.priority = priority; return this; }

        public StowJobListRequest build() { return new StowJobListRequest(this); }
    }
}
