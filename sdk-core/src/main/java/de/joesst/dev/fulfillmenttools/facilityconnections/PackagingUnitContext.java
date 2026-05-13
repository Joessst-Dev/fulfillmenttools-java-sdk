package de.joesst.dev.fulfillmenttools.facilityconnections;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Scopes packaging unit selection to a subset of delivery destinations or categories.
 *
 * @param type     the dimension being filtered (postal code, country, or category)
 * @param values   list of values for the given type
 * @param operator optional logical operator applied to this context; {@code null} means positive match
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PackagingUnitContext(PackagingUnitContextType type, List<String> values, ContextOperator operator) {

    /**
     * Returns a builder for constructing a {@link PackagingUnitContext}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link PackagingUnitContext}.
     */
    public static final class Builder {

        private PackagingUnitContextType type;
        private List<String> values;
        private ContextOperator operator;

        private Builder() {}

        /**
         * @param type the dimension being filtered (postal code, country, or category)
         * @return this builder
         */
        public Builder type(PackagingUnitContextType type) {
            this.type = type;
            return this;
        }

        /**
         * @param values list of values for the given type
         * @return this builder
         */
        public Builder values(List<String> values) {
            this.values = values;
            return this;
        }

        /**
         * @param operator optional logical operator applied to this context
         * @return this builder
         */
        public Builder operator(ContextOperator operator) {
            this.operator = operator;
            return this;
        }

        /**
         * Builds a {@link PackagingUnitContext}.
         *
         * @return a new instance
         */
        public PackagingUnitContext build() {
            return new PackagingUnitContext(type, values, operator);
        }
    }
}
