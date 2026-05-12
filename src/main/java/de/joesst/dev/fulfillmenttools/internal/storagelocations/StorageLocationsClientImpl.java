package de.joesst.dev.fulfillmenttools.internal.storagelocations;

import com.fasterxml.jackson.core.type.TypeReference;
import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.internal.Pages;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.storagelocations.CreateStorageLocationRequest;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocation;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocationListRequest;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocationsClient;
import de.joesst.dev.fulfillmenttools.storagelocations.UpdateStorageLocationRequest;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class StorageLocationsClientImpl implements StorageLocationsClient {

    private static final TypeReference<List<StorageLocation>> STORAGE_LOCATION_LIST_TYPE = new TypeReference<>() {};

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    public StorageLocationsClientImpl(HttpTransport transport, ResponseHandler responseHandler, String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    private String collectionUrl(String facilityId) {
        return baseUrl + "/api/facilities/" + facilityId + "/storagelocations";
    }

    private String itemUrl(String facilityId, String storageLocationId) {
        return collectionUrl(facilityId) + "/" + storageLocationId;
    }

    @Override
    public StorageLocation get(String facilityId, String storageLocationId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(itemUrl(facilityId, storageLocationId))
                .build();
        return responseHandler.handle(execute(request), StorageLocation.class);
    }

    @Override
    public Page<StorageLocation> list(String facilityId, StorageLocationListRequest request) {
        SdkHttpResponse response = execute(buildListRequest(facilityId, request).build());
        List<StorageLocation> items = responseHandler.handle(response, STORAGE_LOCATION_LIST_TYPE);
        return new Page<>(items != null ? items : List.of(), null);
    }

    @Override
    public Iterable<StorageLocation> listAll(String facilityId, StorageLocationListRequest request) {
        return Pages.all(cursor -> {
            StorageLocationListRequest r = cursor == null
                    ? request
                    : request.toBuilder().startAfterId(cursor).build();
            return list(facilityId, r);
        });
    }

    @Override
    public StorageLocation create(String facilityId, CreateStorageLocationRequest request) {
        CreateStorageLocationBody body = new CreateStorageLocationBody(
                request.name(), request.type(), request.scannableCodes(),
                request.runningSequences(), request.information(), request.tenantLocationId(),
                request.zoneRef(), request.zoneName(), request.traitConfig(),
                request.traits(), request.customAttributes());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(collectionUrl(facilityId))
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), StorageLocation.class);
    }

    @Override
    public StorageLocation update(String facilityId, String storageLocationId, UpdateStorageLocationRequest request) {
        UpdateStorageLocationBody body = buildUpdateBody(request);
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(itemUrl(facilityId, storageLocationId))
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), StorageLocation.class);
    }

    @Override
    public void delete(String facilityId, String storageLocationId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.DELETE)
                .url(itemUrl(facilityId, storageLocationId))
                .build();
        responseHandler.handleVoid(execute(request));
    }

    @Override
    public CompletableFuture<StorageLocation> getAsync(String facilityId, String storageLocationId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(itemUrl(facilityId, storageLocationId))
                .build();
        return transport.executeAsync(request)
                .thenApply(response -> responseHandler.handle(response, StorageLocation.class));
    }

    @Override
    public CompletableFuture<Page<StorageLocation>> listAsync(String facilityId, StorageLocationListRequest request) {
        return transport.executeAsync(buildListRequest(facilityId, request).build()).thenApply(response -> {
            List<StorageLocation> items = responseHandler.handle(response, STORAGE_LOCATION_LIST_TYPE);
            return new Page<>(items != null ? items : List.of(), null);
        });
    }

    @Override
    public CompletableFuture<StorageLocation> createAsync(String facilityId, CreateStorageLocationRequest request) {
        CreateStorageLocationBody body = new CreateStorageLocationBody(
                request.name(), request.type(), request.scannableCodes(),
                request.runningSequences(), request.information(), request.tenantLocationId(),
                request.zoneRef(), request.zoneName(), request.traitConfig(),
                request.traits(), request.customAttributes());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(collectionUrl(facilityId))
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, StorageLocation.class));
    }

    @Override
    public CompletableFuture<StorageLocation> updateAsync(String facilityId, String storageLocationId, UpdateStorageLocationRequest request) {
        UpdateStorageLocationBody body = buildUpdateBody(request);
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(itemUrl(facilityId, storageLocationId))
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, StorageLocation.class));
    }

    @Override
    public CompletableFuture<Void> deleteAsync(String facilityId, String storageLocationId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.DELETE)
                .url(itemUrl(facilityId, storageLocationId))
                .build();
        return transport.executeAsync(request).thenApply(response -> {
            responseHandler.handleVoid(response);
            return null;
        });
    }

    private UpdateStorageLocationBody buildUpdateBody(UpdateStorageLocationRequest request) {
        UpdateStorageLocationBody.ModifyStorageLocationAction action =
                new UpdateStorageLocationBody.ModifyStorageLocationAction(
                        request.name(), request.type(), request.scannableCodes(),
                        request.runningSequences(), request.information(), request.tenantLocationId(),
                        request.zoneRef(), request.traitConfig(), request.customAttributes());
        return new UpdateStorageLocationBody(request.version(), List.of(action));
    }

    private SdkHttpRequest.Builder buildListRequest(String facilityId, StorageLocationListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(collectionUrl(facilityId));

        if (request.size() != null) builder.queryParam("size", String.valueOf(request.size()));
        if (request.startAfterId() != null) builder.queryParam("startAfterId", request.startAfterId());
        if (request.scannableCode() != null) builder.queryParam("scannableCode", request.scannableCode());

        return builder;
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
