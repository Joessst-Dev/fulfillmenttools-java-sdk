package de.joesst.dev.fulfillmenttools.eventing;

import java.util.List;

/**
 * Context filter for an event subscription.
 *
 * @param type the context type
 * @param values list of context values to filter on
 */
public record SubscriptionContext(String type, List<String> values) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String type;
        private List<String> values;

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder values(List<String> values) {
            this.values = values;
            return this;
        }

        public SubscriptionContext build() {
            return new SubscriptionContext(type, values);
        }
    }
}
