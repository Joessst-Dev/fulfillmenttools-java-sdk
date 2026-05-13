# Listings Client

The Listings client provides access to product listings. Query and manage product information across your catalog.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.ListingId;

// Get a listing
Listing listing = client.listings().get(new ListingId("list-001"));
System.out.println("SKU: " + listing.getSku());
```

## Listing Products

```java
Page<Listing> page = client.listings().list(
    ListingListRequest.builder()
        .size(50)
        .build()
);
```

Search listings by SKU or other criteria:

```java
Page<Listing> results = client.listings().search(
    ListingSearchRequest.builder()
        .sku("SKU-123")
        .build()
);
```

## API Reference

### get(ListingId)

Get a listing by ID.

**Returns:** `Listing`

### list(ListingListRequest)

List listings with pagination.

**Returns:** `Page<Listing>`

### search(ListingSearchRequest)

Search listings by criteria.

**Returns:** `Page<Listing>`

### searchAll(ListingSearchRequest)

Search all matching listings.

**Returns:** `Iterable<Listing>`
