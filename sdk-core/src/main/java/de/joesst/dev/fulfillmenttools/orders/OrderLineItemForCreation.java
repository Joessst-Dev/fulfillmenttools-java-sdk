package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;
import de.joesst.dev.fulfillmenttools.model.TagReference;

import java.util.List;
import java.util.Objects;

/**
 * Data for creating a new order line item.
 *
 * <p>Maps to the {@code OrderLineItemForCreation} schema in the fulfillmenttools OpenAPI spec.
 * Both {@code article} and {@code quantity} are required.
 *
 * <p>Thread-safety: immutable; safe for concurrent use.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class OrderLineItemForCreation {

    private final OrderLineItemArticleForCreation article;
    private final Integer quantity;
    private final String measurementUnitKey;
    private final String secondaryMeasurementUnitKey;
    private final Integer secondaryQuantity;
    private final List<String> scannableCodes;
    private final List<Substitute> allowedSubstitutes;
    private final MeasurementValidation measurementValidation;
    private final List<TagReference> tags;
    private final CustomAttributes customAttributes;

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

    /**
     * Returns the article for this line item.
     *
     * @return the article (never null)
     */
    public OrderLineItemArticleForCreation article() { return article; }

    /**
     * Returns the quantity.
     *
     * @return the quantity (never null)
     */
    public Integer quantity() { return quantity; }

    /**
     * Returns the measurement unit key.
     *
     * @return the measurement unit key, or null if not set
     */
    public String measurementUnitKey() { return measurementUnitKey; }

    /**
     * Returns the secondary measurement unit key.
     *
     * @return the secondary measurement unit key, or null if not set
     */
    public String secondaryMeasurementUnitKey() { return secondaryMeasurementUnitKey; }

    /**
     * Returns the secondary quantity.
     *
     * @return the secondary quantity, or null if not set
     */
    public Integer secondaryQuantity() { return secondaryQuantity; }

    /**
     * Returns the scannable codes.
     *
     * @return the scannable codes list, or null if not set
     */
    public List<String> scannableCodes() { return scannableCodes; }

    /**
     * Returns allowed substitute articles; empty list means no substitutes permitted.
     *
     * @return the allowed substitutes list, or null if not set
     */
    public List<Substitute> allowedSubstitutes() { return allowedSubstitutes; }

    /**
     * Returns optional pick-quantity tolerance configuration.
     *
     * @return the measurement validation, or null if not set
     */
    public MeasurementValidation measurementValidation() { return measurementValidation; }

    /**
     * Returns the tags for this line item.
     *
     * @return the tags list, or null if not set
     */
    public List<TagReference> tags() { return tags; }

    /**
     * Returns custom attributes for this line item.
     *
     * @return the custom attributes map, or null if not set
     */
    public CustomAttributes customAttributes() { return customAttributes; }

    /**
     * Creates a new builder for constructing an {@code OrderLineItemForCreation}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@link OrderLineItemForCreation}.
     */
    public static final class Builder {

        /** Creates a new Builder. */
        public Builder() {}

        private OrderLineItemArticleForCreation article;
        private Integer quantity;
        private String measurementUnitKey;
        private String secondaryMeasurementUnitKey;
        private Integer secondaryQuantity;
        private List<String> scannableCodes;
        private List<Substitute> allowedSubstitutes;
        private MeasurementValidation measurementValidation;
        private List<TagReference> tags;
        private CustomAttributes customAttributes;

        /**
         * Sets the article (required).
         *
         * @param article the article
         * @return this builder
         */
        public Builder article(OrderLineItemArticleForCreation article) { this.article = article; return this; }

        /**
         * Sets the quantity (required).
         *
         * @param quantity the quantity
         * @return this builder
         */
        public Builder quantity(Integer quantity) { this.quantity = quantity; return this; }

        /**
         * Sets the measurement unit key.
         *
         * @param measurementUnitKey the measurement unit key
         * @return this builder
         */
        public Builder measurementUnitKey(String measurementUnitKey) { this.measurementUnitKey = measurementUnitKey; return this; }

        /**
         * Sets the secondary measurement unit key.
         *
         * @param secondaryMeasurementUnitKey the secondary measurement unit key
         * @return this builder
         */
        public Builder secondaryMeasurementUnitKey(String secondaryMeasurementUnitKey) { this.secondaryMeasurementUnitKey = secondaryMeasurementUnitKey; return this; }

        /**
         * Sets the secondary quantity.
         *
         * @param secondaryQuantity the secondary quantity
         * @return this builder
         */
        public Builder secondaryQuantity(Integer secondaryQuantity) { this.secondaryQuantity = secondaryQuantity; return this; }

        /**
         * Sets the scannable codes.
         *
         * @param scannableCodes the scannable codes list
         * @return this builder
         */
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }

        /**
         * Sets allowed substitute articles.
         *
         * @param allowedSubstitutes the allowed substitutes list
         * @return this builder
         */
        public Builder allowedSubstitutes(List<Substitute> allowedSubstitutes) { this.allowedSubstitutes = allowedSubstitutes; return this; }

        /**
         * Sets the pick-quantity tolerance configuration.
         *
         * @param measurementValidation the measurement validation
         * @return this builder
         */
        public Builder measurementValidation(MeasurementValidation measurementValidation) { this.measurementValidation = measurementValidation; return this; }

        /**
         * Sets the tags for this line item.
         *
         * @param tags the tags list
         * @return this builder
         */
        public Builder tags(List<TagReference> tags) { this.tags = tags; return this; }

        /**
         * Sets custom attributes for this line item.
         *
         * @param customAttributes the custom attributes map
         * @return this builder
         */
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        /**
         * Builds the {@link OrderLineItemForCreation}.
         *
         * @return a new line item
         * @throws NullPointerException if article or quantity has not been set
         */
        public OrderLineItemForCreation build() { return new OrderLineItemForCreation(this); }
    }
}
