package de.joesst.dev.fulfillmenttools.internal.facilities;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.facilities.CreateFacilityRequest;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.facilities.FacilitiesClient;
import de.joesst.dev.fulfillmenttools.facilities.Facility;
import de.joesst.dev.fulfillmenttools.facilities.FacilityListRequest;
import de.joesst.dev.fulfillmenttools.facilities.FacilitySearchRequest;
import de.joesst.dev.fulfillmenttools.facilities.UpdateFacilityRequest;
import de.joesst.dev.fulfillmenttools.internal.Pages;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.model.Page;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class FacilitiesClientImpl implements FacilitiesClient {

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    public FacilitiesClientImpl(HttpTransport transport, ResponseHandler responseHandler, String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    @Override
    public Facility get(FacilityId facilityId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/facilities/" + facilityId.value())
                .build();
        return responseHandler.handle(execute(request), Facility.class);
    }

    @Override
    public Page<Facility> list(FacilityListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/facilities");

        if (request.size() != null) builder.queryParam("size", String.valueOf(request.size()));
        if (request.startAfterId() != null) builder.queryParam("startAfterId", request.startAfterId());
        if (request.tenantFacilityId() != null) builder.queryParam("tenantFacilityId", request.tenantFacilityId());
        if (request.orderBy() != null) builder.queryParam("orderBy", request.orderBy());
        if (request.status() != null) request.status().forEach(s -> builder.queryParam("status", s));
        if (request.type() != null) request.type().forEach(t -> builder.queryParam("type", t));

        SdkHttpResponse response = execute(builder.build());
        FacilityListResponse body = responseHandler.handle(response, FacilityListResponse.class);
        return new Page<>(
                body.facilities() != null ? body.facilities() : List.of(),
                body.nextCursor());
    }

    @Override
    public Iterable<Facility> listAll(FacilityListRequest request) {
        return Pages.all(cursor -> {
            FacilityListRequest r = cursor == null
                    ? request
                    : request.toBuilder().startAfterId(cursor).build();
            return list(r);
        });
    }

    @Override
    public Page<Facility> search(FacilitySearchRequest request) {
        FacilitySearchBody body = new FacilitySearchBody(
                request.query(), request.size(), request.after(), request.before(), request.last());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/facilities/search")
                .body(responseHandler.encode(body))
                .build();
        FacilitySearchResponse resp = responseHandler.handle(execute(httpRequest), FacilitySearchResponse.class);
        String cursor = resp.pageInfo() != null ? resp.pageInfo().endCursor() : null;
        return new Page<>(resp.facilities() != null ? resp.facilities() : List.of(), cursor);
    }

    @Override
    public Iterable<Facility> searchAll(FacilitySearchRequest request) {
        return Pages.all(cursor -> {
            FacilitySearchRequest r = cursor == null
                    ? request
                    : request.toBuilder().after(cursor).build();
            return search(r);
        });
    }

    @Override
    public Facility create(CreateFacilityRequest request) {
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/facilities")
                .body(responseHandler.encode(toCreateBody(request)))
                .build();
        return responseHandler.handle(execute(httpRequest), Facility.class);
    }

    @Override
    public Facility update(FacilityId facilityId, UpdateFacilityRequest request) {
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/facilities/" + facilityId.value())
                .body(responseHandler.encode(toUpdateBody(request)))
                .build();
        return responseHandler.handle(execute(httpRequest), Facility.class);
    }

    @Override
    public Facility replace(FacilityId facilityId, CreateFacilityRequest request) {
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PUT)
                .url(baseUrl + "/api/facilities/" + facilityId.value())
                .body(responseHandler.encode(toCreateBody(request)))
                .build();
        return responseHandler.handle(execute(httpRequest), Facility.class);
    }

    @Override
    public void delete(FacilityId facilityId) {
        delete(facilityId, false);
    }

    @Override
    public void delete(FacilityId facilityId, boolean forceDeletion) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.DELETE)
                .url(baseUrl + "/api/facilities/" + facilityId.value());
        if (forceDeletion) builder.queryParam("forceDeletion", "true");
        responseHandler.handleVoid(execute(builder.build()));
    }

    @Override
    public CompletableFuture<Facility> getAsync(FacilityId facilityId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/facilities/" + facilityId.value())
                .build();
        return transport.executeAsync(request)
                .thenApply(response -> responseHandler.handle(response, Facility.class));
    }

    @Override
    public CompletableFuture<Page<Facility>> listAsync(FacilityListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/facilities");

        if (request.size() != null) builder.queryParam("size", String.valueOf(request.size()));
        if (request.startAfterId() != null) builder.queryParam("startAfterId", request.startAfterId());
        if (request.tenantFacilityId() != null) builder.queryParam("tenantFacilityId", request.tenantFacilityId());
        if (request.orderBy() != null) builder.queryParam("orderBy", request.orderBy());
        if (request.status() != null) request.status().forEach(s -> builder.queryParam("status", s));
        if (request.type() != null) request.type().forEach(t -> builder.queryParam("type", t));

        return transport.executeAsync(builder.build()).thenApply(response -> {
            FacilityListResponse body = responseHandler.handle(response, FacilityListResponse.class);
            return new Page<>(body.facilities() != null ? body.facilities() : List.of(), body.nextCursor());
        });
    }

    @Override
    public CompletableFuture<Page<Facility>> searchAsync(FacilitySearchRequest request) {
        FacilitySearchBody body = new FacilitySearchBody(
                request.query(), request.size(), request.after(), request.before(), request.last());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/facilities/search")
                .body(responseHandler.encode(body))
                .build();
        return transport.executeAsync(httpRequest).thenApply(response -> {
            FacilitySearchResponse resp = responseHandler.handle(response, FacilitySearchResponse.class);
            String cursor = resp.pageInfo() != null ? resp.pageInfo().endCursor() : null;
            return new Page<>(resp.facilities() != null ? resp.facilities() : List.of(), cursor);
        });
    }

    @Override
    public CompletableFuture<Facility> createAsync(CreateFacilityRequest request) {
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/facilities")
                .body(responseHandler.encode(toCreateBody(request)))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, Facility.class));
    }

    @Override
    public CompletableFuture<Facility> updateAsync(FacilityId facilityId, UpdateFacilityRequest request) {
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/facilities/" + facilityId.value())
                .body(responseHandler.encode(toUpdateBody(request)))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, Facility.class));
    }

    @Override
    public CompletableFuture<Facility> replaceAsync(FacilityId facilityId, CreateFacilityRequest request) {
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PUT)
                .url(baseUrl + "/api/facilities/" + facilityId.value())
                .body(responseHandler.encode(toCreateBody(request)))
                .build();
        return transport.executeAsync(httpRequest)
                .thenApply(response -> responseHandler.handle(response, Facility.class));
    }

    @Override
    public CompletableFuture<Void> deleteAsync(FacilityId facilityId) {
        return deleteAsync(facilityId, false);
    }

    @Override
    public CompletableFuture<Void> deleteAsync(FacilityId facilityId, boolean forceDeletion) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.DELETE)
                .url(baseUrl + "/api/facilities/" + facilityId.value());
        if (forceDeletion) builder.queryParam("forceDeletion", "true");
        return transport.executeAsync(builder.build()).thenApply(response -> {
            responseHandler.handleVoid(response);
            return null;
        });
    }

    private CreateFacilityBody toCreateBody(CreateFacilityRequest r) {
        return new CreateFacilityBody(
                r.name(), r.tenantFacilityId(), r.status(), r.type(),
                r.locationType(), r.address(), r.contact(),
                r.pickingMethods(), r.pickingTimes(), r.closingDays(),
                r.scanningRule(), r.capacityEnabled(), r.capacityPlanningTimeframe(),
                r.fulfillmentProcessBuffer(), r.operativeCosts(), r.tags(),
                r.customAttributes());
    }

    private UpdateFacilityBody toUpdateBody(UpdateFacilityRequest r) {
        return new UpdateFacilityBody(
                r.name(), r.tenantFacilityId(), r.status(), r.type(),
                r.locationType(), r.address(), r.contact(),
                r.pickingMethods(), r.pickingTimes(), r.closingDays(),
                r.scanningRule(), r.capacityEnabled(), r.capacityPlanningTimeframe(),
                r.fulfillmentProcessBuffer(), r.operativeCosts(), r.tags(),
                r.customAttributes());
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
