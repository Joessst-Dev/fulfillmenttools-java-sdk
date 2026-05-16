package de.joesst.dev.fulfillmenttools.inbound;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A comment attached to an inbound receipt or receipt line item.
 *
 * @param id the platform-generated identifier for this comment
 * @param content the text content of the comment
 * @param userRef reference to the user who authored the comment
 * @param documentSetRef reference to an associated document set
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record InboundReceiptComment(
        String id,
        String content,
        String userRef,
        String documentSetRef
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String id;
        private String content;
        private String userRef;
        private String documentSetRef;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder userRef(String userRef) {
            this.userRef = userRef;
            return this;
        }

        public Builder documentSetRef(String documentSetRef) {
            this.documentSetRef = documentSetRef;
            return this;
        }

        public InboundReceiptComment build() {
            return new InboundReceiptComment(id, content, userRef, documentSetRef);
        }
    }
}
