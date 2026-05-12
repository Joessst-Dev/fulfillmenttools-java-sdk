package de.joesst.dev.fulfillmenttools.orders;

import java.util.Map;

public record OrderPaymentInfo(
        String currency,
        String method,
        Map<String, Object> methodLocalized
) {}
