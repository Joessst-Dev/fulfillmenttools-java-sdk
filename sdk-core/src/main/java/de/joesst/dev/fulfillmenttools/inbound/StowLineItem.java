package de.joesst.dev.fulfillmenttools.inbound;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

import java.util.List;

/**
 * A single line item within a stow job, describing an article that needs to be taken
 * from one location and stowed to one or more destinations.
 *
 * <p>Maps to the {@code StowLineItem} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id               Server-assigned ID of this stow line item.
 * @param article          The article to be stowed.
 * @param customAttributes Free-form custom attributes.
 * @param heldStockRef     Reference to a transient stock holding the goods after a take but
 *                         before the stow has taken place.
 * @param reasons          Reasons for the stow operation (at most 10).
 * @param stowTo           Instructions on where and how to stow the article.
 * @param takeFrom         Instructions on where and how to take the article from.
 */
public record StowLineItem(
        String id,
        StowLineItemArticle article,
        CustomAttributes customAttributes,
        String heldStockRef,
        List<ExternalStockChangeReason> reasons,
        List<StowLineItemStowTo> stowTo,
        StowLineItemTakeFrom takeFrom
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private StowLineItemArticle article;
        private CustomAttributes customAttributes;
        private String heldStockRef;
        private List<ExternalStockChangeReason> reasons;
        private List<StowLineItemStowTo> stowTo;
        private StowLineItemTakeFrom takeFrom;

        private Builder() {}

        public Builder id(String id) { this.id = id; return this; }
        public Builder article(StowLineItemArticle article) { this.article = article; return this; }
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }
        public Builder heldStockRef(String heldStockRef) { this.heldStockRef = heldStockRef; return this; }
        public Builder reasons(List<ExternalStockChangeReason> reasons) { this.reasons = reasons; return this; }
        public Builder stowTo(List<StowLineItemStowTo> stowTo) { this.stowTo = stowTo; return this; }
        public Builder takeFrom(StowLineItemTakeFrom takeFrom) { this.takeFrom = takeFrom; return this; }

        public StowLineItem build() {
            return new StowLineItem(id, article, customAttributes, heldStockRef, reasons, stowTo, takeFrom);
        }
    }
}
