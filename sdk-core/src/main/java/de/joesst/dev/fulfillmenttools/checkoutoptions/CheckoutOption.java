package de.joesst.dev.fulfillmenttools.checkoutoptions;

import java.util.List;
import java.util.Map;

/**
 * A checkout option with available facilities.
 *
 * @param facilities list of facility options available for checkout
 */
public record CheckoutOption(List<Map<String, Object>> facilities) {}
