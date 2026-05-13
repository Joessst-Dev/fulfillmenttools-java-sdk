# User Management Client

The User Management client manages user accounts and permissions. Create, query, and manage users in your fulfillmenttools project.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.UserId;

// Get a user
User user = client.users().get(new UserId("usr-001"));
System.out.println("Email: " + user.getEmail());
```

## Listing Users

```java
Page<User> page = client.users().list(
    UserListRequest.builder()
        .size(50)
        .build()
);
```

## API Reference

### get(UserId)

Get a user by ID.

**Returns:** `User`

### list(UserListRequest)

List users with pagination.

**Returns:** `Page<User>`

### listAll(UserListRequest)

List all users.

**Returns:** `Iterable<User>`
