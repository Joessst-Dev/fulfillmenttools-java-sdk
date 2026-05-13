package de.joesst.dev.fulfillmenttools.pickjobs;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.OrderId;
import de.joesst.dev.fulfillmenttools.id.PickJobId;
import de.joesst.dev.fulfillmenttools.id.ProcessId;
import de.joesst.dev.fulfillmenttools.id.RoutingPlanId;
import de.joesst.dev.fulfillmenttools.model.AssignedUser;
import de.joesst.dev.fulfillmenttools.model.TagReference;

import java.time.Instant;
import java.util.List;
import java.util.Map;

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
 * @param tenantOrderId            External reference number for the order.
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
        String tenantOrderId,
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
        Map<String, Object> customAttributes
) {}
