package de.joesst.dev.fulfillmenttools.listings;

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
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String facilityId,
        String tenantArticleId,
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
        List<ListingTag> tags,
        ListingLegal legal,
        ListingOutOfStockConfig outOfStockConfig,
        ListingScanningRule scanningRule,
        ListingStockAvailableUntil stockAvailableUntil,
        ListingStockInformation stockinformation,
        Map<String, ListingStockPropertyDefinition> stockProperties,
        ListingAvailabilityTimeframe availabilityTimeframe,
        Map<String, Object> customAttributes
) {}
