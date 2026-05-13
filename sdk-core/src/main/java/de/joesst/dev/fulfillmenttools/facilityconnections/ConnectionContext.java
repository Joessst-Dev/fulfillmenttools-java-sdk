package de.joesst.dev.fulfillmenttools.facilityconnections;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Scopes a facility connection to a subset of entities (e.g. specific facilities,
 * facility groups, categories, or tags).
 *
 * @param type     the kind of entity being referenced
 * @param values   list of entity identifiers of the given type
 * @param operator optional logical operator applied to this context; {@code null} means positive match
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ConnectionContext(ConnectionContextType type, List<String> values, ContextOperator operator) {

    /**
     * Returns a builder for constructing a {@link ConnectionContext}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link ConnectionContext}.
     */
    public static final class Builder {

        private ConnectionContextType type;
        private List<String> values;
        private ContextOperator operator;

        private Builder() {}

        /**
         * @param type the kind of entity being referenced
         * @return this builder
         */
        public Builder type(ConnectionContextType type) {
            this.type = type;
            return this;
        }

        /**
         * @param values list of entity identifiers of the given type
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
         * Builds a {@link ConnectionContext}.
         *
         * @return a new instance
         */
        public ConnectionContext build() {
            return new ConnectionContext(type, values, operator);
        }
    }
}
