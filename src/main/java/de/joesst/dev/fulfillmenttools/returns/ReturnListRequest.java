package de.joesst.dev.fulfillmenttools.returns;

import java.util.List;

public final class ReturnListRequest {

    private final Integer size;
    private final String startAfterId;
    private final String facilityId;
    private final List<String> itemReturnJobStatus;
    private final List<String> itemReturnStatus;
    private final List<String> itemReturnJobScannableCodes;
    private final List<String> itemReturnScannableCodes;
    private final String searchTerm;
    private final Boolean anonymized;

    private ReturnListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
        this.facilityId = builder.facilityId;
        this.itemReturnJobStatus = builder.itemReturnJobStatus;
        this.itemReturnStatus = builder.itemReturnStatus;
        this.itemReturnJobScannableCodes = builder.itemReturnJobScannableCodes;
        this.itemReturnScannableCodes = builder.itemReturnScannableCodes;
        this.searchTerm = builder.searchTerm;
        this.anonymized = builder.anonymized;
    }

    public Integer size() { return size; }
    public String startAfterId() { return startAfterId; }
    public String facilityId() { return facilityId; }
    public List<String> itemReturnJobStatus() { return itemReturnJobStatus; }
    public List<String> itemReturnStatus() { return itemReturnStatus; }
    public List<String> itemReturnJobScannableCodes() { return itemReturnJobScannableCodes; }
    public List<String> itemReturnScannableCodes() { return itemReturnScannableCodes; }
    public String searchTerm() { return searchTerm; }
    public Boolean anonymized() { return anonymized; }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        b.facilityId = this.facilityId;
        b.itemReturnJobStatus = this.itemReturnJobStatus;
        b.itemReturnStatus = this.itemReturnStatus;
        b.itemReturnJobScannableCodes = this.itemReturnJobScannableCodes;
        b.itemReturnScannableCodes = this.itemReturnScannableCodes;
        b.searchTerm = this.searchTerm;
        b.anonymized = this.anonymized;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Integer size;
        private String startAfterId;
        private String facilityId;
        private List<String> itemReturnJobStatus;
        private List<String> itemReturnStatus;
        private List<String> itemReturnJobScannableCodes;
        private List<String> itemReturnScannableCodes;
        private String searchTerm;
        private Boolean anonymized;

        public Builder size(Integer size) { this.size = size; return this; }
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }
        public Builder facilityId(String facilityId) { this.facilityId = facilityId; return this; }
        public Builder itemReturnJobStatus(List<String> itemReturnJobStatus) { this.itemReturnJobStatus = itemReturnJobStatus; return this; }
        public Builder itemReturnStatus(List<String> itemReturnStatus) { this.itemReturnStatus = itemReturnStatus; return this; }
        public Builder itemReturnJobScannableCodes(List<String> itemReturnJobScannableCodes) { this.itemReturnJobScannableCodes = itemReturnJobScannableCodes; return this; }
        public Builder itemReturnScannableCodes(List<String> itemReturnScannableCodes) { this.itemReturnScannableCodes = itemReturnScannableCodes; return this; }
        public Builder searchTerm(String searchTerm) { this.searchTerm = searchTerm; return this; }
        public Builder anonymized(Boolean anonymized) { this.anonymized = anonymized; return this; }

        public ReturnListRequest build() { return new ReturnListRequest(this); }
    }
}
