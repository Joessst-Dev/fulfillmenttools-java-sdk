package de.joesst.dev.fulfillmenttools.orders;

import java.util.Map;

/**
 * Payment information on a read-side order.
 *
 * <p>Maps to the {@code OrderPaymentInfo} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param currency       The currency in which the consumer paid (e.g. {@code EUR}).
 * @param method         The localized payment method name (resolved from {@code methodLocalized}).
 * @param methodLocalized Localized display names for the payment method, keyed by locale.
 */
public record OrderPaymentInfo(
        String currency,
        String method,
        Map<String, String> methodLocalized
) {}
