package de.joesst.dev.fulfillmenttools.externalactions;

import java.util.List;

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

    public Integer size() { return size; }
    public String startAfterId() { return startAfterId; }
    public List<String> groups() { return groups; }
    public String processRef() { return processRef; }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        b.groups = this.groups;
        b.processRef = this.processRef;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Integer size;
        private String startAfterId;
        private List<String> groups;
        private String processRef;

        public Builder size(Integer size) { this.size = size; return this; }
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }
        public Builder groups(List<String> groups) { this.groups = groups; return this; }
        public Builder processRef(String processRef) { this.processRef = processRef; return this; }

        public ExternalActionListRequest build() { return new ExternalActionListRequest(this); }
    }
}
