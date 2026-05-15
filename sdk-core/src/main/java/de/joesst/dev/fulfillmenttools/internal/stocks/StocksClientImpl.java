package de.joesst.dev.fulfillmenttools.internal.stocks;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.id.StockId;
import de.joesst.dev.fulfillmenttools.internal.Pages;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.stocks.CreateStockRequest;
import de.joesst.dev.fulfillmenttools.stocks.StockItem;
import de.joesst.dev.fulfillmenttools.stocks.StockListRequest;
import de.joesst.dev.fulfillmenttools.stocks.StocksClient;
import de.joesst.dev.fulfillmenttools.stocks.UpdateStockRequest;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocationTraitConfigEntry;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public final class StocksClientImpl implements StocksClient {

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    public StocksClientImpl(HttpTransport transport, ResponseHandler responseHandler, String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    @Override
    public Page<StockItem> list(StockListRequest request) {
        StockListResponse body = responseHandler.handle(execute(buildListRequest(request)), StockListResponse.class);
        return toPage(body);
    }

    @Override
    public Iterable<StockItem> listAll(StockListRequest request) {
        return Pages.all(cursor -> {
            StockListRequest r = cursor == null
                    ? request
                    : request.toBuilder().startAfterId(cursor).build();
            return list(r);
        });
    }

    @Override
    public CompletableFuture<Page<StockItem>> listAsync(StockListRequest request) {
        return transport.executeAsync(buildListRequest(request))
                .thenApply(response -> toPage(responseHandler.handle(response, StockListResponse.class)));
    }

    private SdkHttpRequest buildListRequest(StockListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/stocks");

        if (request.size() != null) builder.queryParam("size", String.valueOf(request.size()));
        if (request.startAfterId() != null) builder.queryParam("startAfterId", request.startAfterId());
        if (request.facilityRef() != null) builder.queryParam("facilityRef", request.facilityRef().value());
        if (request.tenantFacilityId() != null) builder.queryParam("tenantFacilityId", request.tenantFacilityId().value());
        if (request.tenantArticleId() != null) request.tenantArticleId().forEach(v -> builder.queryParam("tenantArticleId", v.value()));
        if (request.locationRef() != null) request.locationRef().forEach(v -> builder.queryParam("locationRef", v.value()));

        return builder.build();
    }

    @Override
    public StockItem create(CreateStockRequest request) {
        return responseHandler.handle(execute(buildCreateRequest(request)), StockItem.class);
    }

    @Override
    public CompletableFuture<StockItem> createAsync(CreateStockRequest request) {
        return transport.executeAsync(buildCreateRequest(request))
                .thenApply(r -> responseHandler.handle(r, StockItem.class));
    }

    @Override
    public StockItem update(StockId stockId, UpdateStockRequest request) {
        return responseHandler.handle(execute(buildUpdateRequest(stockId, request)), StockItem.class);
    }

    @Override
    public CompletableFuture<StockItem> updateAsync(StockId stockId, UpdateStockRequest request) {
        return transport.executeAsync(buildUpdateRequest(stockId, request))
                .thenApply(r -> responseHandler.handle(r, StockItem.class));
    }

    private SdkHttpRequest buildCreateRequest(CreateStockRequest request) {
        String facilityRef = request.facilityRef() != null ? request.facilityRef().value() : null;
        String tenantFacilityId = request.tenantFacilityId() != null ? request.tenantFacilityId().value() : null;
        String locationRef = request.locationRef() != null ? request.locationRef().value() : null;
        String tenantStockId = request.tenantStockId() != null ? request.tenantStockId().value() : null;
        return SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/stocks")
                .body(responseHandler.encode(new CreateStockBody(
                        request.tenantArticleId().value(),
                        request.value(),
                        facilityRef,
                        tenantFacilityId,
                        locationRef,
                        tenantStockId,
                        request.availableUntil(),
                        request.receiptDate(),
                        request.conditions(),
                        request.traitConfig(),
                        request.properties(),
                        request.customAttributes())))
                .build();
    }

    private SdkHttpRequest buildUpdateRequest(StockId stockId, UpdateStockRequest request) {
        String locationRef = request.locationRef() != null ? request.locationRef().value() : null;
        String tenantStockId = request.tenantStockId() != null ? request.tenantStockId().value() : null;
        return SdkHttpRequest.builder()
                .method(HttpMethod.PUT)
                .url(baseUrl + "/api/stocks/" + stockId.value())
                .body(responseHandler.encode(new UpdateStockBody(
                        request.version(),
                        request.value(),
                        locationRef,
                        tenantStockId,
                        request.conditions(),
                        request.traitConfig(),
                        request.customAttributes())))
                .build();
    }

    private Page<StockItem> toPage(StockListResponse body) {
        List<StockItem> items = body.stocks() != null ? body.stocks() : List.of();
        String cursor = body.pageInfo() != null ? body.pageInfo().endCursor() : null;
        return new Page<>(items, cursor);
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private record CreateStockBody(
            String tenantArticleId,
            Integer value,
            String facilityRef,
            String tenantFacilityId,
            String locationRef,
            String tenantStockId,
            Instant availableUntil,
            Instant receiptDate,
            List<String> conditions,
            List<StorageLocationTraitConfigEntry> traitConfig,
            Map<String, String> properties,
            Object customAttributes) {}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private record UpdateStockBody(
            Integer version,
            Integer value,
            String locationRef,
            String tenantStockId,
            List<String> conditions,
            List<StorageLocationTraitConfigEntry> traitConfig,
            Object customAttributes) {}
}
