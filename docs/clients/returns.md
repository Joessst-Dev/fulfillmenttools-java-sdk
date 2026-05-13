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

**Parameters:**
- `returnId: ReturnId` — The return identifier

**Returns:** `Return`

**Throws:** `FulfillmenttoolsException` on request failure

### getAsync(ReturnId)

Get a return by ID asynchronously.

**Parameters:**
- `returnId: ReturnId` — The return identifier

**Returns:** `CompletableFuture<Return>`

### list(ReturnListRequest)

List returns with pagination.

**Parameters:**
- `request: ReturnListRequest` — List request with filter and pagination parameters

**Returns:** `Page<Return>`

**Throws:** `FulfillmenttoolsException` on request failure

### listAsync(ReturnListRequest)

List returns asynchronously.

**Parameters:**
- `request: ReturnListRequest` — List request

**Returns:** `CompletableFuture<Page<Return>>`

### listAll(ReturnListRequest)

List all returns, automatically iterating through pages.

**Parameters:**
- `request: ReturnListRequest` — List request

**Returns:** `Iterable<Return>`

### create(CreateReturnRequest)

Create a new return.

**Parameters:**
- `request: CreateReturnRequest` — Create return request

**Returns:** `Return`

**Throws:** `FulfillmenttoolsException` on request failure

### createAsync(CreateReturnRequest)

Create a new return asynchronously.

**Parameters:**
- `request: CreateReturnRequest` — Create return request

**Returns:** `CompletableFuture<Return>`

### start(ReturnId, int)

Start a return process.

**Parameters:**
- `returnId: ReturnId` — The return identifier
- `version: int` — Current version of the return (for optimistic locking)

**Returns:** `Return`

**Throws:** `FulfillmenttoolsException` on request failure or version conflict

### startAsync(ReturnId, int)

Start a return process asynchronously.

**Parameters:**
- `returnId: ReturnId` — The return identifier
- `version: int` — Current version of the return

**Returns:** `CompletableFuture<Return>`

### finish(ReturnId, int)

Finish a return process.

**Parameters:**
- `returnId: ReturnId` — The return identifier
- `version: int` — Current version of the return (for optimistic locking)

**Returns:** `Return`

**Throws:** `FulfillmenttoolsException` on request failure or version conflict

### finishAsync(ReturnId, int)

Finish a return process asynchronously.

**Parameters:**
- `returnId: ReturnId` — The return identifier
- `version: int` — Current version of the return

**Returns:** `CompletableFuture<Return>`

### restart(ReturnId, int)

Restart a return process.

**Parameters:**
- `returnId: ReturnId` — The return identifier
- `version: int` — Current version of the return (for optimistic locking)

**Returns:** `Return`

**Throws:** `FulfillmenttoolsException` on request failure or version conflict

### restartAsync(ReturnId, int)

Restart a return process asynchronously.

**Parameters:**
- `returnId: ReturnId` — The return identifier
- `version: int` — Current version of the return

**Returns:** `CompletableFuture<Return>`
