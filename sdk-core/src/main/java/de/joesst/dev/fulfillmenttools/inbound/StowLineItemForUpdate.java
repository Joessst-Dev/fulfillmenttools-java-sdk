package de.joesst.dev.fulfillmenttools.inbound;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

/**
 * A stow line item as provided in an {@link UpdateStowJobRequest}.
 *
 * <p>An {@code id} may be supplied to update an existing stow line item; omit it to create
 * a new one.
 *
 * <p>Maps to the {@code StowLineItemForUpdate} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id               Optional ID of an existing stow line item to update.
 * @param article          The article to be stowed.
 * @param stowTo           Instructions on where and how to stow the article.
 * @param takeFrom         Instructions on where and how to take the article from.
 * @param reasons          Optional reasons for the stow operation (at most 10).
 * @param customAttributes Free-form custom attributes.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StowLineItemForUpdate(
        String id,
        StowLineItemArticle article,
        List<StowLineItemStowToForUpdate> stowTo,
        StowLineItemTakeFrom takeFrom,
        List<ExternalStockChangeReasonInput> reasons,
        Map<String, Object> customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private StowLineItemArticle article;
        private List<StowLineItemStowToForUpdate> stowTo;
        private StowLineItemTakeFrom takeFrom;
        private List<ExternalStockChangeReasonInput> reasons;
        private Map<String, Object> customAttributes;

        private Builder() {}

        public Builder id(String id) { this.id = id; return this; }
        public Builder article(StowLineItemArticle article) { this.article = article; return this; }
        public Builder stowTo(List<StowLineItemStowToForUpdate> stowTo) { this.stowTo = stowTo; return this; }
        public Builder takeFrom(StowLineItemTakeFrom takeFrom) { this.takeFrom = takeFrom; return this; }
        public Builder reasons(List<ExternalStockChangeReasonInput> reasons) { this.reasons = reasons; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public StowLineItemForUpdate build() {
            return new StowLineItemForUpdate(id, article, stowTo, takeFrom, reasons, customAttributes);
        }
    }
}
