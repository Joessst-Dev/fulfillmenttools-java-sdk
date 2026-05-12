package de.joesst.dev.fulfillmenttools.handoverjobs;

import de.joesst.dev.fulfillmenttools.model.AssignedUser;
import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.orders.ConsumerAddress;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record HandoverJob(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String facilityRef,
        String status,
        String channel,
        Instant targetTime,
        Instant orderDate,
        String tenantOrderId,
        String processId,
        String shortIdentifier,
        String fullIdentifier,
        String pickJobRef,
        String operativeProcessRef,
        String documentsRef,
        String cancelReason,
        List<HandoverLineItem> handoverJobLineItems,
        List<Map<String, Object>> expectedHandoverJobLineItems,
        List<Map<String, Object>> documents,
        List<AssignedUser> assignedUsers,
        ConsumerAddress recipientAddress,
        ConsumerAddress invoiceAddress,
        Map<String, Object> handoverJobParcelInfo,
        Map<String, Object> workflowInformation,
        Map<String, Object> customAttributes,
        List<Map<String, Object>> stickers,
        List<Map<String, Object>> transfers,
        List<TagReference> tags,
        Boolean anonymized,
        Boolean paid
) {}
