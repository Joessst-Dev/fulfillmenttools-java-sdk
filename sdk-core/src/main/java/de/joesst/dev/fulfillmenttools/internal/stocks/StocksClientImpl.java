package de.joesst.dev.fulfillmenttools.internal.stocks;

import com.fasterxml.jackson.annotation.JsonInclude;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.id.StockId;
import de.joesst.dev.fulfillmenttools.internal.Pages;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.stocks.CreateStockRequest;
import de.joesst.dev.fulfillmenttools.stocks.StockItem;
import de.joesst.dev.fulfillmenttools.stocks.StockListRequest;
import de.joesst.dev.fulfillmenttools.stocks.StockSearchRequest;
import de.joesst.dev.fulfillmenttools.stocks.StockUpsertResult;
import de.joesst.dev.fulfillmenttools.stocks.StocksClient;
import de.joesst.dev.fulfillmenttools.stocks.UpdateStockRequest;
import de.joesst.dev.fulfillmenttools.stocks.VersionlessStockCreate;
import de.joesst.dev.fulfillmenttools.stocks.VersionlessStockOperation;
import de.joesst.dev.fulfillmenttools.stocks.VersionlessStockUpdate;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocationTraitConfigEntry;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        Objects.requireNonNull(stockId, "stockId must not be null");
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

    @Override
    public Page<StockItem> search(StockSearchRequest request) {
        StockListResponse body = responseHandler.handle(execute(buildSearchRequest(request)), StockListResponse.class);
        return toPage(body);
    }

    @Override
    public CompletableFuture<Page<StockItem>> searchAsync(StockSearchRequest request) {
        return transport.executeAsync(buildSearchRequest(request))
                .thenApply(r -> toPage(responseHandler.handle(r, StockListResponse.class)));
    }

    @Override
    public Iterable<StockItem> searchAll(StockSearchRequest request) {
        return Pages.all(cursor -> {
            StockSearchRequest r = cursor == null
                    ? request
                    : request.toBuilder().after(cursor).build();
            return search(r);
        });
    }

    private SdkHttpRequest buildSearchRequest(StockSearchRequest request) {
        return SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/stocks/search")
                .body(responseHandler.encode(new StockSearchBody(request.query(), request.size(), request.after())))
                .build();
    }

    @Override
    public List<StockUpsertResult> upsertStocks(List<VersionlessStockOperation> operations) {
        return toUpsertResults(responseHandler.handle(execute(buildUpsertRequest(operations)), UpsertActionResponse.class));
    }

    @Override
    public CompletableFuture<List<StockUpsertResult>> upsertStocksAsync(List<VersionlessStockOperation> operations) {
        return transport.executeAsync(buildUpsertRequest(operations))
                .thenApply(r -> toUpsertResults(responseHandler.handle(r, UpsertActionResponse.class)));
    }

    private SdkHttpRequest buildUpsertRequest(List<VersionlessStockOperation> operations) {
        Objects.requireNonNull(operations, "operations must not be null");
        if (operations.isEmpty()) {
            throw new IllegalArgumentException("operations must not be empty");
        }
        List<Object> bodies = operations.stream().map(this::toOperationBody).toList();
        return SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/stocks/actions")
                .body(responseHandler.encode(new UpsertActionBody("UPDATE_VERSIONLESS", bodies)))
                .build();
    }

    private Object toOperationBody(VersionlessStockOperation op) {
        return switch (op) {
            case VersionlessStockCreate c -> new CreateOperationBody(
                    "CREATE",
                    c.tenantArticleId().value(),
                    c.value(),
                    c.facilityRef() != null ? c.facilityRef().value() : null,
                    c.tenantFacilityId() != null ? c.tenantFacilityId().value() : null,
                    c.locationRef() != null ? c.locationRef().value() : null,
                    c.tenantStockId() != null ? c.tenantStockId().value() : null,
                    c.availableUntil(),
                    c.receiptDate(),
                    c.conditions(),
                    c.traitConfig(),
                    c.properties(),
                    c.customAttributes());
            case VersionlessStockUpdate u -> new UpdateOperationBody(
                    "UPDATE",
                    u.stockId().value(),
                    u.value(),
                    u.locationRef() != null ? u.locationRef().value() : null,
                    u.tenantStockId() != null ? u.tenantStockId().value() : null,
                    u.conditions(),
                    u.traitConfig(),
                    u.customAttributes());
        };
    }

    private List<StockUpsertResult> toUpsertResults(UpsertActionResponse response) {
        if (response.result() == null || response.result().operationResults() == null) {
            return List.of();
        }
        return response.result().operationResults().stream()
                .map(r -> new StockUpsertResult(r.stock(), r.status()))
                .toList();
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
            CustomAttributes customAttributes) {}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private record UpdateStockBody(
            Integer version,
            Integer value,
            String locationRef,
            String tenantStockId,
            List<String> conditions,
            List<StorageLocationTraitConfigEntry> traitConfig,
            CustomAttributes customAttributes) {}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private record UpsertActionBody(String name, List<Object> stocks) {}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private record CreateOperationBody(
            String operationType,
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
            CustomAttributes customAttributes) {}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private record UpdateOperationBody(
            String operationType,
            String id,
            Integer value,
            String locationRef,
            String tenantStockId,
            List<String> conditions,
            List<StorageLocationTraitConfigEntry> traitConfig,
            CustomAttributes customAttributes) {}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private record UpsertActionResponse(VersionlessUpsertResult result) {}
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private record VersionlessUpsertResult(List<UpsertOperationResult> operationResults) {}
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private record UpsertOperationResult(StockItem stock, String status) {}

}
