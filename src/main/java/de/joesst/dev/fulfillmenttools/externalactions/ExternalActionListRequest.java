package de.joesst.dev.fulfillmenttools.externalactions;

import java.util.List;

/**
 * Request parameters for listing external actions.
 */
public final class ExternalActionListRequest {

    private final Integer size;
    private final String startAfterId;
    private final List<String> groups;
    private final String processRef;

    private ExternalActionListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
        this.groups = builder.groups;
        this.processRef = builder.processRef;
    }

    /**
     * Returns the maximum number of external actions to return.
     * @return the page size
     */
    public Integer size() { return size; }

    /**
     * Returns the ID after which to start the list.
     * @return the cursor for pagination
     */
    public String startAfterId() { return startAfterId; }

    /**
     * Returns the group names to filter by.
     * @return the list of group names
     */
    public List<String> groups() { return groups; }

    /**
     * Returns the process reference to filter by.
     * @return the process reference
     */
    public String processRef() { return processRef; }

    /**
     * Creates a new builder from the current state.
     * @return a new builder with current values
     */
    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        b.groups = this.groups;
        b.processRef = this.processRef;
        return b;
    }

    /**
     * Creates a new builder for ExternalActionListRequest.
     * @return a new builder instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for constructing ExternalActionListRequest instances.
     */
    public static final class Builder {

        /** Creates a new Builder. */
        public Builder() {}

        private Integer size;
        private String startAfterId;
        private List<String> groups;
        private String processRef;

        /**
         * Sets the maximum number of external actions to return.
         * @param size the page size
         * @return this builder
         */
        public Builder size(Integer size) { this.size = size; return this; }

        /**
         * Sets the ID after which to start the list.
         * @param startAfterId the cursor for pagination
         * @return this builder
         */
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }

        /**
         * Sets the group names to filter by.
         * @param groups the list of group names
         * @return this builder
         */
        public Builder groups(List<String> groups) { this.groups = groups; return this; }

        /**
         * Sets the process reference to filter by.
         * @param processRef the process reference
         * @return this builder
         */
        public Builder processRef(String processRef) { this.processRef = processRef; return this; }

        /**
         * Builds and returns an ExternalActionListRequest instance.
         * @return a new ExternalActionListRequest
         */
        public ExternalActionListRequest build() { return new ExternalActionListRequest(this); }
    }
}
