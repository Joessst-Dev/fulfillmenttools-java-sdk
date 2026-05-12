package de.joesst.dev.fulfillmenttools.sourcingoptions;

import java.util.List;

public record SourcingOptionsResult(
        String id,
        List<SourcingOption> options
) {}
