package de.joesst.dev.fulfillmenttools.inbound;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.StowJobId;
import de.joesst.dev.fulfillmenttools.model.AssignedUser;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents a stow job in fulfillmenttools.
 *
 * @param id               Server-assigned ID of the stow job.
 * @param version          Current optimistic lock version of the stow job.
 * @param created          Timestamp when the stow job was created.
 * @param lastModified     Timestamp of the last modification to the stow job.
 * @param facilityRef      Reference to the facility where the stow job is being executed.
 * @param status           Current status of the stow job (e.g. {@code OPEN}, {@code IN_PROGRESS}).
 * @param shortId          Human-readable short identifier for display purposes.
 * @param priority         Priority level of the job.
 * @param targetTime       The time by which the job should be completed.
 * @param stowLineItems    The stow line items contained in this job.
 * @param assignedUsers    Users assigned to execute this stow job.
 * @param customAttributes Free-form custom attributes.
 */
public record StowJob(
        StowJobId id,
        Integer version,
        Instant created,
        Instant lastModified,
        FacilityId facilityRef,
        String status,
        String shortId,
        Integer priority,
        Instant targetTime,
        List<StowLineItem> stowLineItems,
        List<AssignedUser> assignedUsers,
        Map<String, Object> customAttributes
) {}
