# Stocks Client

The Stocks client provides access to inventory and stock management across your fulfillment network. Query and manage stock levels for products at different facilities.

## Basic Usage

```java
// List stock entries
Page<StockItem> page = client.stocks().list(
    StockListRequest.builder()
        .size(50)
        .build()
);

for (StockItem item : page.items()) {
    System.out.println("Facility: " + item.getFacilityId());
}
```

## Listing Stocks

List stocks with pagination:

```java
Page<Stock> page = client.stocks().list(
    StockListRequest.builder()
        .size(50)
        .build()
);
```

Iterate through all stocks:

```java
Iterable<StockItem> allStocks = client.stocks().listAll(
    StockListRequest.builder()
        .size(100)
        .build()
);

for (StockItem item : allStocks) {
    System.out.println("Stock: " + item.getStockId());
}
```

## API Reference

### list(StockListRequest)

List stocks with pagination.

**Parameters:**
- `request: StockListRequest` — List request with filters and pagination

**Returns:** `Page<StockItem>`

### listAsync(StockListRequest)

List stocks asynchronously with pagination.

**Parameters:**
- `request: StockListRequest` — List request with filters and pagination

**Returns:** `CompletableFuture<Page<StockItem>>`

### listAll(StockListRequest)

List all stocks, automatically iterating through pages.

**Parameters:**
- `request: StockListRequest` — List request with filters

**Returns:** `Iterable<StockItem>`
