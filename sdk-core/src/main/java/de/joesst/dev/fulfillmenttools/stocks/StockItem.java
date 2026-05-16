package de.joesst.dev.fulfillmenttools.stocks;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.StockId;
import de.joesst.dev.fulfillmenttools.id.StorageLocationId;
import de.joesst.dev.fulfillmenttools.id.TenantArticleId;
import de.joesst.dev.fulfillmenttools.id.TenantStockId;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocationTraitConfigEntry;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * A single stock entry representing the physical availability of an article at a facility.
 *
 * <p>Maps to the {@code Stock} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                   Platform-generated unique identifier for this stock record ({@link StockId}).
 * @param version              Optimistic-locking version counter.
 * @param created              Timestamp when this stock record was created.
 * @param lastModified         Timestamp when this stock record was last modified.
 * @param facilityRef          {@link FacilityId} reference to the owning facility.
 * @param tenantArticleId      The tenant's own article identifier.
 * @param tenantStockId        The tenant's own stock identifier ({@link TenantStockId}).
 * @param value                Total quantity of this stock (integer).
 * @param available            The available quantity (not reserved).
 * @param reserved             The quantity reserved for active orders.
 * @param facilityWideReserved Quantity reserved across the entire facility.
 * @param locationRef          Reference to the storage location holding this stock ({@link StorageLocationId}).
 * @param receiptDate          Date the stock was received at the facility.
 * @param availableUntil       Date until which this stock is considered available (e.g. expiry).
 * @param conditions           Condition tags applied to this stock (e.g. {@code NEW}, {@code USED}).
 * @param scannableCodes       Scannable code values (e.g. barcodes, serial numbers) for this stock.
 * @param scores               Scoring attributes used for stock-selection prioritisation.
 * @param traits               Operational trait identifiers active on this stock.
 * @param traitConfig          Per-trait enable/disable configuration entries for this stock.
 * @param serializedProperties Serialised representation of the stock properties (JSON string).
 * @param facility             Compact facility-identification reference embedded in this stock.
 * @param properties           Tracking properties for the stock (e.g. expiry dates).
 *                             Keys and values are both plain strings.
 * @param customAttributes     Free-form custom metadata; values may be of any JSON type.
 */
public record StockItem(
        StockId id,
        Integer version,
        Instant created,
        Instant lastModified,
        FacilityId facilityRef,
        TenantArticleId tenantArticleId,
        TenantStockId tenantStockId,
        Integer value,
        Double available,
        Double reserved,
        Double facilityWideReserved,
        StorageLocationId locationRef,
        Instant receiptDate,
        Instant availableUntil,
        List<String> conditions,
        List<String> scannableCodes,
        List<String> scores,
        List<String> traits,
        List<StorageLocationTraitConfigEntry> traitConfig,
        String serializedProperties,
        StockFacilityReferences facility,
        Map<String, String> properties,
        CustomAttributes customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private StockId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private FacilityId facilityRef;
        private TenantArticleId tenantArticleId;
        private TenantStockId tenantStockId;
        private Integer value;
        private Double available;
        private Double reserved;
        private Double facilityWideReserved;
        private StorageLocationId locationRef;
        private Instant receiptDate;
        private Instant availableUntil;
        private List<String> conditions;
        private List<String> scannableCodes;
        private List<String> scores;
        private List<String> traits;
        private List<StorageLocationTraitConfigEntry> traitConfig;
        private String serializedProperties;
        private StockFacilityReferences facility;
        private Map<String, String> properties;
        private CustomAttributes customAttributes;

        public Builder id(StockId id) {
            this.id = id;
            return this;
        }

        public Builder version(Integer version) {
            this.version = version;
            return this;
        }

        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        public Builder lastModified(Instant lastModified) {
            this.lastModified = lastModified;
            return this;
        }

        public Builder facilityRef(FacilityId facilityRef) {
            this.facilityRef = facilityRef;
            return this;
        }

        public Builder tenantArticleId(TenantArticleId tenantArticleId) {
            this.tenantArticleId = tenantArticleId;
            return this;
        }

        public Builder tenantStockId(TenantStockId tenantStockId) {
            this.tenantStockId = tenantStockId;
            return this;
        }

        public Builder value(Integer value) {
            this.value = value;
            return this;
        }

        public Builder available(Double available) {
            this.available = available;
            return this;
        }

        public Builder reserved(Double reserved) {
            this.reserved = reserved;
            return this;
        }

        public Builder facilityWideReserved(Double facilityWideReserved) {
            this.facilityWideReserved = facilityWideReserved;
            return this;
        }

        public Builder locationRef(StorageLocationId locationRef) {
            this.locationRef = locationRef;
            return this;
        }

        public Builder receiptDate(Instant receiptDate) {
            this.receiptDate = receiptDate;
            return this;
        }

        public Builder availableUntil(Instant availableUntil) {
            this.availableUntil = availableUntil;
            return this;
        }

        public Builder conditions(List<String> conditions) {
            this.conditions = conditions;
            return this;
        }

        public Builder scannableCodes(List<String> scannableCodes) {
            this.scannableCodes = scannableCodes;
            return this;
        }

        public Builder scores(List<String> scores) {
            this.scores = scores;
            return this;
        }

        public Builder traits(List<String> traits) {
            this.traits = traits;
            return this;
        }

        public Builder traitConfig(List<StorageLocationTraitConfigEntry> traitConfig) {
            this.traitConfig = traitConfig;
            return this;
        }

        public Builder serializedProperties(String serializedProperties) {
            this.serializedProperties = serializedProperties;
            return this;
        }

        public Builder facility(StockFacilityReferences facility) {
            this.facility = facility;
            return this;
        }

        public Builder properties(Map<String, String> properties) {
            this.properties = properties;
            return this;
        }

        public Builder customAttributes(CustomAttributes customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        public StockItem build() {
            return new StockItem(id, version, created, lastModified, facilityRef, tenantArticleId,
                    tenantStockId, value, available, reserved, facilityWideReserved, locationRef,
                    receiptDate, availableUntil, conditions, scannableCodes, scores, traits,
                    traitConfig, serializedProperties, facility, properties, customAttributes);
        }
    }
}
