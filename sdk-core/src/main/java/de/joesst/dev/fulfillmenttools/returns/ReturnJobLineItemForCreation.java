package de.joesst.dev.fulfillmenttools.returns;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.pickjobs.RecordableAttribute;

import java.util.List;
import java.util.Map;

/**
 * A line item supplied when creating or updating a return job, describing an article
 * that is (or is not) returnable.
 *
 * <p>Maps to the {@code ItemReturnJobLineItemForCreation} schema in the fulfillmenttools
 * OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param article               The article being returned. Required.
 * @param delivered             Quantity of this line item that was delivered. Required; minimum 1.
 * @param globalLineItemId      Optional cross-entity line item identifier.
 * @param scannableCodes        Optional codes that can be used for scanning this line item.
 * @param serviceJobRefs        Optional references to service jobs that altered this line item.
 * @param recordableAttributes  Optional attributes whose values can be recorded during the
 *                              return process.
 * @param customAttributes      Free-form custom attributes.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ReturnJobLineItemForCreation(
        ReturnJobLineItemArticle article,
        Double delivered,
        String globalLineItemId,
        List<String> scannableCodes,
        List<String> serviceJobRefs,
        List<RecordableAttribute> recordableAttributes,
        Map<String, Object> customAttributes
) {}
