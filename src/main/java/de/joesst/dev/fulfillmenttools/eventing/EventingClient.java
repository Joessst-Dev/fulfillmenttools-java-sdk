package de.joesst.dev.fulfillmenttools.eventing;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

/**
 * Client for managing event subscriptions in the fulfillmenttools platform.
 */
public interface EventingClient {

    /**
     * Retrieves a subscription by ID.
     * @param subscriptionId the subscription identifier
     * @return the subscription
     */
    Subscription get(String subscriptionId);

    /**
     * Asynchronously retrieves a subscription by ID.
     * @param subscriptionId the subscription identifier
     * @return a future containing the subscription
     */
    CompletableFuture<Subscription> getAsync(String subscriptionId);

    /**
     * Lists subscriptions with pagination.
     * @param request the list request with pagination parameters
     * @return a page of subscriptions
     */
    Page<Subscription> list(SubscriptionListRequest request);

    /**
     * Asynchronously lists subscriptions with pagination.
     * @param request the list request with pagination parameters
     * @return a future containing a page of subscriptions
     */
    CompletableFuture<Page<Subscription>> listAsync(SubscriptionListRequest request);

    /**
     * Lists all subscriptions, automatically handling pagination.
     * @param request the list request parameters
     * @return an iterable of all subscriptions
     */
    Iterable<Subscription> listAll(SubscriptionListRequest request);

    /**
     * Creates a new subscription.
     * @param request the subscription creation request
     * @return the created subscription
     */
    Subscription create(CreateSubscriptionRequest request);

    /**
     * Asynchronously creates a new subscription.
     * @param request the subscription creation request
     * @return a future containing the created subscription
     */
    CompletableFuture<Subscription> createAsync(CreateSubscriptionRequest request);

    /**
     * Updates an existing subscription.
     * @param subscriptionId the subscription identifier
     * @param request the subscription update request
     * @return the updated subscription
     */
    Subscription update(String subscriptionId, UpdateSubscriptionRequest request);

    /**
     * Asynchronously updates an existing subscription.
     * @param subscriptionId the subscription identifier
     * @param request the subscription update request
     * @return a future containing the updated subscription
     */
    CompletableFuture<Subscription> updateAsync(String subscriptionId, UpdateSubscriptionRequest request);

    /**
     * Deletes a subscription.
     * @param subscriptionId the subscription identifier
     */
    void delete(String subscriptionId);

    /**
     * Asynchronously deletes a subscription.
     * @param subscriptionId the subscription identifier
     * @return a future that completes when the deletion is done
     */
    CompletableFuture<Void> deleteAsync(String subscriptionId);
}
