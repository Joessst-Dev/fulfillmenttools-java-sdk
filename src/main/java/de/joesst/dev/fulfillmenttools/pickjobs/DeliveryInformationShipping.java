package de.joesst.dev.fulfillmenttools.pickjobs;

import de.joesst.dev.fulfillmenttools.orders.ConsumerAddress;

import java.util.List;

/**
 * Shipping-specific delivery details within a pick job's delivery information.
 *
 * <p>Maps to the {@code details.shipping} sub-object of
 * {@code PickjobDeliveryInformationForCreation} in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param carrierKey              The carrier to use for shipping.
 * @param carrierProduct          The desired carrier product (e.g. {@code EXPRESS}).
 * @param carrierProductCategory  The product category ({@code STANDARD}, {@code EXPRESS},
 *                                {@code VALUE}, {@code FORWARDING}).
 * @param carrierServices         Additional carrier services (e.g. {@code SIGNATURE}).
 * @param identifier              Identifies the ship-from-store recipient.
 * @param serviceLevel            Delivery service level: {@code DELIVERY} or {@code SAMEDAY}.
 * @param recipientaddress        The recipient's delivery address.
 * @param postalAddress           The postal address for the shipment.
 * @param invoiceAddress          The invoice address for the shipment.
 */
public record DeliveryInformationShipping(
        String carrierKey,
        String carrierProduct,
        String carrierProductCategory,
        List<String> carrierServices,
        String identifier,
        String serviceLevel,
        ConsumerAddress recipientaddress,
        ConsumerAddress postalAddress,
        ConsumerAddress invoiceAddress
) {}
