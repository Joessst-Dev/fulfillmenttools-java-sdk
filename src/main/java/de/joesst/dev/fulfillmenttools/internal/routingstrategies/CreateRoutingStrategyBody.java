package de.joesst.dev.fulfillmenttools.internal.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
record CreateRoutingStrategyBody(Map<String, String> nameLocalized) {}
