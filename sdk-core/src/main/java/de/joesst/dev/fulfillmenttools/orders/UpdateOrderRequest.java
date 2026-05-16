package de.joesst.dev.fulfillmenttools.orders;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * Request to update an existing order.
 */
public final class UpdateOrderRequest {

    private final Integer version;
    private final String comment;
    private final OrderForCreationConsumer consumer;
    private final CustomAttributes customAttributes;
    private final List<OrderLineItemForUpdate> orderLineItems;
    private final Instant preferredHandlingTime;

    private UpdateOrderRequest(Builder builder) {
        this.version = Objects.requireNonNull(builder.version, "version must not be null");
        this.comment = builder.comment;
        this.consumer = builder.consumer;
        this.customAttributes = builder.customAttributes;
        this.orderLineItems = builder.orderLineItems;
        this.preferredHandlingTime = builder.preferredHandlingTime;
    }

    /**
     * Returns the version of the order to update.
     *
     * @return the version
     */
    public Integer version() { return version; }

    /**
     * Returns an optional comment about this update.
     *
     * @return the comment, or null if not set
     */
    public String comment() { return comment; }

    /**
     * Returns the updated consumer information.
     *
     * @return the consumer, or null if not set
     */
    public OrderForCreationConsumer consumer() { return consumer; }

    /**
     * Returns the updated custom attributes.
     *
     * @return the custom attributes map, or null if not set
     */
    public CustomAttributes customAttributes() { return customAttributes; }

    /**
     * Returns the updated order line items.
     *
     * @return the line items list, or null if not set
     */
    public List<OrderLineItemForUpdate> orderLineItems() { return orderLineItems; }

    /**
     * Returns the updated preferred handling time.
     *
     * @return the preferred handling time, or null if not set
     */
    public Instant preferredHandlingTime() { return preferredHandlingTime; }

    /**
     * Creates a new builder for constructing an {@code UpdateOrderRequest}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@link UpdateOrderRequest}.
     */
    public static final class Builder {

        /** Creates a new Builder. */
        public Builder() {}

        private Integer version;
        private String comment;
        private OrderForCreationConsumer consumer;
        private CustomAttributes customAttributes;
        private List<OrderLineItemForUpdate> orderLineItems;
        private Instant preferredHandlingTime;

        /**
         * Sets the version of the order to update.
         *
         * @param version the version (required)
         * @return this builder
         */
        public Builder version(Integer version) { this.version = version; return this; }

        /**
         * Sets an optional comment about this update.
         *
         * @param comment the comment
         * @return this builder
         */
        public Builder comment(String comment) { this.comment = comment; return this; }

        /**
         * Sets the updated consumer information.
         *
         * @param consumer the consumer
         * @return this builder
         */
        public Builder consumer(OrderForCreationConsumer consumer) { this.consumer = consumer; return this; }

        /**
         * Sets the updated custom attributes.
         *
         * @param customAttributes the custom attributes map
         * @return this builder
         */
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        /**
         * Sets the updated order line items.
         *
         * @param orderLineItems the line items list
         * @return this builder
         */
        public Builder orderLineItems(List<OrderLineItemForUpdate> orderLineItems) { this.orderLineItems = orderLineItems; return this; }

        /**
         * Sets the updated preferred handling time.
         *
         * @param preferredHandlingTime the preferred handling time
         * @return this builder
         */
        public Builder preferredHandlingTime(Instant preferredHandlingTime) { this.preferredHandlingTime = preferredHandlingTime; return this; }

        /**
         * Builds the {@link UpdateOrderRequest}.
         *
         * @return a new request
         */
        public UpdateOrderRequest build() { return new UpdateOrderRequest(this); }
    }
}
