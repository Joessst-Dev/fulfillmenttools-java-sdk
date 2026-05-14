# Eventing Client

The Eventing client manages event subscriptions — configure real-time notifications for fulfillment operations delivered to a webhook, Azure Service Bus, or Google Cloud Pub/Sub.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.SubscriptionId;
import de.joesst.dev.fulfillmenttools.eventing.Subscription;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

try {
    Subscription subscription = client.eventing().get(
        SubscriptionId.builder().value("sub-001").build()
    );
    System.out.println("Name: " + subscription.name());
    System.out.println("Event: " + subscription.event());
    System.out.println("Created: " + subscription.created());
} catch (NotFoundException e) {
    System.out.println("Subscription not found");
} catch (FulfillmenttoolsException e) {
    System.out.println("Request failed: " + e.getMessage());
}
```

## Listing Subscriptions

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.eventing.Subscription;
import de.joesst.dev.fulfillmenttools.eventing.SubscriptionListRequest;

Page<Subscription> page = client.eventing().list(
    SubscriptionListRequest.builder()
        .size(50)
        .build()
);

for (Subscription subscription : page.items()) {
    System.out.println(subscription.id().value() + " — " + subscription.event());
}
```

Iterate through all subscriptions automatically:

```java
import de.joesst.dev.fulfillmenttools.eventing.Subscription;
import de.joesst.dev.fulfillmenttools.eventing.SubscriptionListRequest;

Iterable<Subscription> allSubscriptions = client.eventing().listAll(
    SubscriptionListRequest.builder()
        .size(100)
        .build()
);

for (Subscription subscription : allSubscriptions) {
    System.out.println(subscription.id().value() + " — " + subscription.name());
}
```

## Creating a Subscription

`name` and `event` are required. Use `target` with a `WebhookTarget` to deliver events to an HTTP endpoint:

```java
import de.joesst.dev.fulfillmenttools.eventing.Subscription;
import de.joesst.dev.fulfillmenttools.eventing.CreateSubscriptionRequest;
import de.joesst.dev.fulfillmenttools.eventing.WebhookTarget;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

try {
    Subscription subscription = client.eventing().create(
        CreateSubscriptionRequest.builder()
            .name("order-hook")
            .event("ORDER_CREATED")
            .target(
                WebhookTarget.builder()
                    .type("WEBHOOK")
                    .callbackUrl("https://example.com/webhook")
                    .build()
            )
            .build()
    );
    System.out.println("Created subscription: " + subscription.id().value());
} catch (FulfillmenttoolsException e) {
    System.out.println("Creation failed: " + e.getMessage());
}
```

### Azure Service Bus target

```java
import de.joesst.dev.fulfillmenttools.eventing.Subscription;
import de.joesst.dev.fulfillmenttools.eventing.CreateSubscriptionRequest;
import de.joesst.dev.fulfillmenttools.eventing.AzureServiceBusTarget;

Subscription subscription = client.eventing().create(
    CreateSubscriptionRequest.builder()
        .name("order-service-bus")
        .event("ORDER_CREATED")
        .target(
            AzureServiceBusTarget.builder()
                .type("MICROSOFT_AZURE_SERVICE_BUS")
                .tenantId("my-tenant-id")
                .clientId("my-client-id")
                .clientSecret("my-client-secret")
                .namespace("my-namespace")
                .queueOrTopicName("my-queue")
                .build()
        )
        .build()
);
```

## Updating a Subscription

```java
import de.joesst.dev.fulfillmenttools.id.SubscriptionId;
import de.joesst.dev.fulfillmenttools.eventing.Subscription;
import de.joesst.dev.fulfillmenttools.eventing.UpdateSubscriptionRequest;

Subscription updated = client.eventing().update(
    SubscriptionId.builder().value("sub-001").build(),
    UpdateSubscriptionRequest.builder()
        .callbackUrl("https://example.com/webhook-v2")
        .build()
);
System.out.println("Updated callback URL: " + updated.callbackUrl());
```

## Deleting a Subscription

```java
import de.joesst.dev.fulfillmenttools.id.SubscriptionId;

client.eventing().delete(
    SubscriptionId.builder().value("sub-001").build()
);
```

## Async Usage

All methods have async variants returning `CompletableFuture`:

```java
import de.joesst.dev.fulfillmenttools.id.SubscriptionId;
import de.joesst.dev.fulfillmenttools.eventing.Subscription;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

CompletableFuture<Subscription> future = client.eventing().getAsync(
    SubscriptionId.builder().value("sub-001").build()
);

future.whenComplete((subscription, ex) -> {
    if (ex != null) {
        Throwable cause = ex instanceof CompletionException && ex.getCause() != null
            ? ex.getCause() : ex;
        System.out.println("Error: " + cause.getMessage());
    } else {
        System.out.println("Name: " + subscription.name());
        System.out.println("Event: " + subscription.event());
    }
});
```

## API Reference

### get(SubscriptionId)

Get a subscription by ID.

**Parameters:**
- `subscriptionId: SubscriptionId` — The subscription identifier

**Returns:** `Subscription`

**Throws:** `NotFoundException` (404), `FulfillmenttoolsException` if the request fails

### getAsync(SubscriptionId)

Get a subscription by ID asynchronously.

**Parameters:**
- `subscriptionId: SubscriptionId` — The subscription identifier

**Returns:** `CompletableFuture<Subscription>`

### list(SubscriptionListRequest)

List subscriptions with pagination.

**Parameters:**
- `request: SubscriptionListRequest` — List request with `size` and `startAfterId` cursor

**Returns:** `Page<Subscription>`

**Throws:** `FulfillmenttoolsException` if the request fails

### listAsync(SubscriptionListRequest)

List subscriptions asynchronously.

**Parameters:**
- `request: SubscriptionListRequest` — List request

**Returns:** `CompletableFuture<Page<Subscription>>`

### listAll(SubscriptionListRequest)

List all subscriptions, automatically iterating through pages.

**Parameters:**
- `request: SubscriptionListRequest` — List request

**Returns:** `Iterable<Subscription>`

### create(CreateSubscriptionRequest)

Create a new subscription. `name` and `event` are required.

**Parameters:**
- `request: CreateSubscriptionRequest` — Creation request

**Returns:** `Subscription`

**Throws:** `FulfillmenttoolsException` if the request fails

### createAsync(CreateSubscriptionRequest)

Create a subscription asynchronously.

**Parameters:**
- `request: CreateSubscriptionRequest` — Creation request

**Returns:** `CompletableFuture<Subscription>`

### update(SubscriptionId, UpdateSubscriptionRequest)

Update an existing subscription. Updatable fields: `callbackUrl`, `status`.

**Parameters:**
- `subscriptionId: SubscriptionId` — The subscription identifier
- `request: UpdateSubscriptionRequest` — Update request

**Returns:** `Subscription`

**Throws:** `FulfillmenttoolsException` if the request fails

### updateAsync(SubscriptionId, UpdateSubscriptionRequest)

Update a subscription asynchronously.

**Parameters:**
- `subscriptionId: SubscriptionId` — The subscription identifier
- `request: UpdateSubscriptionRequest` — Update request

**Returns:** `CompletableFuture<Subscription>`

### delete(SubscriptionId)

Delete a subscription.

**Parameters:**
- `subscriptionId: SubscriptionId` — The subscription identifier

**Returns:** `void`

**Throws:** `FulfillmenttoolsException` if the request fails

### deleteAsync(SubscriptionId)

Delete a subscription asynchronously.

**Parameters:**
- `subscriptionId: SubscriptionId` — The subscription identifier

**Returns:** `CompletableFuture<Void>`
