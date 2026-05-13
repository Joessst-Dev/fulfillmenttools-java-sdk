package de.joesst.dev.fulfillmenttools.internal.facilitydiscounts;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.CreateFacilityDiscountRequest;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscount;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscountListRequest;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscountsClient;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.UpdateFacilityDiscountRequest;
import de.joesst.dev.fulfillmenttools.id.FacilityDiscountId;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.model.Page;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

public final class FacilityDiscountsClientImpl implements FacilityDiscountsClient {

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    public FacilityDiscountsClientImpl(HttpTransport transport, ResponseHandler responseHandler, String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    @Override
    public FacilityDiscount get(FacilityId facilityId, FacilityDiscountId discountId) {
        return responseHandler.handle(execute(buildGetRequest(facilityId, discountId)), FacilityDiscount.class);
    }

    @Override
    public CompletableFuture<FacilityDiscount> getAsync(FacilityId facilityId, FacilityDiscountId discountId) {
        return transport.executeAsync(buildGetRequest(facilityId, discountId))
                .thenApply(r -> responseHandler.handle(r, FacilityDiscount.class));
    }

    @Override
    public Page<FacilityDiscount> list(FacilityId facilityId, FacilityDiscountListRequest request) {
        FacilityDiscountListResponse resp = responseHandler.handle(
                execute(buildListRequest(facilityId, request)), FacilityDiscountListResponse.class);
        return toPage(resp);
    }

    @Override
    public Iterable<FacilityDiscount> listAll(FacilityId facilityId, FacilityDiscountListRequest request) {
        return () -> new java.util.Iterator<>() {
            private final ArrayDeque<FacilityDiscount> buffer = new ArrayDeque<>();
            private String cursor = null;
            private boolean exhausted = false;
            private int accumulated = 0;

            private void loadNext() {
                FacilityDiscountListRequest r = cursor == null
                        ? request
                        : request.toBuilder().startAfterId(cursor).build();
                FacilityDiscountListResponse resp = responseHandler.handle(
                        execute(buildListRequest(facilityId, r)), FacilityDiscountListResponse.class);
                List<FacilityDiscount> items = resp.items() != null ? resp.items() : List.of();
                buffer.addAll(items);
                accumulated += items.size();
                boolean done = items.isEmpty() || (resp.total() != null && accumulated >= resp.total());
                if (done) { exhausted = true; } else { cursor = items.getLast().id(); }
            }

            @Override public boolean hasNext() {
                if (!buffer.isEmpty()) return true;
                if (exhausted) return false;
                loadNext();
                return !buffer.isEmpty();
            }

            @Override public FacilityDiscount next() {
                if (!hasNext()) throw new NoSuchElementException();
                return buffer.poll();
            }
        };
    }

    @Override
    public CompletableFuture<Page<FacilityDiscount>> listAsync(FacilityId facilityId, FacilityDiscountListRequest request) {
        return transport.executeAsync(buildListRequest(facilityId, request))
                .thenApply(r -> toPage(responseHandler.handle(r, FacilityDiscountListResponse.class)));
    }

    @Override
    public FacilityDiscount create(FacilityId facilityId, CreateFacilityDiscountRequest request) {
        return responseHandler.handle(execute(buildCreateRequest(facilityId, request)), FacilityDiscount.class);
    }

    @Override
    public CompletableFuture<FacilityDiscount> createAsync(FacilityId facilityId, CreateFacilityDiscountRequest request) {
        return transport.executeAsync(buildCreateRequest(facilityId, request))
                .thenApply(r -> responseHandler.handle(r, FacilityDiscount.class));
    }

    @Override
    public FacilityDiscount update(FacilityId facilityId, FacilityDiscountId discountId, UpdateFacilityDiscountRequest request) {
        return responseHandler.handle(execute(buildUpdateRequest(facilityId, discountId, request)), FacilityDiscount.class);
    }

    @Override
    public CompletableFuture<FacilityDiscount> updateAsync(FacilityId facilityId, FacilityDiscountId discountId, UpdateFacilityDiscountRequest request) {
        return transport.executeAsync(buildUpdateRequest(facilityId, discountId, request))
                .thenApply(r -> responseHandler.handle(r, FacilityDiscount.class));
    }

    @Override
    public void delete(FacilityId facilityId, FacilityDiscountId discountId) {
        responseHandler.handleVoid(execute(buildDeleteRequest(facilityId, discountId)));
    }

    @Override
    public CompletableFuture<Void> deleteAsync(FacilityId facilityId, FacilityDiscountId discountId) {
        return transport.executeAsync(buildDeleteRequest(facilityId, discountId))
                .thenApply(r -> { responseHandler.handleVoid(r); return null; });
    }

    private SdkHttpRequest buildGetRequest(FacilityId facilityId, FacilityDiscountId discountId) {
        return SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/facilities/" + facilityId.value() + "/discounts/" + discountId.value())
                .build();
    }

    private SdkHttpRequest buildListRequest(FacilityId facilityId, FacilityDiscountListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/facilities/" + facilityId.value() + "/discounts");
        if (request.size() != null) builder.queryParam("size", String.valueOf(request.size()));
        if (request.startAfterId() != null) builder.queryParam("startAfterId", request.startAfterId());
        return builder.build();
    }

    private SdkHttpRequest buildCreateRequest(FacilityId facilityId, CreateFacilityDiscountRequest request) {
        var body = new CreateFacilityDiscountBody(
                request.type(), request.priority(), request.discount(), request.context());
        return SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/facilities/" + facilityId.value() + "/discounts")
                .body(responseHandler.encode(body))
                .build();
    }

    private SdkHttpRequest buildUpdateRequest(FacilityId facilityId, FacilityDiscountId discountId, UpdateFacilityDiscountRequest request) {
        var body = new UpdateFacilityDiscountBody(
                request.version(), request.type(), request.priority(), request.discount(), request.context());
        return SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/facilities/" + facilityId.value() + "/discounts/" + discountId.value())
                .body(responseHandler.encode(body))
                .build();
    }

    private SdkHttpRequest buildDeleteRequest(FacilityId facilityId, FacilityDiscountId discountId) {
        return SdkHttpRequest.builder()
                .method(HttpMethod.DELETE)
                .url(baseUrl + "/api/facilities/" + facilityId.value() + "/discounts/" + discountId.value())
                .build();
    }

    private Page<FacilityDiscount> toPage(FacilityDiscountListResponse resp) {
        List<FacilityDiscount> items = resp.items() != null ? resp.items() : List.of();
        String cursor = null;
        if (!items.isEmpty()) {
            boolean couldHaveMore = resp.total() == null || items.size() < resp.total();
            if (couldHaveMore) cursor = items.getLast().id();
        }
        return new Page<>(items, cursor);
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
