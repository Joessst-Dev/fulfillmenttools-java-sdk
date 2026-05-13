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

## Creating and Managing Users

```java
User user = client.users().create(
    CreateUserRequest.builder()
        .email("user@example.com")
        .firstName("John")
        .lastName("Doe")
        .build()
);

// Update a user
User updated = client.users().update(
    new UserId("usr-001"),
    UpdateUserRequest.builder()
        .firstName("Jane")
        .build()
);

// Delete a user
client.users().delete(new UserId("usr-001"));
```

## API Reference

### get(UserId)

Get a user by ID.

**Parameters:**
- `userId: UserId` — The user ID

**Returns:** `User`

**Throws:** `FulfillmenttoolsException` if not found

### getAsync(UserId)

Get a user asynchronously.

**Parameters:**
- `userId: UserId` — The user ID

**Returns:** `CompletableFuture<User>`

### list(UserListRequest)

List users with pagination.

**Parameters:**
- `request: UserListRequest` — List request with filters and pagination

**Returns:** `Page<User>`

**Throws:** `FulfillmenttoolsException` if the request fails

### listAsync(UserListRequest)

List users asynchronously.

**Parameters:**
- `request: UserListRequest` — List request with filters and pagination

**Returns:** `CompletableFuture<Page<User>>`

### listAll(UserListRequest)

List all users, automatically iterating through pages.

**Parameters:**
- `request: UserListRequest` — List request with filters

**Returns:** `Iterable<User>`

### create(CreateUserRequest)

Create a new user.

**Parameters:**
- `request: CreateUserRequest` — Creation request

**Returns:** `User`

**Throws:** `FulfillmenttoolsException` if the request fails

### createAsync(CreateUserRequest)

Create a user asynchronously.

**Parameters:**
- `request: CreateUserRequest` — Creation request

**Returns:** `CompletableFuture<User>`

### update(UserId, UpdateUserRequest)

Update an existing user.

**Parameters:**
- `userId: UserId` — The user ID to update
- `request: UpdateUserRequest` — Update request

**Returns:** `User`

**Throws:** `FulfillmenttoolsException` if the request fails

### updateAsync(UserId, UpdateUserRequest)

Update a user asynchronously.

**Parameters:**
- `userId: UserId` — The user ID to update
- `request: UpdateUserRequest` — Update request

**Returns:** `CompletableFuture<User>`

### delete(UserId)

Delete a user.

**Parameters:**
- `userId: UserId` — The user ID to delete

**Throws:** `FulfillmenttoolsException` if the request fails

### deleteAsync(UserId)

Delete a user asynchronously.

**Parameters:**
- `userId: UserId` — The user ID to delete

**Returns:** `CompletableFuture<Void>`
