package de.joesst.dev.fulfillmenttools.internal.facilitydiscounts;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.CreateFacilityDiscountRequest;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscount;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscountListRequest;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.FacilityDiscountsClient;
import de.joesst.dev.fulfillmenttools.facilitydiscounts.UpdateFacilityDiscountRequest;
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
    public FacilityDiscount get(String facilityRef, String discountRef) {
        return responseHandler.handle(execute(buildGetRequest(facilityRef, discountRef)), FacilityDiscount.class);
    }

    @Override
    public CompletableFuture<FacilityDiscount> getAsync(String facilityRef, String discountRef) {
        return transport.executeAsync(buildGetRequest(facilityRef, discountRef))
                .thenApply(r -> responseHandler.handle(r, FacilityDiscount.class));
    }

    @Override
    public Page<FacilityDiscount> list(String facilityRef, FacilityDiscountListRequest request) {
        FacilityDiscountListResponse resp = responseHandler.handle(
                execute(buildListRequest(facilityRef, request)), FacilityDiscountListResponse.class);
        return toPage(resp);
    }

    @Override
    public Iterable<FacilityDiscount> listAll(String facilityRef, FacilityDiscountListRequest request) {
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
                        execute(buildListRequest(facilityRef, r)), FacilityDiscountListResponse.class);
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
    public CompletableFuture<Page<FacilityDiscount>> listAsync(String facilityRef, FacilityDiscountListRequest request) {
        return transport.executeAsync(buildListRequest(facilityRef, request))
                .thenApply(r -> toPage(responseHandler.handle(r, FacilityDiscountListResponse.class)));
    }

    @Override
    public FacilityDiscount create(String facilityRef, CreateFacilityDiscountRequest request) {
        return responseHandler.handle(execute(buildCreateRequest(facilityRef, request)), FacilityDiscount.class);
    }

    @Override
    public CompletableFuture<FacilityDiscount> createAsync(String facilityRef, CreateFacilityDiscountRequest request) {
        return transport.executeAsync(buildCreateRequest(facilityRef, request))
                .thenApply(r -> responseHandler.handle(r, FacilityDiscount.class));
    }

    @Override
    public FacilityDiscount update(String facilityRef, String discountRef, UpdateFacilityDiscountRequest request) {
        return responseHandler.handle(execute(buildUpdateRequest(facilityRef, discountRef, request)), FacilityDiscount.class);
    }

    @Override
    public CompletableFuture<FacilityDiscount> updateAsync(String facilityRef, String discountRef, UpdateFacilityDiscountRequest request) {
        return transport.executeAsync(buildUpdateRequest(facilityRef, discountRef, request))
                .thenApply(r -> responseHandler.handle(r, FacilityDiscount.class));
    }

    @Override
    public void delete(String facilityRef, String discountRef) {
        responseHandler.handleVoid(execute(buildDeleteRequest(facilityRef, discountRef)));
    }

    @Override
    public CompletableFuture<Void> deleteAsync(String facilityRef, String discountRef) {
        return transport.executeAsync(buildDeleteRequest(facilityRef, discountRef))
                .thenApply(r -> { responseHandler.handleVoid(r); return null; });
    }

    private SdkHttpRequest buildGetRequest(String facilityRef, String discountRef) {
        return SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/facilities/" + facilityRef + "/discounts/" + discountRef)
                .build();
    }

    private SdkHttpRequest buildListRequest(String facilityRef, FacilityDiscountListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/facilities/" + facilityRef + "/discounts");
        if (request.size() != null) builder.queryParam("size", String.valueOf(request.size()));
        if (request.startAfterId() != null) builder.queryParam("startAfterId", request.startAfterId());
        return builder.build();
    }

    private SdkHttpRequest buildCreateRequest(String facilityRef, CreateFacilityDiscountRequest request) {
        var body = new CreateFacilityDiscountBody(
                request.type(), request.priority(), request.discount(), request.context());
        return SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/facilities/" + facilityRef + "/discounts")
                .body(responseHandler.encode(body))
                .build();
    }

    private SdkHttpRequest buildUpdateRequest(String facilityRef, String discountRef, UpdateFacilityDiscountRequest request) {
        var body = new UpdateFacilityDiscountBody(
                request.version(), request.type(), request.priority(), request.discount(), request.context());
        return SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/facilities/" + facilityRef + "/discounts/" + discountRef)
                .body(responseHandler.encode(body))
                .build();
    }

    private SdkHttpRequest buildDeleteRequest(String facilityRef, String discountRef) {
        return SdkHttpRequest.builder()
                .method(HttpMethod.DELETE)
                .url(baseUrl + "/api/facilities/" + facilityRef + "/discounts/" + discountRef)
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
