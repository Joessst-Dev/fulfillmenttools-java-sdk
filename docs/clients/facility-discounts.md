# Facility Discounts Client

The Facility Discounts client manages discount configurations scoped to a facility — define relative (percentage) or absolute (fixed amount) discounts that activate under configurable context conditions.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.FacilityDiscountId;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscount;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

try {
    FacilityDiscount discount = client.facilityDiscounts().get(
        FacilityId.builder().value("fac-001").build(),
        FacilityDiscountId.builder().value("fdis-001").build()
    );
    System.out.println("Type: " + discount.type());
    System.out.println("Priority: " + discount.priority());
    System.out.println("Version: " + discount.version());
} catch (NotFoundException e) {
    System.out.println("Discount not found");
} catch (FulfillmenttoolsException e) {
    System.out.println("Request failed: " + e.getMessage());
}
```

## Listing Facility Discounts

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscount;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscountListRequest;

Page<FacilityDiscount> page = client.facilityDiscounts().list(
    FacilityId.builder().value("fac-001").build(),
    FacilityDiscountListRequest.builder()
        .size(50)
        .build()
);

for (FacilityDiscount discount : page.items()) {
    System.out.println(discount.id().value() + " — " + discount.type());
}
```

Iterate through all discounts automatically:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscount;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscountListRequest;

Iterable<FacilityDiscount> allDiscounts = client.facilityDiscounts().listAll(
    FacilityId.builder().value("fac-001").build(),
    FacilityDiscountListRequest.builder()
        .size(100)
        .build()
);

for (FacilityDiscount discount : allDiscounts) {
    System.out.println(discount.id().value() + " — " + discount.type());
}
```

## Discount Values

Every discount has a `FacilityDiscountValue` that is either relative (percentage) or absolute (fixed amount). Use the static factory methods to construct them.

### Relative discount (percentage)

```java
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscountValue;

FacilityDiscountValue value = FacilityDiscountValue.Relative.of(10.0);
```

### Absolute discount (fixed amount)

```java
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscountValue;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscountAbsoluteElement;
import java.util.List;

FacilityDiscountValue value = FacilityDiscountValue.Absolute.of(List.of(
    FacilityDiscountAbsoluteElement.builder()
        .value(500)
        .currency("EUR")
        .decimalPlaces(2)
        .build()
));
```

## Creating a Facility Discount

`type`, `priority`, and `discount` are required:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscount;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.CreateFacilityDiscountRequest;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscountValue;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

try {
    FacilityDiscount discount = client.facilityDiscounts().create(
        FacilityId.builder().value("fac-001").build(),
        CreateFacilityDiscountRequest.builder()
            .type("DELIVERY_COST_DISCOUNT")
            .priority(1)
            .discount(FacilityDiscountValue.Relative.of(10.0))
            .build()
    );
    System.out.println("Created: " + discount.id().value());
} catch (FulfillmenttoolsException e) {
    System.out.println("Creation failed: " + e.getMessage());
}
```

### With context conditions

`context` scopes the discount to specific order types or delivery services:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscount;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.CreateFacilityDiscountRequest;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscountValue;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscountContext;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;
import java.util.List;

try {
    FacilityDiscount discount = client.facilityDiscounts().create(
        FacilityId.builder().value("fac-001").build(),
        CreateFacilityDiscountRequest.builder()
            .type("DELIVERY_COST_DISCOUNT")
            .priority(1)
            .discount(FacilityDiscountValue.Relative.of(10.0))
            .context(List.of(
                FacilityDiscountContext.of("ORDER_TYPE", List.of("CLICK_AND_COLLECT"))
            ))
            .build()
    );
    System.out.println("Created: " + discount.id().value());
} catch (FulfillmenttoolsException e) {
    System.out.println("Creation failed: " + e.getMessage());
}
```

### Absolute discount with context

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscount;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.CreateFacilityDiscountRequest;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscountValue;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscountAbsoluteElement;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscountContext;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;
import java.util.List;

try {
    FacilityDiscount discount = client.facilityDiscounts().create(
        FacilityId.builder().value("fac-001").build(),
        CreateFacilityDiscountRequest.builder()
            .type("DELIVERY_COST_DISCOUNT")
            .priority(2)
            .discount(FacilityDiscountValue.Absolute.of(List.of(
                FacilityDiscountAbsoluteElement.builder()
                    .value(500)
                    .currency("EUR")
                    .decimalPlaces(2)
                    .build(),
                FacilityDiscountAbsoluteElement.builder()
                    .value(499)
                    .currency("CHF")
                    .decimalPlaces(2)
                    .build()
            )))
            .context(List.of(
                FacilityDiscountContext.of("DELIVERY_SERVICE", List.of("EXPRESS"))
            ))
            .build()
    );
    System.out.println("Created: " + discount.id().value());
} catch (FulfillmenttoolsException e) {
    System.out.println("Creation failed: " + e.getMessage());
}
```

## Updating a Facility Discount

`version` is required for optimistic locking. All other fields are optional:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.FacilityDiscountId;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscount;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.UpdateFacilityDiscountRequest;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscountValue;

FacilityDiscount discount = client.facilityDiscounts().get(
    FacilityId.builder().value("fac-001").build(),
    FacilityDiscountId.builder().value("fdis-001").build()
);

FacilityDiscount updated = client.facilityDiscounts().update(
    FacilityId.builder().value("fac-001").build(),
    FacilityDiscountId.builder().value("fdis-001").build(),
    UpdateFacilityDiscountRequest.builder()
        .version(discount.version())
        .priority(5)
        .discount(FacilityDiscountValue.Relative.of(15.0))
        .build()
);
System.out.println("Updated priority: " + updated.priority());
```

## Deleting a Facility Discount

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.FacilityDiscountId;

client.facilityDiscounts().delete(
    FacilityId.builder().value("fac-001").build(),
    FacilityDiscountId.builder().value("fdis-001").build()
);
```

## Async Usage

All methods have async variants returning `CompletableFuture`:

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.FacilityDiscountId;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscount;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

CompletableFuture<FacilityDiscount> future = client.facilityDiscounts().getAsync(
    FacilityId.builder().value("fac-001").build(),
    FacilityDiscountId.builder().value("fdis-001").build()
);

future.whenComplete((discount, ex) -> {
    if (ex != null) {
        Throwable cause = ex instanceof CompletionException && ex.getCause() != null
            ? ex.getCause() : ex;
        System.out.println("Error: " + cause.getMessage());
    } else {
        System.out.println("Type: " + discount.type());
        System.out.println("Priority: " + discount.priority());
    }
});
```

## API Reference

### get(FacilityId, FacilityDiscountId)

Get a facility discount by ID.

**Parameters:**
- `facilityId: FacilityId` — The facility identifier
- `discountId: FacilityDiscountId` — The discount identifier

**Returns:** `FacilityDiscount`

**Throws:** `NotFoundException` (404), `FulfillmenttoolsException` if the request fails

### getAsync(FacilityId, FacilityDiscountId)

Get a facility discount by ID asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The facility identifier
- `discountId: FacilityDiscountId` — The discount identifier

**Returns:** `CompletableFuture<FacilityDiscount>`

### list(FacilityId, FacilityDiscountListRequest)

List facility discounts for a facility with pagination.

**Parameters:**
- `facilityId: FacilityId` — The facility identifier
- `request: FacilityDiscountListRequest` — List request with `size` and `startAfterId` cursor

**Returns:** `Page<FacilityDiscount>`

**Throws:** `FulfillmenttoolsException` if the request fails

### listAsync(FacilityId, FacilityDiscountListRequest)

List facility discounts asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The facility identifier
- `request: FacilityDiscountListRequest` — List request

**Returns:** `CompletableFuture<Page<FacilityDiscount>>`

### listAll(FacilityId, FacilityDiscountListRequest)

List all facility discounts, automatically iterating through pages.

**Parameters:**
- `facilityId: FacilityId` — The facility identifier
- `request: FacilityDiscountListRequest` — List request

**Returns:** `Iterable<FacilityDiscount>`

### create(FacilityId, CreateFacilityDiscountRequest)

Create a new facility discount. `type`, `priority`, and `discount` are required.

**Parameters:**
- `facilityId: FacilityId` — The facility identifier
- `request: CreateFacilityDiscountRequest` — Creation request. Optional field: `context`

**Returns:** `FacilityDiscount`

**Throws:** `FulfillmenttoolsException` if the request fails

### createAsync(FacilityId, CreateFacilityDiscountRequest)

Create a facility discount asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The facility identifier
- `request: CreateFacilityDiscountRequest` — Creation request

**Returns:** `CompletableFuture<FacilityDiscount>`

### update(FacilityId, FacilityDiscountId, UpdateFacilityDiscountRequest)

Update an existing facility discount. `version` is required for optimistic locking. Updatable fields: `type`, `priority`, `discount`, `context`.

**Parameters:**
- `facilityId: FacilityId` — The facility identifier
- `discountId: FacilityDiscountId` — The discount identifier
- `request: UpdateFacilityDiscountRequest` — Update request with current version and new values

**Returns:** `FacilityDiscount`

**Throws:** `FulfillmenttoolsException` if the request fails or a version conflict occurs

### updateAsync(FacilityId, FacilityDiscountId, UpdateFacilityDiscountRequest)

Update a facility discount asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The facility identifier
- `discountId: FacilityDiscountId` — The discount identifier
- `request: UpdateFacilityDiscountRequest` — Update request

**Returns:** `CompletableFuture<FacilityDiscount>`

### delete(FacilityId, FacilityDiscountId)

Delete a facility discount.

**Parameters:**
- `facilityId: FacilityId` — The facility identifier
- `discountId: FacilityDiscountId` — The discount identifier

**Returns:** `void`

**Throws:** `FulfillmenttoolsException` if the request fails

### deleteAsync(FacilityId, FacilityDiscountId)

Delete a facility discount asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The facility identifier
- `discountId: FacilityDiscountId` — The discount identifier

**Returns:** `CompletableFuture<Void>`

## FacilityDiscountValue Variants

| Variant | Factory | Type discriminator |
|---------|---------|-------------------|
| `FacilityDiscountValue.Relative` | `Relative.of(double value)` | `"RELATIVE"` |
| `FacilityDiscountValue.Absolute` | `Absolute.of(List<FacilityDiscountAbsoluteElement>)` | `"ABSOLUTE"` |
