package de.joesst.dev.fulfillmenttools.facilitydiscounts;

import de.joesst.dev.fulfillmenttools.id.FacilityDiscountId;
import de.joesst.dev.fulfillmenttools.id.FacilityId;

import java.time.Instant;
import java.util.List;

/**
 * Represents a discount applied to a facility.
 *
 * @param id the discount's unique {@link FacilityDiscountId}
 * @param version the current version of the resource
 * @param created the timestamp when the discount was created
 * @param lastModified the timestamp when the discount was last updated
 * @param facilityRef the {@link FacilityId} reference to the facility this discount applies to
 * @param type the type of discount
 * @param priority the priority of this discount relative to other discounts
 * @param discount the discount value specification (absolute or relative)
 * @param context the context conditions under which this discount applies
 */
public record FacilityDiscount(
        FacilityDiscountId id,
        Integer version,
        Instant created,
        Instant lastModified,
        FacilityId facilityRef,
        String type,
        Integer priority,
        FacilityDiscountValue discount,
        List<FacilityDiscountContext> context
) {}
