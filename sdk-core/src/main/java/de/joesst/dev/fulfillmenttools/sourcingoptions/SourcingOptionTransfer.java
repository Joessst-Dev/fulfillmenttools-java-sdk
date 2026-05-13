package de.joesst.dev.fulfillmenttools.sourcingoptions;

import java.util.List;

/**
 * A transfer step between two nodes within a sourcing option, describing stock movement
 * from a supplying facility to a receiving node.
 *
 * <p>Maps to the {@code SourcingOptionTransfer} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id              Unique identifier of this transfer.
 * @param sourceNodeRef   Reference to the source node that provides the stock.
 * @param targetNodeRef   Reference to the target node that receives the stock.
 * @param lineItemRefs    References to the line items involved in this transfer.
 */
public record SourcingOptionTransfer(
        String id,
        String sourceNodeRef,
        String targetNodeRef,
        List<String> lineItemRefs
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String id;
        private String sourceNodeRef;
        private String targetNodeRef;
        private List<String> lineItemRefs;

        public Builder id(String id) { this.id = id; return this; }
        public Builder sourceNodeRef(String sourceNodeRef) { this.sourceNodeRef = sourceNodeRef; return this; }
        public Builder targetNodeRef(String targetNodeRef) { this.targetNodeRef = targetNodeRef; return this; }
        public Builder lineItemRefs(List<String> lineItemRefs) { this.lineItemRefs = lineItemRefs; return this; }

        public SourcingOptionTransfer build() {
            return new SourcingOptionTransfer(id, sourceNodeRef, targetNodeRef, lineItemRefs);
        }
    }
}
