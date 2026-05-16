package de.joesst.dev.fulfillmenttools.handoverjobs;

import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.orders.Sticker;

import java.util.List;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * A line item that is expected (anticipated) on a handover job but not yet confirmed.
 *
 * <p>Maps to the {@code ExpectedHandoverLineItem} schema in the fulfillmenttools OpenAPI spec,
 * which extends {@code ExpectedHandoverLineItemForCreation} with a generated {@code id}.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id               Unique identifier of the expected line item. Required.
 * @param quantity         Number of units expected for handover. Required; minimum 1.
 * @param transferId       Reference to the transfer this expected line item belongs to. Required.
 * @param article          The expected article.
 * @param status           Current status of the expected line item ({@code OPEN}, {@code CLOSED}).
 * @param stickers         Visual stickers attached to this expected line item.
 * @param scannableCodes   Scannable codes that can identify this line item.
 * @param tags             Tag references attached to this expected line item.
 * @param customAttributes Free-form custom attributes attached to this line item.
 */
public record ExpectedHandoverLineItem(
        String id,
        Integer quantity,
        String transferId,
        HandoverLineItemArticle article,
        String status,
        List<Sticker> stickers,
        List<String> scannableCodes,
        List<TagReference> tags,
        CustomAttributes customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String id;
        private Integer quantity;
        private String transferId;
        private HandoverLineItemArticle article;
        private String status;
        private List<Sticker> stickers;
        private List<String> scannableCodes;
        private List<TagReference> tags;
        private CustomAttributes customAttributes;

        public Builder id(String id) { this.id = id; return this; }
        public Builder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public Builder transferId(String transferId) { this.transferId = transferId; return this; }
        public Builder article(HandoverLineItemArticle article) { this.article = article; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder stickers(List<Sticker> stickers) { this.stickers = stickers; return this; }
        public Builder scannableCodes(List<String> scannableCodes) { this.scannableCodes = scannableCodes; return this; }
        public Builder tags(List<TagReference> tags) { this.tags = tags; return this; }
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        public ExpectedHandoverLineItem build() {
            return new ExpectedHandoverLineItem(id, quantity, transferId, article, status, stickers, scannableCodes, tags, customAttributes);
        }
    }
}
