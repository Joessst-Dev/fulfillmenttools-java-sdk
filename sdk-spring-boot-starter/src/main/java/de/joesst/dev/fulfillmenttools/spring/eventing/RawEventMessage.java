package de.joesst.dev.fulfillmenttools.spring.eventing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;

@JsonIgnoreProperties(ignoreUnknown = true)
record RawEventMessage(String eventId, String event, JsonNode payload) {}
