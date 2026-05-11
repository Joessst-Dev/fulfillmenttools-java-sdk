package de.joesst.dev.fulfillmenttools.internal.stocks;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.internal.Pages;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.stocks.StockItem;
import de.joesst.dev.fulfillmenttools.stocks.StockListRequest;
import de.joesst.dev.fulfillmenttools.stocks.StocksClient;

import java.io.IOException;
import java.util.List;
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
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/stocks");

        if (request.size() != null) {
            builder.queryParam("size", String.valueOf(request.size()));
        }
        if (request.startAfterId() != null) {
            builder.queryParam("startAfterId", request.startAfterId());
        }
        if (request.facilityRef() != null) {
            builder.queryParam("facilityRef", request.facilityRef());
        }

        SdkHttpResponse response = execute(builder.build());
        StockListResponse body = responseHandler.handle(response, StockListResponse.class);
        return new Page<>(
                body.stocks() != null ? body.stocks() : List.of(),
                body.nextCursor());
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
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/stocks");

        if (request.size() != null) {
            builder.queryParam("size", String.valueOf(request.size()));
        }
        if (request.startAfterId() != null) {
            builder.queryParam("startAfterId", request.startAfterId());
        }
        if (request.facilityRef() != null) {
            builder.queryParam("facilityRef", request.facilityRef());
        }

        return transport.executeAsync(builder.build()).thenApply(response -> {
            StockListResponse body = responseHandler.handle(response, StockListResponse.class);
            return new Page<>(body.stocks() != null ? body.stocks() : List.of(), body.nextCursor());
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
