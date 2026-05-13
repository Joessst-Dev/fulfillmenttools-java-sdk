package de.joesst.dev.fulfillmenttools.listings;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * A condition context used to determine when an out-of-stock behaviour applies.
 *
 * <p>Maps to the {@code OutOfStockBehaviourContext} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param type     The context type; one of {@code FACILITY_GROUP} or {@code TAG_REFERENCE}.
 * @param values   The values used to evaluate the context condition (e.g. facility group IDs).
 * @param operator Optional operator for context evaluation; currently only {@code NOT} is
 *                 supported.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ListingOutOfStockBehaviourContext(
        String type,
        List<String> values,
        String operator
) {}
