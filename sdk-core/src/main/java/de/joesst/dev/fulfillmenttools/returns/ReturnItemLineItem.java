package de.joesst.dev.fulfillmenttools.returns;

import de.joesst.dev.fulfillmenttools.id.TenantArticleId;
import de.joesst.dev.fulfillmenttools.pickjobs.RecordableAttribute;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

import java.util.List;
import java.util.Map;

/**
 * A single returned line item within a return, capturing condition, reasons, and refund details.
 *
 * <p>Maps to the {@code ItemReturnLineItem} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                         Unique identifier. Required.
 * @param status                     Processing status of this returned line item. Required.
 * @param tenantArticleId            The tenant's article reference number. Required.
 * @param itemCondition              Optional free-text condition description (e.g. {@code Damaged}).
 * @param itemConditionComment       Optional comment on the item condition.
 * @param itemConditionLocalized     Localized item condition strings, keyed by locale.
 * @param itemReturnJobLineItemRefs  References to the return job line items. Required.
 * @param scannedCodes               Codes that were scanned for this line item.
 * @param reasons                    Structured reasons for this return.
 * @param refund                     Optional refund information for this line item.
 * @param recordableAttributes       Attributes whose values were recorded during the return.
 * @param customAttributes           Free-form custom attributes.
 */
public record ReturnItemLineItem(
        String id,
        String status,
        TenantArticleId tenantArticleId,
        String itemCondition,
        String itemConditionComment,
        Map<String, String> itemConditionLocalized,
        List<String> itemReturnJobLineItemRefs,
        List<String> scannedCodes,
        List<ReturnLineItemReason> reasons,
        ReturnedLineItemRefund refund,
        List<RecordableAttribute> recordableAttributes,
        CustomAttributes customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String status;
        private TenantArticleId tenantArticleId;
        private String itemCondition;
        private String itemConditionComment;
        private Map<String, String> itemConditionLocalized;
        private List<String> itemReturnJobLineItemRefs;
        private List<String> scannedCodes;
        private List<ReturnLineItemReason> reasons;
        private ReturnedLineItemRefund refund;
        private List<RecordableAttribute> recordableAttributes;
        private CustomAttributes customAttributes;

        private Builder() {}

        public Builder id(String id) { this.id = id; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder tenantArticleId(TenantArticleId tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }
        public Builder itemCondition(String itemCondition) { this.itemCondition = itemCondition; return this; }
        public Builder itemConditionComment(String itemConditionComment) { this.itemConditionComment = itemConditionComment; return this; }
        public Builder itemConditionLocalized(Map<String, String> itemConditionLocalized) { this.itemConditionLocalized = itemConditionLocalized; return this; }
        public Builder itemReturnJobLineItemRefs(List<String> itemReturnJobLineItemRefs) { this.itemReturnJobLineItemRefs = itemReturnJobLineItemRefs; return this; }
        public Builder scannedCodes(List<String> scannedCodes) { this.scannedCodes = scannedCodes; return this; }
        public Builder reasons(List<ReturnLineItemReason> reasons) { this.reasons = reasons; return this; }
        public Builder refund(ReturnedLineItemRefund refund) { this.refund = refund; return this; }
        public Builder recordableAttributes(List<RecordableAttribute> recordableAttributes) { this.recordableAttributes = recordableAttributes; return this; }
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        public ReturnItemLineItem build() {
            return new ReturnItemLineItem(id, status, tenantArticleId, itemCondition, itemConditionComment, itemConditionLocalized, itemReturnJobLineItemRefs, scannedCodes, reasons, refund, recordableAttributes, customAttributes);
        }
    }
}
