package de.joesst.dev.fulfillmenttools.inbound;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.InboundReceiptId;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents a fulfillmenttools inbound receipt, recording the actual goods received
 * against a purchase order.
 *
 * <p>Received as the payload of {@code INBOUND_PROCESS_RECEIPT_CREATED},
 * {@code INBOUND_PROCESS_RECEIPT_LINE_ITEM_CHANGED}, and
 * {@code INBOUND_PROCESS_RECEIPT_STARTED} events.
 *
 * @param id the platform-generated {@link InboundReceiptId}
 * @param receivedDate the timestamp when goods were received
 * @param receivedItems the line items detailing received quantities per article
 * @param comments comments attached to this receipt
 * @param status the current processing status
 * @param asnRef reference to an advance shipping notice document
 * @param customAttributes tenant-defined key/value extension attributes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record InboundReceipt(
        InboundReceiptId id,
        Instant receivedDate,
        List<InboundReceiptLineItem> receivedItems,
        List<InboundReceiptComment> comments,
        InboundReceiptStatus status,
        String asnRef,
        Map<String, Object> customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private InboundReceiptId id;
        private Instant receivedDate;
        private List<InboundReceiptLineItem> receivedItems;
        private List<InboundReceiptComment> comments;
        private InboundReceiptStatus status;
        private String asnRef;
        private Map<String, Object> customAttributes;

        public Builder id(InboundReceiptId id) {
            this.id = id;
            return this;
        }

        public Builder receivedDate(Instant receivedDate) {
            this.receivedDate = receivedDate;
            return this;
        }

        public Builder receivedItems(List<InboundReceiptLineItem> receivedItems) {
            this.receivedItems = receivedItems;
            return this;
        }

        public Builder comments(List<InboundReceiptComment> comments) {
            this.comments = comments;
            return this;
        }

        public Builder status(InboundReceiptStatus status) {
            this.status = status;
            return this;
        }

        public Builder asnRef(String asnRef) {
            this.asnRef = asnRef;
            return this;
        }

        public Builder customAttributes(Map<String, Object> customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        public InboundReceipt build() {
            return new InboundReceipt(id, receivedDate, receivedItems, comments, status, asnRef, customAttributes);
        }
    }
}
