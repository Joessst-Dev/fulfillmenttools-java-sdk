package de.joesst.dev.fulfillmenttools.spring.eventing;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;

/**
 * Parses raw GCP Pub/Sub message bytes, resolves the entity class from the
 * {@link FulfillmenttoolsEventTypeRegistry}, deserializes the payload, and forwards the
 * resulting {@link FulfillmenttoolsEvent} to the registered {@link FulfillmenttoolsEventHandler}.
 *
 * <p>Payload deserialization rules:
 * <ul>
 *   <li>If the event type is registered, the payload is deserialized to the registered class.</li>
 *   <li>If the event type is unknown, the payload is deserialized to
 *       {@code Map<String, Object>}.</li>
 *   <li>If the raw message contains no {@code payload} field (or it is JSON {@code null}),
 *       the event carries a {@code null} payload.</li>
 * </ul>
 *
 * <p>A malformed (non-parseable) message causes an {@link UncheckedIOException} to be thrown.
 */
public class FulfillmenttoolsEventDispatcher {

    private static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<>() {};

    private final ObjectMapper objectMapper;
    private final FulfillmenttoolsEventTypeRegistry registry;
    private final FulfillmenttoolsEventHandler handler;

    public FulfillmenttoolsEventDispatcher(
            ObjectMapper objectMapper,
            FulfillmenttoolsEventTypeRegistry registry,
            FulfillmenttoolsEventHandler handler) {
        this.objectMapper = objectMapper;
        this.registry = registry;
        this.handler = handler;
    }

    /**
     * Parses the given raw Pub/Sub message bytes, builds a {@link FulfillmenttoolsEvent}
     * with the supplied ack/nack callbacks, and publishes it to the application event bus.
     *
     * <p>The caller (typically {@link FulfillmenttoolsSubscriberManager}) is responsible for
     * passing the message's real ack/nack actions. This method does not acknowledge the
     * message itself — that is the responsibility of the event listener via
     * {@link FulfillmenttoolsEvent#ack()} or {@link FulfillmenttoolsEvent#nack()}.
     *
     * @param rawMessage the raw UTF-8 JSON bytes from the Pub/Sub message
     * @param ack        called by the listener to positively acknowledge the message
     * @param nack       called by the listener to negatively acknowledge the message
     * @throws UncheckedIOException if the bytes cannot be parsed as valid JSON
     */
    public void dispatch(byte[] rawMessage, Runnable ack, Runnable nack) {
        RawEventMessage raw = parse(rawMessage);
        Object payload = deserializePayload(raw.event(), raw.payload());
        handler.onEvent(new FulfillmenttoolsEvent<>(raw.event(), raw.eventId(), payload, ack, nack));
    }

    private RawEventMessage parse(byte[] rawMessage) {
        try {
            return objectMapper.readValue(rawMessage, RawEventMessage.class);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to parse fulfillmenttools event message", e);
        }
    }

    private Object deserializePayload(String eventType, JsonNode payloadNode) {
        if (payloadNode == null || payloadNode.isNull()) {
            return null;
        }
        return registry.resolve(eventType)
                .map(entityClass -> deserializeAs(payloadNode, entityClass))
                .orElseGet(() -> deserializeAsMap(payloadNode));
    }

    private Object deserializeAs(JsonNode node, Class<?> entityClass) {
        try {
            return objectMapper.treeToValue(node, entityClass);
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Failed to deserialize payload as " + entityClass.getSimpleName(), e);
        }
    }

    private Map<String, Object> deserializeAsMap(JsonNode node) {
        try {
            return objectMapper.treeToValue(node, MAP_TYPE);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to deserialize payload as Map", e);
        }
    }
}
