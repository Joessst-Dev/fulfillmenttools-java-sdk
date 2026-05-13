# Processes Client

The Processes client manages operational processes. Query and manage fulfillment process definitions.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.id.ProcessId;

// Get a process
OperativeProcess process = client.processes().get(new ProcessId("proc-001"));
System.out.println("Name: " + process.getName());
```

## Listing Processes

```java
Page<Process> page = client.processes().list(
    ProcessListRequest.builder()
        .size(50)
        .build()
);
```

## Searching Processes

```java
Page<Process> results = client.processes().search(
    ProcessSearchRequest.builder()
        .name("Picking")
        .build()
);
```

## API Reference

### get(ProcessId)

Get a process by ID.

**Parameters:**
- `processId: ProcessId` — The process ID

**Returns:** `Process`

**Throws:** `FulfillmenttoolsException` if not found

### getAsync(ProcessId)

Get a process asynchronously.

**Parameters:**
- `processId: ProcessId` — The process ID

**Returns:** `CompletableFuture<Process>`

### list(ProcessListRequest)

List processes with pagination.

**Parameters:**
- `request: ProcessListRequest` — List request with pagination

**Returns:** `Page<Process>`

**Throws:** `FulfillmenttoolsException` if the request fails

### listAsync(ProcessListRequest)

List processes asynchronously.

**Parameters:**
- `request: ProcessListRequest` — List request with pagination

**Returns:** `CompletableFuture<Page<Process>>`

### listAll(ProcessListRequest)

List all processes, automatically iterating through pages.

**Parameters:**
- `request: ProcessListRequest` — List request

**Returns:** `Iterable<Process>`

### search(ProcessSearchRequest)

Search processes by criteria, returning one page of results.

**Parameters:**
- `request: ProcessSearchRequest` — Search request with query and pagination

**Returns:** `Page<Process>`

**Throws:** `FulfillmenttoolsException` if the request fails

### searchAsync(ProcessSearchRequest)

Search processes asynchronously.

**Parameters:**
- `request: ProcessSearchRequest` — Search request with query and pagination

**Returns:** `CompletableFuture<Page<Process>>`

### searchAll(ProcessSearchRequest)

Search all processes, automatically iterating through pages.

**Parameters:**
- `request: ProcessSearchRequest` — Search request with query

**Returns:** `Iterable<Process>`
