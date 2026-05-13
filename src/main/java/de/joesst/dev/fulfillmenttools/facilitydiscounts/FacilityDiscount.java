package de.joesst.dev.fulfillmenttools.facilitydiscounts;

import java.time.Instant;
import java.util.List;

/**
 * Represents a discount applied to a facility.
 *
 * @param id the discount's unique identifier
 * @param version the current version of the resource
 * @param created the timestamp when the discount was created
 * @param lastModified the timestamp when the discount was last updated
 * @param facilityRef the reference to the facility this discount applies to
 * @param type the type of discount
 * @param priority the priority of this discount relative to other discounts
 * @param discount the discount value specification (absolute or relative)
 * @param context the context conditions under which this discount applies
 */
public record FacilityDiscount(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String facilityRef,
        String type,
        Integer priority,
        FacilityDiscountValue discount,
        List<FacilityDiscountContext> context
) {}
