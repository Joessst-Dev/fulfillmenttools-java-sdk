package de.joesst.dev.fulfillmenttools.internal.sourcingoptions;

import de.joesst.dev.fulfillmenttools.sourcingoptions.OptimizationHints;
import de.joesst.dev.fulfillmenttools.sourcingoptions.OrderForSourcingOptionsRequest;
import de.joesst.dev.fulfillmenttools.sourcingoptions.ResourceInvestment;
import de.joesst.dev.fulfillmenttools.sourcingoptions.SourcingOptionsRequestAdditionalInfo;

record SourcingOptionsBody(
        OrderForSourcingOptionsRequest order,
        SourcingOptionsRequestAdditionalInfo additionalInfo,
        OptimizationHints optimizationHints,
        ResourceInvestment resourceInvestment
) {}
