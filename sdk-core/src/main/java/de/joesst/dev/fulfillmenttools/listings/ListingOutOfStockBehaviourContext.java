package de.joesst.dev.fulfillmenttools.listings;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * A condition context used to determine when an out-of-stock behaviour applies.
 *
 * <p>Maps to the {@code OutOfStockBehaviourContext} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param type     The context type; one of {@code FACILITY_GROUP} or {@code TAG_REFERENCE}.
 * @param values   The values used to evaluate the context condition (e.g. facility group IDs).
 * @param operator Optional operator for context evaluation; currently only {@code NOT} is
 *                 supported.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ListingOutOfStockBehaviourContext(
        String type,
        List<String> values,
        String operator
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String type;
        private List<String> values;
        private String operator;

        private Builder() {}

        public Builder type(String type) { this.type = type; return this; }
        public Builder values(List<String> values) { this.values = values; return this; }
        public Builder operator(String operator) { this.operator = operator; return this; }

        public ListingOutOfStockBehaviourContext build() {
            return new ListingOutOfStockBehaviourContext(type, values, operator);
        }
    }
}
