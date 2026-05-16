package de.joesst.dev.fulfillmenttools.purchaseorders;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

/**
 * Specifies the requested delivery date for a purchase order.
 *
 * <p>When {@code type} is {@link RequestedDateType#ASAP} the {@code value} field is absent.
 * When {@code type} is {@link RequestedDateType#TIME_POINT} the {@code value} field holds
 * the requested delivery timestamp.
 *
 * @param type the date type
 * @param value the specific requested delivery timestamp, present only when type is TIME_POINT
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RequestedDate(
        RequestedDateType type,
        Instant value
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private RequestedDateType type;
        private Instant value;

        public Builder type(RequestedDateType type) {
            this.type = type;
            return this;
        }

        public Builder value(Instant value) {
            this.value = value;
            return this;
        }

        public RequestedDate build() {
            return new RequestedDate(type, value);
        }
    }
}
