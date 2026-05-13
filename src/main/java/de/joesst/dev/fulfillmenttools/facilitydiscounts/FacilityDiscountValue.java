package de.joesst.dev.fulfillmenttools.facilitydiscounts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

/**
 * Sealed interface representing a discount value that can be either relative or absolute.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type",
        include = JsonTypeInfo.As.EXISTING_PROPERTY, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FacilityDiscountValue.Relative.class, name = "RELATIVE"),
        @JsonSubTypes.Type(value = FacilityDiscountValue.Absolute.class, name = "ABSOLUTE")
})
public sealed interface FacilityDiscountValue
        permits FacilityDiscountValue.Relative, FacilityDiscountValue.Absolute {

    /**
     * Returns the type of the discount value.
     *
     * @return the type (RELATIVE or ABSOLUTE)
     */
    String type();

    /**
     * A relative discount value (percentage-based).
     *
     * @param type the discount type (RELATIVE)
     * @param value the percentage value to discount
     */
    record Relative(String type, Double value) implements FacilityDiscountValue {
        /**
         * Creates a relative discount with the given percentage value.
         *
         * @param value the percentage value to discount
         * @return a new Relative discount
         */
        public static Relative of(double value) {
            return new Relative("RELATIVE", value);
        }
    }

    /**
     * An absolute discount value (fixed amount).
     *
     * @param type the discount type (ABSOLUTE)
     * @param values the list of absolute discount elements
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Absolute(String type, List<FacilityDiscountAbsoluteElement> values) implements FacilityDiscountValue {
        /**
         * Creates an absolute discount with the given values.
         *
         * @param values the list of absolute discount elements
         * @return a new Absolute discount
         */
        public static Absolute of(List<FacilityDiscountAbsoluteElement> values) {
            return new Absolute("ABSOLUTE", values);
        }
    }
}
