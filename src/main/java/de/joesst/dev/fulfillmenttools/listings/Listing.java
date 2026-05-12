package de.joesst.dev.fulfillmenttools.listings;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record Listing(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String facilityId,
        String tenantArticleId,
        String status,
        String title,
        Map<String, Object> titleLocalized,
        String imageUrl,
        String measurementUnitKey,
        String outOfStockBehaviour,
        String currency,
        Double price,
        Double weight,
        List<String> categoryRefs,
        List<String> scannableCodes,
        List<Map<String, Object>> attributes,
        List<Map<String, Object>> recordableAttributes,
        List<Map<String, Object>> outOfStockBehaviourByContexts,
        List<Map<String, Object>> partialStocks,
        List<Map<String, Object>> tags,
        Map<String, Object> legal,
        Map<String, Object> outOfStockConfig,
        Map<String, Object> scanningRule,
        Map<String, Object> stockAvailableUntil,
        Map<String, Object> stockinformation,
        Map<String, Object> stockProperties,
        Map<String, Object> availabilityTimeframe,
        Map<String, Object> customAttributes
) {}
