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

## API Reference

### get(TagId)

Get a tag by ID.

**Returns:** `Tag`

### list(TagListRequest)

List tags with pagination.

**Returns:** `Page<Tag>`

### listAll(TagListRequest)

List all tags.

**Returns:** `Iterable<Tag>`
