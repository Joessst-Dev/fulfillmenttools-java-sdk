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
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/users");

        if (request.size() != null) {
            builder.queryParam("size", String.valueOf(request.size()));
        }
        if (request.startAfterId() != null) {
            builder.queryParam("startAfterId", request.startAfterId());
        }

        SdkHttpResponse response = execute(builder.build());
        UserListResponse body = responseHandler.handle(response, UserListResponse.class);
        return new Page<>(
                body.users() != null ? body.users() : List.of(),
                body.nextCursor());
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
        CreateUserBody body = new CreateUserBody(request.email(), request.firstName(), request.lastName());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/users")
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), User.class);
    }

    @Override
    public User update(String userId, UpdateUserRequest request) {
        UpdateUserBody body = new UpdateUserBody(request.firstName(), request.lastName(), request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PUT)
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
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/users");

        if (request.size() != null) {
            builder.queryParam("size", String.valueOf(request.size()));
        }
        if (request.startAfterId() != null) {
            builder.queryParam("startAfterId", request.startAfterId());
        }

        return transport.executeAsync(builder.build()).thenApply(response -> {
            UserListResponse body = responseHandler.handle(response, UserListResponse.class);
            return new Page<>(body.users() != null ? body.users() : List.of(), body.nextCursor());
        });
    }

    @Override
    public CompletableFuture<User> createAsync(CreateUserRequest request) {
        CreateUserBody body = new CreateUserBody(request.email(), request.firstName(), request.lastName());
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
        UpdateUserBody body = new UpdateUserBody(request.firstName(), request.lastName(), request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PUT)
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

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
