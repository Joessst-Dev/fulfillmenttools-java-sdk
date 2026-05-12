package de.joesst.dev.fulfillmenttools.eventing;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

public interface EventingClient {

    Subscription get(String subscriptionId);
    CompletableFuture<Subscription> getAsync(String subscriptionId);

    Page<Subscription> list(SubscriptionListRequest request);
    CompletableFuture<Page<Subscription>> listAsync(SubscriptionListRequest request);

    Iterable<Subscription> listAll(SubscriptionListRequest request);

    Subscription create(CreateSubscriptionRequest request);
    CompletableFuture<Subscription> createAsync(CreateSubscriptionRequest request);

    Subscription update(String subscriptionId, UpdateSubscriptionRequest request);
    CompletableFuture<Subscription> updateAsync(String subscriptionId, UpdateSubscriptionRequest request);

    void delete(String subscriptionId);
    CompletableFuture<Void> deleteAsync(String subscriptionId);
}
