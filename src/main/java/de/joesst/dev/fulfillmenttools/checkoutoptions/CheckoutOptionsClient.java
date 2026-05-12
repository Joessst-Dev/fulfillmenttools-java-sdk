package de.joesst.dev.fulfillmenttools.checkoutoptions;

import java.util.concurrent.CompletableFuture;

public interface CheckoutOptionsClient {

    CheckoutOption get(String checkoutOptionId);
    CompletableFuture<CheckoutOption> getAsync(String checkoutOptionId);

    CheckoutOption evaluate(EvaluateCheckoutOptionsRequest request);
    CompletableFuture<CheckoutOption> evaluateAsync(EvaluateCheckoutOptionsRequest request);
}
