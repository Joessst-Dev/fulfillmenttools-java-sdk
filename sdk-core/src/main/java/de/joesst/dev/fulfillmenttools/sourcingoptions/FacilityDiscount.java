package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * A discount applied to the sales price of an article in a sourcing option.
 *
 * <p>Discriminated by the {@code type} field: {@code RELATIVE} maps to
 * {@link FacilityDiscountRelative}, {@code ABSOLUTE} maps to {@link FacilityDiscountAbsolute}.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type",
        include = JsonTypeInfo.As.EXISTING_PROPERTY, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FacilityDiscountRelative.class, name = "RELATIVE"),
        @JsonSubTypes.Type(value = FacilityDiscountAbsolute.class, name = "ABSOLUTE")
})
public sealed interface FacilityDiscount permits FacilityDiscountRelative, FacilityDiscountAbsolute {
}
