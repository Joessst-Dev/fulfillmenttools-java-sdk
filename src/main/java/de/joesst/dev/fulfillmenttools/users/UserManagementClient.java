package de.joesst.dev.fulfillmenttools.users;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

public interface UserManagementClient {

    User get(String userId);
    CompletableFuture<User> getAsync(String userId);

    Page<User> list(UserListRequest request);
    CompletableFuture<Page<User>> listAsync(UserListRequest request);

    Iterable<User> listAll(UserListRequest request);

    User create(CreateUserRequest request);
    CompletableFuture<User> createAsync(CreateUserRequest request);

    User update(String userId, UpdateUserRequest request);
    CompletableFuture<User> updateAsync(String userId, UpdateUserRequest request);

    void delete(String userId);
    CompletableFuture<Void> deleteAsync(String userId);
}
