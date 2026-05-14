# User Management Client

The User Management client creates, retrieves, lists, updates, and deletes users in your fulfillmenttools project.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.UserId;
import de.joesst.dev.fulfillmenttools.users.User;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

try {
    User user = client.users().get(
        UserId.builder().value("usr-001").build()
    );
    System.out.println("Email: " + user.email());
    System.out.println("Name: " + user.firstName() + " " + user.lastName());
    System.out.println("Status: " + user.status());
} catch (NotFoundException e) {
    System.out.println("User not found");
} catch (FulfillmenttoolsException e) {
    System.out.println("Request failed: " + e.getMessage());
}
```

## Listing Users

List users with optional filters:

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.users.User;
import de.joesst.dev.fulfillmenttools.users.UserListRequest;

Page<User> page = client.users().list(
    UserListRequest.builder()
        .size(50)
        .build()
);

for (User user : page.items()) {
    System.out.println(user.id().value() + " — " + user.email());
}
```

Filter by facility or include admin users:

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.users.User;
import de.joesst.dev.fulfillmenttools.users.UserListRequest;
import de.joesst.dev.fulfillmenttools.id.FacilityId;

Page<User> page = client.users().list(
    UserListRequest.builder()
        .facilityId(FacilityId.builder().value("fac-001").build())
        .includeAdminUsers(true)
        .build()
);
```

Iterate through all users automatically:

```java
import de.joesst.dev.fulfillmenttools.users.User;
import de.joesst.dev.fulfillmenttools.users.UserListRequest;

Iterable<User> allUsers = client.users().listAll(
    UserListRequest.builder()
        .size(100)
        .build()
);

for (User user : allUsers) {
    System.out.println(user.id().value() + " — " + user.username());
}
```

Manual pagination using `nextCursor()`:

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.users.User;
import de.joesst.dev.fulfillmenttools.users.UserListRequest;

Page<User> page = client.users().list(
    UserListRequest.builder().size(20).build()
);

while (page.hasMore()) {
    page = client.users().list(
        UserListRequest.builder()
            .size(20)
            .startAfterId(page.nextCursor())
            .build()
    );
    for (User user : page.items()) {
        System.out.println(user.id().value());
    }
}
```

## Creating a User

`username`, `password`, `firstName`, and `lastName` are required:

```java
import de.joesst.dev.fulfillmenttools.users.User;
import de.joesst.dev.fulfillmenttools.users.CreateUserRequest;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

try {
    User user = client.users().create(
        CreateUserRequest.builder()
            .username("jsmith")
            .password("s3cr3tP@ssw0rd")
            .firstName("John")
            .lastName("Smith")
            .email("john.smith@example.com")
            .locale("en_US")
            .build()
    );
    System.out.println("Created user: " + user.id().value());
} catch (FulfillmenttoolsException e) {
    System.out.println("Creation failed: " + e.getMessage());
}
```

## Updating a User

`version` is required for optimistic locking:

```java
import de.joesst.dev.fulfillmenttools.id.UserId;
import de.joesst.dev.fulfillmenttools.users.User;
import de.joesst.dev.fulfillmenttools.users.UpdateUserRequest;

User user = client.users().get(
    UserId.builder().value("usr-001").build()
);

User updated = client.users().update(
    UserId.builder().value("usr-001").build(),
    UpdateUserRequest.builder()
        .version(user.version())
        .firstName("Jane")
        .build()
);
System.out.println("Updated name: " + updated.firstName());
```

## Deleting a User

```java
import de.joesst.dev.fulfillmenttools.id.UserId;

client.users().delete(
    UserId.builder().value("usr-001").build()
);
```

## Async Usage

All methods have async variants returning `CompletableFuture`:

```java
import de.joesst.dev.fulfillmenttools.id.UserId;
import de.joesst.dev.fulfillmenttools.users.User;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

CompletableFuture<User> future = client.users().getAsync(
    UserId.builder().value("usr-001").build()
);

future.whenComplete((user, ex) -> {
    if (ex != null) {
        Throwable cause = ex instanceof CompletionException && ex.getCause() != null
            ? ex.getCause() : ex;
        System.out.println("Error: " + cause.getMessage());
    } else {
        System.out.println("Email: " + user.email());
        System.out.println("Status: " + user.status());
    }
});
```

## API Reference

### get(UserId)

Get a user by ID.

**Parameters:**
- `userId: UserId` — The user identifier

**Returns:** `User`

**Throws:** `NotFoundException` (404), `FulfillmenttoolsException` if the request fails

### getAsync(UserId)

Get a user by ID asynchronously.

**Parameters:**
- `userId: UserId` — The user identifier

**Returns:** `CompletableFuture<User>`

### list(UserListRequest)

List users with optional filtering and pagination. Filters include `facilityId`, `includeAdminUsers`, and `orderBy`.

**Parameters:**
- `request: UserListRequest` — List request with `size`, `startAfterId` cursor, and optional filters

**Returns:** `Page<User>`

**Throws:** `FulfillmenttoolsException` if the request fails

### listAsync(UserListRequest)

List users asynchronously.

**Parameters:**
- `request: UserListRequest` — List request

**Returns:** `CompletableFuture<Page<User>>`

### listAll(UserListRequest)

List all users, automatically iterating through pages.

**Parameters:**
- `request: UserListRequest` — List request

**Returns:** `Iterable<User>`

### create(CreateUserRequest)

Create a new user. `username`, `password`, `firstName`, and `lastName` are required.

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

Update an existing user. `version` is required for optimistic locking. Updatable fields: `firstName`, `lastName`, `email`, `password`, `locale`, `assignedRoles`.

**Parameters:**
- `userId: UserId` — The user identifier
- `request: UpdateUserRequest` — Update request with current version and new values

**Returns:** `User`

**Throws:** `FulfillmenttoolsException` if the request fails or a version conflict occurs

### updateAsync(UserId, UpdateUserRequest)

Update a user asynchronously.

**Parameters:**
- `userId: UserId` — The user identifier
- `request: UpdateUserRequest` — Update request

**Returns:** `CompletableFuture<User>`

### delete(UserId)

Delete a user.

**Parameters:**
- `userId: UserId` — The user identifier

**Returns:** `void`

**Throws:** `FulfillmenttoolsException` if the request fails

### deleteAsync(UserId)

Delete a user asynchronously.

**Parameters:**
- `userId: UserId` — The user identifier

**Returns:** `CompletableFuture<Void>`
