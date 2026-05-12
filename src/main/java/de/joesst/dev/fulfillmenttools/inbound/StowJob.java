package de.joesst.dev.fulfillmenttools.inbound;

import de.joesst.dev.fulfillmenttools.model.AssignedUser;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents a stow job in fulfillmenttools.
 */
public record StowJob(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String facilityRef,
        String status,
        String shortId,
        Integer priority,
        Instant targetTime,
        List<StowLineItem> stowLineItems,
        List<AssignedUser> assignedUsers,
        Map<String, Object> customAttributes
) {}
