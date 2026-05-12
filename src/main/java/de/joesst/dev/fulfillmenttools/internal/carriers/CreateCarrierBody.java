package de.joesst.dev.fulfillmenttools.internal.carriers;

import java.util.List;
import java.util.Map;

record CreateCarrierBody(
        String key,
        String name,
        String status,
        String logoUrl,
        String deliveryType,
        String lifecycle,
        Double defaultParcelWidthInCm,
        Double defaultParcelLengthInCm,
        Double defaultParcelHeightInCm,
        Double defaultParcelWeightInGram,
        Boolean productValueNeeded,
        Map<String, Object> credentials,
        List<Map<String, Object>> parcelLabelClassifications
) {}
