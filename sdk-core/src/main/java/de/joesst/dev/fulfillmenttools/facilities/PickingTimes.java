package de.joesst.dev.fulfillmenttools.facilities;

import java.util.List;

/**
 * Operating hours for facility picking operations, broken down by day of the week.
 *
 * @param monday list of time ranges for Monday operations
 * @param tuesday list of time ranges for Tuesday operations
 * @param wednesday list of time ranges for Wednesday operations
 * @param thursday list of time ranges for Thursday operations
 * @param friday list of time ranges for Friday operations
 * @param saturday list of time ranges for Saturday operations
 * @param sunday list of time ranges for Sunday operations
 */
public record PickingTimes(
        List<TimeRange> monday,
        List<TimeRange> tuesday,
        List<TimeRange> wednesday,
        List<TimeRange> thursday,
        List<TimeRange> friday,
        List<TimeRange> saturday,
        List<TimeRange> sunday
) {

    /**
     * Returns a builder for constructing a {@link PickingTimes}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link PickingTimes}.
     */
    public static final class Builder {

        private List<TimeRange> monday;
        private List<TimeRange> tuesday;
        private List<TimeRange> wednesday;
        private List<TimeRange> thursday;
        private List<TimeRange> friday;
        private List<TimeRange> saturday;
        private List<TimeRange> sunday;

        private Builder() {}

        /**
         * @param monday list of time ranges for Monday operations
         * @return this builder
         */
        public Builder monday(List<TimeRange> monday) {
            this.monday = monday;
            return this;
        }

        /**
         * @param tuesday list of time ranges for Tuesday operations
         * @return this builder
         */
        public Builder tuesday(List<TimeRange> tuesday) {
            this.tuesday = tuesday;
            return this;
        }

        /**
         * @param wednesday list of time ranges for Wednesday operations
         * @return this builder
         */
        public Builder wednesday(List<TimeRange> wednesday) {
            this.wednesday = wednesday;
            return this;
        }

        /**
         * @param thursday list of time ranges for Thursday operations
         * @return this builder
         */
        public Builder thursday(List<TimeRange> thursday) {
            this.thursday = thursday;
            return this;
        }

        /**
         * @param friday list of time ranges for Friday operations
         * @return this builder
         */
        public Builder friday(List<TimeRange> friday) {
            this.friday = friday;
            return this;
        }

        /**
         * @param saturday list of time ranges for Saturday operations
         * @return this builder
         */
        public Builder saturday(List<TimeRange> saturday) {
            this.saturday = saturday;
            return this;
        }

        /**
         * @param sunday list of time ranges for Sunday operations
         * @return this builder
         */
        public Builder sunday(List<TimeRange> sunday) {
            this.sunday = sunday;
            return this;
        }

        /**
         * Builds a {@link PickingTimes}.
         *
         * @return a new instance
         */
        public PickingTimes build() {
            return new PickingTimes(monday, tuesday, wednesday, thursday, friday, saturday, sunday);
        }
    }
}
