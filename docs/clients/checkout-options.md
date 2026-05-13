# Checkout Options Client

The Checkout Options client manages checkout configurations. Define and manage checkout behavior in your fulfillment system.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.CheckoutOptionId;

// Get a checkout option
CheckoutOption option = client.checkoutOptions().get(new CheckoutOptionId("chopt-001"));
System.out.println("Name: " + option.getName());
```

## Listing Checkout Options

```java
Page<CheckoutOption> page = client.checkoutOptions().list(
    CheckoutOptionListRequest.builder()
        .size(50)
        .build()
);
```

## API Reference

### get(CheckoutOptionId)

Get a checkout option by ID.

**Returns:** `CheckoutOption`

### list(CheckoutOptionListRequest)

List checkout options with pagination.

**Returns:** `Page<CheckoutOption>`

### listAll(CheckoutOptionListRequest)

List all checkout options.

**Returns:** `Iterable<CheckoutOption>`
