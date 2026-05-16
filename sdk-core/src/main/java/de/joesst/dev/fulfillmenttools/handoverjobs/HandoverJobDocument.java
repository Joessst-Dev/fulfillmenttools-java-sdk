package de.joesst.dev.fulfillmenttools.handoverjobs;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

import java.util.List;

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
        CustomAttributes customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String id;
        private String documentType;
        private String documentCategory;
        private String status;
        private String name;
        private String path;
        private Double priority;
        private List<String> operations;
        private CustomAttributes customAttributes;

        public Builder id(String id) { this.id = id; return this; }
        public Builder documentType(String documentType) { this.documentType = documentType; return this; }
        public Builder documentCategory(String documentCategory) { this.documentCategory = documentCategory; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder path(String path) { this.path = path; return this; }
        public Builder priority(Double priority) { this.priority = priority; return this; }
        public Builder operations(List<String> operations) { this.operations = operations; return this; }
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        public HandoverJobDocument build() {
            return new HandoverJobDocument(id, documentType, documentCategory, status, name, path, priority, operations, customAttributes);
        }
    }
}
