package de.joesst.dev.fulfillmenttools.internal.facilitygroups;

import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.facilitygroups.CreateFacilityGroupRequest;
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroup;
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroupListRequest;
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroupSearchRequest;
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroupsClient;
import de.joesst.dev.fulfillmenttools.facilitygroups.UpdateFacilityGroupRequest;
import de.joesst.dev.fulfillmenttools.id.FacilityGroupId;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
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

public final class FacilityGroupsClientImpl implements FacilityGroupsClient {

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    public FacilityGroupsClientImpl(HttpTransport transport, ResponseHandler responseHandler, String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    @Override
    public FacilityGroup get(FacilityGroupId facilityGroupId) {
        return responseHandler.handle(execute(SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/facilitygroups/" + facilityGroupId.value())
                .build()), FacilityGroup.class);
    }

    @Override
    public CompletableFuture<FacilityGroup> getAsync(FacilityGroupId facilityGroupId) {
        return transport.executeAsync(SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/facilitygroups/" + facilityGroupId.value())
                .build()).thenApply(r -> responseHandler.handle(r, FacilityGroup.class));
    }

    @Override
    public Page<FacilityGroup> list(FacilityGroupListRequest request) {
        return toPage(responseHandler.handle(execute(buildListRequest(request)), FacilityGroupListResponse.class));
    }

    @Override
    public Iterable<FacilityGroup> listAll(FacilityGroupListRequest request) {
        return Pages.all(cursor -> {
            FacilityGroupListRequest r = cursor == null
                    ? request
                    : request.toBuilder().startAfterId(cursor).build();
            return list(r);
        });
    }

    @Override
    public CompletableFuture<Page<FacilityGroup>> listAsync(FacilityGroupListRequest request) {
        return transport.executeAsync(buildListRequest(request))
                .thenApply(r -> toPage(responseHandler.handle(r, FacilityGroupListResponse.class)));
    }

    @Override
    public FacilityGroup create(CreateFacilityGroupRequest request) {
        return responseHandler.handle(execute(buildCreateRequest(request)), FacilityGroup.class);
    }

    @Override
    public CompletableFuture<FacilityGroup> createAsync(CreateFacilityGroupRequest request) {
        return transport.executeAsync(buildCreateRequest(request))
                .thenApply(r -> responseHandler.handle(r, FacilityGroup.class));
    }

    @Override
    public FacilityGroup update(FacilityGroupId facilityGroupId, UpdateFacilityGroupRequest request) {
        return responseHandler.handle(execute(buildUpdateRequest(facilityGroupId, request)), FacilityGroup.class);
    }

    @Override
    public CompletableFuture<FacilityGroup> updateAsync(FacilityGroupId facilityGroupId, UpdateFacilityGroupRequest request) {
        return transport.executeAsync(buildUpdateRequest(facilityGroupId, request))
                .thenApply(r -> responseHandler.handle(r, FacilityGroup.class));
    }

    @Override
    public void delete(FacilityGroupId facilityGroupId) {
        responseHandler.handleVoid(execute(SdkHttpRequest.builder()
                .method(HttpMethod.DELETE)
                .url(baseUrl + "/api/facilitygroups/" + facilityGroupId.value())
                .build()));
    }

    @Override
    public CompletableFuture<Void> deleteAsync(FacilityGroupId facilityGroupId) {
        return transport.executeAsync(SdkHttpRequest.builder()
                .method(HttpMethod.DELETE)
                .url(baseUrl + "/api/facilitygroups/" + facilityGroupId.value())
                .build()).thenApply(r -> { responseHandler.handleVoid(r); return null; });
    }

    @Override
    public FacilityGroup addFacilities(FacilityGroupId facilityGroupId, List<FacilityId> facilityIds, Integer version) {
        return responseHandler.handle(execute(buildAddFacilitiesRequest(facilityGroupId, facilityIds, version)), FacilityGroup.class);
    }

    @Override
    public CompletableFuture<FacilityGroup> addFacilitiesAsync(FacilityGroupId facilityGroupId, List<FacilityId> facilityIds, Integer version) {
        return transport.executeAsync(buildAddFacilitiesRequest(facilityGroupId, facilityIds, version))
                .thenApply(r -> responseHandler.handle(r, FacilityGroup.class));
    }

    @Override
    public FacilityGroup removeFacilities(FacilityGroupId facilityGroupId, List<FacilityId> facilityIds, Integer version) {
        return responseHandler.handle(execute(buildRemoveFacilitiesRequest(facilityGroupId, facilityIds, version)), FacilityGroup.class);
    }

    @Override
    public CompletableFuture<FacilityGroup> removeFacilitiesAsync(FacilityGroupId facilityGroupId, List<FacilityId> facilityIds, Integer version) {
        return transport.executeAsync(buildRemoveFacilitiesRequest(facilityGroupId, facilityIds, version))
                .thenApply(r -> responseHandler.handle(r, FacilityGroup.class));
    }

    @Override
    public Page<FacilityGroup> search(FacilityGroupSearchRequest request) {
        return toSearchPage(responseHandler.handle(execute(buildSearchRequest(request)), FacilityGroupSearchResponse.class));
    }

    @Override
    public Iterable<FacilityGroup> searchAll(FacilityGroupSearchRequest request) {
        return Pages.all(cursor -> {
            FacilityGroupSearchRequest r = cursor == null
                    ? request
                    : request.toBuilder().after(cursor).build();
            return search(r);
        });
    }

    @Override
    public CompletableFuture<Page<FacilityGroup>> searchAsync(FacilityGroupSearchRequest request) {
        return transport.executeAsync(buildSearchRequest(request))
                .thenApply(r -> toSearchPage(responseHandler.handle(r, FacilityGroupSearchResponse.class)));
    }

    private SdkHttpRequest buildListRequest(FacilityGroupListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/facilitygroups");
        if (request.size() != null) builder.queryParam("size", String.valueOf(request.size()));
        if (request.startAfterId() != null) builder.queryParam("startAfterId", request.startAfterId());
        return builder.build();
    }

    private SdkHttpRequest buildCreateRequest(CreateFacilityGroupRequest request) {
        List<String> facilityRefs = request.facilityRefs().stream().map(FacilityId::value).toList();
        return SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/facilitygroups")
                .body(responseHandler.encode(new CreateFacilityGroupBody(
                        request.tenantFacilityGroupId(),
                        facilityRefs,
                        request.nameLocalized(),
                        request.customAttributes())))
                .build();
    }

    private SdkHttpRequest buildUpdateRequest(FacilityGroupId facilityGroupId, UpdateFacilityGroupRequest request) {
        List<String> facilityRefs = request.facilityRefs() != null
                ? request.facilityRefs().stream().map(FacilityId::value).toList()
                : null;
        return SdkHttpRequest.builder()
                .method(HttpMethod.PUT)
                .url(baseUrl + "/api/facilitygroups/" + facilityGroupId.value())
                .body(responseHandler.encode(new UpdateFacilityGroupBody(
                        request.version(),
                        request.tenantFacilityGroupId(),
                        facilityRefs,
                        request.nameLocalized(),
                        request.customAttributes())))
                .build();
    }

    private SdkHttpRequest buildAddFacilitiesRequest(FacilityGroupId facilityGroupId, List<FacilityId> facilityIds, Integer version) {
        String actionName = version == null ? "VERSIONLESS_ADD_FACILITIES_TO_GROUP" : "ADD_FACILITIES_TO_GROUP";
        List<String> facilityRefs = facilityIds.stream().map(FacilityId::value).toList();
        return SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/facilitygroups/" + facilityGroupId.value() + "/actions")
                .body(responseHandler.encode(new AddFacilitiesToGroupBody(actionName, facilityRefs, version)))
                .build();
    }

    private SdkHttpRequest buildRemoveFacilitiesRequest(FacilityGroupId facilityGroupId, List<FacilityId> facilityIds, Integer version) {
        String actionName = version == null ? "VERSIONLESS_REMOVE_FACILITIES_FROM_GROUP" : "REMOVE_FACILITIES_FROM_GROUP";
        List<String> facilityRefs = facilityIds.stream().map(FacilityId::value).toList();
        return SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/facilitygroups/" + facilityGroupId.value() + "/actions")
                .body(responseHandler.encode(new RemoveFacilitiesFromGroupBody(actionName, facilityRefs, version)))
                .build();
    }

    private SdkHttpRequest buildSearchRequest(FacilityGroupSearchRequest request) {
        return SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/facilitygroups/search")
                .body(responseHandler.encode(new FacilityGroupSearchBody(
                        request.query(),
                        request.size(),
                        request.after(),
                        request.before())))
                .build();
    }

    private Page<FacilityGroup> toPage(FacilityGroupListResponse resp) {
        List<FacilityGroup> items = resp.facilityGroups() != null ? resp.facilityGroups() : List.of();
        String cursor = resp.pageInfo() != null ? resp.pageInfo().endCursor() : null;
        return new Page<>(items, cursor);
    }

    private Page<FacilityGroup> toSearchPage(FacilityGroupSearchResponse resp) {
        List<FacilityGroup> items = resp.facilityGroups() != null ? resp.facilityGroups() : List.of();
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
