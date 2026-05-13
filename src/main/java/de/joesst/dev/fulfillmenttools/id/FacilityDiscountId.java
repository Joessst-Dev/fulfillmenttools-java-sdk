package de.joesst.dev.fulfillmenttools.id;

import java.util.Objects;

/**
 * Platform-assigned identifier for a facility discount ({@code facilityDiscount.ref} in the API).
 *
 * @param value the raw UUID string
 */
public record FacilityDiscountId(String value) implements PlatformId {
    public FacilityDiscountId { Objects.requireNonNull(value, "value"); }
    @Override public String toString() { return value; }
}
