package de.joesst.dev.fulfillmenttools.id;

import java.util.Objects;

/**
 * Platform-assigned identifier for a facility group ({@code facilityGroup.id} in the API).
 *
 * @param value the raw UUID string
 */
public record FacilityGroupId(String value) implements PlatformId {
    public FacilityGroupId { Objects.requireNonNull(value, "value"); }
    @Override public String toString() { return value; }
}
