package de.joesst.dev.fulfillmenttools.handoverjobs;

import de.joesst.dev.fulfillmenttools.id.CarrierId;

/**
 * Parcel and carrier information associated with a handover job.
 *
 * <p>Maps to the {@code HandoverJobParcelInfo} schema in the fulfillmenttools OpenAPI spec,
 * which extends {@code HandoverJobParcelInfoForCreation} with the resolved {@code carrierKey}.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param carrierKey            The key identifying the carrier (e.g. {@code DHL}).
 * @param carrierLogoUrl        URL to the carrier logo image.
 * @param carrierParcelRef      Reference number of the parcel assigned by the carrier.
 * @param carrierRef            ID of the related carrier entity.
 * @param carrierTrackingNumber Tracking number of the parcel.
 * @param parcelRef             Reference to the related parcel entity.
 * @param shipmentRef           Reference to the related shipment entity.
 */
public record HandoverJobParcelInfo(
        String carrierKey,
        String carrierLogoUrl,
        String carrierParcelRef,
        CarrierId carrierRef,
        String carrierTrackingNumber,
        String parcelRef,
        String shipmentRef
) {}
