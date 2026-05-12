package de.joesst.dev.fulfillmenttools.pickjobs;

import de.joesst.dev.fulfillmenttools.model.AssignedUser;
import de.joesst.dev.fulfillmenttools.model.TagReference;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record PickJob(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String facilityRef,
        String status,
        String subStatus,
        String shortId,
        String orderRef,
        Instant orderDate,
        String tenantOrderId,
        String processId,
        String routingPlanRef,
        String operativeProcessRef,
        String pickRunRef,
        String documentsRef,
        Boolean resetBlocked,
        Boolean anonymized,
        List<PickLineItem> pickLineItems,
        List<AssignedUser> assignedUsers,
        PickjobDeliveryInformation deliveryinformation,
        List<Map<String, Object>> expectedPickLineItems,
        List<Map<String, Object>> pickingZones,
        Map<String, Object> workflowInformation,
        Map<String, Object> documentHandling,
        Map<String, Object> editor,
        Map<String, Object> paymentInformation,
        List<Map<String, Object>> usersModificationHistory,
        List<TagReference> tags,
        Map<String, Object> customAttributes
) {}
