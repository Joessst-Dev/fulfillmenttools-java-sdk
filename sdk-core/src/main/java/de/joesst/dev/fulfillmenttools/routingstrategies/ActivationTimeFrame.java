package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

/**
 * Defines a time frame during which the associated entity (node, condition) is active.
 *
 * @param activeFrom  optional date before which the entity is not yet active
 * @param activeUntil optional date after which the entity becomes inactive
 * @param recurrence  whether the time frame recurs (required)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ActivationTimeFrame(
        Instant activeFrom,
        Instant activeUntil,
        ActivationTimeFrameRecurrenceType recurrence
) {}
