package de.joesst.dev.fulfillmenttools.stocks;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocationTraitConfigEntry;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * A single stock entry representing the physical availability of an article at a facility.
 *
 * <p>Maps to the {@code Stock} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                   Platform-generated unique identifier for this stock record.
 * @param version              Optimistic-locking version counter.
 * @param created              Timestamp when this stock record was created.
 * @param lastModified         Timestamp when this stock record was last modified.
 * @param facilityRef          {@link FacilityId} reference to the owning facility.
 * @param tenantArticleId      The tenant's own article identifier.
 * @param tenantStockId        The tenant's own stock identifier.
 * @param value                Total quantity of this stock (integer).
 * @param available            The available quantity (not reserved).
 * @param reserved             The quantity reserved for active orders.
 * @param facilityWideReserved Quantity reserved across the entire facility.
 * @param locationRef          Reference to the storage location holding this stock.
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
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        FacilityId facilityRef,
        String tenantArticleId,
        String tenantStockId,
        Integer value,
        Double available,
        Double reserved,
        Double facilityWideReserved,
        String locationRef,
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
        Map<String, Object> customAttributes
) {}
