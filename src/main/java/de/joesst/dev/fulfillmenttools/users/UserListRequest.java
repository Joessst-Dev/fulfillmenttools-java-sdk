package de.joesst.dev.fulfillmenttools.users;

public final class UserListRequest {

    private final Integer size;
    private final String startAfterId;
    private final String orderBy;
    private final String facilityId;
    private final Boolean includeAdminUsers;

    private UserListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
        this.orderBy = builder.orderBy;
        this.facilityId = builder.facilityId;
        this.includeAdminUsers = builder.includeAdminUsers;
    }

    public Integer size() { return size; }
    public String startAfterId() { return startAfterId; }
    public String orderBy() { return orderBy; }
    public String facilityId() { return facilityId; }
    public Boolean includeAdminUsers() { return includeAdminUsers; }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        b.orderBy = this.orderBy;
        b.facilityId = this.facilityId;
        b.includeAdminUsers = this.includeAdminUsers;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Integer size;
        private String startAfterId;
        private String orderBy;
        private String facilityId;
        private Boolean includeAdminUsers;

        public Builder size(Integer size) { this.size = size; return this; }
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }
        public Builder orderBy(String orderBy) { this.orderBy = orderBy; return this; }
        public Builder facilityId(String facilityId) { this.facilityId = facilityId; return this; }
        public Builder includeAdminUsers(Boolean includeAdminUsers) { this.includeAdminUsers = includeAdminUsers; return this; }

        public UserListRequest build() { return new UserListRequest(this); }
    }
}
