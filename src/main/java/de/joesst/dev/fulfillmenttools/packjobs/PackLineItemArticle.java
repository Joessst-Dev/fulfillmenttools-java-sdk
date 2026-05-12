package de.joesst.dev.fulfillmenttools.packjobs;

import de.joesst.dev.fulfillmenttools.orders.ArticleAttribute;

import java.util.List;
import java.util.Map;

/**
 * The article associated with a pack line item.
 *
 * <p>Maps to the {@code PackLineItemArticle} schema in the fulfillmenttools OpenAPI spec,
 * which is an extension of {@code AbstractArticle}.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param tenantArticleId Tenant-side article identifier (e.g. {@code "4711"}).
 * @param title           Display title of the article.
 * @param imageUrl        URL to an image of the article (no authentication required).
 * @param weight          Weight of the article in grams.
 * @param titleLocalized  Localized translations for the article title, keyed by locale
 *                        (e.g. {@code "en_US"}).
 * @param customAttributes Free-form custom attributes (not used in fulfillment processes).
 * @param attributes      Structured attributes providing additional metadata for the article
 *                        (e.g. subtitle, sales price, dimensions). Up to 40 items.
 */
public record PackLineItemArticle(
        String tenantArticleId,
        String title,
        String imageUrl,
        Double weight,
        Map<String, String> titleLocalized,
        Map<String, Object> customAttributes,
        List<ArticleAttribute> attributes
) {}
