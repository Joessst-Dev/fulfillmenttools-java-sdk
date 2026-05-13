package de.joesst.dev.fulfillmenttools.inbound;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

/**
 * A stow line item as provided in a {@link CreateStowJobRequest}.
 *
 * <p>Maps to the {@code StowLineItemForCreation} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param article          The article to be stowed. Required.
 * @param stowTo           Instructions on where and how to stow the article. Required.
 * @param takeFrom         Instructions on where and how to take the article from. Required.
 * @param reasons          Optional reasons for the stow operation (at most 10).
 * @param customAttributes Free-form custom attributes.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StowLineItemForCreation(
        StowLineItemArticle article,
        List<StowLineItemStowToForCreation> stowTo,
        StowLineItemTakeFrom takeFrom,
        List<ExternalStockChangeReasonInput> reasons,
        Map<String, Object> customAttributes
) {}
