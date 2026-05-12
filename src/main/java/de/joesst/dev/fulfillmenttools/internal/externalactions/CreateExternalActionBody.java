package de.joesst.dev.fulfillmenttools.internal.externalactions;

import java.util.List;
import java.util.Map;

record CreateExternalActionBody(
        String processRef,
        Map<String, Object> nameLocalized,
        List<String> groups,
        Map<String, Object> action,
        Map<String, Object> customAttributes
) {}
