# Facilities Client

The Facilities client provides access to facility information and management in the fulfillmenttools platform. Retrieve and manage fulfillment centers and warehouses.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.FacilityId;

// Get a facility by ID
Facility facility = client.facilities().get(new FacilityId("fac-001"));
System.out.println(facility.getName());

// Get asynchronously
CompletableFuture<Facility> futureFacility = client.facilities()
    .getAsync(new FacilityId("fac-001"));
```

## Listing Facilities

List all facilities with pagination:

```java
Page<Facility> page = client.facilities().list(
    FacilityListRequest.builder()
        .size(50)
        .build()
);

for (Facility facility : page.items()) {
    System.out.println(facility.getName());
}
```

Iterate through all facilities:

```java
Iterable<Facility> allFacilities = client.facilities().listAll(
    FacilityListRequest.builder()
        .size(100)
        .build()
);
```

## Searching Facilities

Search for facilities by name or other criteria:

```java
Page<Facility> results = client.facilities().search(
    FacilitySearchRequest.builder()
        .name("Main Warehouse")
        .build()
);
```

## API Reference

### get(FacilityId)

Get a single facility by ID.

**Parameters:**
- `facilityId: FacilityId` — The facility identifier

**Returns:** `Facility`

**Throws:** `NotFoundException` (404)

### getAsync(FacilityId)

Get a facility asynchronously.

**Parameters:**
- `facilityId: FacilityId` — The facility identifier

**Returns:** `CompletableFuture<Facility>`

### list(FacilityListRequest)

List facilities with pagination.

**Parameters:**
- `request: FacilityListRequest` — List request

**Returns:** `Page<Facility>`

### listAll(FacilityListRequest)

List all facilities, automatically iterating through pages.

**Parameters:**
- `request: FacilityListRequest` — List request

**Returns:** `Iterable<Facility>`

### search(FacilitySearchRequest)

Search for facilities by criteria.

**Parameters:**
- `request: FacilitySearchRequest` — Search request

**Returns:** `Page<Facility>`

### searchAsync(FacilitySearchRequest)

Search for facilities asynchronously.

**Parameters:**
- `request: FacilitySearchRequest` — Search request

**Returns:** `CompletableFuture<Page<Facility>>`

### searchAll(FacilitySearchRequest)

Search for all matching facilities.

**Parameters:**
- `request: FacilitySearchRequest` — Search request

**Returns:** `Iterable<Facility>`
