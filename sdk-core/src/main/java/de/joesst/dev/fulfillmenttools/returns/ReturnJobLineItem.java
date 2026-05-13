package de.joesst.dev.fulfillmenttools.returns;

import de.joesst.dev.fulfillmenttools.pickjobs.RecordableAttribute;

import java.util.List;
import java.util.Map;

/**
 * A line item on a return job, describing an article that is or is not returnable.
 *
 * <p>Maps to the {@code ItemReturnJobLineItem} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                   Unique identifier of this line item. Required.
 * @param status               Processing status of this line item.
 * @param globalLineItemId     Cross-entity identifier linking this line item to related
 *                             operational entities.
 * @param delivered            Quantity of this line item that was delivered. Required.
 * @param returned             Quantity of this line item that has been returned. Required.
 * @param returnable           Quantity of this line item that can still be returned. Required.
 * @param article              The article associated with this line item. Required.
 * @param scannableCodes       Codes that can be used for scanning this line item.
 * @param serviceJobRefs       References to service jobs that altered this line item.
 * @param recordableAttributes Attributes whose values can be recorded during the return process.
 * @param customAttributes     Free-form custom attributes.
 */
public record ReturnJobLineItem(
        String id,
        String status,
        String globalLineItemId,
        Double delivered,
        Double returned,
        Double returnable,
        ReturnJobLineItemArticle article,
        List<String> scannableCodes,
        List<String> serviceJobRefs,
        List<RecordableAttribute> recordableAttributes,
        Map<String, Object> customAttributes
) {}
