package de.joesst.dev.fulfillmenttools.returns;

import de.joesst.dev.fulfillmenttools.id.TenantArticleId;
import de.joesst.dev.fulfillmenttools.pickjobs.RecordableAttribute;

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
        Map<String, Object> customAttributes
) {}
