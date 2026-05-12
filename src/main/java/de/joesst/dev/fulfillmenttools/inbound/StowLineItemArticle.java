package de.joesst.dev.fulfillmenttools.inbound;

import java.util.List;
import java.util.Map;

/**
 * The article associated with a stow line item.
 *
 * <p>Maps to the {@code StowLineItemArticle} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param tenantArticleId   Tenant-specific article identifier that the stow line item refers to.
 * @param title             Display title of the article.
 * @param imageUrl          Optional URL to a product image. No authentication must be required
 *                          to fetch the image.
 * @param measurementUnitKey Identifier for the article's unit of measurement.
 * @param customAttributes  Free-form custom attributes.
 * @param titleLocalized    Localized translations for the article title, keyed by locale
 *                          (e.g. {@code en_US}).
 * @param scannableCodes    Barcodes or other scannable codes related to the article.
 */
public record StowLineItemArticle(
        String tenantArticleId,
        String title,
        String imageUrl,
        String measurementUnitKey,
        Map<String, Object> customAttributes,
        Map<String, String> titleLocalized,
        List<String> scannableCodes
) {}
