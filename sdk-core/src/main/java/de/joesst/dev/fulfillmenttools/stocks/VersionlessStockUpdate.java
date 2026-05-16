package de.joesst.dev.fulfillmenttools.stocks;

import de.joesst.dev.fulfillmenttools.id.StockId;
import de.joesst.dev.fulfillmenttools.id.StorageLocationId;
import de.joesst.dev.fulfillmenttools.id.TenantStockId;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocationTraitConfigEntry;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * An update operation for use in {@link StocksClient#upsertStocks}.
 * Does not require a version — the server applies the update unconditionally.
 *
 * <p>Example:
 * <pre>{@code
 * VersionlessStockUpdate.builder()
 *     .stockId(new StockId("s-abc123"))
 *     .value(50)
 *     .build()
 * }</pre>
 */
public final class VersionlessStockUpdate implements VersionlessStockOperation {

    private final StockId stockId;
    private final Integer value;
    private final StorageLocationId locationRef;
    private final TenantStockId tenantStockId;
    private final List<String> conditions;
    private final List<StorageLocationTraitConfigEntry> traitConfig;
    private final CustomAttributes customAttributes;

    private VersionlessStockUpdate(Builder builder) {
        this.stockId = Objects.requireNonNull(builder.stockId, "stockId must not be null");
        this.value = Objects.requireNonNull(builder.value, "value must not be null");
        this.locationRef = builder.locationRef;
        this.tenantStockId = builder.tenantStockId;
        this.conditions = builder.conditions;
        this.traitConfig = builder.traitConfig;
        this.customAttributes = builder.customAttributes;
    }

    /** Returns the ID of the stock entry to update. */
    public StockId stockId() { return stockId; }

    /** Returns the new stock quantity. Zero is a valid value to set stock to empty. */
    public Integer value() { return value; }

    /** Returns the storage location; if not set, the server value is preserved. */
    public StorageLocationId locationRef() { return locationRef; }

    /** Returns the tenant-defined stock identifier, or {@code null} if not set. */
    public TenantStockId tenantStockId() { return tenantStockId; }

    /** Returns the condition tags (e.g. {@code DEFECTIVE}), or {@code null} if not set. */
    public List<String> conditions() { return conditions; }

    /** Returns the trait configuration entries, or {@code null} if not set. */
    public List<StorageLocationTraitConfigEntry> traitConfig() { return traitConfig; }

    /** Returns the custom attributes, or {@code null} if not set. */
    public CustomAttributes customAttributes() { return customAttributes; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private StockId stockId;
        private Integer value;
        private StorageLocationId locationRef;
        private TenantStockId tenantStockId;
        private List<String> conditions;
        private List<StorageLocationTraitConfigEntry> traitConfig;
        private CustomAttributes customAttributes;

        private Builder() {}

        public Builder stockId(StockId stockId) { this.stockId = stockId; return this; }
        public Builder value(Integer value) { this.value = value; return this; }
        public Builder locationRef(StorageLocationId locationRef) { this.locationRef = locationRef; return this; }
        public Builder tenantStockId(TenantStockId tenantStockId) { this.tenantStockId = tenantStockId; return this; }
        public Builder conditions(List<String> conditions) { this.conditions = conditions; return this; }
        public Builder traitConfig(List<StorageLocationTraitConfigEntry> traitConfig) { this.traitConfig = traitConfig; return this; }
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        public VersionlessStockUpdate build() { return new VersionlessStockUpdate(this); }
    }
}
