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
 * A create operation for use in {@link StocksClient#upsertStocks}.
 *
 * <p>Example:
 * <pre>{@code
 * VersionlessStockCreate.builder()
 *     .tenantArticleId(new TenantArticleId("art-1"))
 *     .facilityRef(new FacilityId("fac-1"))
 *     .value(100)
 *     .build()
 * }</pre>
 */
public final class VersionlessStockCreate implements VersionlessStockOperation {

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

    private VersionlessStockCreate(Builder builder) {
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

    public TenantArticleId tenantArticleId() { return tenantArticleId; }
    public Integer value() { return value; }
    public FacilityId facilityRef() { return facilityRef; }
    public TenantFacilityId tenantFacilityId() { return tenantFacilityId; }
    public StorageLocationId locationRef() { return locationRef; }
    public TenantStockId tenantStockId() { return tenantStockId; }
    public Instant availableUntil() { return availableUntil; }
    public Instant receiptDate() { return receiptDate; }
    public List<String> conditions() { return conditions; }
    public List<StorageLocationTraitConfigEntry> traitConfig() { return traitConfig; }
    public Map<String, String> properties() { return properties; }
    public Map<String, Object> customAttributes() { return customAttributes; }

    public static Builder builder() { return new Builder(); }

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

        public Builder tenantArticleId(TenantArticleId tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }
        public Builder value(Integer value) { this.value = value; return this; }
        public Builder facilityRef(FacilityId facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder tenantFacilityId(TenantFacilityId tenantFacilityId) { this.tenantFacilityId = tenantFacilityId; return this; }
        public Builder locationRef(StorageLocationId locationRef) { this.locationRef = locationRef; return this; }
        public Builder tenantStockId(TenantStockId tenantStockId) { this.tenantStockId = tenantStockId; return this; }
        public Builder availableUntil(Instant availableUntil) { this.availableUntil = availableUntil; return this; }
        public Builder receiptDate(Instant receiptDate) { this.receiptDate = receiptDate; return this; }
        public Builder conditions(List<String> conditions) { this.conditions = conditions; return this; }
        public Builder traitConfig(List<StorageLocationTraitConfigEntry> traitConfig) { this.traitConfig = traitConfig; return this; }
        public Builder properties(Map<String, String> properties) { this.properties = properties; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public VersionlessStockCreate build() { return new VersionlessStockCreate(this); }
    }
}
