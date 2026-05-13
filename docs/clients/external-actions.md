# External Actions Client

The External Actions client manages external actions and integrations. Configure and manage external system integrations in your fulfillment workflow.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.ExternalActionId;

// Get an external action
ExternalAction action = client.externalActions().get(new ExternalActionId("eact-001"));
System.out.println("Name: " + action.getName());
```

## Listing External Actions

```java
Page<ExternalAction> page = client.externalActions().list(
    ExternalActionListRequest.builder()
        .size(50)
        .build()
);
```

## Creating and Managing External Actions

```java
ExternalAction action = client.externalActions().create(
    CreateExternalActionRequest.builder()
        .name("My Integration")
        .type("WEBHOOK")
        .build()
);

// Update an external action
ExternalAction updated = client.externalActions().update(
    new ExternalActionId("eact-001"),
    UpdateExternalActionRequest.builder()
        .name("Updated Integration")
        .build()
);
```

## API Reference

### get(ExternalActionId)

Get an external action by ID.

**Parameters:**
- `externalActionId: ExternalActionId` — The external action identifier

**Returns:** `ExternalAction`

### getAsync(ExternalActionId)

Get an external action asynchronously.

**Parameters:**
- `externalActionId: ExternalActionId` — The external action identifier

**Returns:** `CompletableFuture<ExternalAction>`

### list(ExternalActionListRequest)

List external actions with pagination.

**Parameters:**
- `request: ExternalActionListRequest` — List request with pagination

**Returns:** `Page<ExternalAction>`

### listAsync(ExternalActionListRequest)

List external actions asynchronously.

**Parameters:**
- `request: ExternalActionListRequest` — List request with pagination

**Returns:** `CompletableFuture<Page<ExternalAction>>`

### listAll(ExternalActionListRequest)

List all external actions, automatically iterating through pages.

**Parameters:**
- `request: ExternalActionListRequest` — List request

**Returns:** `Iterable<ExternalAction>`

### create(CreateExternalActionRequest)

Create a new external action.

**Parameters:**
- `request: CreateExternalActionRequest` — Creation request

**Returns:** `ExternalAction`

### createAsync(CreateExternalActionRequest)

Create an external action asynchronously.

**Parameters:**
- `request: CreateExternalActionRequest` — Creation request

**Returns:** `CompletableFuture<ExternalAction>`

### update(ExternalActionId, UpdateExternalActionRequest)

Update an existing external action.

**Parameters:**
- `externalActionId: ExternalActionId` — The external action identifier
- `request: UpdateExternalActionRequest` — Update request

**Returns:** `ExternalAction`

### updateAsync(ExternalActionId, UpdateExternalActionRequest)

Update an external action asynchronously.

**Parameters:**
- `externalActionId: ExternalActionId` — The external action identifier
- `request: UpdateExternalActionRequest` — Update request

**Returns:** `CompletableFuture<ExternalAction>`
