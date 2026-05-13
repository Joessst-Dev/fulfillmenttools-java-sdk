package de.joesst.dev.fulfillmenttools.handoverjobs;

import de.joesst.dev.fulfillmenttools.id.TenantArticleId;
import de.joesst.dev.fulfillmenttools.orders.ArticleAttribute;

import java.util.List;
import java.util.Map;

/**
 * The article associated with a handover job line item.
 *
 * <p>Maps to the {@code HandoverLineItemArticle} schema in the fulfillmenttools OpenAPI spec,
 * which extends {@code AbstractArticle} with an optional {@code attributes} list
 * (maximum 40 items).
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param tenantArticleId  Tenant-specific article reference number. Required.
 * @param title            Display title of the article. Required.
 * @param imageUrl         Optional URL of the product image; no authentication required.
 * @param weight           Optional weight in grams.
 * @param titleLocalized   Localized translations for the title, keyed by locale
 *                         (e.g. {@code en_US}).
 * @param customAttributes Free-form custom attributes attached to the article.
 * @param attributes       Optional article attributes for display and process customization.
 *                         Maximum 40 items.
 */
public record HandoverLineItemArticle(
        TenantArticleId tenantArticleId,
        String title,
        String imageUrl,
        Double weight,
        Map<String, String> titleLocalized,
        Map<String, Object> customAttributes,
        List<ArticleAttribute> attributes
) {}
