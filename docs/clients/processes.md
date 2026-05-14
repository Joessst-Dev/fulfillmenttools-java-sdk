# Processes Client

The Processes client retrieves and searches operative processes. A process tracks the lifecycle of an order across all domain entities — pick jobs, pack jobs, shipments, returns, and more.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.ProcessId;
import de.joesst.dev.fulfillmenttools.processes.Process;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

try {
    Process process = client.processes().get(
        ProcessId.builder().value("proc-001").build()
    );
    System.out.println("Status: " + process.status());
    System.out.println("Operative status: " + process.operativeStatus());
    System.out.println("Order ref: " + process.orderRef().value());
} catch (NotFoundException e) {
    System.out.println("Process not found");
} catch (FulfillmenttoolsException e) {
    System.out.println("Request failed: " + e.getMessage());
}
```

## Listing Processes

List processes with optional filters:

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.processes.Process;
import de.joesst.dev.fulfillmenttools.processes.ProcessListRequest;
import de.joesst.dev.fulfillmenttools.id.TenantOrderId;
import java.util.List;

Page<Process> page = client.processes().list(
    ProcessListRequest.builder()
        .size(50)
        .build()
);

for (Process process : page.items()) {
    System.out.println(process.id().value() + " — " + process.status());
}
```

Filter by status, operative status, or facility:

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.processes.Process;
import de.joesst.dev.fulfillmenttools.processes.ProcessListRequest;
import java.util.List;

Page<Process> page = client.processes().list(
    ProcessListRequest.builder()
        .size(20)
        .status(List.of("OPEN"))
        .operativeStatus(List.of("RUNNING"))
        .build()
);
```

Iterate through all processes automatically:

```java
import de.joesst.dev.fulfillmenttools.processes.Process;
import de.joesst.dev.fulfillmenttools.processes.ProcessListRequest;

Iterable<Process> allProcesses = client.processes().listAll(
    ProcessListRequest.builder()
        .size(100)
        .build()
);

for (Process process : allProcesses) {
    System.out.println(process.id().value() + " — " + process.status());
}
```

Manual pagination using `nextCursor()`:

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.processes.Process;
import de.joesst.dev.fulfillmenttools.processes.ProcessListRequest;

Page<Process> page = client.processes().list(
    ProcessListRequest.builder().size(20).build()
);

while (page.hasMore()) {
    page = client.processes().list(
        ProcessListRequest.builder()
            .size(20)
            .startAfterId(page.nextCursor())
            .build()
    );
    for (Process process : page.items()) {
        System.out.println(process.id().value());
    }
}
```

## Searching Processes

`search` uses a `ProcessSearchQuery` to filter by status, operative status, order reference, date ranges, and more. `query` is required.

### Search by status

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.processes.Process;
import de.joesst.dev.fulfillmenttools.processes.ProcessSearchRequest;
import de.joesst.dev.fulfillmenttools.processes.ProcessSearchQuery;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

try {
    Page<Process> results = client.processes().search(
        ProcessSearchRequest.builder()
            .query(
                ProcessSearchQuery.builder()
                    .statusEq("OPEN")
                    .build()
            )
            .size(50)
            .build()
    );

    System.out.println("Found: " + results.items().size());
    for (Process process : results.items()) {
        System.out.println(process.id().value() + " — " + process.operativeStatus());
    }
} catch (FulfillmenttoolsException e) {
    System.out.println("Search failed: " + e.getMessage());
}
```

### Search by order reference

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.processes.Process;
import de.joesst.dev.fulfillmenttools.processes.ProcessSearchRequest;
import de.joesst.dev.fulfillmenttools.processes.ProcessSearchQuery;

Page<Process> results = client.processes().search(
    ProcessSearchRequest.builder()
        .query(
            ProcessSearchQuery.builder()
                .orderRefEq("order-12345")
                .build()
        )
        .build()
);
```

### Search by date range

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.processes.Process;
import de.joesst.dev.fulfillmenttools.processes.ProcessSearchRequest;
import de.joesst.dev.fulfillmenttools.processes.ProcessSearchQuery;
import java.time.Instant;

Page<Process> results = client.processes().search(
    ProcessSearchRequest.builder()
        .query(
            ProcessSearchQuery.builder()
                .createdBetween(
                    Instant.parse("2024-01-01T00:00:00Z"),
                    Instant.parse("2024-01-31T23:59:59Z")
                )
                .build()
        )
        .size(100)
        .build()
);
```

### Combine filters with AND/OR

```java
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.processes.Process;
import de.joesst.dev.fulfillmenttools.processes.ProcessSearchRequest;
import de.joesst.dev.fulfillmenttools.processes.ProcessSearchQuery;

Page<Process> results = client.processes().search(
    ProcessSearchRequest.builder()
        .query(
            ProcessSearchQuery.builder()
                .and(
                    ProcessSearchQuery.builder().statusEq("OPEN").build(),
                    ProcessSearchQuery.builder().operativeStatusEq("RUNNING").build()
                )
                .build()
        )
        .build()
);
```

Iterate through all matching search results automatically:

```java
import de.joesst.dev.fulfillmenttools.processes.Process;
import de.joesst.dev.fulfillmenttools.processes.ProcessSearchRequest;
import de.joesst.dev.fulfillmenttools.processes.ProcessSearchQuery;

Iterable<Process> allResults = client.processes().searchAll(
    ProcessSearchRequest.builder()
        .query(
            ProcessSearchQuery.builder()
                .statusEq("OPEN")
                .build()
        )
        .size(100)
        .build()
);

for (Process process : allResults) {
    System.out.println(process.id().value());
}
```

## Reading a Process

A `Process` record tracks all domain entity references and multi-dimensional status:

```java
import de.joesst.dev.fulfillmenttools.id.ProcessId;
import de.joesst.dev.fulfillmenttools.processes.Process;
import de.joesst.dev.fulfillmenttools.NotFoundException;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

try {
    Process process = client.processes().get(
        ProcessId.builder().value("proc-001").build()
    );

    System.out.println("ID: " + process.id().value());
    System.out.println("Status: " + process.status());
    System.out.println("DOMS status: " + process.domsStatus());
    System.out.println("Operative status: " + process.operativeStatus());
    System.out.println("Inventory status: " + process.inventoryStatus());
    System.out.println("Return status: " + process.returnStatus());

    if (process.pickJobRefs() != null) {
        System.out.println("Pick jobs: " + process.pickJobRefs().size());
    }
    if (process.packJobRefs() != null) {
        System.out.println("Pack jobs: " + process.packJobRefs().size());
    }
    if (process.facilityRefs() != null) {
        process.facilityRefs().forEach(f -> System.out.println("  Facility: " + f.value()));
    }
} catch (NotFoundException e) {
    System.out.println("Process not found");
} catch (FulfillmenttoolsException e) {
    System.out.println("Request failed: " + e.getMessage());
}
```

## Async Usage

All methods have async variants returning `CompletableFuture`:

```java
import de.joesst.dev.fulfillmenttools.id.ProcessId;
import de.joesst.dev.fulfillmenttools.processes.Process;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

CompletableFuture<Process> future = client.processes().getAsync(
    ProcessId.builder().value("proc-001").build()
);

future.whenComplete((process, ex) -> {
    if (ex != null) {
        Throwable cause = ex instanceof CompletionException && ex.getCause() != null
            ? ex.getCause() : ex;
        System.out.println("Error: " + cause.getMessage());
    } else {
        System.out.println("Status: " + process.status());
        System.out.println("Operative status: " + process.operativeStatus());
    }
});
```

## API Reference

### get(ProcessId)

Get a process by ID.

**Parameters:**
- `processId: ProcessId` — The process identifier

**Returns:** `Process`

**Throws:** `NotFoundException` (404), `FulfillmenttoolsException` if the request fails

### getAsync(ProcessId)

Get a process by ID asynchronously.

**Parameters:**
- `processId: ProcessId` — The process identifier

**Returns:** `CompletableFuture<Process>`

### list(ProcessListRequest)

List processes with optional filtering and pagination. Filters include `status`, `operativeStatus`, `facilityRefs`, `tenantOrderId`, and `searchTerm`.

**Parameters:**
- `request: ProcessListRequest` — List request with `size`, `startAfterId` cursor, and optional filters

**Returns:** `Page<Process>`

**Throws:** `FulfillmenttoolsException` if the request fails

### listAsync(ProcessListRequest)

List processes asynchronously.

**Parameters:**
- `request: ProcessListRequest` — List request

**Returns:** `CompletableFuture<Page<Process>>`

### listAll(ProcessListRequest)

List all processes, automatically iterating through pages.

**Parameters:**
- `request: ProcessListRequest` — List request

**Returns:** `Iterable<Process>`

### search(ProcessSearchRequest)

Search processes using a structured query. `query` is required.

**Parameters:**
- `request: ProcessSearchRequest` — Search request with a `ProcessSearchQuery` and optional pagination (`size`, `after`, `before`)

**Returns:** `Page<Process>`

**Throws:** `FulfillmenttoolsException` if the request fails

### searchAsync(ProcessSearchRequest)

Search processes asynchronously.

**Parameters:**
- `request: ProcessSearchRequest` — Search request

**Returns:** `CompletableFuture<Page<Process>>`

### searchAll(ProcessSearchRequest)

Search all processes matching a query, automatically iterating through pages.

**Parameters:**
- `request: ProcessSearchRequest` — Search request

**Returns:** `Iterable<Process>`

## ProcessSearchQuery Filter Methods

| Method | Description |
|--------|-------------|
| `statusEq(String)` | Match exact status |
| `statusIn(String...)` | Match any of the given statuses |
| `statusNotIn(List<String>)` | Exclude the given statuses |
| `operativeStatusEq(String)` | Match exact operative status |
| `operativeStatusIn(String...)` | Match any of the given operative statuses |
| `domsStatusEq(String)` | Match exact DOMS status |
| `domsStatusIn(List<String>)` | Match any of the given DOMS statuses |
| `returnStatusEq(String)` | Match exact return status |
| `orderRefEq(String)` | Match exact order reference |
| `orderRefIn(List<String>)` | Match any of the given order references |
| `processIdEq(String)` | Match exact process ID |
| `processIdIn(List<String>)` | Match any of the given process IDs |
| `createdGte(Instant)` | Created at or after the given time |
| `createdLte(Instant)` | Created at or before the given time |
| `createdBetween(Instant, Instant)` | Created within the given time range |
| `facilityRefsHasAny(String...)` | Associated with any of the given facilities |
| `pickJobRefsHasAny(List<String>)` | Has any of the given pick job references |
| `packJobRefsHasAny(List<String>)` | Has any of the given pack job references |
| `and(ProcessSearchQuery...)` | Combine multiple queries with AND logic |
| `or(ProcessSearchQuery...)` | Combine multiple queries with OR logic |
