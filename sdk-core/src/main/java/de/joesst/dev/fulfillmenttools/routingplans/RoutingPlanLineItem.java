package de.joesst.dev.fulfillmenttools.routingplans;

import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.orders.MeasurementValidation;
import de.joesst.dev.fulfillmenttools.orders.OrderLineItemArticle;
import de.joesst.dev.fulfillmenttools.orders.Substitute;

import java.util.List;
import java.util.Map;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * A line item on a routing plan, extending the base order line item with routing-specific
 * availability and stock information.
 *
 * <p>Maps to the {@code RoutingPlanLineItem} schema in the fulfillmenttools OpenAPI spec,
 * which extends {@code OrderLineItem}.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                          The platform-generated line item identifier.
 * @param quantity                    The ordered quantity.
 * @param article                     The article details.
 * @param measurementUnitKey          Optional unit of measurement identifier.
 * @param secondaryMeasurementUnitKey Optional secondary unit of measurement.
 * @param secondaryQuantity           Optional secondary quantity.
 * @param scannableCodes              Optional scannable codes for the article.
 * @param allowedSubstitutes          Optional allowed substitute articles.
 * @param measurementValidation       Optional tolerance configuration for quantity deviations.
 * @param tags                        Optional categorization tags.
 * @param customAttributes            Free-form custom attributes.
 * @param available                   The quantity available for this routing plan.
 * @param picked                      The quantity already picked.
 * @param outOfStockBehaviour         The behaviour when the article is out of stock.
 * @param availabilityTimeframe       Optional time frame when the article becomes available.
 */
public record RoutingPlanLineItem(
        String id,
        Integer quantity,
        OrderLineItemArticle article,
        String measurementUnitKey,
        String secondaryMeasurementUnitKey,
        Integer secondaryQuantity,
        List<String> scannableCodes,
        List<Substitute> allowedSubstitutes,
        MeasurementValidation measurementValidation,
        List<TagReference> tags,
        CustomAttributes customAttributes,
        Double available,
        Double picked,
        String outOfStockBehaviour,
        AvailabilityTimeframe availabilityTimeframe
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private Integer quantity;
        private OrderLineItemArticle article;
        private String measurementUnitKey;
        private String secondaryMeasurementUnitKey;
        private Integer secondaryQuantity;
        private List<String> scannableCodes;
        private List<Substitute> allowedSubstitutes;
        private MeasurementValidation measurementValidation;
        private List<TagReference> tags;
        private CustomAttributes customAttributes;
        private Double available;
        private Double picked;
        private String outOfStockBehaviour;
        private AvailabilityTimeframe availabilityTimeframe;

        private Builder() {}

        public Builder id(String id) { this.id = id; return this; }
        public Builder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public Builder article(OrderLineItemArticle article) { this.article = article; return this; }
        public Builder measurementUnitKey(String measurementUnitKey) { this.measurementUnitKey = measurementUnitKey; return this; }
        public Builder secondaryMeasurementUnitKey(String secondaryMeasurementUnitKey) { this.secondaryMeasurementUnitKey = secondaryMeasurementUnitKey; return this; }
        public Builder secondaryQuantity(Integer secondaryQuantity) { this.secondaryQuantity = secondaryQuantity; return this; }
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }
        public Builder allowedSubstitutes(List<Substitute> allowedSubstitutes) { this.allowedSubstitutes = allowedSubstitutes; return this; }
        public Builder measurementValidation(MeasurementValidation measurementValidation) { this.measurementValidation = measurementValidation; return this; }
        public Builder tags(List<TagReference> tags) { this.tags = tags; return this; }
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }
        public Builder available(Double available) { this.available = available; return this; }
        public Builder picked(Double picked) { this.picked = picked; return this; }
        public Builder outOfStockBehaviour(String outOfStockBehaviour) { this.outOfStockBehaviour = outOfStockBehaviour; return this; }
        public Builder availabilityTimeframe(AvailabilityTimeframe availabilityTimeframe) { this.availabilityTimeframe = availabilityTimeframe; return this; }

        public RoutingPlanLineItem build() {
            return new RoutingPlanLineItem(id, quantity, article, measurementUnitKey, secondaryMeasurementUnitKey, secondaryQuantity, scannableCodes, allowedSubstitutes, measurementValidation, tags, customAttributes, available, picked, outOfStockBehaviour, availabilityTimeframe);
        }
    }
}
