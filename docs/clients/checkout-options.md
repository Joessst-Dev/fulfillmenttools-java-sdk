# Checkout Options Client

The Checkout Options client evaluates available checkout options based on order and customer criteria. Use this to determine feasible delivery configurations for checkout flows.

## Evaluating Checkout Options

`deliveryPreferences` and `orderLineItems` are required:

```java
import de.joesst.dev.fulfillmenttools.checkoutoptions.CheckoutOption;
import de.joesst.dev.fulfillmenttools.checkoutoptions.EvaluateCheckoutOptionsRequest;
import de.joesst.dev.fulfillmenttools.orders.DeliveryPreferences;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;
import java.util.List;
import java.util.Map;

try {
    CheckoutOption option = client.checkoutOptions().evaluate(
        EvaluateCheckoutOptionsRequest.builder()
            .deliveryPreferences(/* delivery preferences */)
            .orderLineItems(List.of(/* line items */))
            .build()
    );

    System.out.println("Facilities available: " + option.facilities().size());
} catch (FulfillmenttoolsException e) {
    System.out.println("Evaluation failed: " + e.getMessage());
}
```

## Async Usage

```java
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

CompletableFuture<CheckoutOption> future = client.checkoutOptions().evaluateAsync(
    EvaluateCheckoutOptionsRequest.builder()
        .deliveryPreferences(/* delivery preferences */)
        .orderLineItems(List.of(/* line items */))
        .build()
);

future.whenComplete((option, ex) -> {
    if (ex != null) {
        Throwable cause = ex instanceof CompletionException && ex.getCause() != null
            ? ex.getCause() : ex;
        System.out.println("Error: " + cause.getMessage());
    } else {
        System.out.println("Facilities available: " + option.facilities().size());
    }
});
```

## API Reference

### evaluate(EvaluateCheckoutOptionsRequest)

Evaluate available checkout options. `deliveryPreferences` and `orderLineItems` are required.

**Parameters:**
- `request: EvaluateCheckoutOptionsRequest` — Request with delivery preferences and order line items

**Returns:** `CheckoutOption`

**Throws:** `FulfillmenttoolsException` if the evaluation fails

### evaluateAsync(EvaluateCheckoutOptionsRequest)

Evaluate available checkout options asynchronously.

**Parameters:**
- `request: EvaluateCheckoutOptionsRequest` — Request with delivery preferences and order line items

**Returns:** `CompletableFuture<CheckoutOption>`
