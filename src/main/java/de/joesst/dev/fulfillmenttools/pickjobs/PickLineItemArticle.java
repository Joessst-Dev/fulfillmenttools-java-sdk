package de.joesst.dev.fulfillmenttools.pickjobs;

import de.joesst.dev.fulfillmenttools.id.TenantArticleId;
import de.joesst.dev.fulfillmenttools.orders.ArticleAttribute;

import java.util.List;
import java.util.Map;

/**
 * The article associated with a pick line item.
 *
 * <p>Maps to the {@code PickLineItemArticle} schema (which extends {@code LineItemArticle})
 * in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param tenantArticleId  The tenant-specific article reference number.
 * @param title            The display title of the article.
 * @param imageUrl         Optional URL of the product image; no authentication required.
 * @param weight           Optional weight in grams.
 * @param titleLocalized   Localized translations for the title, keyed by locale
 *                         (e.g. {@code en_US}).
 * @param attributes       Optional article attributes for display and process customization.
 * @param customAttributes Deprecated: use {@code customAttributes} on the line item instead.
 *                         Retained for backward compatibility.
 */
public record PickLineItemArticle(
        TenantArticleId tenantArticleId,
        String title,
        String imageUrl,
        Double weight,
        Map<String, String> titleLocalized,
        List<ArticleAttribute> attributes,
        Map<String, Object> customAttributes
) {}
