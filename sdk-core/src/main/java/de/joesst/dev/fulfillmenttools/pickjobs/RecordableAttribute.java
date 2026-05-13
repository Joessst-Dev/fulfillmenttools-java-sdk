package de.joesst.dev.fulfillmenttools.pickjobs;

import java.util.Map;

/**
 * A customizable attribute on a pick line item whose value can be recorded during the
 * picking process.
 *
 * <p>Maps to the {@code RecordableAttribute} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id             Unique identifier of the recordable attribute.
 * @param keyLocalized   Localized translations of the attribute key, keyed by locale
 *                       (e.g. {@code en_US}).
 * @param key            The translated key resolved from {@code keyLocalized} for the
 *                       request locale.
 * @param value          The recorded value; {@code null} if not yet recorded.
 * @param group          Optional grouping label (e.g. {@code "general"}).
 * @param originId       Id of the original line item the attribute was split from.
 * @param recordingRule  Whether the value must be recorded: {@code MANDATORY} or
 *                       {@code OPTIONAL}.
 */
public record RecordableAttribute(
        String id,
        Map<String, String> keyLocalized,
        String key,
        String value,
        String group,
        String originId,
        String recordingRule
) {}
