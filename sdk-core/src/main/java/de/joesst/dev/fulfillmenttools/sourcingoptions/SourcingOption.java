package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * A single sourcing option returned by the fulfillmenttools routing engine, describing
 * one feasible fulfillment plan for an order across one or more facility nodes.
 *
 * <p>Maps to the {@code SourcingOption} schema in the fulfillmenttools OpenAPI spec.
 *
 * @param id                        unique identifier of this sourcing option
 * @param runId                     identifier of the sourcing options run
 * @param totalPenalty              aggregate penalty score; lower is better
 * @param estimatedDeliveryDate     estimated consumer delivery date (YYYY-MM-DD)
 * @param estimatedPickupDate       estimated pickup date (YYYY-MM-DD)
 * @param validUntil                ISO-8601 timestamp until which this option is valid
 * @param nodes                     facility nodes participating in this option
 * @param transfers                 stock transfers between nodes required by this option
 * @param listingDetails            listing-level details per article
 * @param nonAssignedOrderLineItems articles that could not be assigned to any node
 * @param ratingResults             individual rating criterion scores
 * @param totalCosts                aggregated cost breakdown
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
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
        List<HandledItem> nonAssignedOrderLineItems,
        List<SourcingOptionRatingResult> ratingResults,
        SourcingOptionCosts totalCosts
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String id;
        private String runId;
        private Double totalPenalty;
        private String estimatedDeliveryDate;
        private String estimatedPickupDate;
        private String validUntil;
        private List<SourcingOptionNode> nodes;
        private List<SourcingOptionTransfer> transfers;
        private List<SourcingOptionListingDetails> listingDetails;
        private List<HandledItem> nonAssignedOrderLineItems;
        private List<SourcingOptionRatingResult> ratingResults;
        private SourcingOptionCosts totalCosts;

        public Builder id(String id) { this.id = id; return this; }
        public Builder runId(String runId) { this.runId = runId; return this; }
        public Builder totalPenalty(Double totalPenalty) { this.totalPenalty = totalPenalty; return this; }
        public Builder estimatedDeliveryDate(String estimatedDeliveryDate) { this.estimatedDeliveryDate = estimatedDeliveryDate; return this; }
        public Builder estimatedPickupDate(String estimatedPickupDate) { this.estimatedPickupDate = estimatedPickupDate; return this; }
        public Builder validUntil(String validUntil) { this.validUntil = validUntil; return this; }
        public Builder nodes(List<SourcingOptionNode> nodes) { this.nodes = nodes; return this; }
        public Builder transfers(List<SourcingOptionTransfer> transfers) { this.transfers = transfers; return this; }
        public Builder listingDetails(List<SourcingOptionListingDetails> listingDetails) { this.listingDetails = listingDetails; return this; }
        public Builder nonAssignedOrderLineItems(List<HandledItem> nonAssignedOrderLineItems) { this.nonAssignedOrderLineItems = nonAssignedOrderLineItems; return this; }
        public Builder ratingResults(List<SourcingOptionRatingResult> ratingResults) { this.ratingResults = ratingResults; return this; }
        public Builder totalCosts(SourcingOptionCosts totalCosts) { this.totalCosts = totalCosts; return this; }

        public SourcingOption build() {
            return new SourcingOption(id, runId, totalPenalty, estimatedDeliveryDate, estimatedPickupDate, validUntil,
                    nodes, transfers, listingDetails, nonAssignedOrderLineItems, ratingResults, totalCosts);
        }
    }
}
