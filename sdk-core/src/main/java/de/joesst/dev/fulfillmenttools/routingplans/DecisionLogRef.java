package de.joesst.dev.fulfillmenttools.routingplans;

/**
 * A lightweight reference to a decision log entry on a routing plan.
 *
 * <p>Maps to the {@code DecisionLogRef} schema in the fulfillmenttools OpenAPI spec. The full
 * decision log can be retrieved via the URL contained in this reference.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param url        URL path to the decision log, e.g.
 *                   {@code /api/routingplans/{routingPlanId}/decisionlogs/{routingRun}}.
 * @param routingRun The routing run number this decision log belongs to.
 */
public record DecisionLogRef(
        String url,
        Double routingRun
) {}
