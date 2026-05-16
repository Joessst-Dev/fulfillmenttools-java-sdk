package de.joesst.dev.fulfillmenttools.pickjobs;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.OrderId;
import de.joesst.dev.fulfillmenttools.id.PickJobId;
import de.joesst.dev.fulfillmenttools.id.ProcessId;
import de.joesst.dev.fulfillmenttools.id.RoutingPlanId;
import de.joesst.dev.fulfillmenttools.id.TenantOrderId;
import de.joesst.dev.fulfillmenttools.model.AssignedUser;
import de.joesst.dev.fulfillmenttools.model.TagReference;

import java.time.Instant;
import java.util.List;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * A pick job representing the fulfilment task to pick articles for an order at a facility.
 *
 * <p>Maps to the {@code PickJob} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                       Unique {@link PickJobId} of the pick job.
 * @param version                  Optimistic-locking version counter.
 * @param created                  Timestamp when the pick job was created.
 * @param lastModified             Timestamp when the pick job was last modified.
 * @param facilityRef              {@link FacilityId} reference to the facility responsible for fulfilment.
 * @param status                   Current status of the pick job.
 * @param subStatus                Sub-status providing additional context for the main status.
 * @param shortId                  Short human-readable identifier for the pick job.
 * @param orderRef                 {@link OrderId} reference to the originating order.
 * @param orderDate                Date the order was created in the source system.
 * @param tenantOrderId            External {@link TenantOrderId} reference number for the order.
 * @param processId                {@link ProcessId} of the global process related to this pick job.
 * @param routingPlanRef           {@link RoutingPlanId} reference to the routing plan that created this pick job.
 * @param operativeProcessRef      {@link ProcessId} reference to the operative process.
 * @param pickRunRef               Reference to the pick run this pick job belongs to.
 * @param documentsRef             Reference to the document collection for this pick job.
 * @param resetBlocked             Whether the pick job can still be reset or restarted.
 * @param anonymized               Whether GDPR-related data has been anonymized.
 * @param pickLineItems            The active pick line items.
 * @param assignedUsers            Users assigned to execute this pick job.
 * @param deliveryinformation      Delivery channel and timing information.
 * @param expectedPickLineItems    Pick line items that are anticipated but not yet active.
 * @param pickingZones             Zones from which articles may be picked.
 * @param workflowInformation      Workflow context if this pick job is part of a workflow.
 * @param documentHandling         Label and document handling configuration.
 * @param editor                   The user currently editing this pick job.
 * @param paymentInformation       Payment information associated with this pick job.
 * @param usersModificationHistory Audit trail of user modifications.
 * @param tags                     Tag references attached to this pick job.
 * @param customAttributes         Free-form custom attributes.
 */
public record PickJob(
        PickJobId id,
        Integer version,
        Instant created,
        Instant lastModified,
        FacilityId facilityRef,
        String status,
        String subStatus,
        String shortId,
        OrderId orderRef,
        Instant orderDate,
        TenantOrderId tenantOrderId,
        ProcessId processId,
        RoutingPlanId routingPlanRef,
        ProcessId operativeProcessRef,
        String pickRunRef,
        String documentsRef,
        Boolean resetBlocked,
        Boolean anonymized,
        List<PickLineItem> pickLineItems,
        List<AssignedUser> assignedUsers,
        PickjobDeliveryInformation deliveryinformation,
        List<ExpectedPickLineItem> expectedPickLineItems,
        List<PickingZone> pickingZones,
        WorkflowInformation workflowInformation,
        DocumentHandling documentHandling,
        PickJobEditor editor,
        PickJobPaymentInformation paymentInformation,
        List<UserModificationHistory> usersModificationHistory,
        List<TagReference> tags,
        CustomAttributes customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private PickJobId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private FacilityId facilityRef;
        private String status;
        private String subStatus;
        private String shortId;
        private OrderId orderRef;
        private Instant orderDate;
        private TenantOrderId tenantOrderId;
        private ProcessId processId;
        private RoutingPlanId routingPlanRef;
        private ProcessId operativeProcessRef;
        private String pickRunRef;
        private String documentsRef;
        private Boolean resetBlocked;
        private Boolean anonymized;
        private List<PickLineItem> pickLineItems;
        private List<AssignedUser> assignedUsers;
        private PickjobDeliveryInformation deliveryinformation;
        private List<ExpectedPickLineItem> expectedPickLineItems;
        private List<PickingZone> pickingZones;
        private WorkflowInformation workflowInformation;
        private DocumentHandling documentHandling;
        private PickJobEditor editor;
        private PickJobPaymentInformation paymentInformation;
        private List<UserModificationHistory> usersModificationHistory;
        private List<TagReference> tags;
        private CustomAttributes customAttributes;

        private Builder() {}

        public Builder id(PickJobId id) { this.id = id; return this; }
        public Builder version(Integer version) { this.version = version; return this; }
        public Builder created(Instant created) { this.created = created; return this; }
        public Builder lastModified(Instant lastModified) { this.lastModified = lastModified; return this; }
        public Builder facilityRef(FacilityId facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder subStatus(String subStatus) { this.subStatus = subStatus; return this; }
        public Builder shortId(String shortId) { this.shortId = shortId; return this; }
        public Builder orderRef(OrderId orderRef) { this.orderRef = orderRef; return this; }
        public Builder orderDate(Instant orderDate) { this.orderDate = orderDate; return this; }
        public Builder tenantOrderId(TenantOrderId tenantOrderId) { this.tenantOrderId = tenantOrderId; return this; }
        public Builder processId(ProcessId processId) { this.processId = processId; return this; }
        public Builder routingPlanRef(RoutingPlanId routingPlanRef) { this.routingPlanRef = routingPlanRef; return this; }
        public Builder operativeProcessRef(ProcessId operativeProcessRef) { this.operativeProcessRef = operativeProcessRef; return this; }
        public Builder pickRunRef(String pickRunRef) { this.pickRunRef = pickRunRef; return this; }
        public Builder documentsRef(String documentsRef) { this.documentsRef = documentsRef; return this; }
        public Builder resetBlocked(Boolean resetBlocked) { this.resetBlocked = resetBlocked; return this; }
        public Builder anonymized(Boolean anonymized) { this.anonymized = anonymized; return this; }
        public Builder pickLineItems(List<PickLineItem> pickLineItems) { this.pickLineItems = pickLineItems; return this; }
        public Builder assignedUsers(List<AssignedUser> assignedUsers) { this.assignedUsers = assignedUsers; return this; }
        public Builder deliveryinformation(PickjobDeliveryInformation deliveryinformation) { this.deliveryinformation = deliveryinformation; return this; }
        public Builder expectedPickLineItems(List<ExpectedPickLineItem> expectedPickLineItems) { this.expectedPickLineItems = expectedPickLineItems; return this; }
        public Builder pickingZones(List<PickingZone> pickingZones) { this.pickingZones = pickingZones; return this; }
        public Builder workflowInformation(WorkflowInformation workflowInformation) { this.workflowInformation = workflowInformation; return this; }
        public Builder documentHandling(DocumentHandling documentHandling) { this.documentHandling = documentHandling; return this; }
        public Builder editor(PickJobEditor editor) { this.editor = editor; return this; }
        public Builder paymentInformation(PickJobPaymentInformation paymentInformation) { this.paymentInformation = paymentInformation; return this; }
        public Builder usersModificationHistory(List<UserModificationHistory> usersModificationHistory) { this.usersModificationHistory = usersModificationHistory; return this; }
        public Builder tags(List<TagReference> tags) { this.tags = tags; return this; }
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        public PickJob build() {
            return new PickJob(id, version, created, lastModified, facilityRef, status, subStatus, shortId, orderRef, orderDate, tenantOrderId, processId, routingPlanRef, operativeProcessRef, pickRunRef, documentsRef, resetBlocked, anonymized, pickLineItems, assignedUsers, deliveryinformation, expectedPickLineItems, pickingZones, workflowInformation, documentHandling, editor, paymentInformation, usersModificationHistory, tags, customAttributes);
        }
    }
}
