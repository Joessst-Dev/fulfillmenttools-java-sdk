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
Page<OperativeProcess> page = client.processes().list(
    ProcessListRequest.builder()
        .size(50)
        .build()
);
```

## API Reference

### get(ProcessId)

Get a process by ID.

**Returns:** `OperativeProcess`

### list(ProcessListRequest)

List processes with pagination.

**Returns:** `Page<OperativeProcess>`

### listAll(ProcessListRequest)

List all processes.

**Returns:** `Iterable<OperativeProcess>`
