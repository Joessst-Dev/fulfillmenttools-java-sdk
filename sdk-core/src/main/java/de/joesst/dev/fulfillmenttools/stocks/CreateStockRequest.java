package de.joesst.dev.fulfillmenttools.stocks;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.StorageLocationId;
import de.joesst.dev.fulfillmenttools.id.TenantArticleId;
import de.joesst.dev.fulfillmenttools.id.TenantFacilityId;
import de.joesst.dev.fulfillmenttools.id.TenantStockId;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocationTraitConfigEntry;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Request to create a new stock entry via {@code POST /api/stocks}.
 *
 * <p>Example:
 * <pre>{@code
 * CreateStockRequest request = CreateStockRequest.builder()
 *     .tenantArticleId(new TenantArticleId("art-1"))
 *     .facilityRef(new FacilityId("fac-1"))
 *     .value(100)
 *     .build();
 * }</pre>
 */
public final class CreateStockRequest {

    private final TenantArticleId tenantArticleId;
    private final Integer value;
    private final FacilityId facilityRef;
    private final TenantFacilityId tenantFacilityId;
    private final StorageLocationId locationRef;
    private final TenantStockId tenantStockId;
    private final Instant availableUntil;
    private final Instant receiptDate;
    private final List<String> conditions;
    private final List<StorageLocationTraitConfigEntry> traitConfig;
    private final Map<String, String> properties;
    private final Map<String, Object> customAttributes;

    private CreateStockRequest(Builder builder) {
        this.tenantArticleId = Objects.requireNonNull(builder.tenantArticleId, "tenantArticleId must not be null");
        this.value = Objects.requireNonNull(builder.value, "value must not be null");
        if (builder.facilityRef == null && builder.tenantFacilityId == null) {
            throw new IllegalArgumentException("either facilityRef or tenantFacilityId must be set");
        }
        this.facilityRef = builder.facilityRef;
        this.tenantFacilityId = builder.tenantFacilityId;
        this.locationRef = builder.locationRef;
        this.tenantStockId = builder.tenantStockId;
        this.availableUntil = builder.availableUntil;
        this.receiptDate = builder.receiptDate;
        this.conditions = builder.conditions;
        this.traitConfig = builder.traitConfig;
        this.properties = builder.properties;
        this.customAttributes = builder.customAttributes;
    }

    /**
     * Returns the tenant article ID.
     *
     * @return the tenant article ID
     */
    public TenantArticleId tenantArticleId() { return tenantArticleId; }

    /**
     * Returns the initial stock quantity.
     *
     * @return the stock quantity
     */
    public Integer value() { return value; }

    /**
     * Returns the facility reference for the stock location.
     *
     * @return the facility reference, or {@code null} if not set
     */
    public FacilityId facilityRef() { return facilityRef; }

    /**
     * Returns the tenant facility ID for the stock location.
     *
     * @return the tenant facility ID, or {@code null} if not set
     */
    public TenantFacilityId tenantFacilityId() { return tenantFacilityId; }

    /**
     * Returns the storage location reference.
     *
     * @return the location reference, or {@code null} if not set
     */
    public StorageLocationId locationRef() { return locationRef; }

    /**
     * Returns the tenant-defined stock identifier.
     *
     * @return the tenant stock ID, or {@code null} if not set
     */
    public TenantStockId tenantStockId() { return tenantStockId; }

    /**
     * Returns the date until which this stock is available for routing.
     *
     * @return the available-until timestamp, or {@code null} if not set
     */
    public Instant availableUntil() { return availableUntil; }

    /**
     * Returns the date when this stock was received.
     *
     * @return the receipt date timestamp, or {@code null} if not set
     */
    public Instant receiptDate() { return receiptDate; }

    /**
     * Returns the condition tags for this stock (e.g. {@code DEFECTIVE}).
     *
     * @return the conditions list, or {@code null} if not set
     */
    public List<String> conditions() { return conditions; }

    /**
     * Returns the trait configuration entries for this stock.
     *
     * @return the trait config, or {@code null} if not set
     */
    public List<StorageLocationTraitConfigEntry> traitConfig() { return traitConfig; }

    /**
     * Returns the tracking properties for this stock (e.g. expiry dates).
     *
     * @return the properties map, or {@code null} if not set
     */
    public Map<String, String> properties() { return properties; }

    /**
     * Returns the custom attributes for this stock.
     *
     * @return the custom attributes, or {@code null} if not set
     */
    public Map<String, Object> customAttributes() { return customAttributes; }

    /**
     * Returns a new {@link Builder} for constructing a {@code CreateStockRequest}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link CreateStockRequest}.
     */
    public static final class Builder {

        private TenantArticleId tenantArticleId;
        private Integer value;
        private FacilityId facilityRef;
        private TenantFacilityId tenantFacilityId;
        private StorageLocationId locationRef;
        private TenantStockId tenantStockId;
        private Instant availableUntil;
        private Instant receiptDate;
        private List<String> conditions;
        private List<StorageLocationTraitConfigEntry> traitConfig;
        private Map<String, String> properties;
        private Map<String, Object> customAttributes;

        private Builder() {}

        /**
         * Sets the tenant article ID. Required.
         *
         * @param tenantArticleId the tenant article ID
         * @return this builder
         */
        public Builder tenantArticleId(TenantArticleId tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }

        /**
         * Sets the initial stock quantity. Required.
         *
         * @param value the quantity
         * @return this builder
         */
        public Builder value(Integer value) { this.value = value; return this; }

        /**
         * Sets the facility reference for the stock location.
         *
         * @param facilityRef the facility reference
         * @return this builder
         */
        public Builder facilityRef(FacilityId facilityRef) { this.facilityRef = facilityRef; return this; }

        /**
         * Sets the tenant facility ID for the stock location.
         *
         * @param tenantFacilityId the tenant facility ID
         * @return this builder
         */
        public Builder tenantFacilityId(TenantFacilityId tenantFacilityId) { this.tenantFacilityId = tenantFacilityId; return this; }

        /**
         * Sets the storage location reference.
         *
         * @param locationRef the location reference
         * @return this builder
         */
        public Builder locationRef(StorageLocationId locationRef) { this.locationRef = locationRef; return this; }

        /**
         * Sets the tenant-defined stock identifier.
         *
         * @param tenantStockId the tenant stock ID
         * @return this builder
         */
        public Builder tenantStockId(TenantStockId tenantStockId) { this.tenantStockId = tenantStockId; return this; }

        /**
         * Sets the date until which this stock is available for routing.
         *
         * @param availableUntil the available-until timestamp
         * @return this builder
         */
        public Builder availableUntil(Instant availableUntil) { this.availableUntil = availableUntil; return this; }

        /**
         * Sets the date when this stock was received.
         *
         * @param receiptDate the receipt date timestamp
         * @return this builder
         */
        public Builder receiptDate(Instant receiptDate) { this.receiptDate = receiptDate; return this; }

        /**
         * Sets the condition tags for this stock (e.g. {@code DEFECTIVE}).
         *
         * @param conditions the conditions list
         * @return this builder
         */
        public Builder conditions(List<String> conditions) { this.conditions = conditions; return this; }

        /**
         * Sets the trait configuration entries for this stock.
         *
         * @param traitConfig the trait config
         * @return this builder
         */
        public Builder traitConfig(List<StorageLocationTraitConfigEntry> traitConfig) { this.traitConfig = traitConfig; return this; }

        /**
         * Sets the tracking properties for this stock (e.g. expiry dates).
         *
         * @param properties the properties map
         * @return this builder
         */
        public Builder properties(Map<String, String> properties) { this.properties = properties; return this; }

        /**
         * Sets the custom attributes for this stock.
         *
         * @param customAttributes the custom attributes
         * @return this builder
         */
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        /**
         * Builds the {@link CreateStockRequest}.
         *
         * @return a new {@code CreateStockRequest}
         */
        public CreateStockRequest build() { return new CreateStockRequest(this); }
    }
}
