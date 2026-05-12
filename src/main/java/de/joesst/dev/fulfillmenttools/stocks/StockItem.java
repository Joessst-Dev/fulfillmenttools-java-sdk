package de.joesst.dev.fulfillmenttools.stocks;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record StockItem(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String facilityRef,
        String tenantArticleId,
        String tenantStockId,
        Integer value,
        Double available,
        Double reserved,
        Double facilityWideReserved,
        String locationRef,
        Instant receiptDate,
        Instant availableUntil,
        List<String> conditions,
        List<String> scannableCodes,
        List<String> scores,
        List<String> traits,
        List<Map<String, Object>> traitConfig,
        String serializedProperties,
        Map<String, Object> facility,
        Map<String, Object> properties,
        Map<String, Object> customAttributes
) {}
