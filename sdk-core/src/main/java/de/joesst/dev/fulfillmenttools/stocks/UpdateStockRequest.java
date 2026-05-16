package de.joesst.dev.fulfillmenttools.stocks;

import de.joesst.dev.fulfillmenttools.id.StorageLocationId;
import de.joesst.dev.fulfillmenttools.id.TenantStockId;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocationTraitConfigEntry;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

import java.util.List;
import java.util.Objects;

/**
 * Request to update an existing stock entry via {@code PUT /api/stocks/{stockId}}.
 *
 * <p>Example:
 * <pre>{@code
 * UpdateStockRequest request = UpdateStockRequest.builder()
 *     .version(1)
 *     .value(50)
 *     .build();
 * }</pre>
 */
public final class UpdateStockRequest {

    private final Integer version;
    private final Integer value;
    private final StorageLocationId locationRef;
    private final TenantStockId tenantStockId;
    private final List<String> conditions;
    private final List<StorageLocationTraitConfigEntry> traitConfig;
    private final CustomAttributes customAttributes;

    private UpdateStockRequest(Builder builder) {
        this.version = Objects.requireNonNull(builder.version, "version must not be null");
        this.value = Objects.requireNonNull(builder.value, "value must not be null");
        this.locationRef = builder.locationRef;
        this.tenantStockId = builder.tenantStockId;
        this.conditions = builder.conditions;
        this.traitConfig = builder.traitConfig;
        this.customAttributes = builder.customAttributes;
    }

    /**
     * Returns the optimistic-locking version of the stock entry to update.
     *
     * @return the version
     */
    public Integer version() { return version; }

    /**
     * Returns the new stock quantity. Zero is a valid value to set stock to empty.
     *
     * @return the stock quantity
     */
    public Integer value() { return value; }

    /**
     * Returns the storage location reference. When {@code null}, the field is omitted from the
     * request body and the existing server value is preserved.
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
     * Returns the custom attributes for this stock.
     *
     * @return the custom attributes, or {@code null} if not set
     */
    public CustomAttributes customAttributes() { return customAttributes; }

    /**
     * Returns a new {@link Builder} for constructing an {@code UpdateStockRequest}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link UpdateStockRequest}.
     */
    public static final class Builder {

        private Integer version;
        private Integer value;
        private StorageLocationId locationRef;
        private TenantStockId tenantStockId;
        private List<String> conditions;
        private List<StorageLocationTraitConfigEntry> traitConfig;
        private CustomAttributes customAttributes;

        private Builder() {}

        /**
         * Sets the optimistic-locking version. Required.
         *
         * @param version the version
         * @return this builder
         */
        public Builder version(Integer version) { this.version = version; return this; }

        /**
         * Sets the new stock quantity. Required.
         *
         * @param value the quantity
         * @return this builder
         */
        public Builder value(Integer value) { this.value = value; return this; }

        /**
         * Sets the storage location reference. If not set, the existing location on the server is preserved.
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
         * Sets the custom attributes for this stock.
         *
         * @param customAttributes the custom attributes
         * @return this builder
         */
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        /**
         * Builds the {@link UpdateStockRequest}.
         *
         * @return a new {@code UpdateStockRequest}
         */
        public UpdateStockRequest build() { return new UpdateStockRequest(this); }
    }
}
