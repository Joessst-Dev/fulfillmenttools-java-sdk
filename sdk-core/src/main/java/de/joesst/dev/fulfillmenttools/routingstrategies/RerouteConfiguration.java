package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Configuration for automatic time-triggered rerouting of unfulfilled orders.
 *
 * @param active                             whether this reroute configuration is active
 * @param rerouteAfterMinutes                minutes after which an automated reroute executes
 *                                           (default 1440, minimum 5)
 * @param leadTimeBeforeTimeTriggeredReroute minutes before a time-triggered reroute that a
 *                                           notification is sent; must be less than
 *                                           {@code rerouteAfterMinutes}
 * @param rerouteTargetTimeHours             default 48
 * @param rerouteStartedJobs                 optional configuration for rerouting started jobs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RerouteConfiguration(
        Boolean active,
        Double rerouteAfterMinutes,
        Double leadTimeBeforeTimeTriggeredReroute,
        Double rerouteTargetTimeHours,
        RerouteStartedJobsConfiguration rerouteStartedJobs
) {}
