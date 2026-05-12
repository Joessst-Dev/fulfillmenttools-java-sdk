package de.joesst.dev.fulfillmenttools.listings;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class ListingUpsertItem {

    private final String facilityId;
    private final String tenantArticleId;
    private final String title;
    private final Map<String, Object> titleLocalized;
    private final String imageUrl;
    private final String measurementUnitKey;
    private final String outOfStockBehaviour;
    private final String currency;
    private final Double price;
    private final Double weight;
    private final List<String> categoryRefs;
    private final List<String> scannableCodes;
    private final List<Map<String, Object>> attributes;
    private final List<Map<String, Object>> recordableAttributes;
    private final List<Map<String, Object>> outOfStockBehaviourByContexts;
    private final List<Map<String, Object>> partialStocks;
    private final List<Map<String, Object>> tags;
    private final Map<String, Object> legal;
    private final Map<String, Object> outOfStockConfig;
    private final Map<String, Object> scanningRule;
    private final Map<String, Object> stockAvailableUntil;
    private final Map<String, Object> stockinformation;
    private final Map<String, Object> stockProperties;
    private final Map<String, Object> availabilityTimeframe;
    private final Map<String, Object> customAttributes;

    private ListingUpsertItem(Builder builder) {
        this.facilityId = Objects.requireNonNull(builder.facilityId, "facilityId must not be null");
        this.tenantArticleId = Objects.requireNonNull(builder.tenantArticleId, "tenantArticleId must not be null");
        this.title = builder.title;
        this.titleLocalized = builder.titleLocalized;
        this.imageUrl = builder.imageUrl;
        this.measurementUnitKey = builder.measurementUnitKey;
        this.outOfStockBehaviour = builder.outOfStockBehaviour;
        this.currency = builder.currency;
        this.price = builder.price;
        this.weight = builder.weight;
        this.categoryRefs = builder.categoryRefs;
        this.scannableCodes = builder.scannableCodes;
        this.attributes = builder.attributes;
        this.recordableAttributes = builder.recordableAttributes;
        this.outOfStockBehaviourByContexts = builder.outOfStockBehaviourByContexts;
        this.partialStocks = builder.partialStocks;
        this.tags = builder.tags;
        this.legal = builder.legal;
        this.outOfStockConfig = builder.outOfStockConfig;
        this.scanningRule = builder.scanningRule;
        this.stockAvailableUntil = builder.stockAvailableUntil;
        this.stockinformation = builder.stockinformation;
        this.stockProperties = builder.stockProperties;
        this.availabilityTimeframe = builder.availabilityTimeframe;
        this.customAttributes = builder.customAttributes;
    }

    public String facilityId() { return facilityId; }
    public String tenantArticleId() { return tenantArticleId; }
    public String title() { return title; }
    public Map<String, Object> titleLocalized() { return titleLocalized; }
    public String imageUrl() { return imageUrl; }
    public String measurementUnitKey() { return measurementUnitKey; }
    public String outOfStockBehaviour() { return outOfStockBehaviour; }
    public String currency() { return currency; }
    public Double price() { return price; }
    public Double weight() { return weight; }
    public List<String> categoryRefs() { return categoryRefs; }
    public List<String> scannableCodes() { return scannableCodes; }
    public List<Map<String, Object>> attributes() { return attributes; }
    public List<Map<String, Object>> recordableAttributes() { return recordableAttributes; }
    public List<Map<String, Object>> outOfStockBehaviourByContexts() { return outOfStockBehaviourByContexts; }
    public List<Map<String, Object>> partialStocks() { return partialStocks; }
    public List<Map<String, Object>> tags() { return tags; }
    public Map<String, Object> legal() { return legal; }
    public Map<String, Object> outOfStockConfig() { return outOfStockConfig; }
    public Map<String, Object> scanningRule() { return scanningRule; }
    public Map<String, Object> stockAvailableUntil() { return stockAvailableUntil; }
    public Map<String, Object> stockinformation() { return stockinformation; }
    public Map<String, Object> stockProperties() { return stockProperties; }
    public Map<String, Object> availabilityTimeframe() { return availabilityTimeframe; }
    public Map<String, Object> customAttributes() { return customAttributes; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String facilityId;
        private String tenantArticleId;
        private String title;
        private Map<String, Object> titleLocalized;
        private String imageUrl;
        private String measurementUnitKey;
        private String outOfStockBehaviour;
        private String currency;
        private Double price;
        private Double weight;
        private List<String> categoryRefs;
        private List<String> scannableCodes;
        private List<Map<String, Object>> attributes;
        private List<Map<String, Object>> recordableAttributes;
        private List<Map<String, Object>> outOfStockBehaviourByContexts;
        private List<Map<String, Object>> partialStocks;
        private List<Map<String, Object>> tags;
        private Map<String, Object> legal;
        private Map<String, Object> outOfStockConfig;
        private Map<String, Object> scanningRule;
        private Map<String, Object> stockAvailableUntil;
        private Map<String, Object> stockinformation;
        private Map<String, Object> stockProperties;
        private Map<String, Object> availabilityTimeframe;
        private Map<String, Object> customAttributes;

        public Builder facilityId(String facilityId) { this.facilityId = facilityId; return this; }
        public Builder tenantArticleId(String tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder titleLocalized(Map<String, Object> titleLocalized) { this.titleLocalized = titleLocalized; return this; }
        public Builder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }
        public Builder measurementUnitKey(String measurementUnitKey) { this.measurementUnitKey = measurementUnitKey; return this; }
        public Builder outOfStockBehaviour(String outOfStockBehaviour) { this.outOfStockBehaviour = outOfStockBehaviour; return this; }
        public Builder currency(String currency) { this.currency = currency; return this; }
        public Builder price(Double price) { this.price = price; return this; }
        public Builder weight(Double weight) { this.weight = weight; return this; }
        public Builder categoryRefs(List<String> categoryRefs) { this.categoryRefs = categoryRefs; return this; }
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }
        public Builder attributes(List<Map<String, Object>> attributes) { this.attributes = attributes; return this; }
        public Builder recordableAttributes(List<Map<String, Object>> recordableAttributes) { this.recordableAttributes = recordableAttributes; return this; }
        public Builder outOfStockBehaviourByContexts(List<Map<String, Object>> outOfStockBehaviourByContexts) { this.outOfStockBehaviourByContexts = outOfStockBehaviourByContexts; return this; }
        public Builder partialStocks(List<Map<String, Object>> partialStocks) { this.partialStocks = partialStocks; return this; }
        public Builder tags(List<Map<String, Object>> tags) { this.tags = tags; return this; }
        public Builder legal(Map<String, Object> legal) { this.legal = legal; return this; }
        public Builder outOfStockConfig(Map<String, Object> outOfStockConfig) { this.outOfStockConfig = outOfStockConfig; return this; }
        public Builder scanningRule(Map<String, Object> scanningRule) { this.scanningRule = scanningRule; return this; }
        public Builder stockAvailableUntil(Map<String, Object> stockAvailableUntil) { this.stockAvailableUntil = stockAvailableUntil; return this; }
        public Builder stockinformation(Map<String, Object> stockinformation) { this.stockinformation = stockinformation; return this; }
        public Builder stockProperties(Map<String, Object> stockProperties) { this.stockProperties = stockProperties; return this; }
        public Builder availabilityTimeframe(Map<String, Object> availabilityTimeframe) { this.availabilityTimeframe = availabilityTimeframe; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public ListingUpsertItem build() { return new ListingUpsertItem(this); }
    }
}
