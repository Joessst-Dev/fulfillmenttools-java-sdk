package de.joesst.dev.fulfillmenttools.packjobs;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.OrderId;
import de.joesst.dev.fulfillmenttools.id.PackJobId;
import de.joesst.dev.fulfillmenttools.id.PickJobId;
import de.joesst.dev.fulfillmenttools.id.ProcessId;
import de.joesst.dev.fulfillmenttools.id.TenantOrderId;
import de.joesst.dev.fulfillmenttools.model.AssignedUser;
import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.orders.ConsumerAddress;
import de.joesst.dev.fulfillmenttools.orders.Sticker;
import de.joesst.dev.fulfillmenttools.pickjobs.DocumentHandling;
import de.joesst.dev.fulfillmenttools.pickjobs.PickJobEditor;
import de.joesst.dev.fulfillmenttools.pickjobs.WorkflowInformation;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * A pack job representing the task of packing picked articles into parcels.
 *
 * <p>Maps to the {@code PackJob} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                     Unique {@link PackJobId} of the pack job.
 * @param version                Optimistic-locking version counter.
 * @param created                Timestamp when the pack job was created.
 * @param lastModified           Timestamp when the pack job was last modified.
 * @param facilityRef            {@link FacilityId} reference to the facility responsible for fulfilment.
 * @param status                 Current status of the pack job.
 * @param shortId                Short human-readable identifier for the pack job.
 * @param processId              {@link ProcessId} of the global process related to this pack job.
 * @param operativeProcessRef    {@link ProcessId} reference to the operative process.
 * @param documentsRef           Reference to the document collection for this pack job.
 * @param orderRef               {@link OrderId} reference to the originating order.
 * @param orderDate              Date the order was placed.
 * @param tenantOrderId          External {@link TenantOrderId} reference number for the order.
 * @param pickJobRef             {@link PickJobId} reference to a pick job.
 * @param deliveryChannel        Delivery channel: {@code COLLECT} or {@code SHIPPING}.
 * @param recipientName          The name of the recipient.
 * @param targetTime             Until when the pack job must be finished.
 * @param lineItems              The line items to be packed.
 * @param assignedUsers          Users assigned to execute this pack job.
 * @param recipient              Recipient address.
 * @param invoice                Invoice address.
 * @param customAttributes       Free-form custom attributes (not used in fulfillment processes).
 * @param editor                 The user currently editing this pack job.
 * @param documentHandling       Label and document handling configuration.
 * @param workflowInformation    Workflow context if this pack job is part of a workflow.
 * @param stickers               Visual stickers attached to this pack job.
 * @param transfers              Transfer associations (supplier/receiver) for this pack job.
 * @param packingSourceContainers Source containers from which articles are packed.
 * @param tags                   Tag references attached to this pack job.
 * @param anonymized             Whether GDPR-related data has been anonymized.
 */
public record PackJob(
        PackJobId id,
        Integer version,
        Instant created,
        Instant lastModified,
        FacilityId facilityRef,
        String status,
        String shortId,
        ProcessId processId,
        ProcessId operativeProcessRef,
        String documentsRef,
        OrderId orderRef,
        Instant orderDate,
        TenantOrderId tenantOrderId,
        PickJobId pickJobRef,
        String deliveryChannel,
        String recipientName,
        Instant targetTime,
        List<PackLineItem> lineItems,
        List<AssignedUser> assignedUsers,
        ConsumerAddress recipient,
        ConsumerAddress invoice,
        Map<String, Object> customAttributes,
        PickJobEditor editor,
        DocumentHandling documentHandling,
        WorkflowInformation workflowInformation,
        List<Sticker> stickers,
        List<OperativeTransfer> transfers,
        List<StrippedPackingSourceContainer> packingSourceContainers,
        List<TagReference> tags,
        Boolean anonymized
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private PackJobId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private FacilityId facilityRef;
        private String status;
        private String shortId;
        private ProcessId processId;
        private ProcessId operativeProcessRef;
        private String documentsRef;
        private OrderId orderRef;
        private Instant orderDate;
        private TenantOrderId tenantOrderId;
        private PickJobId pickJobRef;
        private String deliveryChannel;
        private String recipientName;
        private Instant targetTime;
        private List<PackLineItem> lineItems;
        private List<AssignedUser> assignedUsers;
        private ConsumerAddress recipient;
        private ConsumerAddress invoice;
        private Map<String, Object> customAttributes;
        private PickJobEditor editor;
        private DocumentHandling documentHandling;
        private WorkflowInformation workflowInformation;
        private List<Sticker> stickers;
        private List<OperativeTransfer> transfers;
        private List<StrippedPackingSourceContainer> packingSourceContainers;
        private List<TagReference> tags;
        private Boolean anonymized;

        public Builder id(PackJobId id) { this.id = id; return this; }
        public Builder version(Integer version) { this.version = version; return this; }
        public Builder created(Instant created) { this.created = created; return this; }
        public Builder lastModified(Instant lastModified) { this.lastModified = lastModified; return this; }
        public Builder facilityRef(FacilityId facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder shortId(String shortId) { this.shortId = shortId; return this; }
        public Builder processId(ProcessId processId) { this.processId = processId; return this; }
        public Builder operativeProcessRef(ProcessId operativeProcessRef) { this.operativeProcessRef = operativeProcessRef; return this; }
        public Builder documentsRef(String documentsRef) { this.documentsRef = documentsRef; return this; }
        public Builder orderRef(OrderId orderRef) { this.orderRef = orderRef; return this; }
        public Builder orderDate(Instant orderDate) { this.orderDate = orderDate; return this; }
        public Builder tenantOrderId(TenantOrderId tenantOrderId) { this.tenantOrderId = tenantOrderId; return this; }
        public Builder pickJobRef(PickJobId pickJobRef) { this.pickJobRef = pickJobRef; return this; }
        public Builder deliveryChannel(String deliveryChannel) { this.deliveryChannel = deliveryChannel; return this; }
        public Builder recipientName(String recipientName) { this.recipientName = recipientName; return this; }
        public Builder targetTime(Instant targetTime) { this.targetTime = targetTime; return this; }
        public Builder lineItems(List<PackLineItem> lineItems) { this.lineItems = lineItems; return this; }
        public Builder assignedUsers(List<AssignedUser> assignedUsers) { this.assignedUsers = assignedUsers; return this; }
        public Builder recipient(ConsumerAddress recipient) { this.recipient = recipient; return this; }
        public Builder invoice(ConsumerAddress invoice) { this.invoice = invoice; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }
        public Builder editor(PickJobEditor editor) { this.editor = editor; return this; }
        public Builder documentHandling(DocumentHandling documentHandling) { this.documentHandling = documentHandling; return this; }
        public Builder workflowInformation(WorkflowInformation workflowInformation) { this.workflowInformation = workflowInformation; return this; }
        public Builder stickers(List<Sticker> stickers) { this.stickers = stickers; return this; }
        public Builder transfers(List<OperativeTransfer> transfers) { this.transfers = transfers; return this; }
        public Builder packingSourceContainers(List<StrippedPackingSourceContainer> packingSourceContainers) { this.packingSourceContainers = packingSourceContainers; return this; }
        public Builder tags(List<TagReference> tags) { this.tags = tags; return this; }
        public Builder anonymized(Boolean anonymized) { this.anonymized = anonymized; return this; }

        public PackJob build() {
            return new PackJob(id, version, created, lastModified, facilityRef, status, shortId, processId, operativeProcessRef, documentsRef, orderRef, orderDate, tenantOrderId, pickJobRef, deliveryChannel, recipientName, targetTime, lineItems, assignedUsers, recipient, invoice, customAttributes, editor, documentHandling, workflowInformation, stickers, transfers, packingSourceContainers, tags, anonymized);
        }
    }
}
