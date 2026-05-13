package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.TenantArticleId;

import java.util.List;

/**
 * An allowed substitute article for an order line item.
 *
 * <p>Maps to the {@code Substitute} schema in the fulfillmenttools OpenAPI spec.
 * If the original article cannot be picked, the picker may select one of the configured
 * substitutes. Substitutes are ranked by {@code priority} (lower = more preferred).
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param tenantArticleId           The article identifier in the tenant system.
 * @param title                     The display title of the substitute article.
 * @param imageUrl                  Optional URL of the product image.
 * @param priority                  Rank among substitutes; lower value means more preferred.
 * @param measurementUnitKey        Optional unit of measurement identifier.
 * @param secondaryMeasurementUnitKey Optional secondary unit of measurement.
 * @param scannableCodes            Optional list of scannable codes identifying this article.
 * @param measurementValidation     Optional pick-quantity tolerance configuration.
 * @param attributes                Optional article attributes for display/customization.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Substitute(
        TenantArticleId tenantArticleId,
        String title,
        String imageUrl,
        Double priority,
        String measurementUnitKey,
        String secondaryMeasurementUnitKey,
        List<String> scannableCodes,
        MeasurementValidation measurementValidation,
        List<ArticleAttribute> attributes
) {}
