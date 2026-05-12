package de.joesst.dev.fulfillmenttools.internal.carriers;

import java.util.List;
import java.util.Map;

record UpdateCarrierBody(Integer version, List<ModifyCarrierAction> actions) {

    record ModifyCarrierAction(
            String action,
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
            Map<String, Object> credentials
    ) {
        ModifyCarrierAction(
                String name, String status, String logoUrl, String deliveryType,
                String lifecycle, Double defaultParcelWidthInCm, Double defaultParcelLengthInCm,
                Double defaultParcelHeightInCm, Double defaultParcelWeightInGram,
                Boolean productValueNeeded, Map<String, Object> credentials
        ) {
            this("ModifyCarrier", name, status, logoUrl, deliveryType, lifecycle,
                    defaultParcelWidthInCm, defaultParcelLengthInCm, defaultParcelHeightInCm,
                    defaultParcelWeightInGram, productValueNeeded, credentials);
        }
    }
}
