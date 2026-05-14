package de.joesst.dev.fulfillmenttools.facilitydiscounts;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Represents a context condition under which a discount applies.
 *
 * @param type the type of context (e.g., ORDER_TYPE, DELIVERY_SERVICE)
 * @param values the context values to match
 * @param operator the operator to apply (e.g., AND, OR), or null if not set
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record FacilityDiscountContext(String type, List<String> values, String operator) {

    /**
     * Creates a context condition with the given type and values.
     *
     * @param type the context type
     * @param values the context values
     * @return a new FacilityDiscountContext
     */
    public static FacilityDiscountContext of(String type, List<String> values) {
        return new FacilityDiscountContext(type, values, null);
    }

    /**
     * Creates a context condition with the given type, values, and operator.
     *
     * @param type the context type
     * @param values the context values
     * @param operator the operator to apply
     * @return a new FacilityDiscountContext
     */
    public static FacilityDiscountContext withOperator(String type, List<String> values, String operator) {
        return new FacilityDiscountContext(type, values, operator);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String type;
        private List<String> values;
        private String operator;

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder values(List<String> values) {
            this.values = values;
            return this;
        }

        public Builder operator(String operator) {
            this.operator = operator;
            return this;
        }

        public FacilityDiscountContext build() {
            return new FacilityDiscountContext(type, values, operator);
        }
    }
}
