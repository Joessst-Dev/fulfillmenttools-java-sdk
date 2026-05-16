package de.joesst.dev.fulfillmenttools.inbound;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;
import de.joesst.dev.fulfillmenttools.model.Quantity;

import java.util.List;
import java.util.Map;

/**
 * A single line item within an inbound receipt, recording accepted and rejected quantities
 * for a specific article.
 *
 * @param id the platform-generated identifier for this line item
 * @param tenantArticleId the tenant's article identifier
 * @param acceptedQuantity the quantity of units accepted into stock
 * @param rejectedQuantity the quantity of units rejected (e.g. damaged)
 * @param comments comments attached to this line item
 * @param storageLocationRef reference to the storage location where units were stowed
 * @param stockProperties additional stock property key/value pairs
 * @param customAttributes tenant-defined key/value extension attributes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record InboundReceiptLineItem(
        String id,
        String tenantArticleId,
        Quantity acceptedQuantity,
        Quantity rejectedQuantity,
        List<InboundReceiptComment> comments,
        String storageLocationRef,
        Map<String, String> stockProperties,
        CustomAttributes customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String id;
        private String tenantArticleId;
        private Quantity acceptedQuantity;
        private Quantity rejectedQuantity;
        private List<InboundReceiptComment> comments;
        private String storageLocationRef;
        private Map<String, String> stockProperties;
        private CustomAttributes customAttributes;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder tenantArticleId(String tenantArticleId) {
            this.tenantArticleId = tenantArticleId;
            return this;
        }

        public Builder acceptedQuantity(Quantity acceptedQuantity) {
            this.acceptedQuantity = acceptedQuantity;
            return this;
        }

        public Builder rejectedQuantity(Quantity rejectedQuantity) {
            this.rejectedQuantity = rejectedQuantity;
            return this;
        }

        public Builder comments(List<InboundReceiptComment> comments) {
            this.comments = comments;
            return this;
        }

        public Builder storageLocationRef(String storageLocationRef) {
            this.storageLocationRef = storageLocationRef;
            return this;
        }

        public Builder stockProperties(Map<String, String> stockProperties) {
            this.stockProperties = stockProperties;
            return this;
        }

        public Builder customAttributes(CustomAttributes customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        public InboundReceiptLineItem build() {
            return new InboundReceiptLineItem(id, tenantArticleId, acceptedQuantity, rejectedQuantity,
                    comments, storageLocationRef, stockProperties, customAttributes);
        }
    }
}
