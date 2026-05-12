package de.joesst.dev.fulfillmenttools.orders;

import de.joesst.dev.fulfillmenttools.model.TagReference;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class OrderLineItemForCreation {

    private final OrderLineItemArticleForCreation article;
    private final Integer quantity;
    private final String measurementUnitKey;
    private final String secondaryMeasurementUnitKey;
    private final Integer secondaryQuantity;
    private final List<String> scannableCodes;
    private final List<Map<String, Object>> allowedSubstitutes;
    private final Map<String, Object> measurementValidation;
    private final List<TagReference> tags;
    private final Map<String, Object> customAttributes;

    private OrderLineItemForCreation(Builder builder) {
        this.article = Objects.requireNonNull(builder.article, "article must not be null");
        this.quantity = Objects.requireNonNull(builder.quantity, "quantity must not be null");
        this.measurementUnitKey = builder.measurementUnitKey;
        this.secondaryMeasurementUnitKey = builder.secondaryMeasurementUnitKey;
        this.secondaryQuantity = builder.secondaryQuantity;
        this.scannableCodes = builder.scannableCodes;
        this.allowedSubstitutes = builder.allowedSubstitutes;
        this.measurementValidation = builder.measurementValidation;
        this.tags = builder.tags;
        this.customAttributes = builder.customAttributes;
    }

    public OrderLineItemArticleForCreation article() { return article; }
    public Integer quantity() { return quantity; }
    public String measurementUnitKey() { return measurementUnitKey; }
    public String secondaryMeasurementUnitKey() { return secondaryMeasurementUnitKey; }
    public Integer secondaryQuantity() { return secondaryQuantity; }
    public List<String> scannableCodes() { return scannableCodes; }
    public List<Map<String, Object>> allowedSubstitutes() { return allowedSubstitutes; }
    public Map<String, Object> measurementValidation() { return measurementValidation; }
    public List<TagReference> tags() { return tags; }
    public Map<String, Object> customAttributes() { return customAttributes; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private OrderLineItemArticleForCreation article;
        private Integer quantity;
        private String measurementUnitKey;
        private String secondaryMeasurementUnitKey;
        private Integer secondaryQuantity;
        private List<String> scannableCodes;
        private List<Map<String, Object>> allowedSubstitutes;
        private Map<String, Object> measurementValidation;
        private List<TagReference> tags;
        private Map<String, Object> customAttributes;

        public Builder article(OrderLineItemArticleForCreation article) { this.article = article; return this; }
        public Builder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public Builder measurementUnitKey(String measurementUnitKey) { this.measurementUnitKey = measurementUnitKey; return this; }
        public Builder secondaryMeasurementUnitKey(String secondaryMeasurementUnitKey) { this.secondaryMeasurementUnitKey = secondaryMeasurementUnitKey; return this; }
        public Builder secondaryQuantity(Integer secondaryQuantity) { this.secondaryQuantity = secondaryQuantity; return this; }
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }
        public Builder allowedSubstitutes(List<Map<String, Object>> allowedSubstitutes) { this.allowedSubstitutes = allowedSubstitutes; return this; }
        public Builder measurementValidation(Map<String, Object> measurementValidation) { this.measurementValidation = measurementValidation; return this; }
        public Builder tags(List<TagReference> tags) { this.tags = tags; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public OrderLineItemForCreation build() { return new OrderLineItemForCreation(this); }
    }
}
