package de.joesst.dev.fulfillmenttools.listings;

import java.util.Map;
import java.util.Objects;

public final class ListingUpsertItem {

    private final String facilityRef;
    private final String tenantArticleId;
    private final String title;
    private final Map<String, Object> stock;
    private final Map<String, Object> customAttributes;

    private ListingUpsertItem(Builder builder) {
        this.facilityRef = Objects.requireNonNull(builder.facilityRef, "facilityRef must not be null");
        this.tenantArticleId = Objects.requireNonNull(builder.tenantArticleId, "tenantArticleId must not be null");
        this.title = builder.title;
        this.stock = builder.stock;
        this.customAttributes = builder.customAttributes;
    }

    public String facilityRef() { return facilityRef; }
    public String tenantArticleId() { return tenantArticleId; }
    public String title() { return title; }
    public Map<String, Object> stock() { return stock; }
    public Map<String, Object> customAttributes() { return customAttributes; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String facilityRef;
        private String tenantArticleId;
        private String title;
        private Map<String, Object> stock;
        private Map<String, Object> customAttributes;

        public Builder facilityRef(String facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder tenantArticleId(String tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder stock(Map<String, Object> stock) { this.stock = stock; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public ListingUpsertItem build() { return new ListingUpsertItem(this); }
    }
}
