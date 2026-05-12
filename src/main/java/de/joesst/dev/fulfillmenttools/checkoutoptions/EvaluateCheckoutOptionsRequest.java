package de.joesst.dev.fulfillmenttools.checkoutoptions;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class EvaluateCheckoutOptionsRequest {

    private final Map<String, Object> deliveryPreferences;
    private final List<CheckoutOrderLineItem> orderLineItems;
    private final Map<String, Object> consumerAddress;
    private final Map<String, Object> customAttributes;
    private final Boolean filterDuplicates;

    private EvaluateCheckoutOptionsRequest(Builder builder) {
        this.deliveryPreferences = Objects.requireNonNull(builder.deliveryPreferences, "deliveryPreferences must not be null");
        this.orderLineItems = Objects.requireNonNull(builder.orderLineItems, "orderLineItems must not be null");
        this.consumerAddress = builder.consumerAddress;
        this.customAttributes = builder.customAttributes;
        this.filterDuplicates = builder.filterDuplicates;
    }

    public Map<String, Object> deliveryPreferences() { return deliveryPreferences; }
    public List<CheckoutOrderLineItem> orderLineItems() { return orderLineItems; }
    public Map<String, Object> consumerAddress() { return consumerAddress; }
    public Map<String, Object> customAttributes() { return customAttributes; }
    public Boolean filterDuplicates() { return filterDuplicates; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Map<String, Object> deliveryPreferences;
        private List<CheckoutOrderLineItem> orderLineItems;
        private Map<String, Object> consumerAddress;
        private Map<String, Object> customAttributes;
        private Boolean filterDuplicates;

        public Builder deliveryPreferences(Map<String, Object> deliveryPreferences) {
            this.deliveryPreferences = deliveryPreferences;
            return this;
        }

        public Builder orderLineItems(List<CheckoutOrderLineItem> orderLineItems) {
            this.orderLineItems = orderLineItems;
            return this;
        }

        public Builder consumerAddress(Map<String, Object> consumerAddress) {
            this.consumerAddress = consumerAddress;
            return this;
        }

        public Builder customAttributes(Map<String, Object> customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        public Builder filterDuplicates(Boolean filterDuplicates) {
            this.filterDuplicates = filterDuplicates;
            return this;
        }

        public EvaluateCheckoutOptionsRequest build() { return new EvaluateCheckoutOptionsRequest(this); }
    }
}
