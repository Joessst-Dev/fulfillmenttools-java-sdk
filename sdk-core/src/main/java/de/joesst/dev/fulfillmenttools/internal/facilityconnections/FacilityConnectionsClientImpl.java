package de.joesst.dev.fulfillmenttools.internal.facilityconnections;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.facilityconnections.CreateFacilityConnectionRequest;
import de.joesst.dev.fulfillmenttools.facilityconnections.FacilityConnection;
import de.joesst.dev.fulfillmenttools.facilityconnections.FacilityConnectionListRequest;
import de.joesst.dev.fulfillmenttools.facilityconnections.FacilityConnectionsClient;
import de.joesst.dev.fulfillmenttools.facilityconnections.UpdateFacilityConnectionRequest;
import de.joesst.dev.fulfillmenttools.id.ConnectionId;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.model.Page;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class FacilityConnectionsClientImpl implements FacilityConnectionsClient {

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    public FacilityConnectionsClientImpl(HttpTransport transport, ResponseHandler responseHandler, String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    @Override
    public FacilityConnection get(FacilityId facilityId, ConnectionId connectionId) {
        return responseHandler.handle(execute(SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(connectionsUrl(facilityId) + "/" + connectionId.value())
                .build()), FacilityConnection.class);
    }

    @Override
    public CompletableFuture<FacilityConnection> getAsync(FacilityId facilityId, ConnectionId connectionId) {
        return transport.executeAsync(SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(connectionsUrl(facilityId) + "/" + connectionId.value())
                .build()).thenApply(r -> responseHandler.handle(r, FacilityConnection.class));
    }

    @Override
    public Page<FacilityConnection> list(FacilityId facilityId, FacilityConnectionListRequest request) {
        return toPage(responseHandler.handle(execute(buildListRequest(facilityId, request)), FacilityConnectionListResponse.class));
    }

    @Override
    public Iterable<FacilityConnection> listAll(FacilityId facilityId, FacilityConnectionListRequest request) {
        return () -> {
            final FacilityConnectionListRequest[] current = {request};
            return new java.util.Iterator<>() {
                private java.util.Iterator<FacilityConnection> pageIter;
                private boolean done = false;
                private int accumulated = 0;

                { advance(); }

                private void advance() {
                    if (done) return;
                    FacilityConnectionListResponse resp = responseHandler.handle(
                            execute(buildListRequest(facilityId, current[0])),
                            FacilityConnectionListResponse.class);
                    List<FacilityConnection> items = resp.interFacilityConnections() != null
                            ? resp.interFacilityConnections() : List.of();
                    accumulated += items.size();
                    pageIter = items.iterator();
                    if (!items.isEmpty()) {
                        current[0] = current[0].toBuilder().startAfterId(items.getLast().id().value()).build();
                    }
                    done = items.isEmpty() || (resp.total() != null && accumulated >= resp.total());
                }

                @Override
                public boolean hasNext() {
                    if (pageIter.hasNext()) return true;
                    if (done) return false;
                    advance();
                    return pageIter.hasNext();
                }

                @Override
                public FacilityConnection next() {
                    return pageIter.next();
                }
            };
        };
    }

    @Override
    public CompletableFuture<Page<FacilityConnection>> listAsync(FacilityId facilityId, FacilityConnectionListRequest request) {
        return transport.executeAsync(buildListRequest(facilityId, request))
                .thenApply(r -> toPage(responseHandler.handle(r, FacilityConnectionListResponse.class)));
    }

    @Override
    public FacilityConnection create(FacilityId facilityId, CreateFacilityConnectionRequest request) {
        return responseHandler.handle(execute(buildCreateRequest(facilityId, request)), FacilityConnection.class);
    }

    @Override
    public CompletableFuture<FacilityConnection> createAsync(FacilityId facilityId, CreateFacilityConnectionRequest request) {
        return transport.executeAsync(buildCreateRequest(facilityId, request))
                .thenApply(r -> responseHandler.handle(r, FacilityConnection.class));
    }

    @Override
    public FacilityConnection update(FacilityId facilityId, ConnectionId connectionId, UpdateFacilityConnectionRequest request) {
        return responseHandler.handle(execute(buildUpdateRequest(facilityId, connectionId, request)), FacilityConnection.class);
    }

    @Override
    public CompletableFuture<FacilityConnection> updateAsync(FacilityId facilityId, ConnectionId connectionId, UpdateFacilityConnectionRequest request) {
        return transport.executeAsync(buildUpdateRequest(facilityId, connectionId, request))
                .thenApply(r -> responseHandler.handle(r, FacilityConnection.class));
    }

    @Override
    public void delete(FacilityId facilityId, ConnectionId connectionId) {
        responseHandler.handleVoid(execute(SdkHttpRequest.builder()
                .method(HttpMethod.DELETE)
                .url(connectionsUrl(facilityId) + "/" + connectionId.value())
                .build()));
    }

    @Override
    public CompletableFuture<Void> deleteAsync(FacilityId facilityId, ConnectionId connectionId) {
        return transport.executeAsync(SdkHttpRequest.builder()
                .method(HttpMethod.DELETE)
                .url(connectionsUrl(facilityId) + "/" + connectionId.value())
                .build()).thenApply(r -> { responseHandler.handleVoid(r); return null; });
    }

    private SdkHttpRequest buildListRequest(FacilityId facilityId, FacilityConnectionListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(connectionsUrl(facilityId));
        if (request.size() != null) builder.queryParam("size", String.valueOf(request.size()));
        if (request.startAfterId() != null) builder.queryParam("startAfterId", request.startAfterId());
        if (request.targetFacilityRef() != null) builder.queryParam("targetFacilityRef", request.targetFacilityRef());
        return builder.build();
    }

    private SdkHttpRequest buildCreateRequest(FacilityId facilityId, CreateFacilityConnectionRequest request) {
        return SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(connectionsUrl(facilityId))
                .body(responseHandler.encode(new CreateFacilityConnectionBody(
                        request.target().type(),
                        request.target(),
                        request.carrierKey(),
                        request.carrierName(),
                        request.context(),
                        request.fallbackCosts(),
                        request.nonDeliveryDays(),
                        request.packagingUnitsByContexts(),
                        request.cutoffTimes(),
                        request.fallbackTransitTime(),
                        request.customAttributes())))
                .build();
    }

    private SdkHttpRequest buildUpdateRequest(FacilityId facilityId, ConnectionId connectionId, UpdateFacilityConnectionRequest request) {
        return SdkHttpRequest.builder()
                .method(HttpMethod.PUT)
                .url(connectionsUrl(facilityId) + "/" + connectionId.value())
                .body(responseHandler.encode(new UpdateFacilityConnectionBody(
                        request.version(),
                        request.target().type(),
                        request.target(),
                        request.carrierKey(),
                        request.carrierName(),
                        request.context(),
                        request.fallbackCosts(),
                        request.nonDeliveryDays(),
                        request.packagingUnitsByContexts(),
                        request.cutoffTimes(),
                        request.fallbackTransitTime(),
                        request.customAttributes())))
                .build();
    }

    private Page<FacilityConnection> toPage(FacilityConnectionListResponse resp) {
        List<FacilityConnection> items = resp.interFacilityConnections() != null ? resp.interFacilityConnections() : List.of();
        Integer total = resp.total();
        String cursor = (!items.isEmpty() && total != null && total > items.size())
                ? items.getLast().id().value()
                : null;
        return new Page<>(items, cursor);
    }

    private String connectionsUrl(FacilityId facilityId) {
        return baseUrl + "/api/facilities/" + facilityId.value() + "/connections";
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
