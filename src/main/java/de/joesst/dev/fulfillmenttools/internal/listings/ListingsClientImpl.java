package de.joesst.dev.fulfillmenttools.internal.listings;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.internal.Pages;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.listings.Listing;
import de.joesst.dev.fulfillmenttools.listings.ListingBulkUpsertRequest;
import de.joesst.dev.fulfillmenttools.listings.ListingSearchRequest;
import de.joesst.dev.fulfillmenttools.listings.ListingsClient;
import de.joesst.dev.fulfillmenttools.model.Page;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class ListingsClientImpl implements ListingsClient {

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    public ListingsClientImpl(HttpTransport transport, ResponseHandler responseHandler, String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    @Override
    public List<Listing> bulkUpsert(ListingBulkUpsertRequest request) {
        SdkHttpRequest httpRequest = buildBulkUpsertRequest(request);
        ListingBulkUpsertResponse resp = responseHandler.handle(execute(httpRequest), ListingBulkUpsertResponse.class);
        return resp.listings() != null ? resp.listings() : List.of();
    }

    @Override
    public CompletableFuture<List<Listing>> bulkUpsertAsync(ListingBulkUpsertRequest request) {
        SdkHttpRequest httpRequest = buildBulkUpsertRequest(request);
        return transport.executeAsync(httpRequest).thenApply(response -> {
            ListingBulkUpsertResponse resp = responseHandler.handle(response, ListingBulkUpsertResponse.class);
            return resp.listings() != null ? resp.listings() : List.of();
        });
    }

    @Override
    public Page<Listing> search(ListingSearchRequest request) {
        SdkHttpRequest httpRequest = buildSearchRequest(request);
        return toPage(responseHandler.handle(execute(httpRequest), ListingSearchResponse.class));
    }

    @Override
    public Iterable<Listing> searchAll(ListingSearchRequest request) {
        return Pages.all(cursor -> {
            ListingSearchRequest r = cursor == null
                    ? request
                    : request.toBuilder().startAfterId(cursor).build();
            return search(r);
        });
    }

    @Override
    public CompletableFuture<Page<Listing>> searchAsync(ListingSearchRequest request) {
        SdkHttpRequest httpRequest = buildSearchRequest(request);
        return transport.executeAsync(httpRequest)
                .thenApply(response -> toPage(responseHandler.handle(response, ListingSearchResponse.class)));
    }

    private SdkHttpRequest buildBulkUpsertRequest(ListingBulkUpsertRequest request) {
        return SdkHttpRequest.builder()
                .method(HttpMethod.PUT)
                .url(baseUrl + "/api/listings")
                .body(responseHandler.encode(new ListingBulkUpsertBody(request.listings())))
                .build();
    }

    private SdkHttpRequest buildSearchRequest(ListingSearchRequest request) {
        ListingSearchBody body = new ListingSearchBody(
                request.facilityRef(),
                request.tenantArticleId(),
                request.size(),
                request.startAfterId());
        return SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/listings/search")
                .body(responseHandler.encode(body))
                .build();
    }

    private Page<Listing> toPage(ListingSearchResponse resp) {
        List<Listing> items = resp.listings() != null ? resp.listings() : List.of();
        String cursor = resp.pageInfo() != null ? resp.pageInfo().endCursor() : null;
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
