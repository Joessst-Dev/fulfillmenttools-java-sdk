package de.joesst.dev.fulfillmenttools.handoverjobs;

import java.util.List;
import java.util.Map;

/**
 * A printable document associated with a handover job.
 *
 * <p>Maps to the {@code PrintableDocument} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id               Unique identifier of the document.
 * @param documentType     File type of the document ({@code PDF}, {@code PNG}, {@code JPG},
 *                         {@code GIF}, {@code JPEG}, {@code XML}, {@code JSON}).
 * @param documentCategory Category of the document ({@code EXTERNAL}, {@code DELIVERYNOTE},
 *                         {@code RETURNNOTE}, {@code SENDLABEL}, {@code RETURNLABEL},
 *                         {@code TRANSFERLABEL}, {@code CUSTOMS}).
 * @param status           Processing status of the document ({@code AVAILABLE},
 *                         {@code LOADING}, {@code REQUESTABLE}, {@code CANCELED},
 *                         {@code WAITING_FOR_INPUT}).
 * @param name             Optional display name of the document.
 * @param path             Optional path or URL to the document content.
 * @param priority         Optional sort priority; lower value means higher priority.
 * @param operations       Offered operations for this document ({@code PRINT}, {@code VIEW}).
 * @param customAttributes Free-form custom attributes attached to the document.
 */
public record HandoverJobDocument(
        String id,
        String documentType,
        String documentCategory,
        String status,
        String name,
        String path,
        Double priority,
        List<String> operations,
        Map<String, Object> customAttributes
) {}
