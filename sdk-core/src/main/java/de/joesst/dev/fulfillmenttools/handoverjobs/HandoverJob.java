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
import java.util.Map;

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
        Map<String, Object> customAttributes,
        List<Sticker> stickers,
        List<OperativeTransfer> transfers,
        List<TagReference> tags,
        Boolean anonymized,
        Boolean paid
) {}
