# Listings Client

The Listings client provides access to product listings. Query and manage product information across your catalog.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.ListingId;

// Get a listing
Listing listing = client.listings().get(new ListingId("list-001"));
System.out.println("SKU: " + listing.getSku());
```

## Bulk Operations

```java
List<Listing> upserted = client.listings().bulkUpsert(
    ListingBulkUpsertRequest.builder()
        .listings(Arrays.asList(
            CreateListingRequest.builder()
                .sku("SKU-001")
                .title("Product 1")
                .build(),
            CreateListingRequest.builder()
                .sku("SKU-002")
                .title("Product 2")
                .build()
        ))
        .build()
);
```

## Searching Listings

Search listings by SKU or other criteria:

```java
Page<Listing> results = client.listings().search(
    ListingSearchRequest.builder()
        .sku("SKU-123")
        .build()
);
```

## API Reference

### bulkUpsert(ListingBulkUpsertRequest)

Bulk upsert product listings (create or update).

**Parameters:**
- `request: ListingBulkUpsertRequest` — Bulk upsert request containing listings

**Returns:** `List<Listing>`

**Throws:** `FulfillmenttoolsException` if the request fails

### bulkUpsertAsync(ListingBulkUpsertRequest)

Bulk upsert product listings asynchronously.

**Parameters:**
- `request: ListingBulkUpsertRequest` — Bulk upsert request containing listings

**Returns:** `CompletableFuture<List<Listing>>`

### search(ListingSearchRequest)

Search product listings by criteria, returning one page of results.

**Parameters:**
- `request: ListingSearchRequest` — Search request with query and pagination

**Returns:** `Page<Listing>`

**Throws:** `FulfillmenttoolsException` if the request fails

### searchAsync(ListingSearchRequest)

Search product listings asynchronously.

**Parameters:**
- `request: ListingSearchRequest` — Search request with query and pagination

**Returns:** `CompletableFuture<Page<Listing>>`

### searchAll(ListingSearchRequest)

Search all product listings, automatically iterating through pages.

**Parameters:**
- `request: ListingSearchRequest` — Search request with query

**Returns:** `Iterable<Listing>`
