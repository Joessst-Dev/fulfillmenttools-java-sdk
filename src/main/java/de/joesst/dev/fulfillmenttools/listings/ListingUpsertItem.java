package de.joesst.dev.fulfillmenttools.listings;

import de.joesst.dev.fulfillmenttools.orders.ArticleAttribute;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Describes a single listing entry to create or replace in a bulk upsert operation.
 *
 * <p>Maps to the {@code ListingForCreation} / {@code ListingForBulkUpsertByFacility} schemas
 * in the fulfillmenttools OpenAPI spec.
 *
 * <p>Instances are constructed via {@link Builder}; both {@code facilityId} and
 * {@code tenantArticleId} are required.
 *
 * <p>Thread-safety: effectively immutable after construction; safe for concurrent use.
 */
public final class ListingUpsertItem {

    private final String facilityId;
    private final String tenantArticleId;
    private final String title;
    private final Map<String, String> titleLocalized;
    private final String imageUrl;
    private final String measurementUnitKey;
    private final String outOfStockBehaviour;
    private final String currency;
    private final Double price;
    private final Double weight;
    private final List<String> categoryRefs;
    private final List<String> scannableCodes;
    private final List<ArticleAttribute> attributes;
    private final List<ListingRecordableAttribute> recordableAttributes;
    private final List<ListingOutOfStockBehaviourByContext> outOfStockBehaviourByContexts;
    private final List<ListingPartialStock> partialStocks;
    private final List<ListingTag> tags;
    private final ListingLegal legal;
    private final ListingOutOfStockConfig outOfStockConfig;
    private final ListingScanningRule scanningRule;
    private final ListingStockAvailableUntil stockAvailableUntil;
    private final ListingStockInformation stockinformation;
    private final Map<String, ListingStockPropertyDefinition> stockProperties;
    private final ListingAvailabilityTimeframe availabilityTimeframe;
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

    /** @return The facility this listing belongs to. */
    public String facilityId() { return facilityId; }

    /** @return The tenant-assigned article identifier. */
    public String tenantArticleId() { return tenantArticleId; }

    /** @return The human-readable title of the article. */
    public String title() { return title; }

    /** @return Locale-keyed translations of the title. */
    public Map<String, String> titleLocalized() { return titleLocalized; }

    /** @return URL to an image of the article. */
    public String imageUrl() { return imageUrl; }

    /** @return Identifier for the article's unit of measurement. */
    public String measurementUnitKey() { return measurementUnitKey; }

    /** @return Default out-of-stock behaviour. */
    public String outOfStockBehaviour() { return outOfStockBehaviour; }

    /** @return ISO 4217 currency code. Deprecated — use attributes instead. */
    public String currency() { return currency; }

    /** @return Article price. Deprecated — use attributes instead. */
    public Double price() { return price; }

    /** @return Article weight. Deprecated — use attributes instead. */
    public Double weight() { return weight; }

    /** @return Category references for this listing. */
    public List<String> categoryRefs() { return categoryRefs; }

    /** @return Barcodes that identify this article. */
    public List<String> scannableCodes() { return scannableCodes; }

    /** @return Custom attributes attached to this listing. */
    public List<ArticleAttribute> attributes() { return attributes; }

    /** @return Attributes whose values are recorded during picking. */
    public List<ListingRecordableAttribute> recordableAttributes() { return recordableAttributes; }

    /** @return Context-specific out-of-stock behaviour overrides. */
    public List<ListingOutOfStockBehaviourByContext> outOfStockBehaviourByContexts() { return outOfStockBehaviourByContexts; }

    /** @return Deprecated partial stock entries. Use {@code /api/stocks} instead. */
    public List<ListingPartialStock> partialStocks() { return partialStocks; }

    /** @return Tag references attached to this listing. */
    public List<ListingTag> tags() { return tags; }

    /** @return Legal information for this listing. */
    public ListingLegal legal() { return legal; }

    /** @return Configuration for PREORDER/RESTOCK out-of-stock behaviours. */
    public ListingOutOfStockConfig outOfStockConfig() { return outOfStockConfig; }

    /** @return Scanning configuration for this listing. */
    public ListingScanningRule scanningRule() { return scanningRule; }

    /** @return Definition of how the "available until" date is calculated. */
    public ListingStockAvailableUntil stockAvailableUntil() { return stockAvailableUntil; }

    /** @return Deprecated stock information. Use {@code /api/stocks} instead. */
    public ListingStockInformation stockinformation() { return stockinformation; }

    /** @return Key-value definitions for recordable stock properties. */
    public Map<String, ListingStockPropertyDefinition> stockProperties() { return stockProperties; }

    /** @return Deprecated availability timeframe. Use {@code outOfStockConfig} instead. */
    public ListingAvailabilityTimeframe availabilityTimeframe() { return availabilityTimeframe; }

    /** @return Arbitrary caller-defined metadata; not usable in fulfillment processes. */
    public Map<String, Object> customAttributes() { return customAttributes; }

    /**
     * Creates a new {@link Builder} for constructing a {@link ListingUpsertItem}.
     *
     * @return a new builder instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link ListingUpsertItem}.
     */
    public static final class Builder {
        private String facilityId;
        private String tenantArticleId;
        private String title;
        private Map<String, String> titleLocalized;
        private String imageUrl;
        private String measurementUnitKey;
        private String outOfStockBehaviour;
        private String currency;
        private Double price;
        private Double weight;
        private List<String> categoryRefs;
        private List<String> scannableCodes;
        private List<ArticleAttribute> attributes;
        private List<ListingRecordableAttribute> recordableAttributes;
        private List<ListingOutOfStockBehaviourByContext> outOfStockBehaviourByContexts;
        private List<ListingPartialStock> partialStocks;
        private List<ListingTag> tags;
        private ListingLegal legal;
        private ListingOutOfStockConfig outOfStockConfig;
        private ListingScanningRule scanningRule;
        private ListingStockAvailableUntil stockAvailableUntil;
        private ListingStockInformation stockinformation;
        private Map<String, ListingStockPropertyDefinition> stockProperties;
        private ListingAvailabilityTimeframe availabilityTimeframe;
        private Map<String, Object> customAttributes;

        private Builder() {}

        public Builder facilityId(String facilityId) { this.facilityId = facilityId; return this; }
        public Builder tenantArticleId(String tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder titleLocalized(Map<String, String> titleLocalized) { this.titleLocalized = titleLocalized; return this; }
        public Builder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }
        public Builder measurementUnitKey(String measurementUnitKey) { this.measurementUnitKey = measurementUnitKey; return this; }
        public Builder outOfStockBehaviour(String outOfStockBehaviour) { this.outOfStockBehaviour = outOfStockBehaviour; return this; }
        public Builder currency(String currency) { this.currency = currency; return this; }
        public Builder price(Double price) { this.price = price; return this; }
        public Builder weight(Double weight) { this.weight = weight; return this; }
        public Builder categoryRefs(List<String> categoryRefs) { this.categoryRefs = categoryRefs; return this; }
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }
        public Builder attributes(List<ArticleAttribute> attributes) { this.attributes = attributes; return this; }
        public Builder recordableAttributes(List<ListingRecordableAttribute> recordableAttributes) { this.recordableAttributes = recordableAttributes; return this; }
        public Builder outOfStockBehaviourByContexts(List<ListingOutOfStockBehaviourByContext> outOfStockBehaviourByContexts) { this.outOfStockBehaviourByContexts = outOfStockBehaviourByContexts; return this; }
        public Builder partialStocks(List<ListingPartialStock> partialStocks) { this.partialStocks = partialStocks; return this; }
        public Builder tags(List<ListingTag> tags) { this.tags = tags; return this; }
        public Builder legal(ListingLegal legal) { this.legal = legal; return this; }
        public Builder outOfStockConfig(ListingOutOfStockConfig outOfStockConfig) { this.outOfStockConfig = outOfStockConfig; return this; }
        public Builder scanningRule(ListingScanningRule scanningRule) { this.scanningRule = scanningRule; return this; }
        public Builder stockAvailableUntil(ListingStockAvailableUntil stockAvailableUntil) { this.stockAvailableUntil = stockAvailableUntil; return this; }
        public Builder stockinformation(ListingStockInformation stockinformation) { this.stockinformation = stockinformation; return this; }
        public Builder stockProperties(Map<String, ListingStockPropertyDefinition> stockProperties) { this.stockProperties = stockProperties; return this; }
        public Builder availabilityTimeframe(ListingAvailabilityTimeframe availabilityTimeframe) { this.availabilityTimeframe = availabilityTimeframe; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        /**
         * Builds the {@link ListingUpsertItem}.
         *
         * @return a new immutable {@link ListingUpsertItem}
         * @throws NullPointerException if {@code facilityId} or {@code tenantArticleId} is null
         */
        public ListingUpsertItem build() { return new ListingUpsertItem(this); }
    }
}
