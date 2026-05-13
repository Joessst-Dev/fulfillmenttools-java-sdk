package de.joesst.dev.fulfillmenttools.users;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

/**
 * Client for managing users in the fulfillmenttools users module.
 *
 * <p>Provides synchronous and asynchronous operations to create, retrieve, list, update,
 * and delete users within a fulfillmenttools tenant.
 */
public interface UserManagementClient {

    /**
     * Retrieves a single user by ID.
     *
     * @param userId the user ID
     * @return the user
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    User get(String userId);

    /**
     * Retrieves a single user by ID asynchronously.
     *
     * @param userId the user ID
     * @return a {@code CompletableFuture} that resolves to the user
     */
    CompletableFuture<User> getAsync(String userId);

    /**
     * Lists users, returning one page of results.
     *
     * @param request the list request with filters and pagination
     * @return a page of users
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    Page<User> list(UserListRequest request);

    /**
     * Lists users asynchronously, returning one page of results.
     *
     * @param request the list request with filters and pagination
     * @return a {@code CompletableFuture} that resolves to a page of users
     */
    CompletableFuture<Page<User>> listAsync(UserListRequest request);

    /**
     * Lists all users by automatically iterating through pages.
     *
     * @param request the list request with filters
     * @return an {@code Iterable} over all matching users
     */
    Iterable<User> listAll(UserListRequest request);

    /**
     * Creates a new user.
     *
     * @param request the creation request
     * @return the created user
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    User create(CreateUserRequest request);

    /**
     * Creates a new user asynchronously.
     *
     * @param request the creation request
     * @return a {@code CompletableFuture} that resolves to the created user
     */
    CompletableFuture<User> createAsync(CreateUserRequest request);

    /**
     * Updates an existing user.
     *
     * @param userId  the user ID to update
     * @param request the update request
     * @return the updated user
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    User update(String userId, UpdateUserRequest request);

    /**
     * Updates an existing user asynchronously.
     *
     * @param userId  the user ID to update
     * @param request the update request
     * @return a {@code CompletableFuture} that resolves to the updated user
     */
    CompletableFuture<User> updateAsync(String userId, UpdateUserRequest request);

    /**
     * Deletes a user.
     *
     * @param userId the user ID to delete
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    void delete(String userId);

    /**
     * Deletes a user asynchronously.
     *
     * @param userId the user ID to delete
     * @return a {@code CompletableFuture} that completes when the deletion is done
     */
    CompletableFuture<Void> deleteAsync(String userId);
}
