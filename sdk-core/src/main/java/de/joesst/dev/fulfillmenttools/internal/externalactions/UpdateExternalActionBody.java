package de.joesst.dev.fulfillmenttools.internal.externalactions;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.externalactions.ExternalActionDefinition;

import java.util.List;
import java.util.Map;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

@JsonInclude(JsonInclude.Include.NON_NULL)
record UpdateExternalActionBody(
        Integer version,
        Map<String, String> nameLocalized,
        List<String> groups,
        ExternalActionDefinition action,
        CustomAttributes customAttributes
) {}
