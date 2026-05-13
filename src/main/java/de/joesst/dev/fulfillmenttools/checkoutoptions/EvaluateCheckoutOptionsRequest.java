package de.joesst.dev.fulfillmenttools.checkoutoptions;

import de.joesst.dev.fulfillmenttools.orders.DeliveryPreferences;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Request object for evaluating checkout options against the fulfillmenttools promise engine.
 *
 * <p>{@code deliveryPreferences} and {@code orderLineItems} are required; all other fields
 * are optional and omitted from the serialised payload when {@code null}.
 *
 * <p>Instances are constructed via the {@link Builder} returned by {@link #builder()}.
 *
 * <p>Thread-safety: effectively immutable after construction; safe for concurrent use.
 */
public final class EvaluateCheckoutOptionsRequest {

    private final DeliveryPreferences deliveryPreferences;
    private final List<Map<String, Object>> orderLineItems;
    private final CheckoutOptionsConsumerAddress consumerAddress;
    private final Map<String, Object> customAttributes;
    private final Boolean filterDuplicates;
    private final List<Map<String, Object>> customServices;
    private final GeoFence geoFence;
    private final List<Map<String, Object>> tags;

    private EvaluateCheckoutOptionsRequest(Builder builder) {
        this.deliveryPreferences = Objects.requireNonNull(builder.deliveryPreferences, "deliveryPreferences must not be null");
        this.orderLineItems = Objects.requireNonNull(builder.orderLineItems, "orderLineItems must not be null");
        this.consumerAddress = builder.consumerAddress;
        this.customAttributes = builder.customAttributes;
        this.filterDuplicates = builder.filterDuplicates;
        this.customServices = builder.customServices;
        this.geoFence = builder.geoFence;
        this.tags = builder.tags;
    }

    /**
     * Returns the delivery preferences for this request.
     *
     * @return typed delivery preferences; never {@code null}.
     */
    public DeliveryPreferences deliveryPreferences() { return deliveryPreferences; }

    /**
     * Returns the line items to evaluate.
     *
     * @return line items to evaluate; never {@code null}.
     */
    public List<Map<String, Object>> orderLineItems() { return orderLineItems; }

    /**
     * Returns the consumer address.
     *
     * @return consumer address, or {@code null} if not provided.
     */
    public CheckoutOptionsConsumerAddress consumerAddress() { return consumerAddress; }

    /**
     * Returns custom attributes.
     *
     * @return custom attributes map, or {@code null} if not provided.
     */
    public Map<String, Object> customAttributes() { return customAttributes; }

    /**
     * Returns whether duplicate facilities should be filtered.
     *
     * @return whether duplicate facilities should be filtered, or {@code null} if not set.
     */
    public Boolean filterDuplicates() { return filterDuplicates; }

    /**
     * Returns custom service references.
     *
     * @return custom service references, or {@code null} if not provided.
     */
    public List<Map<String, Object>> customServices() { return customServices; }

    /**
     * Returns the geographic fence constraint.
     *
     * @return geographic fence constraint, or {@code null} if not provided.
     */
    public GeoFence geoFence() { return geoFence; }

    /**
     * Returns the tags to filter results.
     *
     * @return tags to filter results, or {@code null} if not provided.
     */
    public List<Map<String, Object>> tags() { return tags; }

    /**
     * Returns a new builder for constructing an {@link EvaluateCheckoutOptionsRequest}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@link EvaluateCheckoutOptionsRequest}.
     */
    public static final class Builder {

        private DeliveryPreferences deliveryPreferences;
        private List<Map<String, Object>> orderLineItems;
        private CheckoutOptionsConsumerAddress consumerAddress;
        private Map<String, Object> customAttributes;
        private Boolean filterDuplicates;
        private List<Map<String, Object>> customServices;
        private GeoFence geoFence;
        private List<Map<String, Object>> tags;

        /** Creates a new Builder. */
        private Builder() {}

        /**
         * Sets the delivery preferences (required).
         *
         * @param deliveryPreferences must not be {@code null}.
         * @return this builder
         */
        public Builder deliveryPreferences(DeliveryPreferences deliveryPreferences) {
            this.deliveryPreferences = deliveryPreferences;
            return this;
        }

        /**
         * Sets the order line items to evaluate (required).
         *
         * @param orderLineItems must not be {@code null}.
         * @return this builder
         */
        public Builder orderLineItems(List<Map<String, Object>> orderLineItems) {
            this.orderLineItems = orderLineItems;
            return this;
        }

        /**
         * Sets the consumer address.
         * @param consumerAddress the consumer address
         * @return this builder
         */
        public Builder consumerAddress(CheckoutOptionsConsumerAddress consumerAddress) {
            this.consumerAddress = consumerAddress;
            return this;
        }

        /**
         * Sets custom attributes to include in the request.
         * @param customAttributes the custom attributes map
         * @return this builder
         */
        public Builder customAttributes(Map<String, Object> customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        /**
         * Sets whether duplicate facilities should be filtered from results.
         * @param filterDuplicates whether to filter duplicates
         * @return this builder
         */
        public Builder filterDuplicates(Boolean filterDuplicates) {
            this.filterDuplicates = filterDuplicates;
            return this;
        }

        /**
         * Sets custom service references.
         * @param customServices the list of custom service references
         * @return this builder
         */
        public Builder customServices(List<Map<String, Object>> customServices) {
            this.customServices = customServices;
            return this;
        }

        /**
         * Sets the geographic fence used to restrict facility results.
         * @param geoFence the geographic fence
         * @return this builder
         */
        public Builder geoFence(GeoFence geoFence) {
            this.geoFence = geoFence;
            return this;
        }

        /**
         * Sets tag filters to narrow facility results.
         * @param tags the list of tags
         * @return this builder
         */
        public Builder tags(List<Map<String, Object>> tags) {
            this.tags = tags;
            return this;
        }

        /**
         * Builds the request.
         *
         * @return a new {@link EvaluateCheckoutOptionsRequest}.
         * @throws NullPointerException if {@code deliveryPreferences} or {@code orderLineItems}
         *                              have not been set.
         */
        public EvaluateCheckoutOptionsRequest build() { return new EvaluateCheckoutOptionsRequest(this); }
    }
}
