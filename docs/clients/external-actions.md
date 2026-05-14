# External Actions Client

The External Actions client manages external actions — configurable integrations that appear in the fulfillmenttools UI and trigger link openings, form submissions, or comment prompts within the context of a process.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.ExternalActionId;
import de.joesst.dev.fulfillmenttools.externalactions.ExternalAction;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

try {
    ExternalAction action = client.externalActions().get(
        ExternalActionId.builder().value("eact-001").build()
    );
    System.out.println("Name: " + action.name());
    System.out.println("Process: " + action.processRef().value());
    System.out.println("Groups: " + action.groups());
} catch (NotFoundException e) {
    System.out.println("External action not found");
} catch (FulfillmenttoolsException e) {
    System.out.println("Request failed: " + e.getMessage());
}
```

## Listing External Actions

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.externalactions.ExternalAction;
import de.joesst.dev.fulfillmenttools.externalactions.ExternalActionListRequest;

Page<ExternalAction> page = client.externalActions().list(
    ExternalActionListRequest.builder()
        .size(50)
        .build()
);

for (ExternalAction action : page.items()) {
    System.out.println(action.id().value() + " — " + action.name());
}
```

Filter by process reference or groups:

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.externalactions.ExternalAction;
import de.joesst.dev.fulfillmenttools.externalactions.ExternalActionListRequest;
import java.util.List;

Page<ExternalAction> page = client.externalActions().list(
    ExternalActionListRequest.builder()
        .processRef("proc-001")
        .groups(List.of("warehouse"))
        .build()
);
```

Iterate through all external actions automatically:

```java
import de.joesst.dev.fulfillmenttools.externalactions.ExternalAction;
import de.joesst.dev.fulfillmenttools.externalactions.ExternalActionListRequest;

Iterable<ExternalAction> allActions = client.externalActions().listAll(
    ExternalActionListRequest.builder()
        .size(100)
        .build()
);

for (ExternalAction action : allActions) {
    System.out.println(action.id().value() + " — " + action.name());
}
```

## Creating an External Action

`processRef`, `nameLocalized`, `groups`, and `action` are required:

```java
import de.joesst.dev.fulfillmenttools.externalactions.ExternalAction;
import de.joesst.dev.fulfillmenttools.externalactions.CreateExternalActionRequest;
import de.joesst.dev.fulfillmenttools.externalactions.ExternalLinkActionDefinition;
import de.joesst.dev.fulfillmenttools.externalactions.ExternalActionType;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;
import java.util.List;
import java.util.Map;

try {
    ExternalAction action = client.externalActions().create(
        CreateExternalActionRequest.builder()
            .processRef("proc-001")
            .nameLocalized(Map.of("en_US", "Notify Warehouse"))
            .groups(List.of("warehouse"))
            .action(
                ExternalLinkActionDefinition.builder()
                    .type(ExternalActionType.BLANK_LINK)
                    .linkUrl("https://example.com/notify")
                    .build()
            )
            .build()
    );
    System.out.println("Created: " + action.id().value());
} catch (FulfillmenttoolsException e) {
    System.out.println("Creation failed: " + e.getMessage());
}
```

## Updating an External Action

`version`, `nameLocalized`, `groups`, and `action` are required. This is a full replacement (PUT):

```java
import de.joesst.dev.fulfillmenttools.id.ExternalActionId;
import de.joesst.dev.fulfillmenttools.externalactions.ExternalAction;
import de.joesst.dev.fulfillmenttools.externalactions.UpdateExternalActionRequest;
import de.joesst.dev.fulfillmenttools.externalactions.ExternalLinkActionDefinition;
import de.joesst.dev.fulfillmenttools.externalactions.ExternalActionType;
import java.util.List;
import java.util.Map;

ExternalAction action = client.externalActions().get(
    ExternalActionId.builder().value("eact-001").build()
);

ExternalAction updated = client.externalActions().update(
    ExternalActionId.builder().value("eact-001").build(),
    UpdateExternalActionRequest.builder()
        .version(action.version())
        .nameLocalized(Map.of("en_US", "Updated Warehouse Notification"))
        .groups(List.of("warehouse"))
        .action(
            ExternalLinkActionDefinition.builder()
                .type(ExternalActionType.BLANK_LINK)
                .linkUrl("https://example.com/notify-v2")
                .build()
        )
        .build()
);
System.out.println("Updated name: " + updated.name());
```

## Async Usage

All methods have async variants returning `CompletableFuture`:

```java
import de.joesst.dev.fulfillmenttools.id.ExternalActionId;
import de.joesst.dev.fulfillmenttools.externalactions.ExternalAction;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

CompletableFuture<ExternalAction> future = client.externalActions().getAsync(
    ExternalActionId.builder().value("eact-001").build()
);

future.whenComplete((action, ex) -> {
    if (ex != null) {
        Throwable cause = ex instanceof CompletionException && ex.getCause() != null
            ? ex.getCause() : ex;
        System.out.println("Error: " + cause.getMessage());
    } else {
        System.out.println("Name: " + action.name());
        System.out.println("Groups: " + action.groups());
    }
});
```

## API Reference

### get(ExternalActionId)

Get an external action by ID.

**Parameters:**
- `externalActionId: ExternalActionId` — The external action identifier

**Returns:** `ExternalAction`

**Throws:** `NotFoundException` (404), `FulfillmenttoolsException` if the request fails

### getAsync(ExternalActionId)

Get an external action by ID asynchronously.

**Parameters:**
- `externalActionId: ExternalActionId` — The external action identifier

**Returns:** `CompletableFuture<ExternalAction>`

### list(ExternalActionListRequest)

List external actions with pagination. Filters include `processRef` and `groups`.

**Parameters:**
- `request: ExternalActionListRequest` — List request with `size`, `startAfterId` cursor, and optional filters

**Returns:** `Page<ExternalAction>`

**Throws:** `FulfillmenttoolsException` if the request fails

### listAsync(ExternalActionListRequest)

List external actions asynchronously.

**Parameters:**
- `request: ExternalActionListRequest` — List request

**Returns:** `CompletableFuture<Page<ExternalAction>>`

### listAll(ExternalActionListRequest)

List all external actions, automatically iterating through pages.

**Parameters:**
- `request: ExternalActionListRequest` — List request

**Returns:** `Iterable<ExternalAction>`

### create(CreateExternalActionRequest)

Create a new external action. `processRef`, `nameLocalized`, `groups`, and `action` are required.

**Parameters:**
- `request: CreateExternalActionRequest` — Creation request

**Returns:** `ExternalAction`

**Throws:** `FulfillmenttoolsException` if the request fails

### createAsync(CreateExternalActionRequest)

Create an external action asynchronously.

**Parameters:**
- `request: CreateExternalActionRequest` — Creation request

**Returns:** `CompletableFuture<ExternalAction>`

### update(ExternalActionId, UpdateExternalActionRequest)

Replace an existing external action (full PUT). `version`, `nameLocalized`, `groups`, and `action` are required.

**Parameters:**
- `externalActionId: ExternalActionId` — The external action identifier
- `request: UpdateExternalActionRequest` — Replacement request with current version and all fields

**Returns:** `ExternalAction`

**Throws:** `FulfillmenttoolsException` if the request fails or a version conflict occurs

### updateAsync(ExternalActionId, UpdateExternalActionRequest)

Replace an external action asynchronously.

**Parameters:**
- `externalActionId: ExternalActionId` — The external action identifier
- `request: UpdateExternalActionRequest` — Replacement request

**Returns:** `CompletableFuture<ExternalAction>`
