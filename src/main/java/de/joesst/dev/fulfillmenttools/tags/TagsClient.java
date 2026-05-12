package de.joesst.dev.fulfillmenttools.tags;

import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.model.TagReference;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface TagsClient {

    Tag get(String tagId);
    CompletableFuture<Tag> getAsync(String tagId);

    Page<Tag> list(TagListRequest request);
    Iterable<Tag> listAll(TagListRequest request);
    CompletableFuture<Page<Tag>> listAsync(TagListRequest request);

    Tag create(CreateTagRequest request);
    CompletableFuture<Tag> createAsync(CreateTagRequest request);

    Tag addAllowedValue(String tagId, String allowedValue, Integer version);
    CompletableFuture<Tag> addAllowedValueAsync(String tagId, String allowedValue, Integer version);

    Page<Tag> search(TagSearchRequest request);
    Iterable<Tag> searchAll(TagSearchRequest request);
    CompletableFuture<Page<Tag>> searchAsync(TagSearchRequest request);

    List<NeedsPacking> needsPacking(List<TagReference> tagRefs);
    CompletableFuture<List<NeedsPacking>> needsPackingAsync(List<TagReference> tagRefs);
}
