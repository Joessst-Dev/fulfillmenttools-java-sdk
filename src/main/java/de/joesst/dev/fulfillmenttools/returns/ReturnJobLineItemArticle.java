package de.joesst.dev.fulfillmenttools.returns;

import de.joesst.dev.fulfillmenttools.orders.ArticleAttribute;

import java.util.List;
import java.util.Map;

/**
 * An article attached to a return job line item.
 *
 * <p>Maps to the {@code ItemReturnJobLineItemArticle} schema (extends {@code AbstractArticle})
 * in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param tenantArticleId The tenant's article reference number. Required.
 * @param title           The article display title. Required.
 * @param imageUrl        Optional URL of the product image.
 * @param weight          Optional article weight in grams.
 * @param titleLocalized  Localized translations for the title, keyed by locale
 *                        (e.g. {@code en_US}).
 * @param attributes      Optional article attributes used for display and customization.
 * @param customAttributes Free-form custom attributes.
 */
public record ReturnJobLineItemArticle(
        String tenantArticleId,
        String title,
        String imageUrl,
        Double weight,
        Map<String, String> titleLocalized,
        List<ArticleAttribute> attributes,
        Map<String, Object> customAttributes
) {}
