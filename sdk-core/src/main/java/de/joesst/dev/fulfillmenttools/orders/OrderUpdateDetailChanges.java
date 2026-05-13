package de.joesst.dev.fulfillmenttools.orders;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents the specific changes made in an order update event.
 *
 * @param consumer the consumer information that was changed
 * @param customAttributes custom attributes that were changed
 * @param orderLineItems the order line items that were changed
 * @param preferredHandlingTime the preferred handling time that was changed
 */
public record OrderUpdateDetailChanges(
        OrderForCreationConsumer consumer,
        Map<String, Object> customAttributes,
        List<OrderLineItemForUpdate> orderLineItems,
        Instant preferredHandlingTime
) {

    /**
     * Returns a builder for constructing an {@code OrderUpdateDetailChanges}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link OrderUpdateDetailChanges}.
     */
    public static final class Builder {

        private OrderForCreationConsumer consumer;
        private Map<String, Object> customAttributes;
        private List<OrderLineItemForUpdate> orderLineItems;
        private Instant preferredHandlingTime;

        private Builder() {}

        /**
         * Sets the consumer information that was changed.
         * @param consumer the updated consumer
         * @return this builder
         */
        public Builder consumer(OrderForCreationConsumer consumer) {
            this.consumer = consumer;
            return this;
        }

        /**
         * Sets the custom attributes that were changed.
         * @param customAttributes the updated custom attributes map
         * @return this builder
         */
        public Builder customAttributes(Map<String, Object> customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        /**
         * Sets the order line items that were changed.
         * @param orderLineItems the updated order line items
         * @return this builder
         */
        public Builder orderLineItems(List<OrderLineItemForUpdate> orderLineItems) {
            this.orderLineItems = orderLineItems;
            return this;
        }

        /**
         * Sets the preferred handling time that was changed.
         * @param preferredHandlingTime the updated preferred handling time
         * @return this builder
         */
        public Builder preferredHandlingTime(Instant preferredHandlingTime) {
            this.preferredHandlingTime = preferredHandlingTime;
            return this;
        }

        /**
         * Builds an {@link OrderUpdateDetailChanges}.
         *
         * @return a new instance.
         */
        public OrderUpdateDetailChanges build() {
            return new OrderUpdateDetailChanges(consumer, customAttributes, orderLineItems, preferredHandlingTime);
        }
    }
}
