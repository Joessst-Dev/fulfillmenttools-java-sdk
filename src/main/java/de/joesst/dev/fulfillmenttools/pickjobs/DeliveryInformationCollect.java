package de.joesst.dev.fulfillmenttools.pickjobs;

/**
 * Collect (click-and-collect) specific delivery details within a pick job's delivery information.
 *
 * <p>Maps to the {@code details.collect} sub-object of
 * {@code PickjobDeliveryInformationForCreation} in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param identifier Information identifying the click-and-collect recipient.
 * @param paid       Whether the order has already been paid. Defaults to {@code false}.
 */
public record DeliveryInformationCollect(
        String identifier,
        Boolean paid
) {}
