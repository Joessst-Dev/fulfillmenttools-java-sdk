package de.joesst.dev.fulfillmenttools.reservations;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * A set of related entity references associated with a {@link Reservation}.
 *
 * <p>All fields are optional; absent arrays are represented as {@code null}.
 *
 * <p>Maps to {@code RelatedRefs} in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param orderRefs       references to related orders
 * @param pickJobRefs     references to related pick jobs
 * @param processRefs     references to related processes
 * @param routingPlanRefs references to related routing plans
 * @param transferRefs    references to related transfers
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RelatedRefs(
        List<String> orderRefs,
        List<String> pickJobRefs,
        List<String> processRefs,
        List<String> routingPlanRefs,
        List<String> transferRefs
) {}
