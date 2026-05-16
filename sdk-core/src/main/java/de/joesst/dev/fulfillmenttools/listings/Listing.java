package de.joesst.dev.fulfillmenttools.listings;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.ListingId;
import de.joesst.dev.fulfillmenttools.id.TenantArticleId;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;
import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.orders.ArticleAttribute;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * A listing represents an article that is offered in a specific facility.
 *
 * <p>Maps to the {@code Listing} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                          Unique identifier of this listing.
 * @param version                     Optimistic locking version.
 * @param created                     Timestamp when this listing was created.
 * @param lastModified                Timestamp when this listing was last modified.
 * @param facilityId                  Reference to the facility this listing belongs to.
 * @param tenantArticleId             Tenant-assigned article identifier.
 * @param status                      Listing status: {@code ACTIVE} or {@code INACTIVE}.
 * @param title                       Human-readable title of the article.
 * @param titleLocalized              Locale-keyed translations of the title (e.g. {@code en_US}).
 * @param imageUrl                    URL to an image of the article (no auth required).
 * @param measurementUnitKey          Identifier for the article's unit of measurement.
 * @param outOfStockBehaviour         Default out-of-stock behaviour for this listing.
 * @param currency                    ISO 4217 currency code. Deprecated — use attributes instead.
 * @param price                       Article price. Deprecated — use attributes instead.
 * @param weight                      Article weight. Deprecated — use attributes instead.
 * @param categoryRefs                References to categories the listing belongs to.
 * @param scannableCodes              Barcodes that identify this article.
 * @param attributes                  Custom attributes attached to this listing.
 * @param recordableAttributes        Attributes whose values are recorded during picking.
 * @param outOfStockBehaviourByContexts Context-specific out-of-stock behaviour overrides.
 * @param partialStocks               Deprecated partial stock entries. Use {@code /api/stocks}.
 * @param tags                        Tag references attached to this listing.
 * @param legal                       Legal information (e.g. HS code for customs).
 * @param outOfStockConfig            Configuration for PREORDER/RESTOCK behaviours.
 * @param scanningRule                Scanning configuration for this listing.
 * @param stockAvailableUntil         How the "available until" date for stock is calculated.
 * @param stockinformation            Deprecated stock information. Use {@code /api/stocks}.
 * @param stockProperties             Key-value definitions for recordable stock properties.
 * @param availabilityTimeframe       Deprecated availability window. Use {@code outOfStockConfig}.
 * @param customAttributes            Arbitrary key-value pairs for caller-defined metadata.
 *                                    Not usable within fulfillment processes.
 */
public record Listing(
        ListingId id,
        Integer version,
        Instant created,
        Instant lastModified,
        FacilityId facilityId,
        TenantArticleId tenantArticleId,
        String status,
        String title,
        Map<String, String> titleLocalized,
        String imageUrl,
        String measurementUnitKey,
        String outOfStockBehaviour,
        String currency,
        Double price,
        Double weight,
        List<String> categoryRefs,
        List<String> scannableCodes,
        List<ArticleAttribute> attributes,
        List<ListingRecordableAttribute> recordableAttributes,
        List<ListingOutOfStockBehaviourByContext> outOfStockBehaviourByContexts,
        List<ListingPartialStock> partialStocks,
        List<TagReference> tags,
        ListingLegal legal,
        ListingOutOfStockConfig outOfStockConfig,
        ListingScanningRule scanningRule,
        ListingStockAvailableUntil stockAvailableUntil,
        ListingStockInformation stockinformation,
        Map<String, ListingStockPropertyDefinition> stockProperties,
        ListingAvailabilityTimeframe availabilityTimeframe,
        CustomAttributes customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private ListingId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private FacilityId facilityId;
        private TenantArticleId tenantArticleId;
        private String status;
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

        public Builder id(ListingId id) { this.id = id; return this; }
        public Builder version(Integer version) { this.version = version; return this; }
        public Builder created(Instant created) { this.created = created; return this; }
        public Builder lastModified(Instant lastModified) { this.lastModified = lastModified; return this; }
        public Builder facilityId(FacilityId facilityId) { this.facilityId = facilityId; return this; }
        public Builder tenantArticleId(TenantArticleId tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }
        public Builder status(String status) { this.status = status; return this; }
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
        public Builder tags(List<TagReference> tags) { this.tags = tags; return this; }
        public Builder legal(ListingLegal legal) { this.legal = legal; return this; }
        public Builder outOfStockConfig(ListingOutOfStockConfig outOfStockConfig) { this.outOfStockConfig = outOfStockConfig; return this; }
        public Builder scanningRule(ListingScanningRule scanningRule) { this.scanningRule = scanningRule; return this; }
        public Builder stockAvailableUntil(ListingStockAvailableUntil stockAvailableUntil) { this.stockAvailableUntil = stockAvailableUntil; return this; }
        public Builder stockinformation(ListingStockInformation stockinformation) { this.stockinformation = stockinformation; return this; }
        public Builder stockProperties(Map<String, ListingStockPropertyDefinition> stockProperties) { this.stockProperties = stockProperties; return this; }
        public Builder availabilityTimeframe(ListingAvailabilityTimeframe availabilityTimeframe) { this.availabilityTimeframe = availabilityTimeframe; return this; }
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        public Listing build() {
            return new Listing(id, version, created, lastModified, facilityId, tenantArticleId, status, title, titleLocalized, imageUrl, measurementUnitKey, outOfStockBehaviour, currency, price, weight, categoryRefs, scannableCodes, attributes, recordableAttributes, outOfStockBehaviourByContexts, partialStocks, tags, legal, outOfStockConfig, scanningRule, stockAvailableUntil, stockinformation, stockProperties, availabilityTimeframe, customAttributes);
        }
    }
}
