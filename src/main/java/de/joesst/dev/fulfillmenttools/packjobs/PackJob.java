package de.joesst.dev.fulfillmenttools.packjobs;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.OrderId;
import de.joesst.dev.fulfillmenttools.id.PackJobId;
import de.joesst.dev.fulfillmenttools.id.PickJobId;
import de.joesst.dev.fulfillmenttools.id.ProcessId;
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
 * @param tenantOrderId          External reference number for the order.
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
        String tenantOrderId,
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
) {}
