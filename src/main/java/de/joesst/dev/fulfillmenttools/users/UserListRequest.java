package de.joesst.dev.fulfillmenttools.users;

import de.joesst.dev.fulfillmenttools.id.FacilityId;

/**
 * Request parameters for listing users via {@link UserManagementClient#list(UserListRequest)}.
 *
 * <p>Use the fluent {@link Builder} to construct instances:
 * <pre>{@code
 * UserListRequest request = UserListRequest.builder()
 *     .size(50)
 *     .facilityId("fac-1")
 *     .build();
 * }</pre>
 *
 * <p>Thread-safety: immutable after construction; safe for concurrent use.
 */
public final class UserListRequest {

    private final Integer size;
    private final String startAfterId;
    private final String orderBy;
    private final FacilityId facilityId;
    private final Boolean includeAdminUsers;

    private UserListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
        this.orderBy = builder.orderBy;
        this.facilityId = builder.facilityId;
        this.includeAdminUsers = builder.includeAdminUsers;
    }

    /**
     * Returns the maximum number of users to return per page.
     *
     * @return the page size, or {@code null} if not set
     */
    public Integer size() { return size; }

    /**
     * Returns the cursor for pagination, indicating where to start fetching results.
     *
     * @return the start-after ID, or {@code null} if not set
     */
    public String startAfterId() { return startAfterId; }

    /**
     * Returns the field and direction to order results by (e.g. {@code "username asc"}).
     *
     * @return the order specification, or {@code null} if not set
     */
    public String orderBy() { return orderBy; }

    /**
     * Returns the facility ID to filter users by.
     *
     * @return the facility ID, or {@code null} if not set
     */
    public FacilityId facilityId() { return facilityId; }

    /**
     * Returns whether to include admin users in the results.
     *
     * @return {@code true} to include admin users, {@code false} to exclude them, or {@code null} if not set
     */
    public Boolean includeAdminUsers() { return includeAdminUsers; }

    /**
     * Returns a new builder initialized with the current values of this request.
     *
     * @return a new builder
     */
    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        b.orderBy = this.orderBy;
        b.facilityId = this.facilityId;
        b.includeAdminUsers = this.includeAdminUsers;
        return b;
    }

    /**
     * Returns a new {@link Builder} for constructing a {@code UserListRequest}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link UserListRequest}.
     */
    public static final class Builder {

        /**
         * Creates a new Builder.
         */
        public Builder() {}

        private Integer size;
        private String startAfterId;
        private String orderBy;
        private FacilityId facilityId;
        private Boolean includeAdminUsers;

        /**
         * Sets the maximum number of users to return per page.
         *
         * @param size the page size
         * @return this builder
         */
        public Builder size(Integer size) { this.size = size; return this; }

        /**
         * Sets the cursor for pagination, indicating where to start fetching results.
         *
         * @param startAfterId the start-after ID
         * @return this builder
         */
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }

        /**
         * Sets the field and direction to order results by (e.g. {@code "username asc"}).
         *
         * @param orderBy the order specification
         * @return this builder
         */
        public Builder orderBy(String orderBy) { this.orderBy = orderBy; return this; }

        /**
         * Sets the facility ID to filter users by.
         *
         * @param facilityId the facility ID
         * @return this builder
         */
        public Builder facilityId(FacilityId facilityId) { this.facilityId = facilityId; return this; }

        /**
         * Sets whether to include admin users in the results.
         *
         * @param includeAdminUsers {@code true} to include admin users, {@code false} to exclude them
         * @return this builder
         */
        public Builder includeAdminUsers(Boolean includeAdminUsers) { this.includeAdminUsers = includeAdminUsers; return this; }

        /**
         * Builds the {@link UserListRequest}.
         *
         * @return a new {@code UserListRequest}
         */
        public UserListRequest build() { return new UserListRequest(this); }
    }
}
