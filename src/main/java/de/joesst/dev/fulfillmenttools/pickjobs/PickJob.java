package de.joesst.dev.fulfillmenttools.pickjobs;

import de.joesst.dev.fulfillmenttools.model.AssignedUser;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents a pick job in fulfillmenttools.
 */
public record PickJob(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String facilityRef,
        String status,
        String shortId,
        String orderRef,
        Instant orderDate,
        String tenantOrderId,
        List<PickLineItem> pickLineItems,
        List<AssignedUser> assignedUsers,
        Map<String, Object> customAttributes,
        Boolean anonymized
) {}
