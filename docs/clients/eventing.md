# Eventing Client

The Eventing client manages event subscriptions and webhooks. Configure real-time event notifications for your fulfillment operations.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.SubscriptionId;

// Get a subscription
Subscription subscription = client.eventing().get(new SubscriptionId("sub-001"));
System.out.println("Topic: " + subscription.getTopic());
```

## Listing Subscriptions

```java
Page<Subscription> page = client.eventing().list(
    SubscriptionListRequest.builder()
        .size(50)
        .build()
);
```

## Creating and Managing Subscriptions

```java
Subscription subscription = client.eventing().create(
    CreateSubscriptionRequest.builder()
        .topic("orders.created")
        .callbackUrl("https://example.com/webhook")
        .build()
);

// Update a subscription
Subscription updated = client.eventing().update(
    new SubscriptionId("sub-001"),
    UpdateSubscriptionRequest.builder()
        .callbackUrl("https://example.com/webhook-v2")
        .build()
);

// Delete a subscription
client.eventing().delete(new SubscriptionId("sub-001"));
```

## API Reference

### get(SubscriptionId)

Get a subscription by ID.

**Parameters:**
- `subscriptionId: SubscriptionId` — The subscription identifier

**Returns:** `Subscription`

### getAsync(SubscriptionId)

Get a subscription asynchronously.

**Parameters:**
- `subscriptionId: SubscriptionId` — The subscription identifier

**Returns:** `CompletableFuture<Subscription>`

### list(SubscriptionListRequest)

List subscriptions with pagination.

**Parameters:**
- `request: SubscriptionListRequest` — List request with pagination

**Returns:** `Page<Subscription>`

### listAsync(SubscriptionListRequest)

List subscriptions asynchronously.

**Parameters:**
- `request: SubscriptionListRequest` — List request with pagination

**Returns:** `CompletableFuture<Page<Subscription>>`

### listAll(SubscriptionListRequest)

List all subscriptions, automatically iterating through pages.

**Parameters:**
- `request: SubscriptionListRequest` — List request

**Returns:** `Iterable<Subscription>`

### create(CreateSubscriptionRequest)

Create a new subscription.

**Parameters:**
- `request: CreateSubscriptionRequest` — Creation request

**Returns:** `Subscription`

### createAsync(CreateSubscriptionRequest)

Create a subscription asynchronously.

**Parameters:**
- `request: CreateSubscriptionRequest` — Creation request

**Returns:** `CompletableFuture<Subscription>`

### update(SubscriptionId, UpdateSubscriptionRequest)

Update an existing subscription.

**Parameters:**
- `subscriptionId: SubscriptionId` — The subscription identifier
- `request: UpdateSubscriptionRequest` — Update request

**Returns:** `Subscription`

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

### deleteAsync(SubscriptionId)

Delete a subscription asynchronously.

**Parameters:**
- `subscriptionId: SubscriptionId` — The subscription identifier

**Returns:** `CompletableFuture<Void>`
