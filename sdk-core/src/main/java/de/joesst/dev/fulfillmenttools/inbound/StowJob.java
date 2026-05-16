package de.joesst.dev.fulfillmenttools.inbound;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.StowJobId;
import de.joesst.dev.fulfillmenttools.model.AssignedUser;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

import java.time.Instant;
import java.util.List;

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
        CustomAttributes customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private StowJobId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private FacilityId facilityRef;
        private String status;
        private String shortId;
        private Integer priority;
        private Instant targetTime;
        private List<StowLineItem> stowLineItems;
        private List<AssignedUser> assignedUsers;
        private CustomAttributes customAttributes;

        private Builder() {}

        public Builder id(StowJobId id) { this.id = id; return this; }
        public Builder version(Integer version) { this.version = version; return this; }
        public Builder created(Instant created) { this.created = created; return this; }
        public Builder lastModified(Instant lastModified) { this.lastModified = lastModified; return this; }
        public Builder facilityRef(FacilityId facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder shortId(String shortId) { this.shortId = shortId; return this; }
        public Builder priority(Integer priority) { this.priority = priority; return this; }
        public Builder targetTime(Instant targetTime) { this.targetTime = targetTime; return this; }
        public Builder stowLineItems(List<StowLineItem> stowLineItems) { this.stowLineItems = stowLineItems; return this; }
        public Builder assignedUsers(List<AssignedUser> assignedUsers) { this.assignedUsers = assignedUsers; return this; }
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        public StowJob build() {
            return new StowJob(id, version, created, lastModified, facilityRef, status, shortId, priority, targetTime, stowLineItems, assignedUsers, customAttributes);
        }
    }
}
