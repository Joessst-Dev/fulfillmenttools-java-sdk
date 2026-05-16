# Listings Client

The Listings client provides synchronous and asynchronous operations to search and bulk upsert product listings. A listing represents an article that is offered in a specific facility, and includes details such as title, pricing, categories, and custom attributes.

## Quick Start

Import the necessary classes and create a bulk upsert request:

```java
import de.joesst.dev.fulfillmenttools.listings.*;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.TenantArticleId;

// Create a listing to upsert
ListingUpsertItem item = ListingUpsertItem.builder()
    .facilityId(FacilityId.builder().value("fac-001").build())
    .tenantArticleId(TenantArticleId.builder().value("art-123").build())
    .title("Classic Widget")
    .imageUrl("https://example.com/image.jpg")
    .scannableCodes(List.of("123456789", "9876543210"))
    .build();

// Bulk upsert
List<Listing> upserted = client.listings().bulkUpsert(
    ListingBulkUpsertRequest.builder()
        .listings(List.of(item))
        .build()
);

System.out.println("Upserted: " + upserted.size() + " listings");
```

## Core Concepts

### Listing Identity
Each listing is identified by a combination of `facilityId` and `tenantArticleId`. The `tenantArticleId` is your internal product identifier — it must be unique within your tenant and never changes for a given article.

### Immutable Records
`Listing` and `ListingUpsertItem` are immutable and thread-safe. Use their respective `builder()` methods to construct instances.

### Pagination
Search results are returned as `Page<Listing>` objects. Use `page.hasMore()` to check if more results are available, and `page.nextCursor()` to fetch the next page.

## Bulk Upsert

Create or update multiple listings in a single operation. Both `facilityId` and `tenantArticleId` are required; all other fields are optional.

### Synchronous Bulk Upsert

```java
List<ListingUpsertItem> items = List.of(
    ListingUpsertItem.builder()
        .facilityId(FacilityId.builder().value("fac-001").build())
        .tenantArticleId(TenantArticleId.builder().value("SKU-001").build())
        .title("Widget Pro")
        .categoryRefs(List.of("widgets", "bestsellers"))
        .build(),
    ListingUpsertItem.builder()
        .facilityId(FacilityId.builder().value("fac-001").build())
        .tenantArticleId(TenantArticleId.builder().value("SKU-002").build())
        .title("Widget Basic")
        .categoryRefs(List.of("widgets"))
        .build()
);

List<Listing> result = client.listings().bulkUpsert(
    ListingBulkUpsertRequest.builder()
        .listings(items)
        .build()
);
```

### Asynchronous Bulk Upsert

```java
client.listings().bulkUpsertAsync(
    ListingBulkUpsertRequest.builder()
        .listings(items)
        .build()
).thenAccept(listings -> {
    System.out.println("Upserted " + listings.size() + " listings");
}).exceptionally(ex -> {
    System.err.println("Bulk upsert failed: " + ex.getMessage());
    return null;
});
```

## Searching Listings

Search for listings using flexible query criteria. Results are returned one page at a time; use `searchAll()` to automatically iterate through all pages.

### Basic Search by Article ID

```java
Page<Listing> page = client.listings().search(
    ListingSearchRequest.builder()
        .query(ListingSearchQuery.builder()
            .tenantArticleIdEq("SKU-001")
            .build())
        .size(50)
        .build()
);

for (Listing listing : page.items()) {
    System.out.println("ID: " + listing.id() + ", Title: " + listing.title());
}
```

### Search with Multiple Filters

```java
Page<Listing> page = client.listings().search(
    ListingSearchRequest.builder()
        .query(ListingSearchQuery.builder()
            .statusEq("ACTIVE")
            .facilityRefEq("fac-001")
            .categoryRefsContains("electronics")
            .build())
        .size(100)
        .build()
);
```

### Search Multiple Values (OR Condition)

```java
Page<Listing> page = client.listings().search(
    ListingSearchRequest.builder()
        .query(ListingSearchQuery.builder()
            .tenantArticleIdIn("SKU-001", "SKU-002", "SKU-003")
            .statusIn("ACTIVE", "INACTIVE")
            .build())
        .size(50)
        .build()
);
```

### Combined Filters (AND/OR Logic)

```java
ListingSearchQuery activeInFac1 = ListingSearchQuery.builder()
    .statusEq("ACTIVE")
    .facilityRefEq("fac-001")
    .build();

ListingSearchQuery activeInFac2 = ListingSearchQuery.builder()
    .statusEq("ACTIVE")
    .facilityRefEq("fac-002")
    .build();

// Find active listings in either facility
Page<Listing> page = client.listings().search(
    ListingSearchRequest.builder()
        .query(ListingSearchQuery.builder()
            .or(activeInFac1, activeInFac2)
            .build())
        .size(50)
        .build()
);
```

### Pagination

Use `after` (cursor-based) or `before` parameters along with `size` to control pagination:

```java
// First page
Page<Listing> page1 = client.listings().search(
    ListingSearchRequest.builder()
        .query(ListingSearchQuery.builder()
            .statusEq("ACTIVE")
            .build())
        .size(50)
        .build()
);

// Fetch next page if available
if (page1.hasMore()) {
    Page<Listing> page2 = client.listings().search(
        ListingSearchRequest.builder()
            .query(ListingSearchQuery.builder()
                .statusEq("ACTIVE")
                .build())
            .size(50)
            .after(page1.nextCursor())
            .build()
    );
}
```

### Search All Results

Automatically iterate through all pages without manual cursor management:

```java
Iterable<Listing> allListings = client.listings().searchAll(
    ListingSearchRequest.builder()
        .query(ListingSearchQuery.builder()
            .statusEq("ACTIVE")
            .build())
        .size(100)  // Page size; API fetches pages automatically
        .build()
);

for (Listing listing : allListings) {
    System.out.println(listing.tenantArticleId().value() + ": " + listing.title());
}
```

### Asynchronous Search

```java
client.listings().searchAsync(
    ListingSearchRequest.builder()
        .query(ListingSearchQuery.builder()
            .facilityRefEq("fac-001")
            .statusEq("ACTIVE")
            .build())
        .size(50)
        .build()
).thenAccept(page -> {
    System.out.println("Found " + page.items().size() + " listings");
    if (page.hasMore()) {
        System.out.println("More results available; use nextCursor: " + page.nextCursor());
    }
}).exceptionally(ex -> {
    System.err.println("Search failed: " + ex.getMessage());
    return null;
});
```

## Accessing Listing Fields

Once a listing is retrieved, access its properties via accessor methods:

```java
Listing listing = upserted.get(0);

// Identity and status
ListingId listingId = listing.id();
String rawId = listingId.value();  // Extract the raw string from the typed ID
TenantArticleId articleId = listing.tenantArticleId();
FacilityId facilityId = listing.facilityId();
String status = listing.status();  // "ACTIVE" or "INACTIVE"

// Content
String title = listing.title();
Map<String, String> titlesByLocale = listing.titleLocalized();
String imageUrl = listing.imageUrl();
List<String> barcodes = listing.scannableCodes();
List<String> categories = listing.categoryRefs();

// Metadata
Integer version = listing.version();
Instant created = listing.created();
Instant lastModified = listing.lastModified();

// Custom metadata
CustomAttributes customAttrs = listing.customAttributes();
Map<String, Object> raw = customAttrs.attributes();
```

## Error Handling

All synchronous operations throw `FulfillmenttoolsException` on failure. This includes HTTP errors, network issues, and authentication failures.

### Catching API Errors

```java
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;
import de.joesst.dev.fulfillmenttools.ApiError;

try {
    List<Listing> upserted = client.listings().bulkUpsert(
        ListingBulkUpsertRequest.builder()
            .listings(items)
            .build()
    );
} catch (FulfillmenttoolsException ex) {
    int status = ex.statusCode();
    String requestId = ex.requestId();
    List<ApiError> errors = ex.errors();
    
    if (status == 400) {
        System.err.println("Bad request:");
        for (ApiError error : errors) {
            System.err.println("  - " + error.summary());
        }
    } else if (status == 401) {
        System.err.println("Authentication failed (request: " + requestId + ")");
    } else if (status >= 500) {
        System.err.println("Server error (request: " + requestId + ")");
    }
}
```

### Handling Async Errors

Async methods return `CompletableFuture` and do not throw directly. Use `exceptionally()` or `handle()` to process errors:

```java
client.listings().bulkUpsertAsync(request)
    .exceptionally(throwable -> {
        Throwable cause = throwable;
        
        // Unwrap CompletionException if present
        if (throwable instanceof java.util.concurrent.CompletionException) {
            cause = throwable.getCause();
        }
        
        if (cause instanceof FulfillmenttoolsException ex) {
            System.err.println("API error: " + ex.getMessage());
            System.err.println("Request ID: " + ex.requestId());
        } else {
            System.err.println("Unexpected error: " + cause.getMessage());
        }
        return null;
    });
```

## API Reference

### bulkUpsert(ListingBulkUpsertRequest)

Bulk upserts product listings (creates or updates). Both `facilityId` and `tenantArticleId` are required on each item; matches are based on this combination.

**Signature:**
```java
List<Listing> bulkUpsert(ListingBulkUpsertRequest request)
```

**Parameters:**
- `request: ListingBulkUpsertRequest` — Request containing a list of listings to upsert

**Returns:** `List<Listing>` — The upserted listings with their assigned IDs and versions

**Throws:** `FulfillmenttoolsException` if the request fails or contains invalid data

**Example:**
```java
List<Listing> upserted = client.listings().bulkUpsert(
    ListingBulkUpsertRequest.builder()
        .listings(List.of(
            ListingUpsertItem.builder()
                .facilityId(FacilityId.builder().value("fac-001").build())
                .tenantArticleId(TenantArticleId.builder().value("SKU-001").build())
                .title("Product A")
                .build()
        ))
        .build()
);
```

---

### bulkUpsertAsync(ListingBulkUpsertRequest)

Asynchronously bulk upserts product listings.

**Signature:**
```java
CompletableFuture<List<Listing>> bulkUpsertAsync(ListingBulkUpsertRequest request)
```

**Parameters:**
- `request: ListingBulkUpsertRequest` — Request containing a list of listings to upsert

**Returns:** `CompletableFuture<List<Listing>>` — Future that resolves to the upserted listings

**Example:**
```java
client.listings().bulkUpsertAsync(request)
    .thenAccept(listings -> System.out.println("Upserted: " + listings.size()))
    .exceptionally(ex -> {
        System.err.println("Failed: " + ex.getMessage());
        return null;
    });
```

---

### search(ListingSearchRequest)

Searches for product listings matching the specified criteria, returning one page of results.

**Signature:**
```java
Page<Listing> search(ListingSearchRequest request)
```

**Parameters:**
- `request: ListingSearchRequest` — Search request with query and pagination parameters

**Returns:** `Page<Listing>` — A single page of matching listings; use `page.hasMore()` and `page.nextCursor()` to fetch additional pages

**Throws:** `FulfillmenttoolsException` if the request fails

**Example:**
```java
Page<Listing> page = client.listings().search(
    ListingSearchRequest.builder()
        .query(ListingSearchQuery.builder()
            .tenantArticleIdEq("SKU-001")
            .statusEq("ACTIVE")
            .build())
        .size(50)
        .build()
);

for (Listing listing : page.items()) {
    System.out.println(listing.title());
}
```

---

### searchAll(ListingSearchRequest)

Searches for product listings and automatically iterates through all pages. Useful for batch operations where you need all matching results.

**Signature:**
```java
Iterable<Listing> searchAll(ListingSearchRequest request)
```

**Parameters:**
- `request: ListingSearchRequest` — Search request with query and optional page size (defaults internally if not set)

**Returns:** `Iterable<Listing>` — An iterable that transparently fetches additional pages as needed

**Example:**
```java
Iterable<Listing> allListings = client.listings().searchAll(
    ListingSearchRequest.builder()
        .query(ListingSearchQuery.builder()
            .statusEq("ACTIVE")
            .build())
        .size(100)
        .build()
);

// Iterate and process — pages are fetched automatically
for (Listing listing : allListings) {
    System.out.println(listing.title());
}
```

---

### searchAsync(ListingSearchRequest)

Asynchronously searches for product listings, returning one page of results.

**Signature:**
```java
CompletableFuture<Page<Listing>> searchAsync(ListingSearchRequest request)
```

**Parameters:**
- `request: ListingSearchRequest` — Search request with query and pagination parameters

**Returns:** `CompletableFuture<Page<Listing>>` — Future that resolves to a page of listings

**Example:**
```java
client.listings().searchAsync(
    ListingSearchRequest.builder()
        .query(ListingSearchQuery.builder()
            .facilityRefEq("fac-001")
            .build())
        .size(50)
        .build()
).thenAccept(page -> {
    System.out.println("Found " + page.items().size() + " listings");
}).exceptionally(ex -> {
    System.err.println("Search failed: " + ex.getMessage());
    return null;
});
```

---

## Query Builder Reference

`ListingSearchQuery` provides a fluent builder for constructing search filters:

### Filtering by Listing ID

- `idEq(String)` — Match a single listing ID
- `idIn(String...)` — Match any of the given listing IDs
- `idNotEq(String)` — Exclude a listing ID
- `idNotIn(List<String>)` — Exclude multiple listing IDs

### Filtering by Tenant Article ID

- `tenantArticleIdEq(String)` — Match a single tenant article ID
- `tenantArticleIdIn(String...)` — Match any of the given tenant article IDs
- `tenantArticleIdNotEq(String)` — Exclude a tenant article ID

### Filtering by Facility

- `facilityRefEq(String)` — Match a single facility reference
- `facilityRefIn(String...)` — Match any of the given facility references
- `facilityRefNotEq(String)` — Exclude a facility reference

### Filtering by Status

- `statusEq(String)` — Match a specific status (e.g., `"ACTIVE"`, `"INACTIVE"`)
- `statusIn(String...)` — Match any of the given statuses
- `statusNotEq(String)` — Exclude a status

### Filtering by Price

- `priceEq(Number)` — Match an exact price
- `priceGt(Number)` — Price greater than
- `priceGte(Number)` — Price greater than or equal
- `priceLt(Number)` — Price less than
- `priceLte(Number)` — Price less than or equal

### Filtering by Weight

- `weightEq(Number)` — Match an exact weight
- `weightGt(Number)` — Weight greater than
- `weightGte(Number)` — Weight greater than or equal
- `weightLt(Number)` — Weight less than
- `weightLte(Number)` — Weight less than or equal

### Filtering by Categories

- `categoryRefsContains(String)` — Match listings that have a specific category

### Filtering by Tags

- `tagsContainId(TagId)` — Match listings that have a tag with a specific ID
- `tagsContainValue(String)` — Match listings that have a tag with a specific value

### Filtering by Creation Date

- `createdEq(String)` — Match an exact creation date (ISO 8601)
- `createdGt(String)` — Created after (ISO 8601)
- `createdGte(String)` — Created on or after (ISO 8601)
- `createdLt(String)` — Created before (ISO 8601)
- `createdLte(String)` — Created on or before (ISO 8601)

### Custom Attributes

- `customAttribute(String key, Object filter)` — Filter on a custom attribute by key

### Combining Filters

- `and(ListingSearchQuery...)` — Combine multiple queries with AND logic
- `or(ListingSearchQuery...)` — Combine multiple queries with OR logic

**Example:**
```java
ListingSearchQuery query = ListingSearchQuery.builder()
    .statusEq("ACTIVE")
    .facilityRefIn("fac-001", "fac-002")
    .categoryRefsContains("electronics")
    .priceGte(10.0)
    .priceLte(100.0)
    .build();
```

## Troubleshooting

### "facilityId must not be null" or "tenantArticleId must not be null"

Both fields are required when constructing a `ListingUpsertItem`. Ensure you provide both:

```java
// Wrong: missing facilityId
ListingUpsertItem item = ListingUpsertItem.builder()
    .tenantArticleId(TenantArticleId.builder().value("SKU-001").build())
    .title("Product")
    .build();  // NullPointerException during build()

// Correct
ListingUpsertItem item = ListingUpsertItem.builder()
    .facilityId(FacilityId.builder().value("fac-001").build())
    .tenantArticleId(TenantArticleId.builder().value("SKU-001").build())
    .title("Product")
    .build();
```

### Search returns no results

Verify that your query criteria match the actual data. Use a broad query to test:

```java
// Broad search to verify data exists
Page<Listing> all = client.listings().search(
    ListingSearchRequest.builder()
        .query(ListingSearchQuery.builder().build())  // Empty query matches all
        .size(10)
        .build()
);

System.out.println("Total listings: " + all.items().size());
```

### CompletionException in async handlers

When using async methods, exceptions are wrapped in `CompletionException`. Unwrap them in error handlers:

```java
future.exceptionally(throwable -> {
    Throwable cause = throwable;
    if (throwable instanceof java.util.concurrent.CompletionException) {
        cause = throwable.getCause();
    }
    
    if (cause instanceof FulfillmenttoolsException ex) {
        // Handle expected SDK exception
    }
    return null;
});
```

### Request timed out

Network timeouts indicate the API is slow or unavailable. Increase the client timeout or retry with exponential backoff:

```java
// Retry logic with exponential backoff
int maxRetries = 3;
int delayMs = 1000;

for (int attempt = 0; attempt < maxRetries; attempt++) {
    try {
        return client.listings().search(request);
    } catch (FulfillmenttoolsException ex) {
        if (attempt < maxRetries - 1 && ex.statusCode() >= 500) {
            Thread.sleep(delayMs);
            delayMs *= 2;
        } else {
            throw ex;
        }
    }
}
```
