package de.joesst.dev.fulfillmenttools.inbound;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * A stow line item as provided in a {@link CreateStowJobRequest}.
 *
 * <p>Maps to the {@code StowLineItemForCreation} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param article          The article to be stowed. Required.
 * @param stowTo           Instructions on where and how to stow the article. Required.
 * @param takeFrom         Instructions on where and how to take the article from. Required.
 * @param reasons          Optional reasons for the stow operation (at most 10).
 * @param customAttributes Free-form custom attributes.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StowLineItemForCreation(
        StowLineItemArticle article,
        List<StowLineItemStowToForCreation> stowTo,
        StowLineItemTakeFrom takeFrom,
        List<ExternalStockChangeReasonInput> reasons,
        CustomAttributes customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private StowLineItemArticle article;
        private List<StowLineItemStowToForCreation> stowTo;
        private StowLineItemTakeFrom takeFrom;
        private List<ExternalStockChangeReasonInput> reasons;
        private CustomAttributes customAttributes;

        private Builder() {}

        public Builder article(StowLineItemArticle article) { this.article = article; return this; }
        public Builder stowTo(List<StowLineItemStowToForCreation> stowTo) { this.stowTo = stowTo; return this; }
        public Builder takeFrom(StowLineItemTakeFrom takeFrom) { this.takeFrom = takeFrom; return this; }
        public Builder reasons(List<ExternalStockChangeReasonInput> reasons) { this.reasons = reasons; return this; }
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        public StowLineItemForCreation build() {
            return new StowLineItemForCreation(article, stowTo, takeFrom, reasons, customAttributes);
        }
    }
}
