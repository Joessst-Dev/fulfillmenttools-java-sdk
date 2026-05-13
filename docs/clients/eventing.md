# Eventing Client

The Eventing client manages event subscriptions and webhooks. Configure real-time event notifications for your fulfillment operations.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.EventSubscriptionId;

// Get an event subscription
EventSubscription subscription = client.eventing().get(new EventSubscriptionId("esub-001"));
System.out.println("Topic: " + subscription.getTopic());
```

## Listing Event Subscriptions

```java
Page<EventSubscription> page = client.eventing().list(
    EventSubscriptionListRequest.builder()
        .size(50)
        .build()
);
```

## API Reference

### get(EventSubscriptionId)

Get an event subscription by ID.

**Returns:** `EventSubscription`

### list(EventSubscriptionListRequest)

List event subscriptions with pagination.

**Returns:** `Page<EventSubscription>`

### listAll(EventSubscriptionListRequest)

List all event subscriptions.

**Returns:** `Iterable<EventSubscription>`
