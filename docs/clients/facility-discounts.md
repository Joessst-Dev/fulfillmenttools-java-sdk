# Facility Discounts Client

The Facility Discounts client manages facility-level discount configurations. Define and manage pricing discounts by facility.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.FacilityDiscountId;

// Get a facility discount
FacilityDiscount discount = client.facilityDiscounts().get(
    new FacilityId("fac-001"),
    new FacilityDiscountId("fdis-001")
);
System.out.println("Percentage: " + discount.getPercentage());
```

## Listing Facility Discounts

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;

Page<FacilityDiscount> page = client.facilityDiscounts().list(
    new FacilityId("fac-001"),
    FacilityDiscountListRequest.builder()
        .size(50)
        .build()
);
```

## Creating and Managing Facility Discounts

```java
FacilityDiscount discount = client.facilityDiscounts().create(
    new FacilityId("fac-001"),
    CreateFacilityDiscountRequest.builder()
        .percentage(10)
        .description("Volume discount")
        .build()
);

// Update a facility discount
FacilityDiscount updated = client.facilityDiscounts().update(
    new FacilityId("fac-001"),
    new FacilityDiscountId("fdis-001"),
    UpdateFacilityDiscountRequest.builder()
        .percentage(15)
        .build()
);

// Delete a facility discount
client.facilityDiscounts().delete(
    new FacilityId("fac-001"),
    new FacilityDiscountId("fdis-001")
);
```

## API Reference

### get(FacilityId, FacilityDiscountId)

Get a facility discount by ID.

**Parameters:**
- `facilityId: FacilityId` — The facility ID
- `discountId: FacilityDiscountId` — The discount ID

**Returns:** `FacilityDiscount`

**Throws:** `FulfillmenttoolsException` if not found

### getAsync(FacilityId, FacilityDiscountId)

Get a facility discount asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The facility ID
- `discountId: FacilityDiscountId` — The discount ID

**Returns:** `CompletableFuture<FacilityDiscount>`

### list(FacilityId, FacilityDiscountListRequest)

List facility discounts for a facility with pagination.

**Parameters:**
- `facilityId: FacilityId` — The facility ID
- `request: FacilityDiscountListRequest` — List request with pagination

**Returns:** `Page<FacilityDiscount>`

**Throws:** `FulfillmenttoolsException` if the request fails

### listAsync(FacilityId, FacilityDiscountListRequest)

List facility discounts asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The facility ID
- `request: FacilityDiscountListRequest` — List request with pagination

**Returns:** `CompletableFuture<Page<FacilityDiscount>>`

### listAll(FacilityId, FacilityDiscountListRequest)

List all facility discounts for a facility, automatically iterating through pages.

**Parameters:**
- `facilityId: FacilityId` — The facility ID
- `request: FacilityDiscountListRequest` — List request

**Returns:** `Iterable<FacilityDiscount>`

### create(FacilityId, CreateFacilityDiscountRequest)

Create a new facility discount.

**Parameters:**
- `facilityId: FacilityId` — The facility ID
- `request: CreateFacilityDiscountRequest` — Creation request

**Returns:** `FacilityDiscount`

**Throws:** `FulfillmenttoolsException` if the request fails

### createAsync(FacilityId, CreateFacilityDiscountRequest)

Create a facility discount asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The facility ID
- `request: CreateFacilityDiscountRequest` — Creation request

**Returns:** `CompletableFuture<FacilityDiscount>`

### update(FacilityId, FacilityDiscountId, UpdateFacilityDiscountRequest)

Update an existing facility discount.

**Parameters:**
- `facilityId: FacilityId` — The facility ID
- `discountId: FacilityDiscountId` — The discount ID
- `request: UpdateFacilityDiscountRequest` — Update request

**Returns:** `FacilityDiscount`

**Throws:** `FulfillmenttoolsException` if the request fails

### updateAsync(FacilityId, FacilityDiscountId, UpdateFacilityDiscountRequest)

Update a facility discount asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The facility ID
- `discountId: FacilityDiscountId` — The discount ID
- `request: UpdateFacilityDiscountRequest` — Update request

**Returns:** `CompletableFuture<FacilityDiscount>`

### delete(FacilityId, FacilityDiscountId)

Delete a facility discount.

**Parameters:**
- `facilityId: FacilityId` — The facility ID
- `discountId: FacilityDiscountId` — The discount ID

**Throws:** `FulfillmenttoolsException` if the request fails

### deleteAsync(FacilityId, FacilityDiscountId)

Delete a facility discount asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The facility ID
- `discountId: FacilityDiscountId` — The discount ID

**Returns:** `CompletableFuture<Void>`
