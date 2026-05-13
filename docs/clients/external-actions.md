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

## API Reference

### get(ExternalActionId)

Get an external action by ID.

**Returns:** `ExternalAction`

### list(ExternalActionListRequest)

List external actions with pagination.

**Returns:** `Page<ExternalAction>`

### listAll(ExternalActionListRequest)

List all external actions.

**Returns:** `Iterable<ExternalAction>`
