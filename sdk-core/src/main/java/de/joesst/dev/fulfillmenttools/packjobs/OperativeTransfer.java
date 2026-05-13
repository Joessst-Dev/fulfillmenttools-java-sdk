package de.joesst.dev.fulfillmenttools.packjobs;

/**
 * A transfer associated with a pack job, identifying either a supplier or receiver party.
 *
 * <p>Maps to the {@code OperativeTransfer} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id   Unique identifier of the transfer.
 * @param type The role of this transfer in the pack job: {@code SUPPLIER} or {@code RECEIVER}.
 */
public record OperativeTransfer(
        String id,
        String type
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String id;
        private String type;

        public Builder id(String id) { this.id = id; return this; }
        public Builder type(String type) { this.type = type; return this; }

        public OperativeTransfer build() {
            return new OperativeTransfer(id, type);
        }
    }
}
