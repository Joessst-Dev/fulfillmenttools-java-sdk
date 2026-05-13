package de.joesst.dev.fulfillmenttools.sourcingoptions;

import java.util.List;

/**
 * A single sourcing option returned by the fulfillmenttools routing engine, describing
 * one feasible fulfillment plan for an order across one or more facility nodes.
 *
 * <p>Maps to the {@code SourcingOption} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                          Unique identifier of this sourcing option.
 * @param runId                       Identifier of the sourcing options run that produced this option.
 * @param totalPenalty                Aggregate penalty score; lower is better.
 * @param estimatedDeliveryDate       ISO-8601 date string for the estimated consumer delivery date.
 * @param estimatedPickupDate         ISO-8601 date string for the estimated pickup date.
 * @param validUntil                  ISO-8601 timestamp until which this option is valid.
 * @param nodes                       The facility nodes participating in this option.
 * @param transfers                   Stock transfers between nodes required by this option.
 * @param listingDetails              Listing-level stock and configuration details per article.
 * @param nonAssignedOrderLineItems   Line items that could not be assigned to any node.
 * @param ratingResults               Individual rating criterion scores contributing to totalPenalty.
 * @param totalCosts                  Aggregated cost breakdown for this option.
 */
public record SourcingOption(
        String id,
        String runId,
        Double totalPenalty,
        String estimatedDeliveryDate,
        String estimatedPickupDate,
        String validUntil,
        List<SourcingOptionNode> nodes,
        List<SourcingOptionTransfer> transfers,
        List<SourcingOptionListingDetails> listingDetails,
        List<SourcingOptionNonAssignedOrderLineItem> nonAssignedOrderLineItems,
        List<SourcingOptionRatingResult> ratingResults,
        SourcingOptionCosts totalCosts
) {}
