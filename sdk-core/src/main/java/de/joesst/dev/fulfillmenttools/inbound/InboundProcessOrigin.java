package de.joesst.dev.fulfillmenttools.inbound;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Describes the origin (supplier) of an inbound delivery.
 *
 * @param name the supplier or origin name
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record InboundProcessOrigin(String name) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String name;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public InboundProcessOrigin build() {
            return new InboundProcessOrigin(name);
        }
    }
}
