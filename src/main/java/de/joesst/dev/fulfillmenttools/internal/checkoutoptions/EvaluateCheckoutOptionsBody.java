package de.joesst.dev.fulfillmenttools.internal.checkoutoptions;

import de.joesst.dev.fulfillmenttools.checkoutoptions.CheckoutOrderLineItem;

import java.util.List;
import java.util.Map;

record EvaluateCheckoutOptionsBody(
        Map<String, Object> deliveryPreferences,
        List<CheckoutOrderLineItem> orderLineItems,
        Map<String, Object> consumerAddress,
        Map<String, Object> customAttributes,
        Boolean filterDuplicates
) {}
