package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.ConnectionId;
import de.joesst.dev.fulfillmenttools.id.SourcingOptionNodeId;

import java.util.List;

/**
 * A transfer step between two nodes within a sourcing option, describing stock movement
 * from a supplying facility to a receiving node.
 *
 * <p>Maps to the {@code SourcingOptionTransfer} schema in the fulfillmenttools OpenAPI spec.
 *
 * @param sourceNodeRef          {@link SourcingOptionNodeId} of the source node providing the stock
 * @param targetNodeRef          {@link SourcingOptionNodeId} of the target node receiving the stock
 * @param lineItems              articles and quantities transferred
 * @param packagingInformation   packaging units used for this transfer
 * @param carrier                carrier assigned to perform the transfer
 * @param facilityConnectionRef  reference to the facility connection used
 * @param timeLine               timing milestones for this transfer
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SourcingOptionTransfer(
        SourcingOptionNodeId sourceNodeRef,
        SourcingOptionNodeId targetNodeRef,
        List<HandledItem> lineItems,
        List<SourcingOptionsTransferPackagingInformation> packagingInformation,
        TransferCarrier carrier,
        ConnectionId facilityConnectionRef,
        TransferTimeLine timeLine
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private SourcingOptionNodeId sourceNodeRef;
        private SourcingOptionNodeId targetNodeRef;
        private List<HandledItem> lineItems;
        private List<SourcingOptionsTransferPackagingInformation> packagingInformation;
        private TransferCarrier carrier;
        private ConnectionId facilityConnectionRef;
        private TransferTimeLine timeLine;

        public Builder sourceNodeRef(SourcingOptionNodeId sourceNodeRef) { this.sourceNodeRef = sourceNodeRef; return this; }
        public Builder targetNodeRef(SourcingOptionNodeId targetNodeRef) { this.targetNodeRef = targetNodeRef; return this; }
        public Builder lineItems(List<HandledItem> lineItems) { this.lineItems = lineItems; return this; }
        public Builder packagingInformation(List<SourcingOptionsTransferPackagingInformation> packagingInformation) { this.packagingInformation = packagingInformation; return this; }
        public Builder carrier(TransferCarrier carrier) { this.carrier = carrier; return this; }
        public Builder facilityConnectionRef(ConnectionId facilityConnectionRef) { this.facilityConnectionRef = facilityConnectionRef; return this; }
        public Builder timeLine(TransferTimeLine timeLine) { this.timeLine = timeLine; return this; }

        public SourcingOptionTransfer build() {
            return new SourcingOptionTransfer(sourceNodeRef, targetNodeRef, lineItems, packagingInformation, carrier, facilityConnectionRef, timeLine);
        }
    }
}
