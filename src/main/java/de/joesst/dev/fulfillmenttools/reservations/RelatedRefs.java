package de.joesst.dev.fulfillmenttools.reservations;

import java.util.List;

/**
 * Related entity references for a reservation.
 */
public record RelatedRefs(
        List<String> orderRefs,
        List<String> pickJobRefs,
        List<String> processRefs,
        List<String> routingPlanRefs,
        List<String> transferRefs
) {}
