package de.joesst.dev.fulfillmenttools.tags;

/**
 * Indicates whether a tag requires packing.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param needsPacking {@code true} if the tag requires packing; {@code false} otherwise.
 */
public record NeedsPacking(Boolean needsPacking) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private Boolean needsPacking;

        public Builder needsPacking(Boolean needsPacking) {
            this.needsPacking = needsPacking;
            return this;
        }

        public NeedsPacking build() {
            return new NeedsPacking(needsPacking);
        }
    }
}
