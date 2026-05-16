package de.joesst.dev.fulfillmenttools.internal.checkoutoptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.checkoutoptions.CheckoutOptionsConsumerAddress;
import de.joesst.dev.fulfillmenttools.checkoutoptions.GeoFence;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;
import de.joesst.dev.fulfillmenttools.orders.DeliveryPreferences;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
record EvaluateCheckoutOptionsBody(
        DeliveryPreferences deliveryPreferences,
        List<Map<String, Object>> orderLineItems,
        CheckoutOptionsConsumerAddress consumerAddress,
        CustomAttributes customAttributes,
        Boolean filterDuplicates,
        List<Map<String, Object>> customServices,
        GeoFence geoFence,
        List<Map<String, Object>> tags
) {}
