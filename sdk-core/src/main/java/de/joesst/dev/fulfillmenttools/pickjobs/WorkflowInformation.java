package de.joesst.dev.fulfillmenttools.pickjobs;

/**
 * Workflow context for a pick job, indicating which workflow instance and node it belongs to.
 *
 * <p>Maps to the {@code WorkflowInformation} schema in the fulfillmenttools OpenAPI spec.
 * When {@code isAvailable} is {@code true}, the pick job is part of a workflow and
 * {@code instanceRef} and {@code nodeRef} identify its position within it. When
 * {@code isAvailable} is {@code false}, the pick job is not part of any workflow.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param isAvailable Whether this entity is part of a workflow.
 * @param instanceRef The id of the workflow instance, present when {@code isAvailable} is true.
 * @param nodeRef     The id of the node within the workflow instance.
 */
public record WorkflowInformation(
        Boolean isAvailable,
        String instanceRef,
        String nodeRef
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Boolean isAvailable;
        private String instanceRef;
        private String nodeRef;

        private Builder() {}

        public Builder isAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; return this; }
        public Builder instanceRef(String instanceRef) { this.instanceRef = instanceRef; return this; }
        public Builder nodeRef(String nodeRef) { this.nodeRef = nodeRef; return this; }

        public WorkflowInformation build() {
            return new WorkflowInformation(isAvailable, instanceRef, nodeRef);
        }
    }
}
