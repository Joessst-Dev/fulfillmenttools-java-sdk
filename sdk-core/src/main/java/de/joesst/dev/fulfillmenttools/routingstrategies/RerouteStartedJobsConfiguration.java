package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Configuration that controls whether rerouting applies to already-started pick jobs.
 *
 * @param active                       whether rerouting started jobs is enabled (required)
 * @param minimumStartedTimeInMinutes  minimum time (in minutes) a job must be in a started status
 *                                     before it is eligible for rerouting (minimum 60)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RerouteStartedJobsConfiguration(
        Boolean active,
        Double minimumStartedTimeInMinutes
) {}
