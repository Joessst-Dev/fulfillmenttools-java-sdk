package de.joesst.dev.fulfillmenttools.internal.externalactions;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.externalactions.ExternalActionDefinition;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
record CreateExternalActionBody(
        String processRef,
        Map<String, String> nameLocalized,
        List<String> groups,
        ExternalActionDefinition action,
        CustomAttributes customAttributes
) {}
