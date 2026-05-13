package de.joesst.dev.fulfillmenttools.internal.users;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.internal.Pages;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.users.CreateUserRequest;
import de.joesst.dev.fulfillmenttools.users.UpdateUserRequest;
import de.joesst.dev.fulfillmenttools.users.User;
import de.joesst.dev.fulfillmenttools.users.UserListRequest;
import de.joesst.dev.fulfillmenttools.users.UserManagementClient;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class UserManagementClientImpl implements UserManagementClient {

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    public UserManagementClientImpl(HttpTransport transport, ResponseHandler responseHandler, String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    @Override
    public User get(String userId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/users/" + userId)
                .build();
        return responseHandler.handle(execute(request), User.class);
    }

    @Override
    public Page<User> list(UserListRequest request) {
        SdkHttpResponse response = execute(buildListRequest(request));
        UserListResponse body = responseHandler.handle(response, UserListResponse.class);
        String cursor = body.pageInfo() != null ? body.pageInfo().endCursor() : null;
        return new Page<>(body.users() != null ? body.users() : List.of(), cursor);
    }

    @Override
    public Iterable<User> listAll(UserListRequest request) {
        return Pages.all(cursor -> {
            UserListRequest r = cursor == null
                    ? request
                    : request.toBuilder().startAfterId(cursor).build();
            return list(r);
        });
    }

    @Override
    public User create(CreateUserRequest request) {
        CreateUserBody body = new CreateUserBody(
                request.username(), request.password(),
                request.firstName(), request.lastName(),
                request.email(), request.locale(), request.assignedRoles());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/users")
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), User.class);
    }

    @Override
    public User update(String userId, UpdateUserRequest request) {
        UpdateUserBody body = buildUpdateBody(request);
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/users/" + userId)
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), User.class);
    }

    @Override
    public void delete(String userId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.DELETE)
                .url(baseUrl + "/api/users/" + userId)
                .build();
        responseHandler.handleVoid(execute(request));
    }

    @Override
    public CompletableFuture<User> getAsync(String userId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/users/" + userId)
                .build();
        return transport.executeAsync(request)
                .thenApply(response -> responseHandler.handle(response, User.class));
    }

    @Override
    public CompletableFuture<Page<User>> listAsync(UserListRequest request) {
        return transport.executeAsync(buildListRequest(request)).thenApply(response -> {
            UserListResponse body = responseHandler.handle(response, UserListResponse.class);
            String cursor = body.pageInfo() != null ? body.pageInfo().endCursor() : null;
            return new Page<>(body.users() != null ? body.users() : List.of(), cursor);
        });
    }

    @Override
    public CompletableFuture<User> createAsync(CreateUserRequest request) {
        CreateUserBody body = new CreateUserBody(
                request.username(), request.password(),
                request.firstName(), request.lastName(),
                request.email(), request.locale(), request.assignedRoles());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/users")
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, User.class));
    }

    @Override
    public CompletableFuture<User> updateAsync(String userId, UpdateUserRequest request) {
        UpdateUserBody body = buildUpdateBody(request);
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/users/" + userId)
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, User.class));
    }

    @Override
    public CompletableFuture<Void> deleteAsync(String userId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.DELETE)
                .url(baseUrl + "/api/users/" + userId)
                .build();
        return transport.executeAsync(request).thenApply(response -> {
            responseHandler.handleVoid(response);
            return null;
        });
    }

    private SdkHttpRequest buildListRequest(UserListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/users");

        if (request.size() != null) {
            builder.queryParam("size", String.valueOf(request.size()));
        }
        if (request.startAfterId() != null) {
            builder.queryParam("startAfterId", request.startAfterId());
        }
        if (request.orderBy() != null) {
            builder.queryParam("orderBy", request.orderBy());
        }
        if (request.facilityId() != null) {
            builder.queryParam("facilityId", request.facilityId());
        }
        if (request.includeAdminUsers() != null) {
            builder.queryParam("includeAdminUsers", String.valueOf(request.includeAdminUsers()));
        }

        return builder.build();
    }

    private UpdateUserBody buildUpdateBody(UpdateUserRequest request) {
        UpdateUserBody.ModifyUserAction action = new UpdateUserBody.ModifyUserAction(
                request.firstName(), request.lastName(), request.email(), request.password(),
                request.locale(), request.assignedRoles());
        return new UpdateUserBody(request.version(), List.of(action));
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
