# Facilities Client

The Facilities client provides access to facility information and management in the fulfillmenttools platform. Retrieve and manage fulfillment centers and warehouses.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;

// Get a facility by ID
FacilityId facilityId = FacilityId.builder().value("fac-001").build();
Facility facility = client.facilities().get(facilityId);
System.out.println(facility.name());

// Get asynchronously
CompletableFuture<Facility> futureFacility = client.facilities()
    .getAsync(facilityId);
```

## Listing Facilities

List facilities with pagination:

```java
Page<Facility> page = client.facilities().list(
    FacilityListRequest.builder()
        .size(50)
        .build()
);

for (Facility facility : page.items()) {
    System.out.println(facility.name());
}
```

Iterate through all facilities:

```java
Iterable<Facility> allFacilities = client.facilities().listAll(
    FacilityListRequest.builder()
        .size(100)
        .build()
);

for (Facility facility : allFacilities) {
    System.out.println(facility.name());
}
```

Filter by status and type:

```java
Page<Facility> page = client.facilities().list(
    FacilityListRequest.builder()
        .status(List.of("ONLINE", "SUSPENDED"))
        .type(List.of("MANAGED_FACILITY"))
        .size(50)
        .build()
);
```

## Searching Facilities

Search for facilities using a complex query with multiple criteria:

```java
FacilitySearchQuery query = FacilitySearchQuery.builder()
    .nameLike("Berlin*")
    .statusIn("ONLINE", "SUSPENDED")
    .cityEq("Berlin")
    .build();

Page<Facility> results = client.facilities().search(
    FacilitySearchRequest.builder()
        .query(query)
        .size(25)
        .build()
);

for (Facility facility : results.items()) {
    System.out.println(facility.name() + " (" + facility.status() + ")");
}
```

### Search Query Filters

The `FacilitySearchQuery` builder provides typed methods for filtering:

```java
FacilitySearchQuery query = FacilitySearchQuery.builder()
    .idEq("fac-001")                    // Exact ID match
    .nameEq("Main Warehouse")            // Exact name match
    .nameLike("Berlin*")                 // Pattern match on name
    .statusIn("ONLINE", "SUSPENDED")     // Status in list
    .typeIn("MANAGED_FACILITY")          // Type in list
    .locationTypeEq("WAREHOUSE")         // Location type match
    .cityEq("Berlin")                    // Address city match
    .countryEq("DE")                     // Address country match
    .build();
```

Iterate through all search results with automatic pagination:

```java
Iterable<Facility> allResults = client.facilities().searchAll(
    FacilitySearchRequest.builder()
        .query(query)
        .size(100)
        .build()
);
```

## Creating a Facility

```java
CreateFacilityRequest request = CreateFacilityRequest.builder()
    .name("Berlin Warehouse")
    .status("ONLINE")
    .type("MANAGED_FACILITY")
    .locationType("WAREHOUSE")
    .build();

Facility created = client.facilities().create(request);
System.out.println("Created facility: " + created.id());
```

With full details:

```java
import de.joesst.dev.fulfillmenttools.facilities.FacilityAddress;

FacilityAddress address = FacilityAddress.builder()
    .street("123 Main St")
    .city("Berlin")
    .postalCode("10115")
    .country("DE")
    .build();

CreateFacilityRequest request = CreateFacilityRequest.builder()
    .name("Berlin Warehouse")
    .status("ONLINE")
    .type("MANAGED_FACILITY")
    .locationType("WAREHOUSE")
    .address(address)
    .pickingMethods(List.of("FIFO", "LIFO"))
    .capacityEnabled(true)
    .capacityPlanningTimeframe(30)
    .build();

Facility created = client.facilities().create(request);
```

## Updating a Facility

Partial updates — only specified fields are changed:

```java
UpdateFacilityRequest request = UpdateFacilityRequest.builder()
    .status("SUSPENDED")
    .build();

FacilityId facilityId = FacilityId.builder().value("fac-001").build();
Facility updated = client.facilities().update(facilityId, request);
```

## Replacing a Facility

Replace all fields of an existing facility:

```java
CreateFacilityRequest request = CreateFacilityRequest.builder()
    .name("New Name")
    .status("ONLINE")
    .type("MANAGED_FACILITY")
    .build();

FacilityId facilityId = FacilityId.builder().value("fac-001").build();
Facility replaced = client.facilities().replace(facilityId, request);
```

## Deleting a Facility

```java
FacilityId facilityId = FacilityId.builder().value("fac-001").build();
client.facilities().delete(facilityId);

// Or with force deletion flag
client.facilities().delete(facilityId, true);
```

## Error Handling

The SDK throws `FulfillmenttoolsException` for any request errors:

```java
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

try {
    FacilityId facilityId = FacilityId.builder().value("nonexistent").build();
    Facility facility = client.facilities().get(facilityId);
} catch (FulfillmenttoolsException e) {
    System.err.println("Error: " + e.getMessage());
    if (e.statusCode() == 404) {
        System.err.println("Facility not found");
    }
}
```

Async error handling:

```java
client.facilities()
    .getAsync(FacilityId.builder().value("fac-001").build())
    .exceptionally(ex -> {
        if (ex instanceof FulfillmenttoolsException) {
            System.err.println("Request failed: " + ex.getMessage());
        }
        return null;
    })
    .thenAccept(facility -> {
        if (facility != null) {
            System.out.println("Got facility: " + facility.name());
        }
    });
```

## API Reference

### get(FacilityId)

Retrieve a single facility by ID.

**Parameters:**
- `facilityId: FacilityId` — The facility identifier

**Returns:** `Facility` — The facility object

**Throws:** `FulfillmenttoolsException` if the facility is not found or a request error occurs

**Example:**
```java
FacilityId id = FacilityId.builder().value("fac-001").build();
Facility facility = client.facilities().get(id);
```

### getAsync(FacilityId)

Asynchronously retrieve a single facility by ID.

**Parameters:**
- `facilityId: FacilityId` — The facility identifier

**Returns:** `CompletableFuture<Facility>` — A future that completes with the facility

**Example:**
```java
FacilityId id = FacilityId.builder().value("fac-001").build();
client.facilities().getAsync(id)
    .thenAccept(f -> System.out.println(f.name()))
    .exceptionally(e -> { System.err.println(e.getMessage()); return null; });
```

### list(FacilityListRequest)

Retrieve a single page of facilities with optional filtering and pagination.

**Parameters:**
- `request: FacilityListRequest` — The list request with size, cursor, status, type, and ordering options

**Returns:** `Page<Facility>` — A page of facilities containing items and pagination metadata

**Throws:** `FulfillmenttoolsException` if a request error occurs

**Example:**
```java
Page<Facility> page = client.facilities().list(
    FacilityListRequest.builder()
        .size(50)
        .status(List.of("ONLINE"))
        .build()
);
```

### listAsync(FacilityListRequest)

Asynchronously retrieve a single page of facilities.

**Parameters:**
- `request: FacilityListRequest` — The list request

**Returns:** `CompletableFuture<Page<Facility>>` — A future that completes with the page

**Example:**
```java
client.facilities().listAsync(
        FacilityListRequest.builder().size(50).build())
    .thenAccept(page -> page.items().forEach(f -> System.out.println(f.name())));
```

### listAll(FacilityListRequest)

Iterate through all facilities, automatically handling pagination.

**Parameters:**
- `request: FacilityListRequest` — The list request (pagination settings are used for each page)

**Returns:** `Iterable<Facility>` — An iterable over all matching facilities

**Example:**
```java
Iterable<Facility> all = client.facilities().listAll(
    FacilityListRequest.builder()
        .size(100)
        .build()
);

for (Facility f : all) {
    System.out.println(f.name());
}
```

### search(FacilitySearchRequest)

Search facilities using a complex query with multiple filter criteria.

**Parameters:**
- `request: FacilitySearchRequest` — The search request containing a `FacilitySearchQuery` and pagination settings

**Returns:** `Page<Facility>` — A page of search results

**Throws:** `FulfillmenttoolsException` if a request error occurs

**Example:**
```java
FacilitySearchQuery query = FacilitySearchQuery.builder()
    .statusIn("ONLINE")
    .nameLike("Berlin*")
    .build();

Page<Facility> results = client.facilities().search(
    FacilitySearchRequest.builder()
        .query(query)
        .size(50)
        .build()
);
```

### searchAsync(FacilitySearchRequest)

Asynchronously search facilities using a complex query.

**Parameters:**
- `request: FacilitySearchRequest` — The search request

**Returns:** `CompletableFuture<Page<Facility>>` — A future that completes with the page of results

**Example:**
```java
FacilitySearchQuery query = FacilitySearchQuery.builder().nameLike("Berlin*").build();
client.facilities().searchAsync(
        FacilitySearchRequest.builder().query(query).size(25).build())
    .thenAccept(page -> page.items().forEach(f -> System.out.println(f.name())));
```

### searchAll(FacilitySearchRequest)

Iterate through all search results, automatically handling pagination.

**Parameters:**
- `request: FacilitySearchRequest` — The search request

**Returns:** `Iterable<Facility>` — An iterable over all matching facilities

**Example:**
```java
FacilitySearchQuery query = FacilitySearchQuery.builder()
    .cityEq("Berlin")
    .build();

Iterable<Facility> allMatches = client.facilities().searchAll(
    FacilitySearchRequest.builder()
        .query(query)
        .size(100)
        .build()
);
```

### create(CreateFacilityRequest)

Create a new facility.

**Parameters:**
- `request: CreateFacilityRequest` — The facility creation payload (name is required)

**Returns:** `Facility` — The created facility with assigned ID and metadata

**Throws:** `FulfillmenttoolsException` if validation fails or a request error occurs

**Example:**
```java
CreateFacilityRequest request = CreateFacilityRequest.builder()
    .name("London Warehouse")
    .status("ONLINE")
    .type("MANAGED_FACILITY")
    .build();

Facility facility = client.facilities().create(request);
```

### createAsync(CreateFacilityRequest)

Asynchronously create a new facility.

**Parameters:**
- `request: CreateFacilityRequest` — The facility creation payload

**Returns:** `CompletableFuture<Facility>` — A future that completes with the created facility

### update(FacilityId, UpdateFacilityRequest)

Update an existing facility with partial changes. Only specified fields are updated.

**Parameters:**
- `facilityId: FacilityId` — The facility ID to update
- `request: UpdateFacilityRequest` — The update payload with new field values (all fields optional)

**Returns:** `Facility` — The updated facility

**Throws:** `FulfillmenttoolsException` if the facility is not found or a request error occurs

**Example:**
```java
FacilityId id = FacilityId.builder().value("fac-001").build();
Facility updated = client.facilities().update(id,
    UpdateFacilityRequest.builder()
        .status("SUSPENDED")
        .build()
);
```

### updateAsync(FacilityId, UpdateFacilityRequest)

Asynchronously update an existing facility.

**Parameters:**
- `facilityId: FacilityId` — The facility ID to update
- `request: UpdateFacilityRequest` — The update payload

**Returns:** `CompletableFuture<Facility>` — A future that completes with the updated facility

### replace(FacilityId, CreateFacilityRequest)

Replace an entire facility, overwriting all fields with the provided values.

**Parameters:**
- `facilityId: FacilityId` — The facility ID to replace
- `request: CreateFacilityRequest` — The replacement facility payload (name is required)

**Returns:** `Facility` — The replaced facility

**Throws:** `FulfillmenttoolsException` if the facility is not found or a request error occurs

**Example:**
```java
FacilityId id = FacilityId.builder().value("fac-001").build();
Facility replaced = client.facilities().replace(id,
    CreateFacilityRequest.builder()
        .name("Updated Facility")
        .status("OFFLINE")
        .type("SUPPLIER")
        .build()
);
```

### replaceAsync(FacilityId, CreateFacilityRequest)

Asynchronously replace an entire facility.

**Parameters:**
- `facilityId: FacilityId` — The facility ID to replace
- `request: CreateFacilityRequest` — The replacement facility payload

**Returns:** `CompletableFuture<Facility>` — A future that completes with the replaced facility

### delete(FacilityId)

Delete a facility.

**Parameters:**
- `facilityId: FacilityId` — The facility ID to delete

**Returns:** `void`

**Throws:** `FulfillmenttoolsException` if the facility is not found or a request error occurs

**Example:**
```java
FacilityId id = FacilityId.builder().value("fac-001").build();
client.facilities().delete(id);
```

### delete(FacilityId, boolean)

Delete a facility, optionally bypassing soft-delete constraints.

**Parameters:**
- `facilityId: FacilityId` — The facility ID to delete
- `forceDeletion: boolean` — If `true`, bypasses soft-delete constraints and immediately removes the facility

**Returns:** `void`

**Throws:** `FulfillmenttoolsException` if a request error occurs

### deleteAsync(FacilityId)

Asynchronously delete a facility.

**Parameters:**
- `facilityId: FacilityId` — The facility ID to delete

**Returns:** `CompletableFuture<Void>` — A future that completes when the deletion is done

### deleteAsync(FacilityId, boolean)

Asynchronously delete a facility with optional force deletion.

**Parameters:**
- `facilityId: FacilityId` — The facility ID to delete
- `forceDeletion: boolean` — If `true`, bypasses soft-delete constraints

**Returns:** `CompletableFuture<Void>` — A future that completes when the deletion is done
