package de.joesst.dev.fulfillmenttools.tags;

import de.joesst.dev.fulfillmenttools.id.TagId;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.model.TagReference;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Client for managing tags in the fulfillmenttools tags module.
 *
 * <p>Provides synchronous and asynchronous operations to create, retrieve, list, search,
 * and modify tags used to organize and categorize orders and items.
 */
public interface TagsClient {

    /**
     * Retrieves a single tag by ID.
     *
     * @param tagId the tag ID
     * @return the tag
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    Tag get(TagId tagId);

    /**
     * Retrieves a single tag by ID asynchronously.
     *
     * @param tagId the tag ID
     * @return a {@code CompletableFuture} that resolves to the tag
     */
    CompletableFuture<Tag> getAsync(TagId tagId);

    /**
     * Lists tags, returning one page of results.
     *
     * @param request the list request with pagination
     * @return a page of tags
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    Page<Tag> list(TagListRequest request);

    /**
     * Lists all tags by automatically iterating through pages.
     *
     * @param request the list request
     * @return an {@code Iterable} over all tags
     */
    Iterable<Tag> listAll(TagListRequest request);

    /**
     * Lists tags asynchronously, returning one page of results.
     *
     * @param request the list request with pagination
     * @return a {@code CompletableFuture} that resolves to a page of tags
     */
    CompletableFuture<Page<Tag>> listAsync(TagListRequest request);

    /**
     * Creates a new tag with allowed values.
     *
     * @param request the creation request
     * @return the created tag
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    Tag create(CreateTagRequest request);

    /**
     * Creates a new tag with allowed values asynchronously.
     *
     * @param request the creation request
     * @return a {@code CompletableFuture} that resolves to the created tag
     */
    CompletableFuture<Tag> createAsync(CreateTagRequest request);

    /**
     * Adds an allowed value to an existing tag.
     *
     * @param tagId        the tag ID
     * @param allowedValue the value to add
     * @param version      the current optimistic lock version of the tag
     * @return the updated tag
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    Tag addAllowedValue(TagId tagId, String allowedValue, Integer version);

    /**
     * Adds an allowed value to an existing tag asynchronously.
     *
     * @param tagId        the tag ID
     * @param allowedValue the value to add
     * @param version      the current optimistic lock version of the tag
     * @return a {@code CompletableFuture} that resolves to the updated tag
     */
    CompletableFuture<Tag> addAllowedValueAsync(TagId tagId, String allowedValue, Integer version);

    /**
     * Searches for tags matching the specified criteria, returning one page of results.
     *
     * @param request the search request with query and pagination
     * @return a page of matching tags
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    Page<Tag> search(TagSearchRequest request);

    /**
     * Searches for all tags matching the specified criteria by automatically iterating through pages.
     *
     * @param request the search request with query
     * @return an {@code Iterable} over all matching tags
     */
    Iterable<Tag> searchAll(TagSearchRequest request);

    /**
     * Searches for tags matching the specified criteria asynchronously, returning one page of results.
     *
     * @param request the search request with query and pagination
     * @return a {@code CompletableFuture} that resolves to a page of matching tags
     */
    CompletableFuture<Page<Tag>> searchAsync(TagSearchRequest request);

    /**
     * Determines if the given tags need packing.
     *
     * @param tagRefs list of tag references to check
     * @return a list of {@code NeedsPacking} results corresponding to each tag reference
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    List<NeedsPacking> needsPacking(List<TagReference> tagRefs);

    /**
     * Determines if the given tags need packing asynchronously.
     *
     * @param tagRefs list of tag references to check
     * @return a {@code CompletableFuture} that resolves to a list of {@code NeedsPacking} results
     */
    CompletableFuture<List<NeedsPacking>> needsPackingAsync(List<TagReference> tagRefs);
}
