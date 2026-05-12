package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

/**
 * A phone number entry on a consumer address.
 *
 * <p>Maps to the inline phone-number object defined in {@code Address.phoneNumbers} in the
 * fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param value The phone number string. No particular format is enforced by the API.
 * @param type  The type of this phone number: {@code MOBILE} or {@code PHONE}.
 * @param label Optional human-readable label (e.g. "private", "business").
 * @param customAttributes Optional free-form custom attributes attached to this phone number.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PhoneNumber(
        String value,
        String type,
        String label,
        Map<String, Object> customAttributes
) {}
