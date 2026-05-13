# Stocks Client

The Stocks client provides access to inventory and stock management across your fulfillment network. Query and manage stock levels for products at different facilities.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.StockId;

// Get stock information
Stock stock = client.stocks().get(new StockId("stk-001"));
System.out.println("Available: " + stock.getAvailable());
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
Iterable<Stock> allStocks = client.stocks().listAll(
    StockListRequest.builder()
        .size(100)
        .build()
);
```

## API Reference

### get(StockId)

Get stock information by ID.

**Parameters:**
- `stockId: StockId` — The stock identifier

**Returns:** `Stock`

### getAsync(StockId)

Get stock information asynchronously.

**Returns:** `CompletableFuture<Stock>`

### list(StockListRequest)

List stocks with pagination.

**Returns:** `Page<Stock>`

### listAll(StockListRequest)

List all stocks, automatically iterating through pages.

**Returns:** `Iterable<Stock>`
