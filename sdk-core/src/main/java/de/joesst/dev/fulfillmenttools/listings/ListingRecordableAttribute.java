package de.joesst.dev.fulfillmenttools.listings;

import java.util.Map;

/**
 * A customizable attribute on a listing whose value can be recorded during the picking process.
 *
 * <p>Maps to the {@code ListingRecordableAttribute} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param keyLocalized   Localized translations of the attribute key, keyed by locale
 *                       (e.g. {@code en_US}).
 * @param key            The translated key resolved from {@code keyLocalized} for the
 *                       request locale.
 * @param recordingRule  Whether the value must be recorded: {@code MANDATORY} or
 *                       {@code OPTIONAL}.
 * @param value          The recorded value; {@code null} if not yet recorded.
 */
public record ListingRecordableAttribute(
        Map<String, String> keyLocalized,
        String key,
        String recordingRule,
        String value
) {}
