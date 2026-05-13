# Sourcing Options Client

The Sourcing Options client manages sourcing configurations. Define where orders can be sourced from within your network.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.SourcingOptionId;

// Get a sourcing option
SourcingOption option = client.sourcingOptions().get(new SourcingOptionId("sopt-001"));
System.out.println("Name: " + option.getName());
```

## Listing Sourcing Options

```java
Page<SourcingOption> page = client.sourcingOptions().list(
    SourcingOptionListRequest.builder()
        .size(50)
        .build()
);
```

## API Reference

### get(SourcingOptionId)

Get a sourcing option by ID.

**Returns:** `SourcingOption`

### list(SourcingOptionListRequest)

List sourcing options with pagination.

**Returns:** `Page<SourcingOption>`

### listAll(SourcingOptionListRequest)

List all sourcing options.

**Returns:** `Iterable<SourcingOption>`
