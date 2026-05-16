package de.joesst.dev.fulfillmenttools.internal.returns;

import de.joesst.dev.fulfillmenttools.orders.ConsumerAddress;
import de.joesst.dev.fulfillmenttools.returns.ReturnJobLineItemForCreation;

import java.util.List;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

record CreateReturnBody(
        List<String> originFacilityRefs,
        String status,
        List<ConsumerAddress> consumerAddresses,
        List<ReturnJobLineItemForCreation> returnableLineItems,
        List<ReturnJobLineItemForCreation> notReturnableLineItems,
        List<String> scannableCodes,
        String shortId,
        String tenantOrderId,
        CustomAttributes customAttributes
) {}
