package de.joesst.dev.fulfillmenttools.packjobs;

import de.joesst.dev.fulfillmenttools.model.AssignedUser;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents a pack job in fulfillmenttools.
 */
public record PackJob(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String facilityRef,
        String status,
        String shortId,
        String processId,
        String orderRef,
        Instant orderDate,
        String tenantOrderId,
        String pickJobRef,
        String deliveryChannel,
        List<PackLineItem> lineItems,
        List<AssignedUser> assignedUsers,
        Map<String, Object> customAttributes,
        Boolean anonymized
) {}
