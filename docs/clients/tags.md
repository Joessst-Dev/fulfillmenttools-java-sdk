# Tags Client

The Tags client manages tagging operations. Organize and categorize resources with flexible tagging.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.TagId;

// Get a tag
Tag tag = client.tags().get(new TagId("tag-001"));
System.out.println("Name: " + tag.getName());
```

## Listing Tags

```java
Page<Tag> page = client.tags().list(
    TagListRequest.builder()
        .size(50)
        .build()
);
```

## Creating and Managing Tags

```java
Tag tag = client.tags().create(
    CreateTagRequest.builder()
        .name("Color")
        .allowedValues(Arrays.asList("Red", "Blue", "Green"))
        .build()
);

// Add an allowed value to a tag
Tag updated = client.tags().addAllowedValue(
    new TagId("tag-001"),
    "Yellow",
    1  // current version
);
```

## Searching Tags

```java
Page<Tag> results = client.tags().search(
    TagSearchRequest.builder()
        .name("Color")
        .build()
);
```

## Checking Packing Requirements

```java
List<NeedsPacking> packingRequirements = client.tags().needsPacking(
    Arrays.asList(
        new TagReference(new TagId("tag-001"), "Red"),
        new TagReference(new TagId("tag-002"), "Large")
    )
);
```

## API Reference

### get(TagId)

Get a tag by ID.

**Parameters:**
- `tagId: TagId` — The tag ID

**Returns:** `Tag`

**Throws:** `FulfillmenttoolsException` if not found

### getAsync(TagId)

Get a tag asynchronously.

**Parameters:**
- `tagId: TagId` — The tag ID

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

Create a new tag with allowed values.

**Parameters:**
- `request: CreateTagRequest` — Creation request

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
- `tagId: TagId` — The tag ID
- `allowedValue: String` — The value to add
- `version: Integer` — Current optimistic lock version

**Returns:** `Tag`

**Throws:** `FulfillmenttoolsException` if the request fails

### addAllowedValueAsync(TagId, String, Integer)

Add an allowed value to a tag asynchronously.

**Parameters:**
- `tagId: TagId` — The tag ID
- `allowedValue: String` — The value to add
- `version: Integer` — Current optimistic lock version

**Returns:** `CompletableFuture<Tag>`

### search(TagSearchRequest)

Search tags by criteria, returning one page of results.

**Parameters:**
- `request: TagSearchRequest` — Search request with query and pagination

**Returns:** `Page<Tag>`

**Throws:** `FulfillmenttoolsException` if the request fails

### searchAsync(TagSearchRequest)

Search tags asynchronously.

**Parameters:**
- `request: TagSearchRequest` — Search request with query and pagination

**Returns:** `CompletableFuture<Page<Tag>>`

### searchAll(TagSearchRequest)

Search all tags, automatically iterating through pages.

**Parameters:**
- `request: TagSearchRequest` — Search request with query

**Returns:** `Iterable<Tag>`

### needsPacking(List<TagReference>)

Determine if the given tags need packing.

**Parameters:**
- `tagRefs: List<TagReference>` — List of tag references to check

**Returns:** `List<NeedsPacking>`

**Throws:** `FulfillmenttoolsException` if the request fails

### needsPackingAsync(List<TagReference>)

Determine packing requirements for tags asynchronously.

**Parameters:**
- `tagRefs: List<TagReference>` — List of tag references to check

**Returns:** `CompletableFuture<List<NeedsPacking>>`
