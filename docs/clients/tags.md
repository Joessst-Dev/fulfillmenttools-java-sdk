# Tags Client

The Tags client manages tagging operations in the fulfillmenttools platform. Tags are flexible, named entities with a defined set of allowed values that can be attached to resources such as facilities and listings.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.TagId;
import de.joesst.dev.fulfillmenttools.tags.Tag;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

// Get a tag by ID
try {
    Tag tag = client.tags().get(TagId.builder().value("color").build());
    System.out.println("Tag: " + tag.id().value());
    System.out.println("Allowed values: " + tag.allowedValues());
} catch (NotFoundException e) {
    System.out.println("Tag not found");
} catch (FulfillmenttoolsException e) {
    System.out.println("Request failed: " + e.getMessage());
}
```

## Listing Tags

List tags with pagination:

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.tags.TagListRequest;

Page<Tag> page = client.tags().list(
    TagListRequest.builder()
        .size(50)
        .build()
);

for (Tag tag : page.items()) {
    System.out.println(tag.id().value() + ": " + tag.allowedValues());
}
```

Iterate through all pages automatically:

```java
Iterable<Tag> allTags = client.tags().listAll(
    TagListRequest.builder()
        .size(100)
        .build()
);

for (Tag tag : allTags) {
    System.out.println(tag.id().value());
}
```

Manual pagination using `nextCursor()`:

```java
Page<Tag> page = client.tags().list(TagListRequest.builder().size(20).build());

while (page.hasMore()) {
    page = client.tags().list(
        TagListRequest.builder()
            .size(20)
            .startAfterId(page.nextCursor())
            .build()
    );
    for (Tag tag : page.items()) {
        System.out.println(tag.id().value());
    }
}
```

## Creating Tags

```java
import de.joesst.dev.fulfillmenttools.tags.CreateTagRequest;
import java.util.List;

Tag tag = client.tags().create(
    CreateTagRequest.builder()
        .id("color")
        .allowedValues(List.of("red", "blue", "green"))
        .build()
);
System.out.println("Created tag: " + tag.id().value());
```

## Adding Allowed Values

Add a new allowed value to an existing tag. Pass the current `version` for optimistic locking:

```java
Tag tag = client.tags().get(TagId.builder().value("color").build());

Tag updated = client.tags().addAllowedValue(
    TagId.builder().value("color").build(),
    "yellow",
    tag.version()
);
System.out.println("Updated allowed values: " + updated.allowedValues());
```

## Searching Tags

Search for tags using `TagSearchQuery` with flexible filter operators:

```java
import de.joesst.dev.fulfillmenttools.tags.TagSearchRequest;
import de.joesst.dev.fulfillmenttools.tags.TagSearchQuery;

// Find tags whose ID matches a pattern
Page<Tag> results = client.tags().search(
    TagSearchRequest.builder()
        .query(TagSearchQuery.builder().idLike("col*").build())
        .size(50)
        .build()
);

for (Tag tag : results.items()) {
    System.out.println(tag.id().value());
}
```

Search with multiple criteria using AND/OR:

```java
// Find tags that have "red" or "blue" as an allowed value
Page<Tag> results = client.tags().search(
    TagSearchRequest.builder()
        .query(
            TagSearchQuery.builder()
                .or(
                    TagSearchQuery.builder().allowedValuesEq("red").build(),
                    TagSearchQuery.builder().allowedValuesEq("blue").build()
                )
                .build()
        )
        .size(50)
        .build()
);
```

Iterate through all search results automatically:

```java
Iterable<Tag> allMatches = client.tags().searchAll(
    TagSearchRequest.builder()
        .query(TagSearchQuery.builder().idLike("col*").build())
        .build()
);
```

## Checking Packing Requirements

Determine whether tags require packing by passing a list of `TagReference` objects:

```java
import de.joesst.dev.fulfillmenttools.model.TagReference;
import de.joesst.dev.fulfillmenttools.tags.NeedsPacking;
import java.util.List;

List<NeedsPacking> results = client.tags().needsPacking(
    List.of(
        TagReference.builder()
            .id(TagId.builder().value("color").build())
            .value("red")
            .build(),
        TagReference.builder()
            .id(TagId.builder().value("size").build())
            .value("large")
            .build()
    )
);

for (NeedsPacking result : results) {
    System.out.println("Needs packing: " + result.needsPacking());
}
```

## Async Usage

All methods have async variants returning `CompletableFuture`:

```java
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

CompletableFuture<Tag> future = client.tags().getAsync(
    TagId.builder().value("color").build()
);

future.whenComplete((tag, ex) -> {
    if (ex != null) {
        Throwable cause = ex instanceof CompletionException && ex.getCause() != null
            ? ex.getCause() : ex;
        System.out.println("Error: " + cause.getMessage());
    } else {
        System.out.println("Tag: " + tag.id().value());
    }
});
```

## API Reference

### get(TagId)

Get a single tag by ID.

**Parameters:**
- `tagId: TagId` — The tag identifier

**Returns:** `Tag`

**Throws:** `NotFoundException` (404), `FulfillmenttoolsException` if the request fails

### getAsync(TagId)

Get a tag asynchronously.

**Parameters:**
- `tagId: TagId` — The tag identifier

**Returns:** `CompletableFuture<Tag>`

### list(TagListRequest)

List tags with pagination.

**Parameters:**
- `request: TagListRequest` — List request with pagination

**Returns:** `Page<Tag>`

**Throws:** `FulfillmenttoolsException` if the request fails

### listAsync(TagListRequest)

List tags asynchronously.

**Parameters:**
- `request: TagListRequest` — List request with pagination

**Returns:** `CompletableFuture<Page<Tag>>`

### listAll(TagListRequest)

List all tags, automatically iterating through pages.

**Parameters:**
- `request: TagListRequest` — List request

**Returns:** `Iterable<Tag>`

### create(CreateTagRequest)

Create a new tag with a set of allowed values. The `id` field is the tag name (e.g. `"color"`).

**Parameters:**
- `request: CreateTagRequest` — Creation request with `id` and `allowedValues`

**Returns:** `Tag`

**Throws:** `FulfillmenttoolsException` if the request fails

### createAsync(CreateTagRequest)

Create a tag asynchronously.

**Parameters:**
- `request: CreateTagRequest` — Creation request

**Returns:** `CompletableFuture<Tag>`

### addAllowedValue(TagId, String, Integer)

Add an allowed value to an existing tag.

**Parameters:**
- `tagId: TagId` — The tag identifier
- `allowedValue: String` — The value to add
- `version: Integer` — Current optimistic lock version

**Returns:** `Tag`

**Throws:** `FulfillmenttoolsException` if the request fails

### addAllowedValueAsync(TagId, String, Integer)

Add an allowed value to a tag asynchronously.

**Parameters:**
- `tagId: TagId` — The tag identifier
- `allowedValue: String` — The value to add
- `version: Integer` — Current optimistic lock version

**Returns:** `CompletableFuture<Tag>`

### search(TagSearchRequest)

Search tags by criteria, returning one page of results.

**Parameters:**
- `request: TagSearchRequest` — Search request with `query`, `size`, `after`, `before`, and `last`

**Returns:** `Page<Tag>`

**Throws:** `FulfillmenttoolsException` if the request fails

### searchAsync(TagSearchRequest)

Search tags asynchronously.

**Parameters:**
- `request: TagSearchRequest` — Search request

**Returns:** `CompletableFuture<Page<Tag>>`

### searchAll(TagSearchRequest)

Search all tags, automatically iterating through pages.

**Parameters:**
- `request: TagSearchRequest` — Search request

**Returns:** `Iterable<Tag>`

### needsPacking(List&lt;TagReference&gt;)

Determine if the given tag references require packing. Returns one `NeedsPacking` result per input reference, in the same order.

**Parameters:**
- `tagRefs: List<TagReference>` — List of tag references to check

**Returns:** `List<NeedsPacking>`

**Throws:** `FulfillmenttoolsException` if the request fails

### needsPackingAsync(List&lt;TagReference&gt;)

Determine packing requirements for tags asynchronously.

**Parameters:**
- `tagRefs: List<TagReference>` — List of tag references to check

**Returns:** `CompletableFuture<List<NeedsPacking>>`
