package de.joesst.dev.fulfillmenttools.checkoutoptions;

import java.util.concurrent.CompletableFuture;

/**
 * Client for evaluating checkout options in the fulfillmenttools platform.
 */
public interface CheckoutOptionsClient {

    /**
     * Evaluates available checkout options based on the provided request parameters.
     * @param request the request containing checkout evaluation criteria
     * @return the evaluated checkout option
     */
    CheckoutOption evaluate(EvaluateCheckoutOptionsRequest request);

    /**
     * Asynchronously evaluates available checkout options based on the provided request parameters.
     * @param request the request containing checkout evaluation criteria
     * @return a future containing the evaluated checkout option
     */
    CompletableFuture<CheckoutOption> evaluateAsync(EvaluateCheckoutOptionsRequest request);
}
