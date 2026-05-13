package de.joesst.dev.fulfillmenttools.packjobs;

import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.orders.Sticker;

import java.util.List;

/**
 * A single line item within a pack job representing an article quantity to be packed.
 *
 * <p>Maps to the {@code PackLineItem} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                 Unique identifier of the line item.
 * @param quantity           Quantity of this article to be packed.
 * @param article            The article to be packed.
 * @param packed             The amount of articles that have already been packed.
 * @param measurementUnitKey Identifier for the item's unit of measurement (e.g. {@code liter}).
 * @param originId           Id of the original line item this one was split from.
 * @param serviceJobRefs     References to service jobs that have altered this line item.
 * @param stickers           Visual stickers attached to this line item.
 * @param tags               Tag references attached to this line item.
 */
public record PackLineItem(
        String id,
        Integer quantity,
        PackLineItemArticle article,
        Integer packed,
        String measurementUnitKey,
        String originId,
        List<String> serviceJobRefs,
        List<Sticker> stickers,
        List<TagReference> tags
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private Integer quantity;
        private PackLineItemArticle article;
        private Integer packed;
        private String measurementUnitKey;
        private String originId;
        private List<String> serviceJobRefs;
        private List<Sticker> stickers;
        private List<TagReference> tags;

        private Builder() {}

        public Builder id(String id) { this.id = id; return this; }
        public Builder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public Builder article(PackLineItemArticle article) { this.article = article; return this; }
        public Builder packed(Integer packed) { this.packed = packed; return this; }
        public Builder measurementUnitKey(String measurementUnitKey) { this.measurementUnitKey = measurementUnitKey; return this; }
        public Builder originId(String originId) { this.originId = originId; return this; }
        public Builder serviceJobRefs(List<String> serviceJobRefs) { this.serviceJobRefs = serviceJobRefs; return this; }
        public Builder stickers(List<Sticker> stickers) { this.stickers = stickers; return this; }
        public Builder tags(List<TagReference> tags) { this.tags = tags; return this; }

        public PackLineItem build() {
            return new PackLineItem(id, quantity, article, packed, measurementUnitKey, originId, serviceJobRefs, stickers, tags);
        }
    }
}
