package de.joesst.dev.fulfillmenttools.handoverjobs;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.HandoverJobId;
import de.joesst.dev.fulfillmenttools.id.TenantOrderId;
import de.joesst.dev.fulfillmenttools.id.PickJobId;
import de.joesst.dev.fulfillmenttools.id.ProcessId;
import de.joesst.dev.fulfillmenttools.model.AssignedUser;
import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.orders.ConsumerAddress;
import de.joesst.dev.fulfillmenttools.orders.Sticker;
import de.joesst.dev.fulfillmenttools.packjobs.OperativeTransfer;
import de.joesst.dev.fulfillmenttools.pickjobs.WorkflowInformation;

import java.time.Instant;
import java.util.List;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * A handover job representing the task of handing over picked articles to a customer or carrier.
 *
 * <p>Maps to the {@code Handoverjob} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                           Unique {@link HandoverJobId} of the handover job. Required.
 * @param version                      Optimistic-locking version counter. Required.
 * @param created                      Timestamp when the handover job was created.
 * @param lastModified                 Timestamp when the handover job was last modified.
 * @param facilityRef                  {@link FacilityId} reference to the facility. Required.
 * @param status                       Current status of the handover job. Required.
 * @param channel                      Delivery channel: {@code DELIVERY} or {@code COLLECT}.
 *                                     Required.
 * @param targetTime                   Expected pick-up time at the facility. Required.
 * @param orderDate                    Date the order was created in the source system. Required.
 * @param tenantOrderId                External {@link TenantOrderId} reference number for the order.
 * @param processId                    {@link ProcessId} of the global process related to this entity.
 * @param shortIdentifier              Short identifier of the shipment.
 * @param fullIdentifier               Full identifier to identify the recipient.
 * @param pickJobRef                   {@link PickJobId} reference to the associated pick job.
 * @param operativeProcessRef          {@link ProcessId} reference to the operative process.
 * @param documentsRef                 Reference to the document collection.
 * @param cancelReason                 Reason for cancellation, if applicable.
 * @param handoverJobLineItems         The active line items of this handover job.
 * @param expectedHandoverJobLineItems Line items expected but not yet confirmed.
 * @param documents                    Printable documents associated with this handover job.
 * @param assignedUsers                Users assigned to execute this handover job.
 * @param recipientAddress             Delivery address of the recipient.
 * @param invoiceAddress               Invoice address.
 * @param handoverJobParcelInfo        Parcel and carrier information.
 * @param workflowInformation          Workflow context if this job is part of a workflow.
 *                                     Required.
 * @param customAttributes             Free-form custom attributes (not used in fulfillment
 *                                     processes).
 * @param stickers                     Visual stickers attached to this handover job.
 * @param transfers                    Transfer associations (supplier/receiver).
 * @param tags                         Tag references attached to this handover job.
 * @param anonymized                   Whether GDPR-related data has been anonymized.
 * @param paid                         Whether the order is already paid.
 */
public record HandoverJob(
        HandoverJobId id,
        Integer version,
        Instant created,
        Instant lastModified,
        FacilityId facilityRef,
        String status,
        String channel,
        Instant targetTime,
        Instant orderDate,
        TenantOrderId tenantOrderId,
        ProcessId processId,
        String shortIdentifier,
        String fullIdentifier,
        PickJobId pickJobRef,
        ProcessId operativeProcessRef,
        String documentsRef,
        String cancelReason,
        List<HandoverLineItem> handoverJobLineItems,
        List<ExpectedHandoverLineItem> expectedHandoverJobLineItems,
        List<HandoverJobDocument> documents,
        List<AssignedUser> assignedUsers,
        ConsumerAddress recipientAddress,
        ConsumerAddress invoiceAddress,
        HandoverJobParcelInfo handoverJobParcelInfo,
        WorkflowInformation workflowInformation,
        CustomAttributes customAttributes,
        List<Sticker> stickers,
        List<OperativeTransfer> transfers,
        List<TagReference> tags,
        Boolean anonymized,
        Boolean paid
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private HandoverJobId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private FacilityId facilityRef;
        private String status;
        private String channel;
        private Instant targetTime;
        private Instant orderDate;
        private TenantOrderId tenantOrderId;
        private ProcessId processId;
        private String shortIdentifier;
        private String fullIdentifier;
        private PickJobId pickJobRef;
        private ProcessId operativeProcessRef;
        private String documentsRef;
        private String cancelReason;
        private List<HandoverLineItem> handoverJobLineItems;
        private List<ExpectedHandoverLineItem> expectedHandoverJobLineItems;
        private List<HandoverJobDocument> documents;
        private List<AssignedUser> assignedUsers;
        private ConsumerAddress recipientAddress;
        private ConsumerAddress invoiceAddress;
        private HandoverJobParcelInfo handoverJobParcelInfo;
        private WorkflowInformation workflowInformation;
        private CustomAttributes customAttributes;
        private List<Sticker> stickers;
        private List<OperativeTransfer> transfers;
        private List<TagReference> tags;
        private Boolean anonymized;
        private Boolean paid;

        public Builder id(HandoverJobId id) { this.id = id; return this; }
        public Builder version(Integer version) { this.version = version; return this; }
        public Builder created(Instant created) { this.created = created; return this; }
        public Builder lastModified(Instant lastModified) { this.lastModified = lastModified; return this; }
        public Builder facilityRef(FacilityId facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder channel(String channel) { this.channel = channel; return this; }
        public Builder targetTime(Instant targetTime) { this.targetTime = targetTime; return this; }
        public Builder orderDate(Instant orderDate) { this.orderDate = orderDate; return this; }
        public Builder tenantOrderId(TenantOrderId tenantOrderId) { this.tenantOrderId = tenantOrderId; return this; }
        public Builder processId(ProcessId processId) { this.processId = processId; return this; }
        public Builder shortIdentifier(String shortIdentifier) { this.shortIdentifier = shortIdentifier; return this; }
        public Builder fullIdentifier(String fullIdentifier) { this.fullIdentifier = fullIdentifier; return this; }
        public Builder pickJobRef(PickJobId pickJobRef) { this.pickJobRef = pickJobRef; return this; }
        public Builder operativeProcessRef(ProcessId operativeProcessRef) { this.operativeProcessRef = operativeProcessRef; return this; }
        public Builder documentsRef(String documentsRef) { this.documentsRef = documentsRef; return this; }
        public Builder cancelReason(String cancelReason) { this.cancelReason = cancelReason; return this; }
        public Builder handoverJobLineItems(List<HandoverLineItem> handoverJobLineItems) { this.handoverJobLineItems = handoverJobLineItems; return this; }
        public Builder expectedHandoverJobLineItems(List<ExpectedHandoverLineItem> expectedHandoverJobLineItems) { this.expectedHandoverJobLineItems = expectedHandoverJobLineItems; return this; }
        public Builder documents(List<HandoverJobDocument> documents) { this.documents = documents; return this; }
        public Builder assignedUsers(List<AssignedUser> assignedUsers) { this.assignedUsers = assignedUsers; return this; }
        public Builder recipientAddress(ConsumerAddress recipientAddress) { this.recipientAddress = recipientAddress; return this; }
        public Builder invoiceAddress(ConsumerAddress invoiceAddress) { this.invoiceAddress = invoiceAddress; return this; }
        public Builder handoverJobParcelInfo(HandoverJobParcelInfo handoverJobParcelInfo) { this.handoverJobParcelInfo = handoverJobParcelInfo; return this; }
        public Builder workflowInformation(WorkflowInformation workflowInformation) { this.workflowInformation = workflowInformation; return this; }
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }
        public Builder stickers(List<Sticker> stickers) { this.stickers = stickers; return this; }
        public Builder transfers(List<OperativeTransfer> transfers) { this.transfers = transfers; return this; }
        public Builder tags(List<TagReference> tags) { this.tags = tags; return this; }
        public Builder anonymized(Boolean anonymized) { this.anonymized = anonymized; return this; }
        public Builder paid(Boolean paid) { this.paid = paid; return this; }

        public HandoverJob build() {
            return new HandoverJob(id, version, created, lastModified, facilityRef, status, channel, targetTime, orderDate, tenantOrderId, processId, shortIdentifier, fullIdentifier, pickJobRef, operativeProcessRef, documentsRef, cancelReason, handoverJobLineItems, expectedHandoverJobLineItems, documents, assignedUsers, recipientAddress, invoiceAddress, handoverJobParcelInfo, workflowInformation, customAttributes, stickers, transfers, tags, anonymized, paid);
        }
    }
}
