package de.joesst.dev.fulfillmenttools.handoverjobs;

import de.joesst.dev.fulfillmenttools.model.AssignedUser;

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
        String pickJobRef,
        String operativeProcessRef,
        List<AssignedUser> assignedUsers,
        Boolean anonymized,
        Boolean paid,
        Map<String, Object> customAttributes
) {}
