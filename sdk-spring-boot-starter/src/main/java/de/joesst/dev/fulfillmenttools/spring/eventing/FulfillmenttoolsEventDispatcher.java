package de.joesst.dev.fulfillmenttools.spring.eventing;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationEventPublisher;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;

/**
 * Parses raw GCP Pub/Sub message bytes, resolves the entity class from the
 * {@link FulfillmenttoolsEventTypeRegistry}, deserializes the payload, and publishes a
 * {@link FulfillmenttoolsEvent} to Spring's {@link ApplicationEventPublisher}.
 *
 * <p>Payload deserialization rules:
 * <ul>
 *   <li>If the event type is registered, the payload is deserialized to the registered class.</li>
 *   <li>If the event type is unknown, the payload is deserialized to
 *       {@code Map<String, Object>}.</li>
 *   <li>If the raw message contains no {@code payload} field (or it is JSON {@code null}),
 *       the published event carries a {@code null} payload.</li>
 * </ul>
 *
 * <p>A malformed (non-parseable) message causes an {@link UncheckedIOException} to be thrown.
 *
 * <p>This class is not thread-safe by itself; thread-safety of the underlying
 * {@link ApplicationEventPublisher} depends on the Spring context implementation.
 */
public class FulfillmenttoolsEventDispatcher {

    private static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<>() {};

    private final ObjectMapper objectMapper;
    private final FulfillmenttoolsEventTypeRegistry registry;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Creates a new dispatcher.
     *
     * @param objectMapper   the Jackson mapper used for deserialization
     * @param registry       the registry that maps event type strings to entity classes
     * @param eventPublisher the Spring event publisher to dispatch events to
     */
    public FulfillmenttoolsEventDispatcher(
            ObjectMapper objectMapper,
            FulfillmenttoolsEventTypeRegistry registry,
            ApplicationEventPublisher eventPublisher) {
        this.objectMapper = objectMapper;
        this.registry = registry;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Parses the given raw Pub/Sub message bytes and publishes a
     * {@link FulfillmenttoolsEvent} to the application event bus.
     *
     * @param rawMessage the raw UTF-8 JSON bytes from the Pub/Sub message
     * @throws UncheckedIOException if the bytes cannot be parsed as valid JSON
     */
    public void dispatch(byte[] rawMessage) {
        RawEventMessage raw = parse(rawMessage);
        Object payload = deserializePayload(raw.event(), raw.payload());
        eventPublisher.publishEvent(new FulfillmenttoolsEvent<>(raw.event(), raw.eventId(), payload));
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
