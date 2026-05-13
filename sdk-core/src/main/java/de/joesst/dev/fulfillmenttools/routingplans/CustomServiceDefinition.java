package de.joesst.dev.fulfillmenttools.routingplans;

import java.util.List;
import java.util.Map;

/**
 * The definition of a custom service applied to order line items.
 *
 * <p>Maps to the {@code CustomServiceDefinition} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param customServiceRef         Reference to the custom service entity.
 * @param tenantCustomServiceId    The tenant's ID for this custom service.
 * @param isBundled                If {@code true}, all articles under this service form a bundle.
 * @param additionalInformation    Additional information required to fulfil the custom service.
 * @param customAttributes         Free-form custom attributes.
 */
public record CustomServiceDefinition(
        String customServiceRef,
        String tenantCustomServiceId,
        Boolean isBundled,
        List<CustomServiceAdditionalInformation> additionalInformation,
        Map<String, Object> customAttributes
) {}
