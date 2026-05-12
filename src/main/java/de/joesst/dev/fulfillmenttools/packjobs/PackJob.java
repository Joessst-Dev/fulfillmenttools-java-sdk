package de.joesst.dev.fulfillmenttools.packjobs;

import de.joesst.dev.fulfillmenttools.model.AssignedUser;
import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.orders.ConsumerAddress;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record PackJob(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String facilityRef,
        String status,
        String shortId,
        String processId,
        String operativeProcessRef,
        String documentsRef,
        String orderRef,
        Instant orderDate,
        String tenantOrderId,
        String pickJobRef,
        String deliveryChannel,
        String recipientName,
        Instant targetTime,
        List<PackLineItem> lineItems,
        List<AssignedUser> assignedUsers,
        ConsumerAddress recipient,
        ConsumerAddress invoice,
        Map<String, Object> customAttributes,
        Map<String, Object> editor,
        Map<String, Object> documentHandling,
        Map<String, Object> workflowInformation,
        List<Map<String, Object>> stickers,
        List<Map<String, Object>> transfers,
        List<Map<String, Object>> packingSourceContainers,
        List<TagReference> tags,
        Boolean anonymized
) {}
