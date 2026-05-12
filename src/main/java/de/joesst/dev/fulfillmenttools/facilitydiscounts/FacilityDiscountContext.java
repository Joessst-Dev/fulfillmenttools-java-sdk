package de.joesst.dev.fulfillmenttools.facilitydiscounts;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record FacilityDiscountContext(String type, List<String> values, String operator) {

    public static FacilityDiscountContext of(String type, List<String> values) {
        return new FacilityDiscountContext(type, values, null);
    }

    public static FacilityDiscountContext withOperator(String type, List<String> values, String operator) {
        return new FacilityDiscountContext(type, values, operator);
    }
}
