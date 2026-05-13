# Checkout Options Client

The Checkout Options client evaluates available checkout options based on order and customer criteria. Use this to determine feasible delivery and payment configurations for checkout flows.

## Basic Usage

```java
import de.joesst.dev.fulfillmenttools.checkoutoptions.EvaluateCheckoutOptionsRequest;

// Evaluate checkout options for a customer order
CheckoutOption option = client.checkoutOptions().evaluate(
    EvaluateCheckoutOptionsRequest.builder()
        .order(/* order details */)
        .build()
);

System.out.println("Available checkout: " + option);
```

## API Reference

### evaluate(EvaluateCheckoutOptionsRequest)

Evaluate available checkout options based on the provided request parameters.

**Parameters:**
- `request: EvaluateCheckoutOptionsRequest` — Request with order and checkout criteria

**Returns:** `CheckoutOption`

**Throws:** `FulfillmenttoolsException` on evaluation failure

### evaluateAsync(EvaluateCheckoutOptionsRequest)

Evaluate available checkout options asynchronously based on the provided request parameters.

**Parameters:**
- `request: EvaluateCheckoutOptionsRequest` — Request with order and checkout criteria

**Returns:** `CompletableFuture<CheckoutOption>`
