# Facility Discounts Client

The Facility Discounts client manages facility-level discount configurations. Define and manage pricing discounts by facility.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.FacilityDiscountId;

// Get a facility discount
FacilityDiscount discount = client.facilityDiscounts().get(new FacilityDiscountId("fdis-001"));
System.out.println("Percentage: " + discount.getPercentage());
```

## Listing Facility Discounts

```java
Page<FacilityDiscount> page = client.facilityDiscounts().list(
    FacilityDiscountListRequest.builder()
        .size(50)
        .build()
);
```

## API Reference

### get(FacilityDiscountId)

Get a facility discount by ID.

**Returns:** `FacilityDiscount`

### list(FacilityDiscountListRequest)

List facility discounts with pagination.

**Returns:** `Page<FacilityDiscount>`

### listAll(FacilityDiscountListRequest)

List all facility discounts.

**Returns:** `Iterable<FacilityDiscount>`
