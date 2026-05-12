package de.joesst.dev.fulfillmenttools.facilitydiscounts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type",
        include = JsonTypeInfo.As.EXISTING_PROPERTY, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FacilityDiscountValue.Relative.class, name = "RELATIVE"),
        @JsonSubTypes.Type(value = FacilityDiscountValue.Absolute.class, name = "ABSOLUTE")
})
public sealed interface FacilityDiscountValue
        permits FacilityDiscountValue.Relative, FacilityDiscountValue.Absolute {

    String type();

    record Relative(String type, Double value) implements FacilityDiscountValue {
        public static Relative of(double value) { return new Relative("RELATIVE", value); }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Absolute(String type, List<FacilityDiscountAbsoluteElement> values) implements FacilityDiscountValue {
        public static Absolute of(List<FacilityDiscountAbsoluteElement> values) {
            return new Absolute("ABSOLUTE", values);
        }
    }
}
