package de.joesst.dev.fulfillmenttools.listings;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.TenantArticleId;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;
import de.joesst.dev.fulfillmenttools.model.TagReference;
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

    private final FacilityId facilityId;
    private final TenantArticleId tenantArticleId;
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
    private final List<TagReference> tags;
    private final ListingLegal legal;
    private final ListingOutOfStockConfig outOfStockConfig;
    private final ListingScanningRule scanningRule;
    private final ListingStockAvailableUntil stockAvailableUntil;
    private final ListingStockInformation stockinformation;
    private final Map<String, ListingStockPropertyDefinition> stockProperties;
    private final ListingAvailabilityTimeframe availabilityTimeframe;
    private final CustomAttributes customAttributes;

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

    /**
     * Returns the facility this listing belongs to.
     * @return the facility ID; never {@code null}
     */
    public FacilityId facilityId() { return facilityId; }

    /**
     * Returns the tenant-assigned article identifier.
     * @return the tenant article ID; never {@code null}
     */
    public TenantArticleId tenantArticleId() { return tenantArticleId; }

    /**
     * Returns the human-readable title of the article.
     * @return the title, or {@code null} if not set
     */
    public String title() { return title; }

    /**
     * Returns the locale-keyed translations of the title.
     * @return the localized titles, or {@code null} if not set
     */
    public Map<String, String> titleLocalized() { return titleLocalized; }

    /**
     * Returns the URL to an image of the article.
     * @return the image URL, or {@code null} if not set
     */
    public String imageUrl() { return imageUrl; }

    /**
     * Returns the identifier for the article's unit of measurement.
     * @return the measurement unit key, or {@code null} if not set
     */
    public String measurementUnitKey() { return measurementUnitKey; }

    /**
     * Returns the default out-of-stock behaviour.
     * @return the out-of-stock behaviour, or {@code null} if not set
     */
    public String outOfStockBehaviour() { return outOfStockBehaviour; }

    /**
     * Returns the ISO 4217 currency code.
     * @deprecated use attributes instead
     * @return the currency, or {@code null} if not set
     */
    @Deprecated
    public String currency() { return currency; }

    /**
     * Returns the article price.
     * @deprecated use attributes instead
     * @return the price, or {@code null} if not set
     */
    @Deprecated
    public Double price() { return price; }

    /**
     * Returns the article weight.
     * @deprecated use attributes instead
     * @return the weight, or {@code null} if not set
     */
    @Deprecated
    public Double weight() { return weight; }

    /**
     * Returns the category references for this listing.
     * @return the category references, or {@code null} if not set
     */
    public List<String> categoryRefs() { return categoryRefs; }

    /**
     * Returns the barcodes that identify this article.
     * @return the scannable codes, or {@code null} if not set
     */
    public List<String> scannableCodes() { return scannableCodes; }

    /**
     * Returns the custom attributes attached to this listing.
     * @return the attributes, or {@code null} if not set
     */
    public List<ArticleAttribute> attributes() { return attributes; }

    /**
     * Returns the attributes whose values are recorded during picking.
     * @return the recordable attributes, or {@code null} if not set
     */
    public List<ListingRecordableAttribute> recordableAttributes() { return recordableAttributes; }

    /**
     * Returns the context-specific out-of-stock behaviour overrides.
     * @return the out-of-stock behaviour by context, or {@code null} if not set
     */
    public List<ListingOutOfStockBehaviourByContext> outOfStockBehaviourByContexts() { return outOfStockBehaviourByContexts; }

    /**
     * Returns the deprecated partial stock entries.
     * @deprecated use {@code /api/stocks} instead
     * @return the partial stocks, or {@code null} if not set
     */
    @Deprecated
    public List<ListingPartialStock> partialStocks() { return partialStocks; }

    /**
     * Returns the tag references attached to this listing.
     * @return the tags, or {@code null} if not set
     */
    public List<TagReference> tags() { return tags; }

    /**
     * Returns the legal information for this listing.
     * @return the legal info, or {@code null} if not set
     */
    public ListingLegal legal() { return legal; }

    /**
     * Returns the configuration for PREORDER/RESTOCK out-of-stock behaviours.
     * @return the out-of-stock config, or {@code null} if not set
     */
    public ListingOutOfStockConfig outOfStockConfig() { return outOfStockConfig; }

    /**
     * Returns the scanning configuration for this listing.
     * @return the scanning rule, or {@code null} if not set
     */
    public ListingScanningRule scanningRule() { return scanningRule; }

    /**
     * Returns the definition of how the "available until" date is calculated.
     * @return the stock available until config, or {@code null} if not set
     */
    public ListingStockAvailableUntil stockAvailableUntil() { return stockAvailableUntil; }

    /**
     * Returns the deprecated stock information.
     * @deprecated use {@code /api/stocks} instead
     * @return the stock information, or {@code null} if not set
     */
    @Deprecated
    public ListingStockInformation stockinformation() { return stockinformation; }

    /**
     * Returns the key-value definitions for recordable stock properties.
     * @return the stock property definitions, or {@code null} if not set
     */
    public Map<String, ListingStockPropertyDefinition> stockProperties() { return stockProperties; }

    /**
     * Returns the deprecated availability timeframe.
     * @deprecated use {@code outOfStockConfig} instead
     * @return the availability timeframe, or {@code null} if not set
     */
    @Deprecated
    public ListingAvailabilityTimeframe availabilityTimeframe() { return availabilityTimeframe; }

    /**
     * Returns the arbitrary caller-defined metadata.
     * @return the custom attributes, or {@code null} if not set
     */
    public CustomAttributes customAttributes() { return customAttributes; }

    /**
     * Creates a new {@link Builder} for constructing a {@link ListingUpsertItem}.
     * @return a new builder instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link ListingUpsertItem}.
     */
    public static final class Builder {
        private FacilityId facilityId;
        private TenantArticleId tenantArticleId;
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
        private List<TagReference> tags;
        private ListingLegal legal;
        private ListingOutOfStockConfig outOfStockConfig;
        private ListingScanningRule scanningRule;
        private ListingStockAvailableUntil stockAvailableUntil;
        private ListingStockInformation stockinformation;
        private Map<String, ListingStockPropertyDefinition> stockProperties;
        private ListingAvailabilityTimeframe availabilityTimeframe;
        private CustomAttributes customAttributes;

        private Builder() {}

        /**
         * Sets the facility ID (required).
         * @param facilityId the facility ID
         * @return this builder
         */
        public Builder facilityId(FacilityId facilityId) { this.facilityId = facilityId; return this; }

        /**
         * Sets the tenant article ID (required).
         * @param tenantArticleId the tenant article ID
         * @return this builder
         */
        public Builder tenantArticleId(TenantArticleId tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }

        /**
         * Sets the human-readable title of the article.
         * @param title the title
         * @return this builder
         */
        public Builder title(String title) { this.title = title; return this; }

        /**
         * Sets the locale-keyed translations of the title.
         * @param titleLocalized the localized titles
         * @return this builder
         */
        public Builder titleLocalized(Map<String, String> titleLocalized) { this.titleLocalized = titleLocalized; return this; }

        /**
         * Sets the URL to an image of the article.
         * @param imageUrl the image URL
         * @return this builder
         */
        public Builder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }

        /**
         * Sets the identifier for the article's unit of measurement.
         * @param measurementUnitKey the measurement unit key
         * @return this builder
         */
        public Builder measurementUnitKey(String measurementUnitKey) { this.measurementUnitKey = measurementUnitKey; return this; }

        /**
         * Sets the default out-of-stock behaviour.
         * @param outOfStockBehaviour the out-of-stock behaviour
         * @return this builder
         */
        public Builder outOfStockBehaviour(String outOfStockBehaviour) { this.outOfStockBehaviour = outOfStockBehaviour; return this; }

        /**
         * Sets the ISO 4217 currency code.
         * @param currency the currency code
         * @return this builder
         */
        public Builder currency(String currency) { this.currency = currency; return this; }

        /**
         * Sets the article price.
         * @param price the price
         * @return this builder
         */
        public Builder price(Double price) { this.price = price; return this; }

        /**
         * Sets the article weight.
         * @param weight the weight
         * @return this builder
         */
        public Builder weight(Double weight) { this.weight = weight; return this; }

        /**
         * Sets the category references for this listing.
         * @param categoryRefs the category references
         * @return this builder
         */
        public Builder categoryRefs(List<String> categoryRefs) { this.categoryRefs = categoryRefs; return this; }

        /**
         * Sets the barcodes that identify this article.
         * @param scannableCodes the scannable codes
         * @return this builder
         */
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }

        /**
         * Sets the custom attributes attached to this listing.
         * @param attributes the attributes
         * @return this builder
         */
        public Builder attributes(List<ArticleAttribute> attributes) { this.attributes = attributes; return this; }

        /**
         * Sets the attributes whose values are recorded during picking.
         * @param recordableAttributes the recordable attributes
         * @return this builder
         */
        public Builder recordableAttributes(List<ListingRecordableAttribute> recordableAttributes) { this.recordableAttributes = recordableAttributes; return this; }

        /**
         * Sets the context-specific out-of-stock behaviour overrides.
         * @param outOfStockBehaviourByContexts the out-of-stock behaviour by context
         * @return this builder
         */
        public Builder outOfStockBehaviourByContexts(List<ListingOutOfStockBehaviourByContext> outOfStockBehaviourByContexts) { this.outOfStockBehaviourByContexts = outOfStockBehaviourByContexts; return this; }

        /**
         * Sets the partial stock entries.
         * @param partialStocks the partial stocks
         * @return this builder
         */
        public Builder partialStocks(List<ListingPartialStock> partialStocks) { this.partialStocks = partialStocks; return this; }

        /**
         * Sets the tag references attached to this listing.
         * @param tags the tags
         * @return this builder
         */
        public Builder tags(List<TagReference> tags) { this.tags = tags; return this; }

        /**
         * Sets the legal information for this listing.
         * @param legal the legal info
         * @return this builder
         */
        public Builder legal(ListingLegal legal) { this.legal = legal; return this; }

        /**
         * Sets the configuration for PREORDER/RESTOCK out-of-stock behaviours.
         * @param outOfStockConfig the out-of-stock config
         * @return this builder
         */
        public Builder outOfStockConfig(ListingOutOfStockConfig outOfStockConfig) { this.outOfStockConfig = outOfStockConfig; return this; }

        /**
         * Sets the scanning configuration for this listing.
         * @param scanningRule the scanning rule
         * @return this builder
         */
        public Builder scanningRule(ListingScanningRule scanningRule) { this.scanningRule = scanningRule; return this; }

        /**
         * Sets the definition of how the "available until" date is calculated.
         * @param stockAvailableUntil the stock available until config
         * @return this builder
         */
        public Builder stockAvailableUntil(ListingStockAvailableUntil stockAvailableUntil) { this.stockAvailableUntil = stockAvailableUntil; return this; }

        /**
         * Sets the stock information.
         * @param stockinformation the stock information
         * @return this builder
         */
        public Builder stockinformation(ListingStockInformation stockinformation) { this.stockinformation = stockinformation; return this; }

        /**
         * Sets the key-value definitions for recordable stock properties.
         * @param stockProperties the stock property definitions
         * @return this builder
         */
        public Builder stockProperties(Map<String, ListingStockPropertyDefinition> stockProperties) { this.stockProperties = stockProperties; return this; }

        /**
         * Sets the availability timeframe.
         * @param availabilityTimeframe the availability timeframe
         * @return this builder
         */
        public Builder availabilityTimeframe(ListingAvailabilityTimeframe availabilityTimeframe) { this.availabilityTimeframe = availabilityTimeframe; return this; }

        /**
         * Sets the arbitrary caller-defined metadata.
         * @param customAttributes the custom attributes
         * @return this builder
         */
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        /**
         * Builds the {@link ListingUpsertItem}.
         * @return a new immutable {@link ListingUpsertItem}
         * @throws NullPointerException if {@code facilityId} or {@code tenantArticleId} is null
         */
        public ListingUpsertItem build() { return new ListingUpsertItem(this); }
    }
}
