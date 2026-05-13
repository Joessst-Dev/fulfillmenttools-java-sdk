# Returns Client

The Returns client provides access to return management operations. Handle and track product returns through your fulfillment network.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.ReturnId;

// Get a return
Return returnOrder = client.returns().get(new ReturnId("ret-001"));
System.out.println("Status: " + returnOrder.getStatus());
```

## Listing Returns

```java
Page<Return> page = client.returns().list(
    ReturnListRequest.builder()
        .size(50)
        .build()
);
```

Iterate through all returns:

```java
Iterable<Return> allReturns = client.returns().listAll(
    ReturnListRequest.builder()
        .size(100)
        .build()
);
```

## API Reference

### get(ReturnId)

Get a return by ID.

**Returns:** `Return`

### list(ReturnListRequest)

List returns with pagination.

**Returns:** `Page<Return>`

### listAll(ReturnListRequest)

List all returns.

**Returns:** `Iterable<Return>`
