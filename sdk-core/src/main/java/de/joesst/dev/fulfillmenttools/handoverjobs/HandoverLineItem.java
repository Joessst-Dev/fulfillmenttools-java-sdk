package de.joesst.dev.fulfillmenttools.handoverjobs;

import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.orders.Sticker;

import java.util.List;

/**
 * A line item within a handover job, representing a quantity of an article to be handed over.
 *
 * <p>Maps to the {@code HandoverLineItem} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                   Unique identifier of this line item. Required.
 * @param quantity             Number of units provided for handover. Required; minimum 1.
 * @param handedOverQuantity   Number of units that have actually been handed over.
 *                             Required; minimum 0.
 * @param originId             The original line item id before any split occurred.
 * @param status               Current status of the line item ({@code OPEN}, {@code CLOSED}).
 * @param article              The article to be handed over. Required.
 * @param refused              Refusal records for units not accepted by the recipient.
 * @param stickers             Visual stickers attached to this line item.
 * @param substituteLineItems  Alternative articles offered when the original is unavailable
 *                             (Beta feature).
 * @param tags                 Tag references attached to this line item.
 */
public record HandoverLineItem(
        String id,
        Integer quantity,
        Integer handedOverQuantity,
        String originId,
        String status,
        HandoverLineItemArticle article,
        List<HandoverRefusedItem> refused,
        List<Sticker> stickers,
        List<HandoverSubstituteLineItem> substituteLineItems,
        List<TagReference> tags
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private Integer quantity;
        private Integer handedOverQuantity;
        private String originId;
        private String status;
        private HandoverLineItemArticle article;
        private List<HandoverRefusedItem> refused;
        private List<Sticker> stickers;
        private List<HandoverSubstituteLineItem> substituteLineItems;
        private List<TagReference> tags;

        private Builder() {}

        public Builder id(String id) { this.id = id; return this; }
        public Builder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public Builder handedOverQuantity(Integer handedOverQuantity) { this.handedOverQuantity = handedOverQuantity; return this; }
        public Builder originId(String originId) { this.originId = originId; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder article(HandoverLineItemArticle article) { this.article = article; return this; }
        public Builder refused(List<HandoverRefusedItem> refused) { this.refused = refused; return this; }
        public Builder stickers(List<Sticker> stickers) { this.stickers = stickers; return this; }
        public Builder substituteLineItems(List<HandoverSubstituteLineItem> substituteLineItems) { this.substituteLineItems = substituteLineItems; return this; }
        public Builder tags(List<TagReference> tags) { this.tags = tags; return this; }

        public HandoverLineItem build() {
            return new HandoverLineItem(id, quantity, handedOverQuantity, originId, status, article, refused, stickers, substituteLineItems, tags);
        }
    }
}
