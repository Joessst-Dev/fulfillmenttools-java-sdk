# Stocks Client

The Stocks client provides access to inventory and stock management across your fulfillment network. Query stock levels for articles at different facilities and storage locations, with support for filtering by facility, article, and location.

## Quick Start

List the first page of stock entries:

```java
import de.joesst.dev.fulfillmenttools.stocks.StockListRequest;
import de.joesst.dev.fulfillmenttools.stocks.StockItem;
import de.joesst.dev.fulfillmenttools.model.Page;

Page<StockItem> page = client.stocks().list(
    StockListRequest.builder()
        .size(50)
        .build()
);

for (StockItem item : page.items()) {
    System.out.println("Stock ID: " + item.id());
    System.out.println("Facility: " + item.facilityRef());
    System.out.println("Available: " + item.available());
}
```

Iterate through all stocks across all pages:

```java
Iterable<StockItem> allStocks = client.stocks().listAll(
    StockListRequest.builder()
        .size(100)
        .build()
);

for (StockItem item : allStocks) {
    System.out.println(item.tenantArticleId() + " @ " + item.facilityRef() 
        + ": " + item.available() + " available");
}
```

## Core Concepts

**StockItem**: An immutable record representing a single inventory entry. Key fields:
- `id()` — Platform-generated unique stock identifier
- `facilityRef()` — The facility where this stock is located
- `tenantArticleId()` — Your organization's article identifier
- `available()` — Quantity available for allocation (Double)
- `reserved()` — Quantity reserved for active orders
- `locationRef()` — Optional storage location within the facility
- `value()` — Total quantity (Integer)
- `conditions()` — List of condition tags (e.g., "NEW", "USED")

**StockListRequest**: Builder-based immutable request object for filtering and pagination. Use `StockListRequest.builder()` to construct.

## Filtering

Filter stocks by facility, article, or location:

### By Facility

Query stocks in a specific facility:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;

FacilityId facilityId = FacilityId.builder().value("fac-1").build();

Page<StockItem> facilityStocks = client.stocks().list(
    StockListRequest.builder()
        .facilityRef(facilityId)
        .size(50)
        .build()
);
```

### By Article

Filter stocks for specific articles:

```java
import de.joesst.dev.fulfillmenttools.id.TenantArticleId;
import java.util.List;

List<TenantArticleId> articleIds = List.of(
    TenantArticleId.builder().value("art-001").build(),
    TenantArticleId.builder().value("art-002").build()
);

Page<StockItem> articleStocks = client.stocks().list(
    StockListRequest.builder()
        .tenantArticleId(articleIds)
        .size(50)
        .build()
);
```

### By Storage Location

Query stocks at a specific location within a facility:

```java
import de.joesst.dev.fulfillmenttools.id.StorageLocationId;

List<StorageLocationId> locations = List.of(
    StorageLocationId.builder().value("loc-bin-01").build()
);

Page<StockItem> locationStocks = client.stocks().list(
    StockListRequest.builder()
        .locationRef(locations)
        .size(50)
        .build()
);
```

### Combined Filters

Filters are combined with AND logic:

```java
Page<StockItem> filtered = client.stocks().list(
    StockListRequest.builder()
        .facilityRef(FacilityId.builder().value("fac-1").build())
        .tenantArticleId(List.of(
            TenantArticleId.builder().value("art-001").build()
        ))
        .size(50)
        .build()
);
```

## Pagination

The `list()` method returns a single page of results. Use `startAfterId` to fetch the next page:

```java
Page<StockItem> page1 = client.stocks().list(
    StockListRequest.builder().size(50).build()
);

String cursor = page1.nextCursor();
if (cursor != null) {
    Page<StockItem> page2 = client.stocks().list(
        StockListRequest.builder()
            .startAfterId(cursor)
            .size(50)
            .build()
    );
}

// Or use the convenience method:
if (page1.hasMore()) {
    Page<StockItem> page2 = client.stocks().list(
        StockListRequest.builder()
            .startAfterId(page1.nextCursor())
            .size(50)
            .build()
    );
}
```

To automatically paginate through all results, use `listAll()`:

```java
Iterable<StockItem> allResults = client.stocks().listAll(
    StockListRequest.builder()
        .facilityRef(FacilityId.builder().value("fac-1").build())
        .build()
);

for (StockItem item : allResults) {
    System.out.println(item);
}
```

## Asynchronous Requests

For non-blocking list operations, use `listAsync()`:

```java
import java.util.concurrent.CompletableFuture;

CompletableFuture<Page<StockItem>> future = client.stocks().listAsync(
    StockListRequest.builder()
        .facilityRef(FacilityId.builder().value("fac-1").build())
        .size(50)
        .build()
);

future
    .thenAccept(page -> {
        for (StockItem item : page.items()) {
            System.out.println("Stock: " + item.id() + ", Available: " + item.available());
        }
    })
    .exceptionally(ex -> {
        System.err.println("Failed to fetch stocks: " + ex.getMessage());
        return null;
    });
```

## Error Handling

The stocks client throws `FulfillmenttoolsException` for HTTP and transport errors. Status code 404 is wrapped in the specific `NotFoundException` subclass.

### Handling Failures

```java
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

try {
    Page<StockItem> page = client.stocks().list(
        StockListRequest.builder()
            .facilityRef(FacilityId.builder().value("fac-1").build())
            .size(50)
            .build()
    );
} catch (FulfillmenttoolsException ex) {
    // Fulfillmenttools API error
    System.out.println("API Error (" + ex.statusCode() + "): " + ex.getMessage());
    for (var error : ex.errors()) {
        System.out.println("  - " + error.summary());
    }
} catch (Exception ex) {
    // Transport or authentication failure
    System.out.println("Unexpected error: " + ex.getMessage());
}
```

### Async Error Handling

```java
CompletableFuture<Page<StockItem>> future = client.stocks().listAsync(
    StockListRequest.builder().size(50).build()
);

future.handle((page, ex) -> {
    if (ex != null) {
        Throwable cause = ex.getCause() != null ? ex.getCause() : ex;
        if (cause instanceof FulfillmenttoolsException fte) {
            System.out.println("API error: " + fte.statusCode());
        } else {
            System.out.println("Unexpected error: " + cause.getMessage());
        }
        return null;
    }
    System.out.println("Found " + page.items().size() + " stocks");
    return page;
});
```

## API Reference

### list(StockListRequest)

Returns one page of stock entries matching the request filters and pagination settings.

**Parameters:**
- `request: StockListRequest` — List request with optional filters (`facilityRef`, `tenantArticleId`, `locationRef`) and pagination (`size`, `startAfterId`)

**Returns:** `Page<StockItem>` — A page containing matching stock entries and a cursor for fetching the next page

**Throws:** `FulfillmenttoolsException` (or subclass) if the request fails

**Example:**

```java
Page<StockItem> page = client.stocks().list(
    StockListRequest.builder()
        .facilityRef(FacilityId.builder().value("fac-1").build())
        .size(50)
        .build()
);

for (StockItem item : page.items()) {
    System.out.println(item.id() + ": " + item.available() + " available");
}
```

### listAsync(StockListRequest)

Asynchronously returns one page of stock entries matching the request filters and pagination settings.

**Parameters:**
- `request: StockListRequest` — List request with optional filters and pagination

**Returns:** `CompletableFuture<Page<StockItem>>` — A future that resolves to a page of matching stock entries

**Throws:** Exception propagated via `CompletableFuture`; call `.exceptionally()` or `.handle()` to catch

**Example:**

```java
client.stocks().listAsync(
    StockListRequest.builder()
        .tenantArticleId(List.of(
            TenantArticleId.builder().value("art-001").build()
        ))
        .size(50)
        .build()
)
    .thenAccept(page -> {
        System.out.println("Retrieved " + page.items().size() + " stocks");
    })
    .exceptionally(ex -> {
        System.err.println("Failed: " + ex.getMessage());
        return null;
    });
```

### listAll(StockListRequest)

Automatically iterates through all pages and returns an `Iterable` over all matching stock entries.

**Parameters:**
- `request: StockListRequest` — List request with optional filters (pagination settings are managed internally)

**Returns:** `Iterable<StockItem>` — An iterable that transparently fetches pages as needed

**Throws:** `FulfillmenttoolsException` (or subclass) if any page fetch fails

**Example:**

```java
Iterable<StockItem> allStocks = client.stocks().listAll(
    StockListRequest.builder()
        .facilityRef(FacilityId.builder().value("fac-1").build())
        .build()
);

for (StockItem item : allStocks) {
    System.out.println(item.tenantArticleId() + ": " + item.value() + " units");
}
```

## StockItem Fields

The `StockItem` record exposes the following accessor methods (via record components):

| Accessor | Type | Description |
|----------|------|-------------|
| `id()` | `StockId` | Platform-generated unique stock identifier |
| `facilityRef()` | `FacilityId` | Reference to the owning facility |
| `tenantArticleId()` | `TenantArticleId` | Your organization's article identifier |
| `tenantStockId()` | `TenantStockId` | Your organization's stock identifier |
| `value()` | `Integer` | Total quantity of this stock |
| `available()` | `Double` | Quantity available for allocation |
| `reserved()` | `Double` | Quantity reserved for active orders |
| `facilityWideReserved()` | `Double` | Quantity reserved across the entire facility |
| `locationRef()` | `StorageLocationId` | Reference to the storage location (nullable) |
| `version()` | `Integer` | Optimistic-locking version counter |
| `created()` | `Instant` | Timestamp when this stock record was created |
| `lastModified()` | `Instant` | Timestamp when this stock record was last modified |
| `receiptDate()` | `Instant` | Date the stock was received at the facility (nullable) |
| `availableUntil()` | `Instant` | Date until which this stock is available (e.g., expiry) (nullable) |
| `conditions()` | `List<String>` | Condition tags (e.g., "NEW", "USED") |
| `scannableCodes()` | `List<String>` | Barcode and serial number values |
| `scores()` | `List<String>` | Scoring attributes for prioritization |
| `traits()` | `List<String>` | Operational trait identifiers |
| `traitConfig()` | `List<StorageLocationTraitConfigEntry>` | Per-trait configuration entries |
| `properties()` | `Map<String, String>` | Tracking properties (key-value pairs) |
| `customAttributes()` | `Map<String, Object>` | Free-form custom metadata |
| `serializedProperties()` | `String` | Serialised representation of the stock properties (JSON string) |
| `facility()` | `StockFacilityReferences` | Embedded facility reference data (nullable) |

## StockListRequest Parameters

The `StockListRequest.builder()` accepts the following options:

| Method | Type | Description |
|--------|------|-------------|
| `size(Integer)` | Optional | Maximum number of entries per page. Defaults to server-defined limit if not set |
| `startAfterId(String)` | Optional | Cursor for pagination; retrieves the next page after this ID |
| `facilityRef(FacilityId)` | Optional | Filter by facility (exact match) |
| `tenantFacilityId(TenantFacilityId)` | Optional | Filter by tenant's facility ID |
| `tenantArticleId(List<TenantArticleId>)` | Optional | Filter by one or more article IDs (OR logic) |
| `locationRef(List<StorageLocationId>)` | Optional | Filter by one or more storage locations (OR logic) |

Filters are applied with AND logic (all specified filters must match). Within a multi-value filter (like `tenantArticleId`), OR logic applies.
