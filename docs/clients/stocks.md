# Stocks Client

The Stocks client provides access to inventory and stock management across your fulfillment network. Create new stock entries, update existing ones, perform bulk upserts without version constraints, and query stock levels for articles at different facilities and storage locations.

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
    System.out.println("Stock ID: " + item.id().value());
    System.out.println("Facility: " + item.facilityRef().value());
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
    System.out.println(item.tenantArticleId().value() + " @ " + item.facilityRef().value()
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

## Creating Stock

Create a new stock entry with the required `tenantArticleId` and `value`. Either `facilityRef` or `tenantFacilityId` must also be provided to identify the facility:

```java
import de.joesst.dev.fulfillmenttools.stocks.CreateStockRequest;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.TenantArticleId;

StockItem stock = client.stocks().create(
    CreateStockRequest.builder()
        .tenantArticleId(new TenantArticleId("art-001"))
        .facilityRef(new FacilityId("fac-1"))
        .value(100)
        .build()
);

System.out.println("Created stock: " + stock.id().value());
```

With optional fields:

```java
import de.joesst.dev.fulfillmenttools.id.StorageLocationId;
import java.util.List;

StockItem stock = client.stocks().create(
    CreateStockRequest.builder()
        .tenantArticleId(new TenantArticleId("art-001"))
        .facilityRef(new FacilityId("fac-1"))
        .value(100)
        .locationRef(new StorageLocationId("loc-bin-01"))
        .conditions(List.of("DEFECTIVE"))
        .build()
);
```

Asynchronously:

```java
client.stocks().createAsync(
    CreateStockRequest.builder()
        .tenantArticleId(new TenantArticleId("art-001"))
        .facilityRef(new FacilityId("fac-1"))
        .value(50)
        .build()
).thenAccept(stock -> System.out.println("Created: " + stock.id().value()));
```

## Updating Stock

Update an existing stock entry by ID, providing the current version for optimistic locking:

```java
import de.joesst.dev.fulfillmenttools.stocks.UpdateStockRequest;
import de.joesst.dev.fulfillmenttools.id.StockId;

StockItem updated = client.stocks().update(
    new StockId("s-abc123"),
    UpdateStockRequest.builder()
        .version(1)
        .value(75)
        .build()
);

System.out.println("Updated to version: " + updated.version());
```

With optional fields:

```java
StockItem updated = client.stocks().update(
    new StockId("s-abc123"),
    UpdateStockRequest.builder()
        .version(1)
        .value(75)
        .locationRef(new StorageLocationId("loc-bin-02"))
        .conditions(List.of("DEFECTIVE"))
        .build()
);
```

Asynchronously:

```java
client.stocks().updateAsync(
    new StockId("s-abc123"),
    UpdateStockRequest.builder()
        .version(2)
        .value(60)
        .build()
).thenAccept(stock -> System.out.println("Updated version: " + stock.version()));
```

## Bulk Upsert

For scenarios where you need to create or update multiple stocks without tracking versions, use the bulk upsert endpoint. The server applies all operations unconditionally — no optimistic locking, no version required.

- Mix create and update operations in a single call
- Results are returned in the same order as the input operations
- Each call accepts 1–25 operations

Create and update stocks in a single batch call:

```java
import de.joesst.dev.fulfillmenttools.stocks.VersionlessStockCreate;
import de.joesst.dev.fulfillmenttools.stocks.VersionlessStockUpdate;
import de.joesst.dev.fulfillmenttools.stocks.StockUpsertResult;
import de.joesst.dev.fulfillmenttools.id.StockId;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.TenantArticleId;
import java.util.List;

List<StockUpsertResult> results = client.stocks().upsertStocks(
    List.of(
        VersionlessStockCreate.builder()
            .tenantArticleId(new TenantArticleId("art-001"))
            .facilityRef(new FacilityId("fac-1"))
            .value(100)
            .build(),
        VersionlessStockUpdate.builder()
            .stockId(new StockId("s-existing-001"))
            .value(150)
            .build()
    )
);

for (StockUpsertResult result : results) {
    System.out.println("Stock: " + result.stock().id().value() 
        + ", Status: " + result.status());
}
```

Asynchronously:

```java
import java.util.concurrent.CompletableFuture;

client.stocks().upsertStocksAsync(
    List.of(
        VersionlessStockCreate.builder()
            .tenantArticleId(new TenantArticleId("art-002"))
            .facilityRef(new FacilityId("fac-1"))
            .value(50)
            .build(),
        VersionlessStockUpdate.builder()
            .stockId(new StockId("s-existing-002"))
            .value(75)
            .build()
    )
)
    .thenAccept(results -> {
        for (StockUpsertResult result : results) {
            System.out.println(result.status() + ": " + result.stock().id().value());
        }
    })
    .exceptionally(ex -> {
        System.err.println("Upsert failed: " + ex.getMessage());
        return null;
    });
```

## Searching Stock

The search endpoint provides a more expressive alternative to filtering, with typed builders for complex queries, numeric comparisons, logical combinators (AND/OR), and multi-value filters. Use search when you need to find stocks matching specific conditions, ranges, or complex logical expressions. The `query` parameter is optional — omitting it returns all stocks subject to pagination.

Search for stocks by stock value:

```java
import de.joesst.dev.fulfillmenttools.stocks.StockSearchRequest;
import de.joesst.dev.fulfillmenttools.stocks.StockSearchQuery;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.id.FacilityId;

StockSearchQuery query = StockSearchQuery.builder()
    .facilityRefEq(new FacilityId("fac-1"))
    .valueGte(50)
    .build();

Page<StockItem> page = client.stocks().search(
    StockSearchRequest.builder()
        .query(query)
        .size(50)
        .build()
);

for (StockItem item : page.items()) {
    System.out.println("Stock: " + item.id().value() 
        + ", Value: " + item.value());
}
```

Search with multiple values (OR logic within filter):

```java
import de.joesst.dev.fulfillmenttools.id.TenantArticleId;

StockSearchQuery query = StockSearchQuery.builder()
    .tenantArticleIdIn(
        new TenantArticleId("art-001"),
        new TenantArticleId("art-002"),
        new TenantArticleId("art-003")
    )
    .build();

Page<StockItem> page = client.stocks().search(
    StockSearchRequest.builder()
        .query(query)
        .size(100)
        .build()
);
```

Search with logical combinators:

```java
// Find stocks with value > 10 OR value < 5
StockSearchQuery lowOrHigh = StockSearchQuery.builder()
    .or(
        StockSearchQuery.builder().valueGt(10).build(),
        StockSearchQuery.builder().valueLt(5).build()
    )
    .build();

Page<StockItem> page = client.stocks().search(
    StockSearchRequest.builder()
        .query(lowOrHigh)
        .size(50)
        .build()
);
```

Paginate through all search results:

```java
Iterable<StockItem> allResults = client.stocks().searchAll(
    StockSearchRequest.builder()
        .query(StockSearchQuery.builder()
            .facilityRefEq(new FacilityId("fac-1"))
            .valueGte(10)
            .build()
        )
        .build()
);

for (StockItem item : allResults) {
    System.out.println(item.tenantArticleId().value() 
        + " @ " + item.facilityRef().value() 
        + ": " + item.value() + " units");
}
```

Asynchronously search for stocks:

```java
import java.util.concurrent.CompletableFuture;

CompletableFuture<Page<StockItem>> future = client.stocks().searchAsync(
    StockSearchRequest.builder()
        .query(StockSearchQuery.builder()
            .conditionsContainsIn(StockCondition.DEFECTIVE)
            .build()
        )
        .size(50)
        .build()
);

future
    .thenAccept(page -> {
        System.out.println("Found " + page.items().size() + " defective stocks");
    })
    .exceptionally(ex -> {
        System.err.println("Search failed: " + ex.getMessage());
        return null;
    });
```

## Filtering

Filter stocks by facility, article, or location:

### By Facility

Query stocks in a specific facility:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;

FacilityId facilityId = new FacilityId("fac-1");

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
    new TenantArticleId("art-001"),
    new TenantArticleId("art-002")
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
    new StorageLocationId("loc-bin-01")
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
        .facilityRef(new FacilityId("fac-1"))
        .tenantArticleId(List.of(
            new TenantArticleId("art-001")
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
        .facilityRef(new FacilityId("fac-1"))
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
        .facilityRef(new FacilityId("fac-1"))
        .size(50)
        .build()
);

future
    .thenAccept(page -> {
        for (StockItem item : page.items()) {
            System.out.println("Stock: " + item.id().value() + ", Available: " + item.available());
        }
    })
    .exceptionally(ex -> {
        Throwable cause = ex.getCause() != null ? ex.getCause() : ex;
        System.err.println("Failed to fetch stocks: " + cause.getMessage());
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
            .facilityRef(new FacilityId("fac-1"))
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
        .facilityRef(new FacilityId("fac-1"))
        .size(50)
        .build()
);

for (StockItem item : page.items()) {
    System.out.println(item.id().value() + ": " + item.available() + " available");
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
            new TenantArticleId("art-001")
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
        .facilityRef(new FacilityId("fac-1"))
        .build()
);

for (StockItem item : allStocks) {
    System.out.println(item.tenantArticleId().value() + ": " + item.value() + " units");
}
```

### create(CreateStockRequest)

Creates a new stock entry.

**Parameters:**
- `request: CreateStockRequest` — Create request with required fields (`tenantArticleId`, `value`, and one of `facilityRef`/`tenantFacilityId`) and optional fields (`locationRef`, `conditions`, etc.)

**Returns:** `StockItem` — The newly created stock entry

**Throws:** `FulfillmenttoolsException` (or subclass) if the request fails

### createAsync(CreateStockRequest)

Asynchronously creates a new stock entry.

**Returns:** `CompletableFuture<StockItem>`

**Throws:** Exception propagated via `CompletableFuture`; call `.exceptionally()` or `.handle()` to catch

### update(StockId, UpdateStockRequest)

Updates an existing stock entry. Uses optimistic locking — the `version` in the request must match the current version on the server.

**Parameters:**
- `stockId: StockId` — The ID of the stock entry to update
- `request: UpdateStockRequest` — Update request with required fields (`version`, `value`) and optional fields (`locationRef`, `conditions`, `traitConfig`, `customAttributes`)

**Returns:** `StockItem` — The updated stock entry

**Throws:** `FulfillmenttoolsException` (or subclass) if the request fails or the version does not match

### updateAsync(StockId, UpdateStockRequest)

Asynchronously updates an existing stock entry.

**Returns:** `CompletableFuture<StockItem>`

**Throws:** Exception propagated via `CompletableFuture`; call `.exceptionally()` or `.handle()` to catch

### upsertStocks(List&lt;VersionlessStockOperation&gt;)

Creates or updates multiple stock entries in a single batch call without version constraints. Supports mixing create and update operations. Results are returned in the same order as the input operations.

**Parameters:**
- `operations: List<VersionlessStockOperation>` — A list of 1–25 operations, each either `VersionlessStockCreate` or `VersionlessStockUpdate`

**Returns:** `List<StockUpsertResult>` — A list of results, each containing the resulting `StockItem` and the operation status (`"CREATED"`, `"UPDATED"`, or `"UNCHANGED"`)

**Throws:** `IllegalArgumentException` if the list is null or empty; `FulfillmenttoolsException` (or subclass) if the request fails

**Example:**

```java
List<StockUpsertResult> results = client.stocks().upsertStocks(
    List.of(
        VersionlessStockCreate.builder()
            .tenantArticleId(new TenantArticleId("art-001"))
            .facilityRef(new FacilityId("fac-1"))
            .value(100)
            .build(),
        VersionlessStockUpdate.builder()
            .stockId(new StockId("s-abc123"))
            .value(200)
            .build()
    )
);

for (StockUpsertResult result : results) {
    System.out.println(result.status() + ": " + result.stock().id().value());
}
```

### upsertStocksAsync(List&lt;VersionlessStockOperation&gt;)

Asynchronously creates or updates multiple stock entries in a single batch call.

**Parameters:**
- `operations: List<VersionlessStockOperation>` — A list of 1–25 operations, each either `VersionlessStockCreate` or `VersionlessStockUpdate`

**Returns:** `CompletableFuture<List<StockUpsertResult>>` — A future that resolves to a list of results

**Throws:** Exception propagated via `CompletableFuture`; call `.exceptionally()` or `.handle()` to catch

**Example:**

```java
client.stocks().upsertStocksAsync(
    List.of(
        VersionlessStockCreate.builder()
            .tenantArticleId(new TenantArticleId("art-003"))
            .facilityRef(new FacilityId("fac-1"))
            .value(75)
            .build()
    )
)
    .thenAccept(results -> {
        System.out.println("Upserted " + results.size() + " stocks");
    })
    .exceptionally(ex -> {
        System.err.println("Upsert failed: " + ex.getMessage());
        return null;
    });
```

### search(StockSearchRequest)

Searches for stock entries matching a typed query with optional filters and pagination. Returns one page of results.

**Parameters:**
- `request: StockSearchRequest` — Search request with optional `query` (if omitted, returns all stocks) and pagination (`size`, `after`)

**Returns:** `Page&lt;StockItem&gt;` — A page containing matching stock entries and a cursor for fetching the next page

**Throws:** `FulfillmenttoolsException` (or subclass) if the request fails

**Example:**

```java
StockSearchQuery query = StockSearchQuery.builder()
    .facilityRefEq(new FacilityId("fac-1"))
    .valueGte(50)
    .build();

Page<StockItem> page = client.stocks().search(
    StockSearchRequest.builder()
        .query(query)
        .size(50)
        .build()
);

for (StockItem item : page.items()) {
    System.out.println("Stock " + item.id().value() 
        + ": " + item.value() + " units");
}
```

### searchAsync(StockSearchRequest)

Asynchronously searches for stock entries matching a typed query with optional filters and pagination.

**Parameters:**
- `request: StockSearchRequest` — Search request with optional `query` and pagination

**Returns:** `CompletableFuture&lt;Page&lt;StockItem&gt;&gt;` — A future that resolves to a page of matching stock entries

**Throws:** Exception propagated via `CompletableFuture`; call `.exceptionally()` or `.handle()` to catch

**Example:**

```java
client.stocks().searchAsync(
    StockSearchRequest.builder()
        .query(StockSearchQuery.builder()
            .tenantArticleIdIn(
                new TenantArticleId("art-001"),
                new TenantArticleId("art-002")
            )
            .build()
        )
        .size(100)
        .build()
)
    .thenAccept(page -> {
        System.out.println("Found " + page.items().size() + " stocks");
    })
    .exceptionally(ex -> {
        System.err.println("Search failed: " + ex.getMessage());
        return null;
    });
```

### searchAll(StockSearchRequest)

Automatically iterates through all pages and returns an `Iterable` over all matching stock entries.

**Parameters:**
- `request: StockSearchRequest` — Search request with optional `query` (pagination settings are managed internally)

**Returns:** `Iterable&lt;StockItem&gt;` — An iterable that transparently fetches pages as needed

**Throws:** `FulfillmenttoolsException` (or subclass) if any page fetch fails

**Example:**

```java
Iterable<StockItem> allResults = client.stocks().searchAll(
    StockSearchRequest.builder()
        .query(StockSearchQuery.builder()
            .facilityRefEq(new FacilityId("fac-1"))
            .valueGte(10)
            .build()
        )
        .build()
);

for (StockItem item : allResults) {
    System.out.println(item.tenantArticleId().value() + ": " + item.value());
}
```

## CreateStockRequest Parameters

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `tenantArticleId(TenantArticleId)` | `TenantArticleId` | Yes | Your article identifier |
| `value(Integer)` | `Integer` | Yes | Initial stock quantity |
| `facilityRef(FacilityId)` | `FacilityId` | One of | The facility where the stock is located |
| `tenantFacilityId(TenantFacilityId)` | `TenantFacilityId` | One of | Alternative to `facilityRef`; one of the two must be set |
| `locationRef(StorageLocationId)` | `StorageLocationId` | No | Storage location within the facility |
| `tenantStockId(TenantStockId)` | `TenantStockId` | No | Your own stock identifier |
| `availableUntil(Instant)` | `Instant` | No | Expiry date for routing availability |
| `receiptDate(Instant)` | `Instant` | No | When the stock arrived |
| `conditions(List<String>)` | `List<String>` | No | Condition tags (e.g. `DEFECTIVE`) |
| `traitConfig(List<StorageLocationTraitConfigEntry>)` | `List<...>` | No | Trait configuration entries |
| `properties(Map<String,String>)` | `Map<String,String>` | No | Tracking properties (e.g. expiry dates) |
| `customAttributes(Map<String,Object>)` | `Map<String,Object>` | No | Free-form metadata |

## UpdateStockRequest Parameters

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `version(Integer)` | `Integer` | Yes | Current version for optimistic locking |
| `value(Integer)` | `Integer` | Yes | New stock quantity |
| `locationRef(StorageLocationId)` | `StorageLocationId` | No | Storage location; if not set, the server value is preserved |
| `tenantStockId(TenantStockId)` | `TenantStockId` | No | Your own stock identifier |
| `conditions(List<String>)` | `List<String>` | No | Condition tags (e.g. `DEFECTIVE`) |
| `traitConfig(List<StorageLocationTraitConfigEntry>)` | `List<...>` | No | Trait configuration entries |
| `customAttributes(Map<String,Object>)` | `Map<String,Object>` | No | Free-form metadata |

## VersionlessStockCreate Parameters

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `tenantArticleId(TenantArticleId)` | `TenantArticleId` | Yes | Your article identifier |
| `value(Integer)` | `Integer` | Yes | Initial stock quantity (zero is valid) |
| `facilityRef(FacilityId)` | `FacilityId` | One of | The facility where the stock is located |
| `tenantFacilityId(TenantFacilityId)` | `TenantFacilityId` | One of | Alternative to `facilityRef`; one of the two must be set |
| `locationRef(StorageLocationId)` | `StorageLocationId` | No | Storage location within the facility |
| `tenantStockId(TenantStockId)` | `TenantStockId` | No | Your own stock identifier |
| `availableUntil(Instant)` | `Instant` | No | Expiry date for routing availability |
| `receiptDate(Instant)` | `Instant` | No | When the stock arrived |
| `conditions(List<String>)` | `List<String>` | No | Condition tags (e.g. `DEFECTIVE`) |
| `traitConfig(List<StorageLocationTraitConfigEntry>)` | `List<...>` | No | Trait configuration entries |
| `properties(Map<String,String>)` | `Map<String,String>` | No | Tracking properties (e.g. expiry dates) |
| `customAttributes(Map<String,Object>)` | `Map<String,Object>` | No | Free-form metadata |

## VersionlessStockUpdate Parameters

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `stockId(StockId)` | `StockId` | Yes | ID of the stock to update |
| `value(Integer)` | `Integer` | Yes | New stock quantity (zero is valid) |
| `locationRef(StorageLocationId)` | `StorageLocationId` | No | Storage location; if not set, the server value is preserved |
| `tenantStockId(TenantStockId)` | `TenantStockId` | No | Your own stock identifier |
| `conditions(List<String>)` | `List<String>` | No | Condition tags (e.g. `DEFECTIVE`) |
| `traitConfig(List<StorageLocationTraitConfigEntry>)` | `List<...>` | No | Trait configuration entries |
| `customAttributes(Map<String,Object>)` | `Map<String,Object>` | No | Free-form metadata |

## StockSearchRequest Parameters

The `StockSearchRequest.builder()` accepts the following options. All parameters are optional.

| Parameter | Type | Description |
|-----------|------|-------------|
| `query(StockSearchQuery)` | `StockSearchQuery` | Typed search query with filters, comparisons, and logical combinators. If not set, returns all stocks subject to pagination |
| `size(Integer)` | `Integer` | Maximum number of entries per page. Defaults to server-defined limit if not set |
| `after(String)` | `String` | Cursor for pagination; retrieves the next page. Use the `pageInfo.endCursor` from the previous response (not `startAfterId`) |

## StockSearchQuery Parameters

The `StockSearchQuery.builder()` provides typed filter methods. All parameters are optional; unset filters are omitted from the request.

**String Filters** (ID and reference fields):

| Method | Parameters | Description |
|--------|------------|-------------|
| `idEq(StockId)` | `StockId` | Match stock by exact ID |
| `idIn(StockId...)` | varargs `StockId` | Match any stock in the list |
| `idIn(List<StockId>)` | `List<StockId>` | Match any stock in the list (List variant) |
| `idNotEq(StockId)` | `StockId` | Exclude stocks with this ID |
| `tenantArticleIdEq(TenantArticleId)` | `TenantArticleId` | Match by exact tenant article ID |
| `tenantArticleIdIn(TenantArticleId...)` | varargs `TenantArticleId` | Match any article in the list |
| `tenantArticleIdIn(List<TenantArticleId>)` | `List<TenantArticleId>` | Match any article in the list (List variant) |
| `tenantArticleIdNotEq(TenantArticleId)` | `TenantArticleId` | Exclude this article |
| `tenantStockIdEq(TenantStockId)` | `TenantStockId` | Match by exact tenant stock ID |
| `tenantStockIdIn(TenantStockId...)` | varargs `TenantStockId` | Match any tenant stock ID in the list |
| `tenantStockIdIn(List<TenantStockId>)` | `List<TenantStockId>` | Match any tenant stock ID in the list (List variant) |
| `facilityRefEq(FacilityId)` | `FacilityId` | Match by exact facility |
| `facilityRefIn(FacilityId...)` | varargs `FacilityId` | Match any facility in the list |
| `facilityRefIn(List<FacilityId>)` | `List<FacilityId>` | Match any facility in the list (List variant) |
| `facilityRefNotEq(FacilityId)` | `FacilityId` | Exclude this facility |
| `tenantFacilityIdEq(TenantFacilityId)` | `TenantFacilityId` | Match by exact tenant facility ID |
| `tenantFacilityIdIn(TenantFacilityId...)` | varargs `TenantFacilityId` | Match any tenant facility ID in the list |
| `tenantFacilityIdIn(List<TenantFacilityId>)` | `List<TenantFacilityId>` | Match any tenant facility ID in the list (List variant) |
| `locationRefEq(StorageLocationId)` | `StorageLocationId` | Match by exact storage location |
| `locationRefIn(StorageLocationId...)` | varargs `StorageLocationId` | Match any location in the list |
| `locationRefIn(List<StorageLocationId>)` | `List<StorageLocationId>` | Match any location in the list (List variant) |

**Numeric Filters** (stock quantity):

| Method | Parameters | Description |
|--------|------------|-------------|
| `valueEq(int)` | `int` | Match stocks with exact quantity |
| `valueGt(int)` | `int` | Match stocks with value greater than (exclusive) |
| `valueGte(int)` | `int` | Match stocks with value greater than or equal (inclusive) |
| `valueLt(int)` | `int` | Match stocks with value less than (exclusive) |
| `valueLte(int)` | `int` | Match stocks with value less than or equal (inclusive) |

**Conditions Filters** (condition tags):

| Method | Parameters | Description |
|--------|------------|-------------|
| `conditionsContainsEq(String)` | `String` | Match stocks that have this condition. Use `StockCondition.DEFECTIVE` for the constant |
| `conditionsContainsIn(String...)` | varargs `String` | Match stocks that have any of these conditions |

**Logical Combinators**:

| Method | Parameters | Description |
|--------|------------|-------------|
| `and(StockSearchQuery...)` | varargs `StockSearchQuery` | Combine queries with AND logic (all must match) |
| `and(List<StockSearchQuery>)` | `List<StockSearchQuery>` | Combine queries with AND logic (List variant) |
| `or(StockSearchQuery...)` | varargs `StockSearchQuery` | Combine queries with OR logic (any may match) |
| `or(List<StockSearchQuery>)` | `List<StockSearchQuery>` | Combine queries with OR logic (List variant) |

## StockCondition Constants

Use these constants with the conditions filter:

| Constant | Value | Description |
|----------|-------|-------------|
| `StockCondition.DEFECTIVE` | `"DEFECTIVE"` | Stock marked as defective |

## StockUpsertResult Fields

The `StockUpsertResult` record exposes the following accessor methods (via record components):

| Accessor | Type | Description |
|----------|------|-------------|
| `stock()` | `StockItem` | The resulting stock entry after the create or update operation |
| `status()` | `String` | The operation status: `"CREATED"` (new stock), `"UPDATED"` (existing stock modified), or `"UNCHANGED"` (existing stock unchanged) |

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

The `StockListRequest.builder()` accepts the following options. All parameters are optional.

| Parameter | Type | Description |
|-----------|------|-------------|
| `size(Integer)` | `Integer` | Maximum number of entries per page. Defaults to server-defined limit if not set |
| `startAfterId(String)` | `String` | Cursor for pagination; retrieves the next page after this ID |
| `facilityRef(FacilityId)` | `FacilityId` | Filter by facility (exact match) |
| `tenantFacilityId(TenantFacilityId)` | `TenantFacilityId` | Filter by tenant's facility ID |
| `tenantArticleId(List<TenantArticleId>)` | `List<TenantArticleId>` | Filter by one or more article IDs (OR logic) |
| `locationRef(List<StorageLocationId>)` | `List<StorageLocationId>` | Filter by one or more storage locations (OR logic) |

Filters are applied with AND logic (all specified filters must match). Within a multi-value filter (like `tenantArticleId`), OR logic applies.
