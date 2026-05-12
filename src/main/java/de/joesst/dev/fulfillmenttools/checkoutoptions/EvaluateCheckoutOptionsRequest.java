package de.joesst.dev.fulfillmenttools.checkoutoptions;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class EvaluateCheckoutOptionsRequest {

    private final Map<String, Object> deliveryPreferences;
    private final List<Map<String, Object>> orderLineItems;
    private final Map<String, Object> consumerAddress;
    private final Map<String, Object> customAttributes;
    private final Boolean filterDuplicates;
    private final List<Map<String, Object>> customServices;
    private final Map<String, Object> geoFence;
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

    public Map<String, Object> deliveryPreferences() { return deliveryPreferences; }
    public List<Map<String, Object>> orderLineItems() { return orderLineItems; }
    public Map<String, Object> consumerAddress() { return consumerAddress; }
    public Map<String, Object> customAttributes() { return customAttributes; }
    public Boolean filterDuplicates() { return filterDuplicates; }
    public List<Map<String, Object>> customServices() { return customServices; }
    public Map<String, Object> geoFence() { return geoFence; }
    public List<Map<String, Object>> tags() { return tags; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Map<String, Object> deliveryPreferences;
        private List<Map<String, Object>> orderLineItems;
        private Map<String, Object> consumerAddress;
        private Map<String, Object> customAttributes;
        private Boolean filterDuplicates;
        private List<Map<String, Object>> customServices;
        private Map<String, Object> geoFence;
        private List<Map<String, Object>> tags;

        public Builder deliveryPreferences(Map<String, Object> deliveryPreferences) {
            this.deliveryPreferences = deliveryPreferences;
            return this;
        }

        public Builder orderLineItems(List<Map<String, Object>> orderLineItems) {
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

        public Builder customServices(List<Map<String, Object>> customServices) {
            this.customServices = customServices;
            return this;
        }

        public Builder geoFence(Map<String, Object> geoFence) {
            this.geoFence = geoFence;
            return this;
        }

        public Builder tags(List<Map<String, Object>> tags) {
            this.tags = tags;
            return this;
        }

        public EvaluateCheckoutOptionsRequest build() { return new EvaluateCheckoutOptionsRequest(this); }
    }
}
