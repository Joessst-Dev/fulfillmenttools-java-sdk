# Shared Model Types

These types appear across multiple clients in the SDK. All are immutable Java records unless noted.

## Page&lt;T&gt;

Returned by every `list()` method. Carries the current page of items and an optional cursor for fetching the next page.

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.facilities.Facility;
import de.joesst.dev.fulfillmenttools.facilities.FacilityListRequest;

Page<Facility> page = client.facilities().list(
    FacilityListRequest.builder().size(20).build()
);

System.out.println("Items on this page: " + page.items().size());
System.out.println("Has more: " + page.hasMore());

if (page.hasMore()) {
    Page<Facility> next = client.facilities().list(
        FacilityListRequest.builder()
            .size(20)
            .startAfterId(page.nextCursor())
            .build()
    );
}
```

| Method | Returns | Description |
|--------|---------|-------------|
| `items()` | `List<T>` | Items on the current page |
| `nextCursor()` | `String` (nullable) | Cursor to pass as `startAfterId` for the next page |
| `hasMore()` | `boolean` | `true` when `nextCursor` is non-null and non-empty |

See the [Pagination guide](pagination.md) for automatic full-iteration with `listAll()`.

## Coordinates

Geographic coordinates for a facility location.

```java
import de.joesst.dev.fulfillmenttools.model.Coordinates;

Coordinates coords = Coordinates.builder()
    .lat(52.520008)
    .lon(13.404954)
    .build();

System.out.println(coords.lat() + ", " + coords.lon());
```

| Field | Type | Description |
|-------|------|-------------|
| `lat()` | `double` | Latitude in degrees |
| `lon()` | `double` | Longitude in degrees |

## TagReference

A reference to a tag, carrying the tag's typed ID and its value. Used on facilities, listings, and other resources that support tagging.

```java
import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.id.TagId;

TagReference tag = TagReference.builder()
    .id(TagId.builder().value("tag-001").build())
    .value("priority")
    .build();

System.out.println(tag.id().value() + " = " + tag.value());
```

| Field | Type | Description |
|-------|------|-------------|
| `id()` | `TagId` | The tag's unique identifier |
| `value()` | `String` | The tag's label or value |

## AssignedUser

A user assigned to a pick job, pack job, or similar work item.

```java
import de.joesst.dev.fulfillmenttools.model.AssignedUser;

// Reading from a job response:
AssignedUser user = pickJob.assignedUser();
System.out.println(user.username() + " (" + user.userId().value() + ")");
```

| Field | Type | Description |
|-------|------|-------------|
| `username()` | `String` | The user's login name |
| `userId()` | `UserId` | The user's platform identifier |

## TimeZone

Time zone metadata for a facility or location.

```java
import de.joesst.dev.fulfillmenttools.model.TimeZone;

// Reading from a facility response:
TimeZone tz = facility.timeZone();
System.out.println(tz.timeZoneId());   // e.g. "Europe/Berlin"
System.out.println(tz.timeZoneName()); // e.g. "Central European Time"
System.out.println(tz.offsetInSeconds()); // e.g. 3600.0
```

| Field | Type | Description |
|-------|------|-------------|
| `timeZoneId()` | `String` | IANA time zone identifier (e.g. `"Europe/Berlin"`) |
| `timeZoneName()` | `String` | Human-readable name |
| `offsetInSeconds()` | `Double` (nullable) | UTC offset in seconds; `null` if unknown |
| `source()` | `String` | System that determined this time zone |

## CustomAttributes

Tenant-defined extension data attached to a resource. Every model that exposes a `customAttributes` field returns a `CustomAttributes` instance instead of a raw `Map<String, Object>`.

### Constructing for a request

Use `CustomAttributes.of(Map)` when building a request:

```java
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

CustomAttributes attrs = CustomAttributes.of(Map.of(
    "priority", "high",
    "source",   "ERP"
));
```

Pass the result directly to any builder method that accepts `customAttributes`:

```java
import de.joesst.dev.fulfillmenttools.stocks.CreateStockRequest;

CreateStockRequest request = CreateStockRequest.builder()
    .tenantArticleId(new TenantArticleId("SKU-001"))
    .value(100)
    .facilityRef(FacilityId.builder().value("fac-001").build())
    .customAttributes(CustomAttributes.of(Map.of("warehouse_zone", "A3")))
    .build();
```

> **Note:** Before PR #175, these builder methods accepted `Map<String, Object>` directly. Any code that previously passed `Map.of(...)` must now wrap the map: `CustomAttributes.of(Map.of(...))`.

### Reading attributes from a response

`attributes()` returns the raw map (unmodifiable):

```java
CustomAttributes attrs = stock.customAttributes();
Map<String, Object> raw = attrs.attributes();
System.out.println(raw.get("warehouse_zone")); // "A3"
```

### Converting to a typed POJO

`as(Class<T>)` converts the attributes map to your own type using the SDK's Jackson ObjectMapper — no I/O is performed:

```java
record WarehouseMeta(String warehouseZone, int shelfNumber) {}

WarehouseMeta meta = stock.customAttributes().as(WarehouseMeta.class);
System.out.println(meta.warehouseZone()); // "A3"
System.out.println(meta.shelfNumber());   // 7
```

Conversion fails with `IllegalArgumentException` if the attributes map is missing required fields or contains incompatible types.

### Wiring into a custom ObjectMapper

If you configure your own Jackson `ObjectMapper` with field-level visibility (e.g. `PropertyAccessor.FIELD = ANY`), register the SDK's module so that `as()` uses that same mapper:

```java
import com.fasterxml.jackson.databind.ObjectMapper;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

ObjectMapper myMapper = new ObjectMapper();
// ... your own configuration ...
myMapper.registerModule(CustomAttributes.jacksonModule(myMapper));
```

Without this registration, `as()` still works — it falls back to the SDK's default mapper — but the conversion ignores any custom serializers you may have configured.

| Method / Factory | Returns | Description |
|------------------|---------|-------------|
| `CustomAttributes.of(Map<String, Object>)` | `CustomAttributes` | Static factory for building requests |
| `attributes()` | `Map<String, Object>` | Raw attributes map (unmodifiable) |
| `as(Class<T> type)` | `T` | Converts attributes to the given POJO type |
| `CustomAttributes.jacksonModule(ObjectMapper)` | `SimpleModule` | Jackson module to wire a custom mapper into `as()` |

## Money

A monetary amount with currency information. Used throughout the SDK to represent prices, costs, and financial values.

```java
import de.joesst.dev.fulfillmenttools.model.Money;

Money price = Money.builder()
    .value(499.0)
    .currency("EUR")
    .decimalPlaces(2.0)
    .build();

System.out.println(price.value());        // 499.0
System.out.println(price.currency());     // "EUR"
System.out.println(price.decimalPlaces()); // 2.0
System.out.println(price.toAmount());     // 4.99
```

| Field / Method | Type | Description |
|----------------|------|-------------|
| `value()` | `Double` | The raw numeric amount as returned by the API (minor units, e.g. `499.0` for €4.99) |
| `currency()` | `String` | ISO 4217 currency code (e.g. `"EUR"`, `"USD"`) |
| `decimalPlaces()` | `Double` | The number of decimal places for this currency (e.g. `2.0` for most currencies, `0.0` for JPY) |
| `toAmount()` | `Double` | Converts the raw value to its decimal representation by dividing by 10^`decimalPlaces` (e.g. `499.0` → `4.99`). Returns `value` unchanged when `decimalPlaces` is null or zero; returns `null` when `value` is null. |

---

## Exceptions

All SDK exceptions extend `FulfillmenttoolsException`, which extends `RuntimeException`. You never need to declare them in method signatures.

### Exception hierarchy

```
FulfillmenttoolsException  (base — all SDK errors)
├── NotFoundException        (404)
├── ValidationException      (400)
├── ConflictException        (409)
├── AuthenticationException  (401)
├── AuthorizationException   (403)
├── RateLimitException       (429)
├── ServerException          (5xx)
└── TransportException       (network / IO failures)
```

### FulfillmenttoolsException

The base class provides structured access to the error response:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;
import de.joesst.dev.fulfillmenttools.ApiError;

try {
    client.facilities().get(FacilityId.builder().value("fac-001").build());
} catch (FulfillmenttoolsException e) {
    System.out.println("HTTP status: " + e.statusCode());
    System.out.println("Request ID: " + e.requestId());
    for (ApiError error : e.errors()) {
        System.out.println(error.summary() + ": " + error.description());
    }
}
```

| Method | Returns | Description |
|--------|---------|-------------|
| `statusCode()` | `int` | HTTP status code, or `-1` for non-HTTP failures |
| `requestId()` | `String` (nullable) | Value of the `x-request-id` response header |
| `errors()` | `List<ApiError>` | Structured error details; empty for non-HTTP failures |
| `getMessage()` | `String` | Human-readable summary (first error's `summary`, or `"HTTP <code>"`) |

### NotFoundException

Thrown for 404 responses. Catch it before `FulfillmenttoolsException` when you want to handle "not found" separately:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

try {
    client.facilities().get(FacilityId.builder().value("fac-001").build());
} catch (NotFoundException e) {
    System.out.println("Facility does not exist");
} catch (FulfillmenttoolsException e) {
    System.out.println("Request failed: " + e.getMessage());
}
```

### RateLimitException

Thrown for 429 responses. Provides the optional `Retry-After` delay:

```java
import de.joesst.dev.fulfillmenttools.RateLimitException;
import java.time.Duration;

try {
    client.facilities().list(FacilityListRequest.builder().build());
} catch (RateLimitException e) {
    Duration wait = e.retryAfter().orElse(Duration.ofSeconds(60));
    System.out.println("Rate limited — retry after " + wait.toSeconds() + "s");
}
```

### ApiError

Structured error detail returned inside `FulfillmenttoolsException.errors()`:

| Field | Type | Description |
|-------|------|-------------|
| `summary()` | `String` | Short error title |
| `description()` | `String` | Detailed error message |
| `requestVersion()` | `int` | Request format version |
| `version()` | `int` | Error response format version |
