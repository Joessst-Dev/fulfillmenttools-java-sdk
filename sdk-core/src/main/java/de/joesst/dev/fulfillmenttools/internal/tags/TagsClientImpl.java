package de.joesst.dev.fulfillmenttools.internal.tags;

import com.fasterxml.jackson.core.type.TypeReference;
import de.joesst.dev.fulfillmenttools.TransportException;
import de.joesst.dev.fulfillmenttools.internal.Pages;
import de.joesst.dev.fulfillmenttools.internal.http.HttpMethod;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.internal.http.SdkHttpRequest;
import de.joesst.dev.fulfillmenttools.id.TagId;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.tags.CreateTagRequest;
import de.joesst.dev.fulfillmenttools.tags.NeedsPacking;
import de.joesst.dev.fulfillmenttools.tags.Tag;
import de.joesst.dev.fulfillmenttools.tags.TagListRequest;
import de.joesst.dev.fulfillmenttools.tags.TagSearchRequest;
import de.joesst.dev.fulfillmenttools.tags.TagsClient;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class TagsClientImpl implements TagsClient {

    private static final TypeReference<List<NeedsPacking>> NEEDS_PACKING_LIST =
            new TypeReference<>() {};

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    public TagsClientImpl(HttpTransport transport, ResponseHandler responseHandler, String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    @Override
    public Tag get(TagId tagId) {
        return responseHandler.handle(execute(SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/tags/" + tagId.value())
                .build()), Tag.class);
    }

    @Override
    public CompletableFuture<Tag> getAsync(TagId tagId) {
        return transport.executeAsync(SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/tags/" + tagId.value())
                .build()).thenApply(r -> responseHandler.handle(r, Tag.class));
    }

    @Override
    public Page<Tag> list(TagListRequest request) {
        return toPage(responseHandler.handle(execute(buildListRequest(request)), TagListResponse.class));
    }

    @Override
    public Iterable<Tag> listAll(TagListRequest request) {
        return () -> new java.util.Iterator<>() {
            private final java.util.ArrayDeque<Tag> buffer = new java.util.ArrayDeque<>();
            private String cursor = null;
            private boolean exhausted = false;
            private int accumulated = 0;

            private void loadNext() {
                TagListRequest r = cursor == null ? request : request.toBuilder().startAfterId(cursor).build();
                TagListResponse resp = responseHandler.handle(execute(buildListRequest(r)), TagListResponse.class);
                List<Tag> items = resp.tags() != null ? resp.tags() : List.of();
                buffer.addAll(items);
                accumulated += items.size();
                boolean done = items.isEmpty() || (resp.total() != null && accumulated >= resp.total());
                if (done) { exhausted = true; } else { cursor = items.getLast().id().value(); }
            }

            @Override public boolean hasNext() {
                if (!buffer.isEmpty()) return true;
                if (exhausted) return false;
                loadNext();
                return !buffer.isEmpty();
            }

            @Override public Tag next() {
                if (!hasNext()) throw new java.util.NoSuchElementException();
                return buffer.poll();
            }
        };
    }

    @Override
    public CompletableFuture<Page<Tag>> listAsync(TagListRequest request) {
        return transport.executeAsync(buildListRequest(request))
                .thenApply(r -> toPage(responseHandler.handle(r, TagListResponse.class)));
    }

    @Override
    public Tag create(CreateTagRequest request) {
        return responseHandler.handle(execute(buildCreateRequest(request)), Tag.class);
    }

    @Override
    public CompletableFuture<Tag> createAsync(CreateTagRequest request) {
        return transport.executeAsync(buildCreateRequest(request))
                .thenApply(r -> responseHandler.handle(r, Tag.class));
    }

    @Override
    public Tag addAllowedValue(TagId tagId, String allowedValue, Integer version) {
        return responseHandler.handle(execute(buildAddAllowedValueRequest(tagId, allowedValue, version)), Tag.class);
    }

    @Override
    public CompletableFuture<Tag> addAllowedValueAsync(TagId tagId, String allowedValue, Integer version) {
        return transport.executeAsync(buildAddAllowedValueRequest(tagId, allowedValue, version))
                .thenApply(r -> responseHandler.handle(r, Tag.class));
    }

    @Override
    public Page<Tag> search(TagSearchRequest request) {
        return toSearchPage(responseHandler.handle(execute(buildSearchRequest(request)), TagSearchResponse.class));
    }

    @Override
    public Iterable<Tag> searchAll(TagSearchRequest request) {
        return Pages.all(cursor -> {
            TagSearchRequest r = cursor == null ? request : request.toBuilder().after(cursor).build();
            return search(r);
        });
    }

    @Override
    public CompletableFuture<Page<Tag>> searchAsync(TagSearchRequest request) {
        return transport.executeAsync(buildSearchRequest(request))
                .thenApply(r -> toSearchPage(responseHandler.handle(r, TagSearchResponse.class)));
    }

    @Override
    public List<NeedsPacking> needsPacking(List<TagReference> tagRefs) {
        return responseHandler.handle(execute(buildNeedsPackingRequest(tagRefs)), NEEDS_PACKING_LIST);
    }

    @Override
    public CompletableFuture<List<NeedsPacking>> needsPackingAsync(List<TagReference> tagRefs) {
        return transport.executeAsync(buildNeedsPackingRequest(tagRefs))
                .thenApply(r -> responseHandler.handle(r, NEEDS_PACKING_LIST));
    }

    private SdkHttpRequest buildListRequest(TagListRequest request) {
        SdkHttpRequest.Builder builder = SdkHttpRequest.builder()
                .method(HttpMethod.GET)
                .url(baseUrl + "/api/tags");
        if (request.size() != null) builder.queryParam("size", String.valueOf(request.size()));
        if (request.startAfterId() != null) builder.queryParam("startAfterId", request.startAfterId());
        return builder.build();
    }

    private SdkHttpRequest buildCreateRequest(CreateTagRequest request) {
        return SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/tags")
                .body(responseHandler.encode(new CreateTagBody(request.id(), request.allowedValues())))
                .build();
    }

    private SdkHttpRequest buildAddAllowedValueRequest(TagId tagId, String allowedValue, Integer version) {
        var body = new AddAllowedValueBody(version,
                List.of(new AddAllowedValueBody.ActionItem("AddAllowedValueToTag", allowedValue)));
        return SdkHttpRequest.builder()
                .method(HttpMethod.PATCH)
                .url(baseUrl + "/api/tags/" + tagId.value())
                .body(responseHandler.encode(body))
                .build();
    }

    private SdkHttpRequest buildSearchRequest(TagSearchRequest request) {
        return SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/tags/search")
                .body(responseHandler.encode(new TagSearchBody(
                        request.query(), request.size(), request.after(), request.before(), request.last())))
                .build();
    }

    private SdkHttpRequest buildNeedsPackingRequest(List<TagReference> tagRefs) {
        return SdkHttpRequest.builder()
                .method(HttpMethod.POST)
                .url(baseUrl + "/api/tags/packing/needspacking")
                .body(responseHandler.encode(tagRefs))
                .build();
    }

    private Page<Tag> toPage(TagListResponse resp) {
        List<Tag> items = resp.tags() != null ? resp.tags() : List.of();
        String cursor = null;
        if (!items.isEmpty()) {
            boolean couldHaveMore = resp.total() == null || items.size() < resp.total();
            if (couldHaveMore) cursor = items.getLast().id().value();
        }
        return new Page<>(items, cursor);
    }

    private Page<Tag> toSearchPage(TagSearchResponse resp) {
        List<Tag> items = resp.tags() != null ? resp.tags() : List.of();
        String cursor = resp.pageInfo() != null ? resp.pageInfo().endCursor() : null;
        return new Page<>(items, cursor);
    }

    private de.joesst.dev.fulfillmenttools.internal.http.SdkHttpResponse execute(SdkHttpRequest request) {
        try {
            return transport.execute(request);
        } catch (IOException e) {
            throw new TransportException("HTTP request failed", e);
        }
    }
}
