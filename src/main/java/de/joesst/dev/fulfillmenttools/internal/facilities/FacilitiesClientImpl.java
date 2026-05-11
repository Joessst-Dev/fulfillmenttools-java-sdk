package de.joesst.dev.fulfillmenttools.internal.facilities;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.facilities.CreateFacilityRequest;
import de.joesst.dev.fulfillmenttools.facilities.FacilitiesClient;
import de.joesst.dev.fulfillmenttools.facilities.Facility;
import de.joesst.dev.fulfillmenttools.facilities.FacilityListRequest;
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
    public Facility get(String facilityId) {
        SdkHttpRequest request = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/facilities/" + facilityId)
                .build();
        return responseHandler.handle(execute(request), Facility.class);
    }

    @Override
    public Page<Facility> list(FacilityListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/facilities");

        if (request.size() != null) {
            builder.queryParam("size", String.valueOf(request.size()));
        }
        if (request.startAfterId() != null) {
            builder.queryParam("startAfterId", request.startAfterId());
        }

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
    public Facility create(CreateFacilityRequest request) {
        CreateFacilityBody body = new CreateFacilityBody(request.name(), request.tenantFacilityId());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/facilities")
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), Facility.class);
    }

    @Override
    public Facility update(String facilityId, UpdateFacilityRequest request) {
        UpdateFacilityBody body = new UpdateFacilityBody(request.name(), request.status());
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/facilities/" + facilityId)
                .body(responseHandler.encode(body))
                .build();
        return responseHandler.handle(execute(httpRequest), Facility.class);
    }

    @Override
    public void delete(String facilityId) {
        SdkHttpRequest httpRequest = SdkHttpRequest.builder()
                .method(HttpMethod.DELETE)
                .url(baseUrl + "/api/facilities/" + facilityId)
                .build();
        responseHandler.handleVoid(execute(httpRequest));
    }

    private SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
