package de.joesst.dev.fulfillmenttools.orders;

import de.joesst.dev.fulfillmenttools.model.TagReference;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * A fulfillmenttools order as returned by the read API.
 *
 * <p>Maps to the {@code Order} schema in the fulfillmenttools OpenAPI spec, which extends
 * {@code OrderForCreation} and {@code VersionedResource}.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id               The platform-generated order identifier.
 * @param version          The optimistic-locking version.
 * @param created          The timestamp when this order was created.
 * @param lastModified     The timestamp of the last modification.
 * @param tenantOrderId    The tenant's external order reference number.
 * @param status           The current order status (e.g. {@code OPEN}, {@code LOCKED}).
 * @param processId        The ID of the global process related to this order.
 * @param schemaVersion    The schema version of this order document.
 * @param orderDate        The date this order was created in the source system.
 * @param orderLineItems   The line items on this order.
 * @param consumer         The consumer this order is for.
 * @param deliveryPreferences How and where this order should be delivered.
 * @param paymentInfo      Payment information for this order.
 * @param cancelationReason The reason this order was cancelled, if applicable.
 * @param customServices   Optional references to custom services on this order.
 * @param updateDetails    Log of updates made to this order.
 * @param tags             Tags attached to this order.
 * @param stickers         Visual stickers attached to this order.
 * @param statusReasons    Reasons explaining the current order status.
 * @param source           The external system that created this order.
 * @param customAttributes Free-form custom attributes.
 * @param anonymized       Whether GDPR-related data has been anonymized.
 */
public record Order(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String tenantOrderId,
        String status,
        String processId,
        Double schemaVersion,
        Instant orderDate,
        List<OrderLineItem> orderLineItems,
        OrderForCreationConsumer consumer,
        DeliveryPreferences deliveryPreferences,
        OrderPaymentInfo paymentInfo,
        CancelationReason cancelationReason,
        List<Map<String, Object>> customServices,
        List<OrderUpdateDetail> updateDetails,
        List<TagReference> tags,
        List<Sticker> stickers,
        List<OrderStatusReason> statusReasons,
        OrderSource source,
        Map<String, Object> customAttributes,
        Boolean anonymized
) {}
