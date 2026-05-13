package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Configuration for routing a not-routable order to a pre-configured fallback facility after a
 * specified wait time.
 *
 * @param facilityRefs     list of facility IDs to use as fallback (required, 1 item)
 * @param active           whether fallback facility routing is active (required, default false)
 * @param fallbackAfterTime ISO 8601 duration after which routing to the fallback facility is
 *                          triggered (required, default {@code PT0H})
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoutingStrategyFallbackFacilityConfig(
        List<String> facilityRefs,
        Boolean active,
        String fallbackAfterTime
) {}
