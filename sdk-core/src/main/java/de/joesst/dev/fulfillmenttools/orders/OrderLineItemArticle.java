package de.joesst.dev.fulfillmenttools.orders;

import de.joesst.dev.fulfillmenttools.id.TenantArticleId;

import java.util.List;
import java.util.Map;

/**
 * An article on a read-side order line item.
 *
 * <p>Maps to the {@code OrderLineItemArticle} schema (extends {@code AbstractArticle}) in the
 * fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param tenantArticleId The tenant's article reference number.
 * @param title           The article display title.
 * @param imageUrl        Optional URL of the product image.
 * @param weight          Optional article weight in grams.
 * @param titleLocalized  Localized translations for the title, keyed by locale (e.g.
 *                        {@code en_US}).
 * @param attributes      Optional article attributes used for display and customization.
 * @param customAttributes Free-form custom attributes (deprecated on article level; prefer
 *                         {@code customAttributes} on the line item).
 */
public record OrderLineItemArticle(
        TenantArticleId tenantArticleId,
        String title,
        String imageUrl,
        Double weight,
        Map<String, String> titleLocalized,
        List<ArticleAttribute> attributes,
        Map<String, Object> customAttributes
) {}
